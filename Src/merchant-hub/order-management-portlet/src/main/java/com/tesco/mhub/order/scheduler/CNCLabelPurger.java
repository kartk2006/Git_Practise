package com.tesco.mhub.order.scheduler;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.tesco.mhub.model.OrderLabelDetails;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.service.OrderLabelDetailsLocalServiceUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CNCLabelPurger implements MessageListener {

	/**
	 * Object of Log class: logs errors, information, exceptions.
	 */
	private static Log log = LogFactoryUtil.getLog(CNCLabelPurger.class);

	@Override
	public void receive(Message message) throws MessageListenerException {
		log.debug("Scheduler has been started for C&C ...");
		Date endDate = new Date();
		StringBuffer pdfFilePath = new StringBuffer();
		String pdfFolderPath = OrderConstants.YODL_LABEL_PATH;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		int count;
		try {
			count = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailsesCount();

			List<OrderLabelDetails> orderLblDtlLst = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetailses(0,count);
			List<OrderLabelDetails> orderLblDtlLstFiltered = new ArrayList<OrderLabelDetails>();
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1 = null;
			String orderDate = null;
			String currentDate = sdformat.format(new Date());
			Date d2 = null;
			for (OrderLabelDetails itr : orderLblDtlLst) {
				try {
					orderDate = sdformat.format(itr.getCreatedDate());
					d1 = sdformat.parse(orderDate);
					d2 = sdformat.parse(currentDate);
					long secs = (d2.getTime() - d1.getTime()) / 1000;
					int hours = (int) (secs / 3600); 
					if (hours >= 48) {
						orderLblDtlLstFiltered.add(itr);
						log.debug("This Label is added into deletion list for tracking ID = "+itr.getTrackingId());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			File pdfFl = null;
			boolean isDeleted = false;
			for (OrderLabelDetails itr1 : orderLblDtlLstFiltered) {
				isDeleted = false;
				pdfFilePath = new StringBuffer();
				pdfFilePath.append(pdfFolderPath);
				pdfFilePath.append(itr1.getTrackingId() + ".pdf");
				pdfFl = new File(pdfFilePath.toString());// Delete label PDF from folder
				if(pdfFl.exists()){
					pdfFl.setWritable(true);
					isDeleted = pdfFl.delete();
					if (isDeleted) { // If label is deleted from Folder, delete from DB
						OrderLabelDetailsLocalServiceUtil.deleteOrderLabelDetails(itr1.getTrackingId());
						log.debug("This Label got deleted from DB and PDF also removed for tracking ID = "+itr1.getTrackingId());
					} else {
						throw new Exception("Unable to delete the PDF file");
					}
				}else{
					OrderLabelDetails  orderLabelDetails  = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetails(itr1.getTrackingId());
					if(orderLabelDetails!=null){
						OrderLabelDetailsLocalServiceUtil.deleteOrderLabelDetails(itr1.getTrackingId());
						log.debug("This Label got deleted from DB and PDF also removed for tracking ID = "+itr1.getTrackingId());
					}else{
						log.debug("Error in deletion.This file may be corrupted or moved to somewhere else!!!");
					}
				}
			}
		} catch (Exception e) {
			log.debug("Response is ERROR");
		}
		log.debug("Scheduler has been stopped for C&C ...");
	}
}
