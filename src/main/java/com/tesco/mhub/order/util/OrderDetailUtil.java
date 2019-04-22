package com.tesco.mhub.order.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.tesco.mhub.model.OrderLabelDetails;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.service.OrderLabelDetailsLocalServiceUtil;

import java.util.List;

public class OrderDetailUtil {
	public void updateFinalSubmit(List<Order> cAndCOrdLst){
	//Update the Corresponding OrderLabelDetails with isFinalSubmit 
			try {
				for (Order cAndCOrd : cAndCOrdLst) {
					OrderLabelDetails orderLabelDet = OrderLabelDetailsLocalServiceUtil.getOrderLabelDetails(cAndCOrd.getTrackingId());
					orderLabelDet.setIsFinalSubmit(true);
					OrderLabelDetailsLocalServiceUtil.updateOrderLabelDetails(orderLabelDet);
				}
			} catch (PortalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}
}
