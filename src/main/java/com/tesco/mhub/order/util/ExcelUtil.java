package com.tesco.mhub.order.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.tesco.mhub.order.constants.OrderConstants;
import com.tesco.mhub.order.model.Order;
import com.tesco.mhub.order.model.OrderLineItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

public abstract class ExcelUtil {
	/**
	 * row number to insert data at first position.
	 */
	private static final String ROW_NUM_ONE = "2";
	/**
	 * log object.
	 */
	private static Log log = LogFactoryUtil.getLog(ExcelUtil.class);

	public static File writeFile(List<Order> orderList) {
		/*
		 * Date date = new Date(); SimpleDateFormat formatDate = new
		 * SimpleDateFormat ("yyyy-MM-dd"); long dateTime = date.getTime();
		 * String fileName = "Data" + dateTime; log.info("File name is = " +
		 * fileName);
		 */

		File newOrderFile = FileUtil.createTempFile("Order Details");
		// File newOrderFile = new File(fileName);
		FileOutputStream out = null;
		HSSFWorkbook workbook = new HSSFWorkbook();

		CreationHelper createHelper = workbook.getCreationHelper();
		CellStyle cellStyleDate = workbook.createCellStyle();
		CellStyle cellStyleEmptyRow = workbook.createCellStyle();
		CellStyle cellStyleBold = workbook.createCellStyle();
		CellStyle cellStyleBold1 = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		
		cellStyleBold.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		cellStyleBold.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyleBold.setFont(font);
		
		HSSFSheet sheet = workbook.createSheet("Sheet 1");
		Row row1 = sheet.createRow((short) 1);
		row1.setHeight((short) 500);
		for(int i=0;i<42;i++){
			Cell cell = row1.createCell((short) i);
			cell.setCellStyle(cellStyleBold);
		}
		
		//R1
	    /*sheet.addMergedRegion(new CellRangeAddress(1,1,0,8));
	    sheet.addMergedRegion(new CellRangeAddress(1,1,9,12));
	    sheet.addMergedRegion(new CellRangeAddress(1,1,13,19));
	    sheet.addMergedRegion(new CellRangeAddress(1,1,20,25));
	    Cell cell = row1.getCell((short) 0);
	    Cell cell1 = row1.getCell((short) 9);
	    Cell cell2 = row1.getCell((short) 13);
	    Cell cell3 = row1.getCell((short) 20);*/
		
		//R2	    
		sheet.addMergedRegion(new CellRangeAddress(1,1,0,12));
	    sheet.addMergedRegion(new CellRangeAddress(1,1,13,20));
	    sheet.addMergedRegion(new CellRangeAddress(1,1,21,35));
	    sheet.addMergedRegion(new CellRangeAddress(1,1,36,43));

	    Cell cell = row1.getCell((short) 0);
	    Cell cell1 = row1.getCell((short) 13);
	    Cell cell2 = row1.getCell((short) 21);
	    Cell cell3 = row1.getCell((short) 36);
	    
	    cell.setCellValue("Order Details");
	    cell1.setCellValue("Customer Details");
	    cell2.setCellValue("Shipment Details");
	    cell3.setCellValue("Order Dispatch / Cancellation Details");

	    //log.info("creating sheet");

		try {
			out = new FileOutputStream(newOrderFile);
		} catch (FileNotFoundException e) {
			log.error(OrderConstants.ORDMG_E023+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E023)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}

		Map<String, Object[]> header = new TreeMap<String, Object[]>();

		header.put(ROW_NUM_ONE,new Object[] { "Customer Order Number","Seller Order Number", "Order Date",
						"Line Number","Merchant SKU Id", "Item Short Description",
						"Ordered Quantity", "Unit Of Sale", "Retail Price",
						"Is Gift Wrapped Order","Gift Message","Installation LineNumber",
						"Bundle Item Line Number","First Name", 
						"Last Name","Mobile No",
						"HomeTel-Day","HomeTel-Night", "Email",
						"Ship To First Name", "Ship To Last Name",
						"Ship To Address Line1", "Ship To Address Line2","Ship To Address Line3", 
						"Ship To Address Line4","Ship To Address Line5", "Ship To Address Line6",
						"Ship To City","Ship To County" ,"Ship To Country", 
						"Ship To Post Code","Ship To Mobile No.", "Ship to Store Number",
						"Ship To Store Name","Delivery Option","Expected Delivery Date","Expected Dispatch Date",
						"Minimum Age Required","IsPodRequired","Delivery Instructions",
						"Delivery Charges","Quantity",
						"Cancellation Reason 1","Cancellation Reason 2",
						});

		/*log.info("MAP CREATED !!!");*/
		int rownum = 2;
		Set<String> keyset = header.keySet(); // keys will be 1,2,3,4....
		for (String key : keyset) { // key = 1 for the first tym , in all take
									// values of keys like 1,2,3,4,5,6......
			Row row = sheet.createRow(rownum); // first(zeroth) row will b
												// created
			row.setHeight((short) 500);
			Object[] objArr = header.get(key); // fetch the objects stored at
												// key = 1,2,3,4.......
												// respectively
			int cellnum = 0; // indicates cell number = 0
			//sheet.autoSizeColumn(0);
			for (Object obj : objArr) { // will refer to respective objects
										// represented by key
				Cell cells = row.createCell(cellnum++); // create a new cell each
														// tym
				if (obj instanceof Date) {
					cellStyleDate.setDataFormat(createHelper.createDataFormat()
							.getFormat("dd/mm/yyyy"));
					cells.setCellValue((Date) obj);
					cells.setCellStyle(cellStyleDate);
				} else if (obj instanceof Boolean) {
					cells.setCellValue((Boolean) obj);
				} else if (obj instanceof String) {
					cells.setCellValue((String) obj);
				} else if (obj instanceof Double) {
					cells.setCellValue((Double) obj);
				}

				// cellStyleDate.setWrapText(true);
				cellStyleBold1.setWrapText(true);
				cells.setCellStyle(cellStyleBold1);
				sheet.autoSizeColumn(cellnum);
			}
		}

		Object[] object1 = new Object[] {StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,StringPool.BLANK,
				StringPool.BLANK,StringPool.BLANK,};	
		Map<String, Object[]> headerRow = new TreeMap<String, Object[]>();
		
	    //headerRow.put("3",new Object[] {"","","","","","","","","","","","","","","","","","","","","","","","","","",});
		headerRow.put("3",object1);
	    Set<String> headerRowData = headerRow.keySet();
	    writeDataToEmptyRow(createHelper, cellStyleEmptyRow, sheet, headerRow,headerRowData);

		
		for (Order odr : orderList) {
			log.debug("Selected orderids are  = : " + odr.getOrderId());
		}

		Map<String, Object[]> data = new TreeMap<String, Object[]>();

		for (Order order : orderList) {
			/*log.info(" putting data for : = " + order.getOrderId());*/
			Map<Integer,OrderLineItem> lineItemDetails = order.getOrderLineItems();
			/*log.info("Download -  size of LineItems : = " +lineItemDetails.size());*/
			for(Integer key : lineItemDetails.keySet()){
				OrderLineItem item = lineItemDetails.get(key);
				/*log.info("Order Line Number ::"+item.getOrderLineNumber());
				log.info(" putting data for : = " +item.getOrderLineNumber());*/
				data.put(ROW_NUM_ONE,new Object[] { order.getCustomerOrderNumber(),order.getOrderId(),order.getOrderPlacedDate(),
						(double) item.getOrderLineNumber(),item.getSku(),item.getDescription(),
						item.getOrderedQuantity(),item.getUnitOfSale(), item.getCostPerItem(),
						item.getIsGiftWrappedOrder(),item.getGiftMessage(),item.getInstallationLineNumber(),
						item.getBundleItemLineNumber(),order.getCustomerFirstName(),
						order.getCustomerLastName(),order.getCustomerMobile(),
						order.getCustomerDayTelephone(),order.getCustomerEveningTelephone(),order.getCustomerEmail(),
						item.getShipToFirstName(),item.getShipToLastName(),
						item.getShipingAddress1(),item.getShipingAddress2(),item.getShipingAddress3(),
						item.getShipingAddress4(),item.getShipingAddress5(), item.getShipingAddress6(),
						item.getShipToCity(),item.getShipToCounty(),item.getShipToCountry(),
						item.getShipToPostcode(),order.getShipToMobile(),item.getShipToStoreNumber(),
						item.getShipToStoreName(),order.getDeliveryOption(),
						item.getExpectedDeliveryDate(),order.getExpectedDispatchDate(),
						item.getMinimumAgeRequired(),item.getIsPODRequired(),item.getDeliveryInstructions(),
						order.getDeliveryCost(),item.getOpenQuantity(),
						item.getPrimaryReasonForCancellation(),item.getSecondaryReasonForReturn(),});
			
			log.info("item.getDispatchedQuantity() = " + item.getDispatchedQuantity());
			/*log.info("trying to input data");*/

			Set<String> keysetData = data.keySet(); // keys will be 1,2,3,4....
			writeDataToCell(createHelper, cellStyleDate, sheet, data,keysetData);
			}
			
			
			/*log.info("putting empty line");*/
			data.put(ROW_NUM_ONE,object1);
			Set<String> keysetData = data.keySet(); // keys will be 1,2,3,4....
			writeDataToEmptyRow(createHelper, cellStyleEmptyRow, sheet, data,keysetData);
			/*log.info("done!!!");*/
		}
		
		/*log.info("data has been put");*/
		try {
			workbook.write(out);
			out.flush();
			out.close();
			workbook = new HSSFWorkbook(new FileInputStream(newOrderFile));
			// workbook = new HSSFWorkbook(new
			// FileInputStream(FileUtil.createTempFile()));
			/*log.info("Excel written successfully..");*/
		} catch (FileNotFoundException e) {
			log.error(OrderConstants.ORDMG_E024+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E024)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		} catch (IOException e) {
			log.error(OrderConstants.ORDMG_E025+OrderConstants.LOG_MESSAGE_SEPERATOR+PortletProps.get(OrderConstants.ORDMG_E025)+OrderConstants.LOG_MESSAGE_SEPERATOR+e.getMessage());
		}

		return newOrderFile;

	}

	private static void writeDataToCell(CreationHelper createHelper,
			CellStyle cellStyleDate, HSSFSheet sheet,
			Map<String, Object[]> data, Set<String> keysetData) {
		for (String keyN : keysetData) { 
			Row row = sheet.createRow(sheet.getLastRowNum() + 1); 
			row.setHeight((short) 500);
			Object[] objArrs = data.get(keyN); 
			int cellnum = 0; // indicates cell number = 0
			for (Object obj : objArrs) { // will refer to respective objects represented by key
				Cell cell = row.createCell(cellnum++); // create a new cell each tym
				if (obj instanceof Date) {
					cellStyleDate.setWrapText(true);
					cellStyleDate.setDataFormat(createHelper
							.createDataFormat().getFormat("dd/mm/yyyy"));
					cell.setCellValue((Date) obj);
					/*cellStyleDate.setFillForegroundColor(HSSFColor.WHITE.index);
					cellStyleDate.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);*/
					cell.setCellStyle(cellStyleDate);
				} else if (obj instanceof Boolean) {
					cell.setCellValue((Boolean) obj);
				} else if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Double) {
					cell.setCellValue((Double) obj);
				}
				sheet.autoSizeColumn(cellnum);
			}

		}
	}
	
	private static void writeDataToEmptyRow(CreationHelper createHelperEmptyRow,
			CellStyle cellStyleEmptyRow, HSSFSheet sheetName,
			Map<String, Object[]> emptyData, Set<String> keysetForEmptyData) {
		for (String keyN : keysetForEmptyData) { 
			Row row = sheetName.createRow(sheetName.getLastRowNum() + 1); 
			row.setHeight((short) 100);
			Object[] objArrs = emptyData.get(keyN); 
			int cellnum = 0; // indicates cell number = 0
			for (Object obj : objArrs) { // will refer to respective objects represented by key
				Cell cell = row.createCell(cellnum++); // create a new cell each tym
				cellStyleEmptyRow.setWrapText(true);
				cellStyleEmptyRow.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
				cellStyleEmptyRow.setLeftBorderColor(HSSFColor.BLACK.index);
				cellStyleEmptyRow.setRightBorderColor(HSSFColor.BLACK.index);
				cellStyleEmptyRow.setBottomBorderColor(HSSFColor.BLACK.index);
				cellStyleEmptyRow.setTopBorderColor(HSSFColor.BLACK.index);
				cellStyleEmptyRow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(cellStyleEmptyRow);
				sheetName.autoSizeColumn(cellnum);
			}
		}
	}
}
