/**
 * 
 */
package com.tesco.mhub.order.model;

import java.util.Date;

/**
 * @author TE50
 *
 */
public class OrderLineItemExt {

	/**
	 *  isGiftWrappedOrder.
	 */
	private String isGiftWrappedOrder;
	/**
	 * giftMessage.
	 */
	private String giftMessage;
	/**
	 * installationLineNumber.
	 */
	private String installationLineNumber;
	/**
	 * bundleItemLineNumber.
	 */
	private String bundleItemLineNumber;
	/**
	 * shipingAddress3.
	 */
	private String shipingAddress3;
	/**
	 * shipingAddress4.
	 */
	private String shipingAddress4;
	/**
	 * shipingAddress5.
	 */
	private String shipingAddress5;
	/**
	 * shipingAddress6.
	 */
	private String shipingAddress6;
	/**
	 * shipToCounty.
	 */
	private String shipToCounty;
	/**
	 * shipToStoreNumber.
	 */
	private String shipToStoreNumber;
	/**
	 * shipToStoreName.
	 */
	private String shipToStoreName;
	/**
	 * shipToHomeTelDay.
	 */
	private String shipToHomeTelDay;
	/**
	 * shipToHomeTelNight.
	 */
	private String shipToHomeTelNight;
	/**
	 * carrierName.
	 */
	
	private String carrierName;
	/**
	 * carrierServiceCode.
	 */
	private String carrierServiceCode;
	/**
	 * expectedDeliveryDate.
	 */
	private Date expectedDeliveryDate;
	/**
	 * minimumAgeRequired.
	 */
	private String minimumAgeRequired;
	/**
	 * isPODRequired.
	 */
	private String isPODRequired;
	/**
	 * deliveryInstructions.
	 */
	private String deliveryInstructions;
	/**
	 * Tracking ID.
	 */
	private StringBuilder trackingId;
	/**
	 * Collected Quantity.
	 */
	private double collectedQuantity;
	/**
	 * Collected Quantity.
	 */
	private double awaitCollectedQuantity;
	
	public String getIsGiftWrappedOrder() {
		return isGiftWrappedOrder;
	}
	public void setIsGiftWrappedOrder(String isGiftWrappedOrder) {
		this.isGiftWrappedOrder = isGiftWrappedOrder;
	}
	public String getGiftMessage() {
		return giftMessage;
	}
	public void setGiftMessage(String giftMessage) {
		this.giftMessage = giftMessage;
	}
	/**
	 * @return the installationLineNumber
	 */
	public String getInstallationLineNumber() {
		return installationLineNumber;
	}
	/**
	 * @param installationLineNumber the installationLineNumber to set
	 */
	public void setInstallationLineNumber(String installationLineNumber) {
		this.installationLineNumber = installationLineNumber;
	}
	/**
	 * @return the bundleItemLineNumber
	 */
	public String getBundleItemLineNumber() {
		return bundleItemLineNumber;
	}
	/**
	 * @param bundleItemLineNumber the bundleItemLineNumber to set
	 */
	public void setBundleItemLineNumber(String bundleItemLineNumber) {
		this.bundleItemLineNumber = bundleItemLineNumber;
	}
	/**
	 * @return the shipingAddress3
	 */
	public String getShipingAddress3() {
		return shipingAddress3;
	}
	/**
	 * @param shipingAddress3 the shipingAddress3 to set
	 */
	public void setShipingAddress3(String shipingAddress3) {
		this.shipingAddress3 = shipingAddress3;
	}
	/**
	 * @return the shipingAddress4
	 */
	public String getShipingAddress4() {
		return shipingAddress4;
	}
	/**
	 * @param shipingAddress4 the shipingAddress4 to set
	 */
	public void setShipingAddress4(String shipingAddress4) {
		this.shipingAddress4 = shipingAddress4;
	}
	/**
	 * @return the shipingAddress5
	 */
	public String getShipingAddress5() {
		return shipingAddress5;
	}
	/**
	 * @param shipingAddress5 the shipingAddress5 to set
	 */
	public void setShipingAddress5(String shipingAddress5) {
		this.shipingAddress5 = shipingAddress5;
	}
	/**
	 * @return the shipingAddress6
	 */
	public String getShipingAddress6() {
		return shipingAddress6;
	}
	/**
	 * @param shipingAddress6 the shipingAddress6 to set
	 */
	public void setShipingAddress6(String shipingAddress6) {
		this.shipingAddress6 = shipingAddress6;
	}
	/**
	 * @return the shipToCounty
	 */
	public String getShipToCounty() {
		return shipToCounty;
	}
	/**
	 * @param shipToCounty the shipToCounty to set
	 */
	public void setShipToCounty(String shipToCounty) {
		this.shipToCounty = shipToCounty;
	}
	/**
	 * @return the shipToStoreNumber
	 */
	public String getShipToStoreNumber() {
		return shipToStoreNumber;
	}
	/**
	 * @param shipToStoreNumber the shipToStoreNumber to set
	 */
	public void setShipToStoreNumber(String shipToStoreNumber) {
		this.shipToStoreNumber = shipToStoreNumber;
	}
	/**
	 * @return the shipToStoreName
	 */
	public String getShipToStoreName() {
		return shipToStoreName;
	}
	/**
	 * @param shipToStoreName the shipToStoreName to set
	 */
	public void setShipToStoreName(String shipToStoreName) {
		this.shipToStoreName = shipToStoreName;
	}
	/**
	 * @return the carrierName
	 */
	public String getCarrierName() {
		return carrierName;
	}
	/**
	 * @param carrierName the carrierName to set
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	/**
	 * @return the carrierServiceCode
	 */
	public String getCarrierServiceCode() {
		return carrierServiceCode;
	}
	/**
	 * @param carrierServiceCode the carrierServiceCode to set
	 */
	public void setCarrierServiceCode(String carrierServiceCode) {
		this.carrierServiceCode = carrierServiceCode;
	}
	/**
	 * @return the expectedDeliveryDate
	 */
	public Date getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}
	/**
	 * @param expectedDeliveryDate the expectedDeliveryDate to set
	 */
	public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	/**
	 * @return the minimumAgeRequired
	 */
	public String getMinimumAgeRequired() {
		return minimumAgeRequired;
	}
	/**
	 * @param minimumAgeRequired the minimumAgeRequired to set
	 */
	public void setMinimumAgeRequired(String minimumAgeRequired) {
		this.minimumAgeRequired = minimumAgeRequired;
	}
	/**
	 * @return the isPODRequired
	 */
	public String getIsPODRequired() {
		return isPODRequired;
	}
	/**
	 * @param isPODRequired the isPODRequired to set
	 */
	public void setIsPODRequired(String isPODRequired) {
		this.isPODRequired = isPODRequired;
	}
	/**
	 * @return the deliveryInstructions
	 */
	public String getDeliveryInstructions() {
		return deliveryInstructions;
	}
	/**
	 * @param deliveryInstructions the deliveryInstructions to set
	 */
	public void setDeliveryInstructions(String deliveryInstructions) {
		this.deliveryInstructions = deliveryInstructions;
	}
	/**
	 * @return the ShipToHomeTelDay
	 */
	public String getShipToHomeTelDay() {
		return shipToHomeTelDay;
	}
	/**
	 * @param ShipToHomeTelDay the deliveryInstructions to set
	 */
	public void setShipToHomeTelDay(String shipToHomeTelDay) {
		this.shipToHomeTelDay = shipToHomeTelDay;
	}
	/**
	 * @return the ShipToHomeTelNight
	 */
	public String getShipToHomeTelNight() {
		return shipToHomeTelNight;
	}
	/**
	 * @param ShipToHomeTelNight the deliveryInstructions to set
	 */
	public void setShipToHomeTelNight(String shipToHomeTelNight) {
		this.shipToHomeTelNight = shipToHomeTelNight;
	}
	public StringBuilder getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(StringBuilder trackingId) {
		this.trackingId = trackingId;
	}
	public double getCollectedQuantity() {
		return collectedQuantity;
	}
	public void setCollectedQuantity(double collectedQuantity) {
		this.collectedQuantity = collectedQuantity;
	}
	public double getAwaitCollectedQuantity() {
		return awaitCollectedQuantity;
	}
	public void setAwaitCollectedQuantity(double awaitCollectedQuantity) {
		this.awaitCollectedQuantity = awaitCollectedQuantity;
	}
	
}
