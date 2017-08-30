package com.ilabquality.gtaf.guitests.deal.iss;

import org.joda.time.DateTime;

import com.ilabquality.gtaf.gtafAppl.Runner;

public class DealPOJO {
	private String productType, productSubType,step, customer, appChecksCorrectnessDueDate,appChecksCorrectnessNotes,appChecksIndemnitiesDueDate,appChecksIndemnitiesNotes,appChecksAMLDueDate,appChecksAMLNotes,expiryType,expiryDate,currency,
			dealCreditDueDate,dealCreditNotes,dealBeneficiary,dealSanctionsDueDate,dealSanctionsNotes,dealProductionReferenceNumber,sanctionsReferenceNumber="";
	private boolean appCheckCorrectnessYes,appCheckCorrectnessNo,appChecksSignatureYes,appChecksSignatureNo,appChecksIndemnitiesYes,appChecksIndemnitiesNo,appChecksAMLYes,appChecksAMLNo,appChecksJurisdictionYes,appChecksJurisdictionNo,dealCashCoverYes,
					dealCashCoverNo,dealCreditYes,dealCreditNo,dealCompletedOnProductionYes,dealCompletedOnProductionNo,customerKYC,sanctionsCheckCompletedYes,sanctionsCheckCompletedNo,deliveryInfoStandard,deliveryInfoNonStandard,relevantCharges,
					recoverySchedule, reviewedOnProduction = false;
	private double dealAmount = 0;
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductSubType() {
		return productSubType;
	}
	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getAppChecksCorrectnessDueDate() {
		return appChecksCorrectnessDueDate;
	}
	public void setAppChecksCorrectnessDueDate(String appChecksCorrectnessDueDate) {
		this.appChecksCorrectnessDueDate = appChecksCorrectnessDueDate;
	}
	public String getAppChecksCorrectnessNotes() {
		return appChecksCorrectnessNotes;
	}
	public void setAppChecksCorrectnessNotes(String appChecksCorrectnessNotes) {
		this.appChecksCorrectnessNotes = appChecksCorrectnessNotes;
	}
	public String getAppChecksIndemnitiesDueDate() {
		return appChecksIndemnitiesDueDate;
	}
	public void setAppChecksIndemnitiesDueDate(String appChecksIndemnitiesDueDate) {
		this.appChecksIndemnitiesDueDate = appChecksIndemnitiesDueDate;
	}
	public String getAppChecksIndemnitiesNotes() {
		return appChecksIndemnitiesNotes;
	}
	public void setAppChecksIndemnitiesNotes(String appChecksIndemnitiesNotes) {
		this.appChecksIndemnitiesNotes = appChecksIndemnitiesNotes;
	}
	public String getAppChecksAMLDueDate() {
		return appChecksAMLDueDate;
	}
	public void setAppChecksAMLDueDate(String appChecksAMLDueDate) {
		this.appChecksAMLDueDate = appChecksAMLDueDate;
	}
	public String getAppChecksAMLNotes() {
		return appChecksAMLNotes;
	}
	public void setAppChecksAMLNotes(String appChecksAMLNotes) {
		this.appChecksAMLNotes = appChecksAMLNotes;
	}
	public String getExpiryType() {
		return expiryType;
	}
	public void setExpiryType(String expiryType) {
		this.expiryType = expiryType;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDealCreditDueDate() {
		return dealCreditDueDate;
	}
	public void setDealCreditDueDate(String dealCreditDueDate) {
		this.dealCreditDueDate = dealCreditDueDate;
	}
	public String getDealCreditNotes() {
		return dealCreditNotes;
	}
	public void setDealCreditNotes(String dealCreditNotes) {
		this.dealCreditNotes = dealCreditNotes;
	}
	public String getDealBeneficiary() {
		return dealBeneficiary;
	}
	public void setDealBeneficiary(String dealBeneficiary) {
		this.dealBeneficiary = dealBeneficiary;
	}
	public String getDealSanctionsDueDate() {
		dealSanctionsDueDate = Runner.DATE_FORMAT.print(DateTime.now());
		return dealSanctionsDueDate;
	}
	public void setDealSanctionsDueDate(String dealSanctionsDueDate) {
		this.dealSanctionsDueDate = dealSanctionsDueDate;
	}
	public String getDealSanctionsNotes() {
		return dealSanctionsNotes;
	}
	public void setDealSanctionsNotes(String dealSanctionsNotes) {
		this.dealSanctionsNotes = dealSanctionsNotes;
	}
	public String getDealProductionReferenceNumber() {
		return dealProductionReferenceNumber;
	}
	public void setDealProductionReferenceNumber(String dealProductionReferenceNumber) {
		this.dealProductionReferenceNumber = dealProductionReferenceNumber;
	}
	public boolean isAppCheckCorrectnessYes() {
		return appCheckCorrectnessYes;
	}
	public void setAppCheckCorrectnessYes(boolean appCheckCorrectnessYes) {
		this.appCheckCorrectnessYes = appCheckCorrectnessYes;
	}
	public boolean isAppCheckCorrectnessNo() {
		return appCheckCorrectnessNo;
	}
	public void setAppCheckCorrectnessNo(boolean appCheckCorrectnessNo) {
		this.appCheckCorrectnessNo = appCheckCorrectnessNo;
	}
	public boolean isAppChecksSignatureYes() {
		return appChecksSignatureYes;
	}
	public void setAppChecksSignatureYes(boolean appChecksSignatureYes) {
		this.appChecksSignatureYes = appChecksSignatureYes;
	}
	public boolean isAppChecksSignatureNo() {
		return appChecksSignatureNo;
	}
	public void setAppChecksSignatureNo(boolean appChecksSignatureNo) {
		this.appChecksSignatureNo = appChecksSignatureNo;
	}
	public boolean isAppChecksIndemnitiesYes() {
		return appChecksIndemnitiesYes;
	}
	public void setAppChecksIndemnitiesYes(boolean appChecksIndemnitiesYes) {
		this.appChecksIndemnitiesYes = appChecksIndemnitiesYes;
	}
	public boolean isAppChecksIndemnitiesNo() {
		return appChecksIndemnitiesNo;
	}
	public void setAppChecksIndemnitiesNo(boolean appChecksIndemnitiesNo) {
		this.appChecksIndemnitiesNo = appChecksIndemnitiesNo;
	}
	public boolean isAppChecksAMLYes() {
		return appChecksAMLYes;
	}
	public void setAppChecksAMLYes(boolean appChecksAMLYes) {
		this.appChecksAMLYes = appChecksAMLYes;
	}
	public boolean isAppChecksAMLNo() {
		return appChecksAMLNo;
	}
	public void setAppChecksAMLNo(boolean appChecksAMLNo) {
		this.appChecksAMLNo = appChecksAMLNo;
	}
	public boolean isAppChecksJurisdictionYes() {
		return appChecksJurisdictionYes;
	}
	public void setAppChecksJurisdictionYes(boolean appChecksJurisdictionYes) {
		this.appChecksJurisdictionYes = appChecksJurisdictionYes;
	}
	public boolean isAppChecksJurisdictionNo() {
		return appChecksJurisdictionNo;
	}
	public void setAppChecksJurisdictionNo(boolean appChecksJurisdictionNo) {
		this.appChecksJurisdictionNo = appChecksJurisdictionNo;
	}
	public boolean isDealCashCoverYes() {
		return dealCashCoverYes;
	}
	public void setDealCashCoverYes(boolean dealCashCoverYes) {
		this.dealCashCoverYes = dealCashCoverYes;
	}
	public boolean isDealCashCoverNo() {
		return dealCashCoverNo;
	}
	public void setDealCashCoverNo(boolean dealCashCoverNo) {
		this.dealCashCoverNo = dealCashCoverNo;
	}
	public boolean isDealCreditYes() {
		return dealCreditYes;
	}
	public void setDealCreditYes(boolean dealCreditYes) {
		this.dealCreditYes = dealCreditYes;
	}
	public boolean isDealCreditNo() {
		return dealCreditNo;
	}
	public void setDealCreditNo(boolean dealCreditNo) {
		this.dealCreditNo = dealCreditNo;
	}
	public boolean isDealCompletedOnProductionYes() {
		return dealCompletedOnProductionYes;
	}
	public void setDealCompletedOnProductionYes(boolean dealCompletedOnProductionYes) {
		this.dealCompletedOnProductionYes = dealCompletedOnProductionYes;
	}
	public boolean isDealCompletedOnProductionNo() {
		return dealCompletedOnProductionNo;
	}
	public void setDealCompletedOnProductionNo(boolean dealCompletedOnProductionNo) {
		this.dealCompletedOnProductionNo = dealCompletedOnProductionNo;
	}
	public double getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(double dealAmount) {
		this.dealAmount = dealAmount;
	}
	public boolean isCustomerKYC() {
		return customerKYC;
	}
	public void setCustomerKYC(boolean customerKYC) {
		this.customerKYC = customerKYC;
	}

	public boolean isSanctionsCheckCompletedYes() {
		return sanctionsCheckCompletedYes;
	}
	public void setSanctionsCheckCompletedYes(boolean sanctionsCheckCompletedYes) {
		this.sanctionsCheckCompletedYes = sanctionsCheckCompletedYes;
	}
	public boolean isSanctionsCheckCompletedNo() {
		return sanctionsCheckCompletedNo;
	}
	public void setSanctionsCheckCompletedNo(boolean sanctionsCheckCompletedNo) {
		this.sanctionsCheckCompletedNo = sanctionsCheckCompletedNo;
	}
	public boolean isDeliveryInfoStandard() {
		return deliveryInfoStandard;
	}
	public void setDeliveryInfoStandard(boolean deliveryInfoStandard) {
		this.deliveryInfoStandard = deliveryInfoStandard;
	}
	public boolean isDeliveryInfoNonStandard() {
		return deliveryInfoNonStandard;
	}
	public void setDeliveryInfoNonStandard(boolean deliveryInfoNonStandard) {
		this.deliveryInfoNonStandard = deliveryInfoNonStandard;
	}
	public boolean isRelevantCharges() {
		return relevantCharges;
	}
	public void setRelevantCharges(boolean relevantCharges) {
		this.relevantCharges = relevantCharges;
	}
	public boolean isRecoverySchedule() {
		return recoverySchedule;
	}
	public void setRecoverySchedule(boolean recoverySchedule) {
		this.recoverySchedule = recoverySchedule;
	}
	public boolean isReviewedOnProduction() {
		return reviewedOnProduction;
	}
	public void setReviewedOnProduction(boolean reviewedOnProduction) {
		this.reviewedOnProduction = reviewedOnProduction;
	}

	public String getSanctionsReferenceNumber() {
		return sanctionsReferenceNumber;
	}
	public void setSanctionsReferenceNumber(String sanctionsReferenceNumber) {
		this.sanctionsReferenceNumber = sanctionsReferenceNumber;
	}
	
}
