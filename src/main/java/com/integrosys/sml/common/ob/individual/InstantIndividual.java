/**
 * InstantIndividual.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.common.ob.individual;

public class InstantIndividual  implements java.io.Serializable {
    private com.integrosys.sml.common.ob.account.Account[] accounts;

    private com.integrosys.sml.common.ob.address.Address[] addresses;

    private java.math.BigDecimal age;

    private java.math.BigDecimal ageMonth;

    private java.lang.String benefitPlusSegment;

    private java.util.Calendar birthDate;

    private java.lang.String birthPlace;

    private java.math.BigDecimal bonusYearly;

    private java.lang.String businessSubType;

    private java.lang.String businessType;

    private java.math.BigDecimal caId;

    private java.math.BigDecimal cifId;

    private java.lang.String cifRelCode;

    private java.lang.String companyType;

    private java.util.Calendar contractEmpEndDate;

    private java.util.Calendar contractEmpStartDate;

    private java.lang.String contractEmployedFlag;

    private java.lang.String countryOfRegAddr;

    private com.integrosys.sml.common.ob.creditcard.InstantCreditCard[] creditCards;

    private java.lang.String currentEmployerCode;

    private java.lang.String currentJobTitle;

    private java.lang.String custContactTime;

    private java.math.BigDecimal customerLevel;

    private java.lang.String customerRiskRM;

    private java.lang.String customerType;

    private java.lang.String discloseCustInfoFlag;

    private java.lang.String educationLevel;

    private java.lang.String email;

    private java.lang.String emailStatementFlag;

    private java.lang.String employmentBizNature;

    private java.lang.String employmentDept;

    private java.lang.String employmentFaxNo;

    private java.math.BigDecimal employmentFinalTotalIncome;

    private java.lang.String employmentInfoSavedFlag;

    private java.lang.String employmentMonth;

    private java.lang.String employmentName;

    private java.lang.String employmentOccupation;

    private java.lang.String employmentProvince;

    private java.lang.String employmentStaffId;

    private java.lang.String employmentStatus;

    private java.lang.String employmentTelephoneDirectNo;

    private java.lang.String employmentTelephoneExtNo;

    private java.lang.String employmentTelephoneNo;

    private java.lang.String employmentYear;

    private java.lang.String excludeIncomeDebtFlag;

    private java.lang.String existingSavingTDAccount;

    private java.util.Calendar expiryDate;

    private java.lang.String extTelephoneNo;

    private java.lang.String fixNetAsset;

    private java.lang.String gender;

    private java.lang.String hostCifNo;

    private java.lang.String idIssueCtry1;

    private java.lang.String idNo1;

    private java.lang.String idType1;

    private java.lang.String idenPresentToBank;

    private java.math.BigDecimal inTotalExtraIncome;

    private java.math.BigDecimal inTotalFixedIncome;

    private java.math.BigDecimal inTotalIncome;

    private java.math.BigDecimal inTotalOtherIncome;

    private java.lang.String includeIncomeFlag;

    private java.lang.String incomeBankAccoutNumber;

    private java.lang.String incomeBankName;

    private java.math.BigDecimal incomeBasicSalary;

    private java.math.BigDecimal incomeDeclared;

    private java.math.BigDecimal incomeExtraOther;

    private java.math.BigDecimal incomeExtraRental;

    private java.lang.String incomeInfoSavedFlag;

    private java.math.BigDecimal incomeOtherCommAverage;

    private java.math.BigDecimal incomeOtherCommMonth1;

    private java.math.BigDecimal incomeOtherCommMonth2;

    private java.math.BigDecimal incomeOtherCommMonth3;

    private java.math.BigDecimal incomeOtherDiemAverage;

    private java.math.BigDecimal incomeOtherDiemMonth1;

    private java.math.BigDecimal incomeOtherDiemMonth2;

    private java.math.BigDecimal incomeOtherDiemMonth3;

    private java.math.BigDecimal incomeOtherFlightAverage;

    private java.math.BigDecimal incomeOtherFlightMonth1;

    private java.math.BigDecimal incomeOtherFlightMonth2;

    private java.math.BigDecimal incomeOtherFlightMonth3;

    private java.math.BigDecimal incomeOtherIncome;

    private java.math.BigDecimal incomeOtherOTAverage;

    private java.math.BigDecimal incomeOtherOTMonth1;

    private java.math.BigDecimal incomeOtherOTMonth2;

    private java.math.BigDecimal incomeOtherOTMonth3;

    private java.math.BigDecimal incomeSharedHolderPercent;

    private java.lang.String incomeType;

    private java.math.BigDecimal incometotalLastMthCreditAcct1;

    private java.math.BigDecimal incometotalLastMthCreditAcct2;

    private java.lang.String insiderCode;

    private java.util.Calendar issuedDate;

    private java.lang.String lifeTimeFlag;

    private java.lang.String mailingPreference;

    private java.lang.String maritalStatus;

    private java.lang.String mobileNo;

    private java.lang.String nameLine1;

    private java.lang.String nameLine2;

    private java.lang.String nationality;

    private java.lang.String nationality2;

    private java.lang.String ncbConsentFlag;

    private java.lang.String noOfDependent;

    private java.lang.String originatedUnit;

    private java.lang.String personalInfoSavedFlag;

    private java.math.BigDecimal preScoreCash;

    private java.lang.String professionalCode;

    private java.lang.String race;

    private java.lang.String registerExtTelephoneNo;

    private java.lang.String registerTelephoneNo;

    private java.lang.String relationWithMain;

    private java.lang.String residentFlag;

    private java.lang.String residentType;

    private java.lang.String rmOccupation;

    private java.lang.String rmWealthFlag;

    private java.lang.String salutationCode;

    private java.lang.String sameRegisterAddressFlag;

    private java.math.BigDecimal selfEmployInTotalIncome;

    private java.math.BigDecimal selfEmployIncomeExtraOther;

    private java.math.BigDecimal selfEmployIncomeExtraRental;

    private java.math.BigDecimal selfEmployIncomeExtraTotal;

    private java.math.BigDecimal selfEmployIncomeTotal;

    private java.lang.String sourceFromCountry;

    private java.lang.String sourceOfRisk;

    private java.util.Calendar spBirthDate;

    private java.lang.String spIdNo;

    private java.lang.String spIdType;

    private java.lang.String spSalutationCode;

    private java.lang.String spThaiName;

    private java.lang.String spThaiSurName;

    private java.lang.String spTitleTypeCode;

    private java.lang.String telephoneNo;

    private java.lang.String thaiName;

    private java.lang.String thaiSalutationCode;

    private java.lang.String thaiSurName;

    private java.lang.String titleTypeCode;

    private java.lang.String workingAddrCopyFrom;

    public InstantIndividual() {
    }

    public InstantIndividual(
           com.integrosys.sml.common.ob.account.Account[] accounts,
           com.integrosys.sml.common.ob.address.Address[] addresses,
           java.math.BigDecimal age,
           java.math.BigDecimal ageMonth,
           java.lang.String benefitPlusSegment,
           java.util.Calendar birthDate,
           java.lang.String birthPlace,
           java.math.BigDecimal bonusYearly,
           java.lang.String businessSubType,
           java.lang.String businessType,
           java.math.BigDecimal caId,
           java.math.BigDecimal cifId,
           java.lang.String cifRelCode,
           java.lang.String companyType,
           java.util.Calendar contractEmpEndDate,
           java.util.Calendar contractEmpStartDate,
           java.lang.String contractEmployedFlag,
           java.lang.String countryOfRegAddr,
           com.integrosys.sml.common.ob.creditcard.InstantCreditCard[] creditCards,
           java.lang.String currentEmployerCode,
           java.lang.String currentJobTitle,
           java.lang.String custContactTime,
           java.math.BigDecimal customerLevel,
           java.lang.String customerRiskRM,
           java.lang.String customerType,
           java.lang.String discloseCustInfoFlag,
           java.lang.String educationLevel,
           java.lang.String email,
           java.lang.String emailStatementFlag,
           java.lang.String employmentBizNature,
           java.lang.String employmentDept,
           java.lang.String employmentFaxNo,
           java.math.BigDecimal employmentFinalTotalIncome,
           java.lang.String employmentInfoSavedFlag,
           java.lang.String employmentMonth,
           java.lang.String employmentName,
           java.lang.String employmentOccupation,
           java.lang.String employmentProvince,
           java.lang.String employmentStaffId,
           java.lang.String employmentStatus,
           java.lang.String employmentTelephoneDirectNo,
           java.lang.String employmentTelephoneExtNo,
           java.lang.String employmentTelephoneNo,
           java.lang.String employmentYear,
           java.lang.String excludeIncomeDebtFlag,
           java.lang.String existingSavingTDAccount,
           java.util.Calendar expiryDate,
           java.lang.String extTelephoneNo,
           java.lang.String fixNetAsset,
           java.lang.String gender,
           java.lang.String hostCifNo,
           java.lang.String idIssueCtry1,
           java.lang.String idNo1,
           java.lang.String idType1,
           java.lang.String idenPresentToBank,
           java.math.BigDecimal inTotalExtraIncome,
           java.math.BigDecimal inTotalFixedIncome,
           java.math.BigDecimal inTotalIncome,
           java.math.BigDecimal inTotalOtherIncome,
           java.lang.String includeIncomeFlag,
           java.lang.String incomeBankAccoutNumber,
           java.lang.String incomeBankName,
           java.math.BigDecimal incomeBasicSalary,
           java.math.BigDecimal incomeDeclared,
           java.math.BigDecimal incomeExtraOther,
           java.math.BigDecimal incomeExtraRental,
           java.lang.String incomeInfoSavedFlag,
           java.math.BigDecimal incomeOtherCommAverage,
           java.math.BigDecimal incomeOtherCommMonth1,
           java.math.BigDecimal incomeOtherCommMonth2,
           java.math.BigDecimal incomeOtherCommMonth3,
           java.math.BigDecimal incomeOtherDiemAverage,
           java.math.BigDecimal incomeOtherDiemMonth1,
           java.math.BigDecimal incomeOtherDiemMonth2,
           java.math.BigDecimal incomeOtherDiemMonth3,
           java.math.BigDecimal incomeOtherFlightAverage,
           java.math.BigDecimal incomeOtherFlightMonth1,
           java.math.BigDecimal incomeOtherFlightMonth2,
           java.math.BigDecimal incomeOtherFlightMonth3,
           java.math.BigDecimal incomeOtherIncome,
           java.math.BigDecimal incomeOtherOTAverage,
           java.math.BigDecimal incomeOtherOTMonth1,
           java.math.BigDecimal incomeOtherOTMonth2,
           java.math.BigDecimal incomeOtherOTMonth3,
           java.math.BigDecimal incomeSharedHolderPercent,
           java.lang.String incomeType,
           java.math.BigDecimal incometotalLastMthCreditAcct1,
           java.math.BigDecimal incometotalLastMthCreditAcct2,
           java.lang.String insiderCode,
           java.util.Calendar issuedDate,
           java.lang.String lifeTimeFlag,
           java.lang.String mailingPreference,
           java.lang.String maritalStatus,
           java.lang.String mobileNo,
           java.lang.String nameLine1,
           java.lang.String nameLine2,
           java.lang.String nationality,
           java.lang.String nationality2,
           java.lang.String ncbConsentFlag,
           java.lang.String noOfDependent,
           java.lang.String originatedUnit,
           java.lang.String personalInfoSavedFlag,
           java.math.BigDecimal preScoreCash,
           java.lang.String professionalCode,
           java.lang.String race,
           java.lang.String registerExtTelephoneNo,
           java.lang.String registerTelephoneNo,
           java.lang.String relationWithMain,
           java.lang.String residentFlag,
           java.lang.String residentType,
           java.lang.String rmOccupation,
           java.lang.String rmWealthFlag,
           java.lang.String salutationCode,
           java.lang.String sameRegisterAddressFlag,
           java.math.BigDecimal selfEmployInTotalIncome,
           java.math.BigDecimal selfEmployIncomeExtraOther,
           java.math.BigDecimal selfEmployIncomeExtraRental,
           java.math.BigDecimal selfEmployIncomeExtraTotal,
           java.math.BigDecimal selfEmployIncomeTotal,
           java.lang.String sourceFromCountry,
           java.lang.String sourceOfRisk,
           java.util.Calendar spBirthDate,
           java.lang.String spIdNo,
           java.lang.String spIdType,
           java.lang.String spSalutationCode,
           java.lang.String spThaiName,
           java.lang.String spThaiSurName,
           java.lang.String spTitleTypeCode,
           java.lang.String telephoneNo,
           java.lang.String thaiName,
           java.lang.String thaiSalutationCode,
           java.lang.String thaiSurName,
           java.lang.String titleTypeCode,
           java.lang.String workingAddrCopyFrom) {
           this.accounts = accounts;
           this.addresses = addresses;
           this.age = age;
           this.ageMonth = ageMonth;
           this.benefitPlusSegment = benefitPlusSegment;
           this.birthDate = birthDate;
           this.birthPlace = birthPlace;
           this.bonusYearly = bonusYearly;
           this.businessSubType = businessSubType;
           this.businessType = businessType;
           this.caId = caId;
           this.cifId = cifId;
           this.cifRelCode = cifRelCode;
           this.companyType = companyType;
           this.contractEmpEndDate = contractEmpEndDate;
           this.contractEmpStartDate = contractEmpStartDate;
           this.contractEmployedFlag = contractEmployedFlag;
           this.countryOfRegAddr = countryOfRegAddr;
           this.creditCards = creditCards;
           this.currentEmployerCode = currentEmployerCode;
           this.currentJobTitle = currentJobTitle;
           this.custContactTime = custContactTime;
           this.customerLevel = customerLevel;
           this.customerRiskRM = customerRiskRM;
           this.customerType = customerType;
           this.discloseCustInfoFlag = discloseCustInfoFlag;
           this.educationLevel = educationLevel;
           this.email = email;
           this.emailStatementFlag = emailStatementFlag;
           this.employmentBizNature = employmentBizNature;
           this.employmentDept = employmentDept;
           this.employmentFaxNo = employmentFaxNo;
           this.employmentFinalTotalIncome = employmentFinalTotalIncome;
           this.employmentInfoSavedFlag = employmentInfoSavedFlag;
           this.employmentMonth = employmentMonth;
           this.employmentName = employmentName;
           this.employmentOccupation = employmentOccupation;
           this.employmentProvince = employmentProvince;
           this.employmentStaffId = employmentStaffId;
           this.employmentStatus = employmentStatus;
           this.employmentTelephoneDirectNo = employmentTelephoneDirectNo;
           this.employmentTelephoneExtNo = employmentTelephoneExtNo;
           this.employmentTelephoneNo = employmentTelephoneNo;
           this.employmentYear = employmentYear;
           this.excludeIncomeDebtFlag = excludeIncomeDebtFlag;
           this.existingSavingTDAccount = existingSavingTDAccount;
           this.expiryDate = expiryDate;
           this.extTelephoneNo = extTelephoneNo;
           this.fixNetAsset = fixNetAsset;
           this.gender = gender;
           this.hostCifNo = hostCifNo;
           this.idIssueCtry1 = idIssueCtry1;
           this.idNo1 = idNo1;
           this.idType1 = idType1;
           this.idenPresentToBank = idenPresentToBank;
           this.inTotalExtraIncome = inTotalExtraIncome;
           this.inTotalFixedIncome = inTotalFixedIncome;
           this.inTotalIncome = inTotalIncome;
           this.inTotalOtherIncome = inTotalOtherIncome;
           this.includeIncomeFlag = includeIncomeFlag;
           this.incomeBankAccoutNumber = incomeBankAccoutNumber;
           this.incomeBankName = incomeBankName;
           this.incomeBasicSalary = incomeBasicSalary;
           this.incomeDeclared = incomeDeclared;
           this.incomeExtraOther = incomeExtraOther;
           this.incomeExtraRental = incomeExtraRental;
           this.incomeInfoSavedFlag = incomeInfoSavedFlag;
           this.incomeOtherCommAverage = incomeOtherCommAverage;
           this.incomeOtherCommMonth1 = incomeOtherCommMonth1;
           this.incomeOtherCommMonth2 = incomeOtherCommMonth2;
           this.incomeOtherCommMonth3 = incomeOtherCommMonth3;
           this.incomeOtherDiemAverage = incomeOtherDiemAverage;
           this.incomeOtherDiemMonth1 = incomeOtherDiemMonth1;
           this.incomeOtherDiemMonth2 = incomeOtherDiemMonth2;
           this.incomeOtherDiemMonth3 = incomeOtherDiemMonth3;
           this.incomeOtherFlightAverage = incomeOtherFlightAverage;
           this.incomeOtherFlightMonth1 = incomeOtherFlightMonth1;
           this.incomeOtherFlightMonth2 = incomeOtherFlightMonth2;
           this.incomeOtherFlightMonth3 = incomeOtherFlightMonth3;
           this.incomeOtherIncome = incomeOtherIncome;
           this.incomeOtherOTAverage = incomeOtherOTAverage;
           this.incomeOtherOTMonth1 = incomeOtherOTMonth1;
           this.incomeOtherOTMonth2 = incomeOtherOTMonth2;
           this.incomeOtherOTMonth3 = incomeOtherOTMonth3;
           this.incomeSharedHolderPercent = incomeSharedHolderPercent;
           this.incomeType = incomeType;
           this.incometotalLastMthCreditAcct1 = incometotalLastMthCreditAcct1;
           this.incometotalLastMthCreditAcct2 = incometotalLastMthCreditAcct2;
           this.insiderCode = insiderCode;
           this.issuedDate = issuedDate;
           this.lifeTimeFlag = lifeTimeFlag;
           this.mailingPreference = mailingPreference;
           this.maritalStatus = maritalStatus;
           this.mobileNo = mobileNo;
           this.nameLine1 = nameLine1;
           this.nameLine2 = nameLine2;
           this.nationality = nationality;
           this.nationality2 = nationality2;
           this.ncbConsentFlag = ncbConsentFlag;
           this.noOfDependent = noOfDependent;
           this.originatedUnit = originatedUnit;
           this.personalInfoSavedFlag = personalInfoSavedFlag;
           this.preScoreCash = preScoreCash;
           this.professionalCode = professionalCode;
           this.race = race;
           this.registerExtTelephoneNo = registerExtTelephoneNo;
           this.registerTelephoneNo = registerTelephoneNo;
           this.relationWithMain = relationWithMain;
           this.residentFlag = residentFlag;
           this.residentType = residentType;
           this.rmOccupation = rmOccupation;
           this.rmWealthFlag = rmWealthFlag;
           this.salutationCode = salutationCode;
           this.sameRegisterAddressFlag = sameRegisterAddressFlag;
           this.selfEmployInTotalIncome = selfEmployInTotalIncome;
           this.selfEmployIncomeExtraOther = selfEmployIncomeExtraOther;
           this.selfEmployIncomeExtraRental = selfEmployIncomeExtraRental;
           this.selfEmployIncomeExtraTotal = selfEmployIncomeExtraTotal;
           this.selfEmployIncomeTotal = selfEmployIncomeTotal;
           this.sourceFromCountry = sourceFromCountry;
           this.sourceOfRisk = sourceOfRisk;
           this.spBirthDate = spBirthDate;
           this.spIdNo = spIdNo;
           this.spIdType = spIdType;
           this.spSalutationCode = spSalutationCode;
           this.spThaiName = spThaiName;
           this.spThaiSurName = spThaiSurName;
           this.spTitleTypeCode = spTitleTypeCode;
           this.telephoneNo = telephoneNo;
           this.thaiName = thaiName;
           this.thaiSalutationCode = thaiSalutationCode;
           this.thaiSurName = thaiSurName;
           this.titleTypeCode = titleTypeCode;
           this.workingAddrCopyFrom = workingAddrCopyFrom;
    }


    /**
     * Gets the accounts value for this InstantIndividual.
     * 
     * @return accounts
     */
    public com.integrosys.sml.common.ob.account.Account[] getAccounts() {
        return accounts;
    }


    /**
     * Sets the accounts value for this InstantIndividual.
     * 
     * @param accounts
     */
    public void setAccounts(com.integrosys.sml.common.ob.account.Account[] accounts) {
        this.accounts = accounts;
    }


    /**
     * Gets the addresses value for this InstantIndividual.
     * 
     * @return addresses
     */
    public com.integrosys.sml.common.ob.address.Address[] getAddresses() {
        return addresses;
    }


    /**
     * Sets the addresses value for this InstantIndividual.
     * 
     * @param addresses
     */
    public void setAddresses(com.integrosys.sml.common.ob.address.Address[] addresses) {
        this.addresses = addresses;
    }


    /**
     * Gets the age value for this InstantIndividual.
     * 
     * @return age
     */
    public java.math.BigDecimal getAge() {
        return age;
    }


    /**
     * Sets the age value for this InstantIndividual.
     * 
     * @param age
     */
    public void setAge(java.math.BigDecimal age) {
        this.age = age;
    }


    /**
     * Gets the ageMonth value for this InstantIndividual.
     * 
     * @return ageMonth
     */
    public java.math.BigDecimal getAgeMonth() {
        return ageMonth;
    }


    /**
     * Sets the ageMonth value for this InstantIndividual.
     * 
     * @param ageMonth
     */
    public void setAgeMonth(java.math.BigDecimal ageMonth) {
        this.ageMonth = ageMonth;
    }


    /**
     * Gets the benefitPlusSegment value for this InstantIndividual.
     * 
     * @return benefitPlusSegment
     */
    public java.lang.String getBenefitPlusSegment() {
        return benefitPlusSegment;
    }


    /**
     * Sets the benefitPlusSegment value for this InstantIndividual.
     * 
     * @param benefitPlusSegment
     */
    public void setBenefitPlusSegment(java.lang.String benefitPlusSegment) {
        this.benefitPlusSegment = benefitPlusSegment;
    }


    /**
     * Gets the birthDate value for this InstantIndividual.
     * 
     * @return birthDate
     */
    public java.util.Calendar getBirthDate() {
        return birthDate;
    }


    /**
     * Sets the birthDate value for this InstantIndividual.
     * 
     * @param birthDate
     */
    public void setBirthDate(java.util.Calendar birthDate) {
        this.birthDate = birthDate;
    }


    /**
     * Gets the birthPlace value for this InstantIndividual.
     * 
     * @return birthPlace
     */
    public java.lang.String getBirthPlace() {
        return birthPlace;
    }


    /**
     * Sets the birthPlace value for this InstantIndividual.
     * 
     * @param birthPlace
     */
    public void setBirthPlace(java.lang.String birthPlace) {
        this.birthPlace = birthPlace;
    }


    /**
     * Gets the bonusYearly value for this InstantIndividual.
     * 
     * @return bonusYearly
     */
    public java.math.BigDecimal getBonusYearly() {
        return bonusYearly;
    }


    /**
     * Sets the bonusYearly value for this InstantIndividual.
     * 
     * @param bonusYearly
     */
    public void setBonusYearly(java.math.BigDecimal bonusYearly) {
        this.bonusYearly = bonusYearly;
    }


    /**
     * Gets the businessSubType value for this InstantIndividual.
     * 
     * @return businessSubType
     */
    public java.lang.String getBusinessSubType() {
        return businessSubType;
    }


    /**
     * Sets the businessSubType value for this InstantIndividual.
     * 
     * @param businessSubType
     */
    public void setBusinessSubType(java.lang.String businessSubType) {
        this.businessSubType = businessSubType;
    }


    /**
     * Gets the businessType value for this InstantIndividual.
     * 
     * @return businessType
     */
    public java.lang.String getBusinessType() {
        return businessType;
    }


    /**
     * Sets the businessType value for this InstantIndividual.
     * 
     * @param businessType
     */
    public void setBusinessType(java.lang.String businessType) {
        this.businessType = businessType;
    }


    /**
     * Gets the caId value for this InstantIndividual.
     * 
     * @return caId
     */
    public java.math.BigDecimal getCaId() {
        return caId;
    }


    /**
     * Sets the caId value for this InstantIndividual.
     * 
     * @param caId
     */
    public void setCaId(java.math.BigDecimal caId) {
        this.caId = caId;
    }


    /**
     * Gets the cifId value for this InstantIndividual.
     * 
     * @return cifId
     */
    public java.math.BigDecimal getCifId() {
        return cifId;
    }


    /**
     * Sets the cifId value for this InstantIndividual.
     * 
     * @param cifId
     */
    public void setCifId(java.math.BigDecimal cifId) {
        this.cifId = cifId;
    }


    /**
     * Gets the cifRelCode value for this InstantIndividual.
     * 
     * @return cifRelCode
     */
    public java.lang.String getCifRelCode() {
        return cifRelCode;
    }


    /**
     * Sets the cifRelCode value for this InstantIndividual.
     * 
     * @param cifRelCode
     */
    public void setCifRelCode(java.lang.String cifRelCode) {
        this.cifRelCode = cifRelCode;
    }


    /**
     * Gets the companyType value for this InstantIndividual.
     * 
     * @return companyType
     */
    public java.lang.String getCompanyType() {
        return companyType;
    }


    /**
     * Sets the companyType value for this InstantIndividual.
     * 
     * @param companyType
     */
    public void setCompanyType(java.lang.String companyType) {
        this.companyType = companyType;
    }


    /**
     * Gets the contractEmpEndDate value for this InstantIndividual.
     * 
     * @return contractEmpEndDate
     */
    public java.util.Calendar getContractEmpEndDate() {
        return contractEmpEndDate;
    }


    /**
     * Sets the contractEmpEndDate value for this InstantIndividual.
     * 
     * @param contractEmpEndDate
     */
    public void setContractEmpEndDate(java.util.Calendar contractEmpEndDate) {
        this.contractEmpEndDate = contractEmpEndDate;
    }


    /**
     * Gets the contractEmpStartDate value for this InstantIndividual.
     * 
     * @return contractEmpStartDate
     */
    public java.util.Calendar getContractEmpStartDate() {
        return contractEmpStartDate;
    }


    /**
     * Sets the contractEmpStartDate value for this InstantIndividual.
     * 
     * @param contractEmpStartDate
     */
    public void setContractEmpStartDate(java.util.Calendar contractEmpStartDate) {
        this.contractEmpStartDate = contractEmpStartDate;
    }


    /**
     * Gets the contractEmployedFlag value for this InstantIndividual.
     * 
     * @return contractEmployedFlag
     */
    public java.lang.String getContractEmployedFlag() {
        return contractEmployedFlag;
    }


    /**
     * Sets the contractEmployedFlag value for this InstantIndividual.
     * 
     * @param contractEmployedFlag
     */
    public void setContractEmployedFlag(java.lang.String contractEmployedFlag) {
        this.contractEmployedFlag = contractEmployedFlag;
    }


    /**
     * Gets the countryOfRegAddr value for this InstantIndividual.
     * 
     * @return countryOfRegAddr
     */
    public java.lang.String getCountryOfRegAddr() {
        return countryOfRegAddr;
    }


    /**
     * Sets the countryOfRegAddr value for this InstantIndividual.
     * 
     * @param countryOfRegAddr
     */
    public void setCountryOfRegAddr(java.lang.String countryOfRegAddr) {
        this.countryOfRegAddr = countryOfRegAddr;
    }


    /**
     * Gets the creditCards value for this InstantIndividual.
     * 
     * @return creditCards
     */
    public com.integrosys.sml.common.ob.creditcard.InstantCreditCard[] getCreditCards() {
        return creditCards;
    }


    /**
     * Sets the creditCards value for this InstantIndividual.
     * 
     * @param creditCards
     */
    public void setCreditCards(com.integrosys.sml.common.ob.creditcard.InstantCreditCard[] creditCards) {
        this.creditCards = creditCards;
    }


    /**
     * Gets the currentEmployerCode value for this InstantIndividual.
     * 
     * @return currentEmployerCode
     */
    public java.lang.String getCurrentEmployerCode() {
        return currentEmployerCode;
    }


    /**
     * Sets the currentEmployerCode value for this InstantIndividual.
     * 
     * @param currentEmployerCode
     */
    public void setCurrentEmployerCode(java.lang.String currentEmployerCode) {
        this.currentEmployerCode = currentEmployerCode;
    }


    /**
     * Gets the currentJobTitle value for this InstantIndividual.
     * 
     * @return currentJobTitle
     */
    public java.lang.String getCurrentJobTitle() {
        return currentJobTitle;
    }


    /**
     * Sets the currentJobTitle value for this InstantIndividual.
     * 
     * @param currentJobTitle
     */
    public void setCurrentJobTitle(java.lang.String currentJobTitle) {
        this.currentJobTitle = currentJobTitle;
    }


    /**
     * Gets the custContactTime value for this InstantIndividual.
     * 
     * @return custContactTime
     */
    public java.lang.String getCustContactTime() {
        return custContactTime;
    }


    /**
     * Sets the custContactTime value for this InstantIndividual.
     * 
     * @param custContactTime
     */
    public void setCustContactTime(java.lang.String custContactTime) {
        this.custContactTime = custContactTime;
    }


    /**
     * Gets the customerLevel value for this InstantIndividual.
     * 
     * @return customerLevel
     */
    public java.math.BigDecimal getCustomerLevel() {
        return customerLevel;
    }


    /**
     * Sets the customerLevel value for this InstantIndividual.
     * 
     * @param customerLevel
     */
    public void setCustomerLevel(java.math.BigDecimal customerLevel) {
        this.customerLevel = customerLevel;
    }


    /**
     * Gets the customerRiskRM value for this InstantIndividual.
     * 
     * @return customerRiskRM
     */
    public java.lang.String getCustomerRiskRM() {
        return customerRiskRM;
    }


    /**
     * Sets the customerRiskRM value for this InstantIndividual.
     * 
     * @param customerRiskRM
     */
    public void setCustomerRiskRM(java.lang.String customerRiskRM) {
        this.customerRiskRM = customerRiskRM;
    }


    /**
     * Gets the customerType value for this InstantIndividual.
     * 
     * @return customerType
     */
    public java.lang.String getCustomerType() {
        return customerType;
    }


    /**
     * Sets the customerType value for this InstantIndividual.
     * 
     * @param customerType
     */
    public void setCustomerType(java.lang.String customerType) {
        this.customerType = customerType;
    }


    /**
     * Gets the discloseCustInfoFlag value for this InstantIndividual.
     * 
     * @return discloseCustInfoFlag
     */
    public java.lang.String getDiscloseCustInfoFlag() {
        return discloseCustInfoFlag;
    }


    /**
     * Sets the discloseCustInfoFlag value for this InstantIndividual.
     * 
     * @param discloseCustInfoFlag
     */
    public void setDiscloseCustInfoFlag(java.lang.String discloseCustInfoFlag) {
        this.discloseCustInfoFlag = discloseCustInfoFlag;
    }


    /**
     * Gets the educationLevel value for this InstantIndividual.
     * 
     * @return educationLevel
     */
    public java.lang.String getEducationLevel() {
        return educationLevel;
    }


    /**
     * Sets the educationLevel value for this InstantIndividual.
     * 
     * @param educationLevel
     */
    public void setEducationLevel(java.lang.String educationLevel) {
        this.educationLevel = educationLevel;
    }


    /**
     * Gets the email value for this InstantIndividual.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this InstantIndividual.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the emailStatementFlag value for this InstantIndividual.
     * 
     * @return emailStatementFlag
     */
    public java.lang.String getEmailStatementFlag() {
        return emailStatementFlag;
    }


    /**
     * Sets the emailStatementFlag value for this InstantIndividual.
     * 
     * @param emailStatementFlag
     */
    public void setEmailStatementFlag(java.lang.String emailStatementFlag) {
        this.emailStatementFlag = emailStatementFlag;
    }


    /**
     * Gets the employmentBizNature value for this InstantIndividual.
     * 
     * @return employmentBizNature
     */
    public java.lang.String getEmploymentBizNature() {
        return employmentBizNature;
    }


    /**
     * Sets the employmentBizNature value for this InstantIndividual.
     * 
     * @param employmentBizNature
     */
    public void setEmploymentBizNature(java.lang.String employmentBizNature) {
        this.employmentBizNature = employmentBizNature;
    }


    /**
     * Gets the employmentDept value for this InstantIndividual.
     * 
     * @return employmentDept
     */
    public java.lang.String getEmploymentDept() {
        return employmentDept;
    }


    /**
     * Sets the employmentDept value for this InstantIndividual.
     * 
     * @param employmentDept
     */
    public void setEmploymentDept(java.lang.String employmentDept) {
        this.employmentDept = employmentDept;
    }


    /**
     * Gets the employmentFaxNo value for this InstantIndividual.
     * 
     * @return employmentFaxNo
     */
    public java.lang.String getEmploymentFaxNo() {
        return employmentFaxNo;
    }


    /**
     * Sets the employmentFaxNo value for this InstantIndividual.
     * 
     * @param employmentFaxNo
     */
    public void setEmploymentFaxNo(java.lang.String employmentFaxNo) {
        this.employmentFaxNo = employmentFaxNo;
    }


    /**
     * Gets the employmentFinalTotalIncome value for this InstantIndividual.
     * 
     * @return employmentFinalTotalIncome
     */
    public java.math.BigDecimal getEmploymentFinalTotalIncome() {
        return employmentFinalTotalIncome;
    }


    /**
     * Sets the employmentFinalTotalIncome value for this InstantIndividual.
     * 
     * @param employmentFinalTotalIncome
     */
    public void setEmploymentFinalTotalIncome(java.math.BigDecimal employmentFinalTotalIncome) {
        this.employmentFinalTotalIncome = employmentFinalTotalIncome;
    }


    /**
     * Gets the employmentInfoSavedFlag value for this InstantIndividual.
     * 
     * @return employmentInfoSavedFlag
     */
    public java.lang.String getEmploymentInfoSavedFlag() {
        return employmentInfoSavedFlag;
    }


    /**
     * Sets the employmentInfoSavedFlag value for this InstantIndividual.
     * 
     * @param employmentInfoSavedFlag
     */
    public void setEmploymentInfoSavedFlag(java.lang.String employmentInfoSavedFlag) {
        this.employmentInfoSavedFlag = employmentInfoSavedFlag;
    }


    /**
     * Gets the employmentMonth value for this InstantIndividual.
     * 
     * @return employmentMonth
     */
    public java.lang.String getEmploymentMonth() {
        return employmentMonth;
    }


    /**
     * Sets the employmentMonth value for this InstantIndividual.
     * 
     * @param employmentMonth
     */
    public void setEmploymentMonth(java.lang.String employmentMonth) {
        this.employmentMonth = employmentMonth;
    }


    /**
     * Gets the employmentName value for this InstantIndividual.
     * 
     * @return employmentName
     */
    public java.lang.String getEmploymentName() {
        return employmentName;
    }


    /**
     * Sets the employmentName value for this InstantIndividual.
     * 
     * @param employmentName
     */
    public void setEmploymentName(java.lang.String employmentName) {
        this.employmentName = employmentName;
    }


    /**
     * Gets the employmentOccupation value for this InstantIndividual.
     * 
     * @return employmentOccupation
     */
    public java.lang.String getEmploymentOccupation() {
        return employmentOccupation;
    }


    /**
     * Sets the employmentOccupation value for this InstantIndividual.
     * 
     * @param employmentOccupation
     */
    public void setEmploymentOccupation(java.lang.String employmentOccupation) {
        this.employmentOccupation = employmentOccupation;
    }


    /**
     * Gets the employmentProvince value for this InstantIndividual.
     * 
     * @return employmentProvince
     */
    public java.lang.String getEmploymentProvince() {
        return employmentProvince;
    }


    /**
     * Sets the employmentProvince value for this InstantIndividual.
     * 
     * @param employmentProvince
     */
    public void setEmploymentProvince(java.lang.String employmentProvince) {
        this.employmentProvince = employmentProvince;
    }


    /**
     * Gets the employmentStaffId value for this InstantIndividual.
     * 
     * @return employmentStaffId
     */
    public java.lang.String getEmploymentStaffId() {
        return employmentStaffId;
    }


    /**
     * Sets the employmentStaffId value for this InstantIndividual.
     * 
     * @param employmentStaffId
     */
    public void setEmploymentStaffId(java.lang.String employmentStaffId) {
        this.employmentStaffId = employmentStaffId;
    }


    /**
     * Gets the employmentStatus value for this InstantIndividual.
     * 
     * @return employmentStatus
     */
    public java.lang.String getEmploymentStatus() {
        return employmentStatus;
    }


    /**
     * Sets the employmentStatus value for this InstantIndividual.
     * 
     * @param employmentStatus
     */
    public void setEmploymentStatus(java.lang.String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }


    /**
     * Gets the employmentTelephoneDirectNo value for this InstantIndividual.
     * 
     * @return employmentTelephoneDirectNo
     */
    public java.lang.String getEmploymentTelephoneDirectNo() {
        return employmentTelephoneDirectNo;
    }


    /**
     * Sets the employmentTelephoneDirectNo value for this InstantIndividual.
     * 
     * @param employmentTelephoneDirectNo
     */
    public void setEmploymentTelephoneDirectNo(java.lang.String employmentTelephoneDirectNo) {
        this.employmentTelephoneDirectNo = employmentTelephoneDirectNo;
    }


    /**
     * Gets the employmentTelephoneExtNo value for this InstantIndividual.
     * 
     * @return employmentTelephoneExtNo
     */
    public java.lang.String getEmploymentTelephoneExtNo() {
        return employmentTelephoneExtNo;
    }


    /**
     * Sets the employmentTelephoneExtNo value for this InstantIndividual.
     * 
     * @param employmentTelephoneExtNo
     */
    public void setEmploymentTelephoneExtNo(java.lang.String employmentTelephoneExtNo) {
        this.employmentTelephoneExtNo = employmentTelephoneExtNo;
    }


    /**
     * Gets the employmentTelephoneNo value for this InstantIndividual.
     * 
     * @return employmentTelephoneNo
     */
    public java.lang.String getEmploymentTelephoneNo() {
        return employmentTelephoneNo;
    }


    /**
     * Sets the employmentTelephoneNo value for this InstantIndividual.
     * 
     * @param employmentTelephoneNo
     */
    public void setEmploymentTelephoneNo(java.lang.String employmentTelephoneNo) {
        this.employmentTelephoneNo = employmentTelephoneNo;
    }


    /**
     * Gets the employmentYear value for this InstantIndividual.
     * 
     * @return employmentYear
     */
    public java.lang.String getEmploymentYear() {
        return employmentYear;
    }


    /**
     * Sets the employmentYear value for this InstantIndividual.
     * 
     * @param employmentYear
     */
    public void setEmploymentYear(java.lang.String employmentYear) {
        this.employmentYear = employmentYear;
    }


    /**
     * Gets the excludeIncomeDebtFlag value for this InstantIndividual.
     * 
     * @return excludeIncomeDebtFlag
     */
    public java.lang.String getExcludeIncomeDebtFlag() {
        return excludeIncomeDebtFlag;
    }


    /**
     * Sets the excludeIncomeDebtFlag value for this InstantIndividual.
     * 
     * @param excludeIncomeDebtFlag
     */
    public void setExcludeIncomeDebtFlag(java.lang.String excludeIncomeDebtFlag) {
        this.excludeIncomeDebtFlag = excludeIncomeDebtFlag;
    }


    /**
     * Gets the existingSavingTDAccount value for this InstantIndividual.
     * 
     * @return existingSavingTDAccount
     */
    public java.lang.String getExistingSavingTDAccount() {
        return existingSavingTDAccount;
    }


    /**
     * Sets the existingSavingTDAccount value for this InstantIndividual.
     * 
     * @param existingSavingTDAccount
     */
    public void setExistingSavingTDAccount(java.lang.String existingSavingTDAccount) {
        this.existingSavingTDAccount = existingSavingTDAccount;
    }


    /**
     * Gets the expiryDate value for this InstantIndividual.
     * 
     * @return expiryDate
     */
    public java.util.Calendar getExpiryDate() {
        return expiryDate;
    }


    /**
     * Sets the expiryDate value for this InstantIndividual.
     * 
     * @param expiryDate
     */
    public void setExpiryDate(java.util.Calendar expiryDate) {
        this.expiryDate = expiryDate;
    }


    /**
     * Gets the extTelephoneNo value for this InstantIndividual.
     * 
     * @return extTelephoneNo
     */
    public java.lang.String getExtTelephoneNo() {
        return extTelephoneNo;
    }


    /**
     * Sets the extTelephoneNo value for this InstantIndividual.
     * 
     * @param extTelephoneNo
     */
    public void setExtTelephoneNo(java.lang.String extTelephoneNo) {
        this.extTelephoneNo = extTelephoneNo;
    }


    /**
     * Gets the fixNetAsset value for this InstantIndividual.
     * 
     * @return fixNetAsset
     */
    public java.lang.String getFixNetAsset() {
        return fixNetAsset;
    }


    /**
     * Sets the fixNetAsset value for this InstantIndividual.
     * 
     * @param fixNetAsset
     */
    public void setFixNetAsset(java.lang.String fixNetAsset) {
        this.fixNetAsset = fixNetAsset;
    }


    /**
     * Gets the gender value for this InstantIndividual.
     * 
     * @return gender
     */
    public java.lang.String getGender() {
        return gender;
    }


    /**
     * Sets the gender value for this InstantIndividual.
     * 
     * @param gender
     */
    public void setGender(java.lang.String gender) {
        this.gender = gender;
    }


    /**
     * Gets the hostCifNo value for this InstantIndividual.
     * 
     * @return hostCifNo
     */
    public java.lang.String getHostCifNo() {
        return hostCifNo;
    }


    /**
     * Sets the hostCifNo value for this InstantIndividual.
     * 
     * @param hostCifNo
     */
    public void setHostCifNo(java.lang.String hostCifNo) {
        this.hostCifNo = hostCifNo;
    }


    /**
     * Gets the idIssueCtry1 value for this InstantIndividual.
     * 
     * @return idIssueCtry1
     */
    public java.lang.String getIdIssueCtry1() {
        return idIssueCtry1;
    }


    /**
     * Sets the idIssueCtry1 value for this InstantIndividual.
     * 
     * @param idIssueCtry1
     */
    public void setIdIssueCtry1(java.lang.String idIssueCtry1) {
        this.idIssueCtry1 = idIssueCtry1;
    }


    /**
     * Gets the idNo1 value for this InstantIndividual.
     * 
     * @return idNo1
     */
    public java.lang.String getIdNo1() {
        return idNo1;
    }


    /**
     * Sets the idNo1 value for this InstantIndividual.
     * 
     * @param idNo1
     */
    public void setIdNo1(java.lang.String idNo1) {
        this.idNo1 = idNo1;
    }


    /**
     * Gets the idType1 value for this InstantIndividual.
     * 
     * @return idType1
     */
    public java.lang.String getIdType1() {
        return idType1;
    }


    /**
     * Sets the idType1 value for this InstantIndividual.
     * 
     * @param idType1
     */
    public void setIdType1(java.lang.String idType1) {
        this.idType1 = idType1;
    }


    /**
     * Gets the idenPresentToBank value for this InstantIndividual.
     * 
     * @return idenPresentToBank
     */
    public java.lang.String getIdenPresentToBank() {
        return idenPresentToBank;
    }


    /**
     * Sets the idenPresentToBank value for this InstantIndividual.
     * 
     * @param idenPresentToBank
     */
    public void setIdenPresentToBank(java.lang.String idenPresentToBank) {
        this.idenPresentToBank = idenPresentToBank;
    }


    /**
     * Gets the inTotalExtraIncome value for this InstantIndividual.
     * 
     * @return inTotalExtraIncome
     */
    public java.math.BigDecimal getInTotalExtraIncome() {
        return inTotalExtraIncome;
    }


    /**
     * Sets the inTotalExtraIncome value for this InstantIndividual.
     * 
     * @param inTotalExtraIncome
     */
    public void setInTotalExtraIncome(java.math.BigDecimal inTotalExtraIncome) {
        this.inTotalExtraIncome = inTotalExtraIncome;
    }


    /**
     * Gets the inTotalFixedIncome value for this InstantIndividual.
     * 
     * @return inTotalFixedIncome
     */
    public java.math.BigDecimal getInTotalFixedIncome() {
        return inTotalFixedIncome;
    }


    /**
     * Sets the inTotalFixedIncome value for this InstantIndividual.
     * 
     * @param inTotalFixedIncome
     */
    public void setInTotalFixedIncome(java.math.BigDecimal inTotalFixedIncome) {
        this.inTotalFixedIncome = inTotalFixedIncome;
    }


    /**
     * Gets the inTotalIncome value for this InstantIndividual.
     * 
     * @return inTotalIncome
     */
    public java.math.BigDecimal getInTotalIncome() {
        return inTotalIncome;
    }


    /**
     * Sets the inTotalIncome value for this InstantIndividual.
     * 
     * @param inTotalIncome
     */
    public void setInTotalIncome(java.math.BigDecimal inTotalIncome) {
        this.inTotalIncome = inTotalIncome;
    }


    /**
     * Gets the inTotalOtherIncome value for this InstantIndividual.
     * 
     * @return inTotalOtherIncome
     */
    public java.math.BigDecimal getInTotalOtherIncome() {
        return inTotalOtherIncome;
    }


    /**
     * Sets the inTotalOtherIncome value for this InstantIndividual.
     * 
     * @param inTotalOtherIncome
     */
    public void setInTotalOtherIncome(java.math.BigDecimal inTotalOtherIncome) {
        this.inTotalOtherIncome = inTotalOtherIncome;
    }


    /**
     * Gets the includeIncomeFlag value for this InstantIndividual.
     * 
     * @return includeIncomeFlag
     */
    public java.lang.String getIncludeIncomeFlag() {
        return includeIncomeFlag;
    }


    /**
     * Sets the includeIncomeFlag value for this InstantIndividual.
     * 
     * @param includeIncomeFlag
     */
    public void setIncludeIncomeFlag(java.lang.String includeIncomeFlag) {
        this.includeIncomeFlag = includeIncomeFlag;
    }


    /**
     * Gets the incomeBankAccoutNumber value for this InstantIndividual.
     * 
     * @return incomeBankAccoutNumber
     */
    public java.lang.String getIncomeBankAccoutNumber() {
        return incomeBankAccoutNumber;
    }


    /**
     * Sets the incomeBankAccoutNumber value for this InstantIndividual.
     * 
     * @param incomeBankAccoutNumber
     */
    public void setIncomeBankAccoutNumber(java.lang.String incomeBankAccoutNumber) {
        this.incomeBankAccoutNumber = incomeBankAccoutNumber;
    }


    /**
     * Gets the incomeBankName value for this InstantIndividual.
     * 
     * @return incomeBankName
     */
    public java.lang.String getIncomeBankName() {
        return incomeBankName;
    }


    /**
     * Sets the incomeBankName value for this InstantIndividual.
     * 
     * @param incomeBankName
     */
    public void setIncomeBankName(java.lang.String incomeBankName) {
        this.incomeBankName = incomeBankName;
    }


    /**
     * Gets the incomeBasicSalary value for this InstantIndividual.
     * 
     * @return incomeBasicSalary
     */
    public java.math.BigDecimal getIncomeBasicSalary() {
        return incomeBasicSalary;
    }


    /**
     * Sets the incomeBasicSalary value for this InstantIndividual.
     * 
     * @param incomeBasicSalary
     */
    public void setIncomeBasicSalary(java.math.BigDecimal incomeBasicSalary) {
        this.incomeBasicSalary = incomeBasicSalary;
    }


    /**
     * Gets the incomeDeclared value for this InstantIndividual.
     * 
     * @return incomeDeclared
     */
    public java.math.BigDecimal getIncomeDeclared() {
        return incomeDeclared;
    }


    /**
     * Sets the incomeDeclared value for this InstantIndividual.
     * 
     * @param incomeDeclared
     */
    public void setIncomeDeclared(java.math.BigDecimal incomeDeclared) {
        this.incomeDeclared = incomeDeclared;
    }


    /**
     * Gets the incomeExtraOther value for this InstantIndividual.
     * 
     * @return incomeExtraOther
     */
    public java.math.BigDecimal getIncomeExtraOther() {
        return incomeExtraOther;
    }


    /**
     * Sets the incomeExtraOther value for this InstantIndividual.
     * 
     * @param incomeExtraOther
     */
    public void setIncomeExtraOther(java.math.BigDecimal incomeExtraOther) {
        this.incomeExtraOther = incomeExtraOther;
    }


    /**
     * Gets the incomeExtraRental value for this InstantIndividual.
     * 
     * @return incomeExtraRental
     */
    public java.math.BigDecimal getIncomeExtraRental() {
        return incomeExtraRental;
    }


    /**
     * Sets the incomeExtraRental value for this InstantIndividual.
     * 
     * @param incomeExtraRental
     */
    public void setIncomeExtraRental(java.math.BigDecimal incomeExtraRental) {
        this.incomeExtraRental = incomeExtraRental;
    }


    /**
     * Gets the incomeInfoSavedFlag value for this InstantIndividual.
     * 
     * @return incomeInfoSavedFlag
     */
    public java.lang.String getIncomeInfoSavedFlag() {
        return incomeInfoSavedFlag;
    }


    /**
     * Sets the incomeInfoSavedFlag value for this InstantIndividual.
     * 
     * @param incomeInfoSavedFlag
     */
    public void setIncomeInfoSavedFlag(java.lang.String incomeInfoSavedFlag) {
        this.incomeInfoSavedFlag = incomeInfoSavedFlag;
    }


    /**
     * Gets the incomeOtherCommAverage value for this InstantIndividual.
     * 
     * @return incomeOtherCommAverage
     */
    public java.math.BigDecimal getIncomeOtherCommAverage() {
        return incomeOtherCommAverage;
    }


    /**
     * Sets the incomeOtherCommAverage value for this InstantIndividual.
     * 
     * @param incomeOtherCommAverage
     */
    public void setIncomeOtherCommAverage(java.math.BigDecimal incomeOtherCommAverage) {
        this.incomeOtherCommAverage = incomeOtherCommAverage;
    }


    /**
     * Gets the incomeOtherCommMonth1 value for this InstantIndividual.
     * 
     * @return incomeOtherCommMonth1
     */
    public java.math.BigDecimal getIncomeOtherCommMonth1() {
        return incomeOtherCommMonth1;
    }


    /**
     * Sets the incomeOtherCommMonth1 value for this InstantIndividual.
     * 
     * @param incomeOtherCommMonth1
     */
    public void setIncomeOtherCommMonth1(java.math.BigDecimal incomeOtherCommMonth1) {
        this.incomeOtherCommMonth1 = incomeOtherCommMonth1;
    }


    /**
     * Gets the incomeOtherCommMonth2 value for this InstantIndividual.
     * 
     * @return incomeOtherCommMonth2
     */
    public java.math.BigDecimal getIncomeOtherCommMonth2() {
        return incomeOtherCommMonth2;
    }


    /**
     * Sets the incomeOtherCommMonth2 value for this InstantIndividual.
     * 
     * @param incomeOtherCommMonth2
     */
    public void setIncomeOtherCommMonth2(java.math.BigDecimal incomeOtherCommMonth2) {
        this.incomeOtherCommMonth2 = incomeOtherCommMonth2;
    }


    /**
     * Gets the incomeOtherCommMonth3 value for this InstantIndividual.
     * 
     * @return incomeOtherCommMonth3
     */
    public java.math.BigDecimal getIncomeOtherCommMonth3() {
        return incomeOtherCommMonth3;
    }


    /**
     * Sets the incomeOtherCommMonth3 value for this InstantIndividual.
     * 
     * @param incomeOtherCommMonth3
     */
    public void setIncomeOtherCommMonth3(java.math.BigDecimal incomeOtherCommMonth3) {
        this.incomeOtherCommMonth3 = incomeOtherCommMonth3;
    }


    /**
     * Gets the incomeOtherDiemAverage value for this InstantIndividual.
     * 
     * @return incomeOtherDiemAverage
     */
    public java.math.BigDecimal getIncomeOtherDiemAverage() {
        return incomeOtherDiemAverage;
    }


    /**
     * Sets the incomeOtherDiemAverage value for this InstantIndividual.
     * 
     * @param incomeOtherDiemAverage
     */
    public void setIncomeOtherDiemAverage(java.math.BigDecimal incomeOtherDiemAverage) {
        this.incomeOtherDiemAverage = incomeOtherDiemAverage;
    }


    /**
     * Gets the incomeOtherDiemMonth1 value for this InstantIndividual.
     * 
     * @return incomeOtherDiemMonth1
     */
    public java.math.BigDecimal getIncomeOtherDiemMonth1() {
        return incomeOtherDiemMonth1;
    }


    /**
     * Sets the incomeOtherDiemMonth1 value for this InstantIndividual.
     * 
     * @param incomeOtherDiemMonth1
     */
    public void setIncomeOtherDiemMonth1(java.math.BigDecimal incomeOtherDiemMonth1) {
        this.incomeOtherDiemMonth1 = incomeOtherDiemMonth1;
    }


    /**
     * Gets the incomeOtherDiemMonth2 value for this InstantIndividual.
     * 
     * @return incomeOtherDiemMonth2
     */
    public java.math.BigDecimal getIncomeOtherDiemMonth2() {
        return incomeOtherDiemMonth2;
    }


    /**
     * Sets the incomeOtherDiemMonth2 value for this InstantIndividual.
     * 
     * @param incomeOtherDiemMonth2
     */
    public void setIncomeOtherDiemMonth2(java.math.BigDecimal incomeOtherDiemMonth2) {
        this.incomeOtherDiemMonth2 = incomeOtherDiemMonth2;
    }


    /**
     * Gets the incomeOtherDiemMonth3 value for this InstantIndividual.
     * 
     * @return incomeOtherDiemMonth3
     */
    public java.math.BigDecimal getIncomeOtherDiemMonth3() {
        return incomeOtherDiemMonth3;
    }


    /**
     * Sets the incomeOtherDiemMonth3 value for this InstantIndividual.
     * 
     * @param incomeOtherDiemMonth3
     */
    public void setIncomeOtherDiemMonth3(java.math.BigDecimal incomeOtherDiemMonth3) {
        this.incomeOtherDiemMonth3 = incomeOtherDiemMonth3;
    }


    /**
     * Gets the incomeOtherFlightAverage value for this InstantIndividual.
     * 
     * @return incomeOtherFlightAverage
     */
    public java.math.BigDecimal getIncomeOtherFlightAverage() {
        return incomeOtherFlightAverage;
    }


    /**
     * Sets the incomeOtherFlightAverage value for this InstantIndividual.
     * 
     * @param incomeOtherFlightAverage
     */
    public void setIncomeOtherFlightAverage(java.math.BigDecimal incomeOtherFlightAverage) {
        this.incomeOtherFlightAverage = incomeOtherFlightAverage;
    }


    /**
     * Gets the incomeOtherFlightMonth1 value for this InstantIndividual.
     * 
     * @return incomeOtherFlightMonth1
     */
    public java.math.BigDecimal getIncomeOtherFlightMonth1() {
        return incomeOtherFlightMonth1;
    }


    /**
     * Sets the incomeOtherFlightMonth1 value for this InstantIndividual.
     * 
     * @param incomeOtherFlightMonth1
     */
    public void setIncomeOtherFlightMonth1(java.math.BigDecimal incomeOtherFlightMonth1) {
        this.incomeOtherFlightMonth1 = incomeOtherFlightMonth1;
    }


    /**
     * Gets the incomeOtherFlightMonth2 value for this InstantIndividual.
     * 
     * @return incomeOtherFlightMonth2
     */
    public java.math.BigDecimal getIncomeOtherFlightMonth2() {
        return incomeOtherFlightMonth2;
    }


    /**
     * Sets the incomeOtherFlightMonth2 value for this InstantIndividual.
     * 
     * @param incomeOtherFlightMonth2
     */
    public void setIncomeOtherFlightMonth2(java.math.BigDecimal incomeOtherFlightMonth2) {
        this.incomeOtherFlightMonth2 = incomeOtherFlightMonth2;
    }


    /**
     * Gets the incomeOtherFlightMonth3 value for this InstantIndividual.
     * 
     * @return incomeOtherFlightMonth3
     */
    public java.math.BigDecimal getIncomeOtherFlightMonth3() {
        return incomeOtherFlightMonth3;
    }


    /**
     * Sets the incomeOtherFlightMonth3 value for this InstantIndividual.
     * 
     * @param incomeOtherFlightMonth3
     */
    public void setIncomeOtherFlightMonth3(java.math.BigDecimal incomeOtherFlightMonth3) {
        this.incomeOtherFlightMonth3 = incomeOtherFlightMonth3;
    }


    /**
     * Gets the incomeOtherIncome value for this InstantIndividual.
     * 
     * @return incomeOtherIncome
     */
    public java.math.BigDecimal getIncomeOtherIncome() {
        return incomeOtherIncome;
    }


    /**
     * Sets the incomeOtherIncome value for this InstantIndividual.
     * 
     * @param incomeOtherIncome
     */
    public void setIncomeOtherIncome(java.math.BigDecimal incomeOtherIncome) {
        this.incomeOtherIncome = incomeOtherIncome;
    }


    /**
     * Gets the incomeOtherOTAverage value for this InstantIndividual.
     * 
     * @return incomeOtherOTAverage
     */
    public java.math.BigDecimal getIncomeOtherOTAverage() {
        return incomeOtherOTAverage;
    }


    /**
     * Sets the incomeOtherOTAverage value for this InstantIndividual.
     * 
     * @param incomeOtherOTAverage
     */
    public void setIncomeOtherOTAverage(java.math.BigDecimal incomeOtherOTAverage) {
        this.incomeOtherOTAverage = incomeOtherOTAverage;
    }


    /**
     * Gets the incomeOtherOTMonth1 value for this InstantIndividual.
     * 
     * @return incomeOtherOTMonth1
     */
    public java.math.BigDecimal getIncomeOtherOTMonth1() {
        return incomeOtherOTMonth1;
    }


    /**
     * Sets the incomeOtherOTMonth1 value for this InstantIndividual.
     * 
     * @param incomeOtherOTMonth1
     */
    public void setIncomeOtherOTMonth1(java.math.BigDecimal incomeOtherOTMonth1) {
        this.incomeOtherOTMonth1 = incomeOtherOTMonth1;
    }


    /**
     * Gets the incomeOtherOTMonth2 value for this InstantIndividual.
     * 
     * @return incomeOtherOTMonth2
     */
    public java.math.BigDecimal getIncomeOtherOTMonth2() {
        return incomeOtherOTMonth2;
    }


    /**
     * Sets the incomeOtherOTMonth2 value for this InstantIndividual.
     * 
     * @param incomeOtherOTMonth2
     */
    public void setIncomeOtherOTMonth2(java.math.BigDecimal incomeOtherOTMonth2) {
        this.incomeOtherOTMonth2 = incomeOtherOTMonth2;
    }


    /**
     * Gets the incomeOtherOTMonth3 value for this InstantIndividual.
     * 
     * @return incomeOtherOTMonth3
     */
    public java.math.BigDecimal getIncomeOtherOTMonth3() {
        return incomeOtherOTMonth3;
    }


    /**
     * Sets the incomeOtherOTMonth3 value for this InstantIndividual.
     * 
     * @param incomeOtherOTMonth3
     */
    public void setIncomeOtherOTMonth3(java.math.BigDecimal incomeOtherOTMonth3) {
        this.incomeOtherOTMonth3 = incomeOtherOTMonth3;
    }


    /**
     * Gets the incomeSharedHolderPercent value for this InstantIndividual.
     * 
     * @return incomeSharedHolderPercent
     */
    public java.math.BigDecimal getIncomeSharedHolderPercent() {
        return incomeSharedHolderPercent;
    }


    /**
     * Sets the incomeSharedHolderPercent value for this InstantIndividual.
     * 
     * @param incomeSharedHolderPercent
     */
    public void setIncomeSharedHolderPercent(java.math.BigDecimal incomeSharedHolderPercent) {
        this.incomeSharedHolderPercent = incomeSharedHolderPercent;
    }


    /**
     * Gets the incomeType value for this InstantIndividual.
     * 
     * @return incomeType
     */
    public java.lang.String getIncomeType() {
        return incomeType;
    }


    /**
     * Sets the incomeType value for this InstantIndividual.
     * 
     * @param incomeType
     */
    public void setIncomeType(java.lang.String incomeType) {
        this.incomeType = incomeType;
    }


    /**
     * Gets the incometotalLastMthCreditAcct1 value for this InstantIndividual.
     * 
     * @return incometotalLastMthCreditAcct1
     */
    public java.math.BigDecimal getIncometotalLastMthCreditAcct1() {
        return incometotalLastMthCreditAcct1;
    }


    /**
     * Sets the incometotalLastMthCreditAcct1 value for this InstantIndividual.
     * 
     * @param incometotalLastMthCreditAcct1
     */
    public void setIncometotalLastMthCreditAcct1(java.math.BigDecimal incometotalLastMthCreditAcct1) {
        this.incometotalLastMthCreditAcct1 = incometotalLastMthCreditAcct1;
    }


    /**
     * Gets the incometotalLastMthCreditAcct2 value for this InstantIndividual.
     * 
     * @return incometotalLastMthCreditAcct2
     */
    public java.math.BigDecimal getIncometotalLastMthCreditAcct2() {
        return incometotalLastMthCreditAcct2;
    }


    /**
     * Sets the incometotalLastMthCreditAcct2 value for this InstantIndividual.
     * 
     * @param incometotalLastMthCreditAcct2
     */
    public void setIncometotalLastMthCreditAcct2(java.math.BigDecimal incometotalLastMthCreditAcct2) {
        this.incometotalLastMthCreditAcct2 = incometotalLastMthCreditAcct2;
    }


    /**
     * Gets the insiderCode value for this InstantIndividual.
     * 
     * @return insiderCode
     */
    public java.lang.String getInsiderCode() {
        return insiderCode;
    }


    /**
     * Sets the insiderCode value for this InstantIndividual.
     * 
     * @param insiderCode
     */
    public void setInsiderCode(java.lang.String insiderCode) {
        this.insiderCode = insiderCode;
    }


    /**
     * Gets the issuedDate value for this InstantIndividual.
     * 
     * @return issuedDate
     */
    public java.util.Calendar getIssuedDate() {
        return issuedDate;
    }


    /**
     * Sets the issuedDate value for this InstantIndividual.
     * 
     * @param issuedDate
     */
    public void setIssuedDate(java.util.Calendar issuedDate) {
        this.issuedDate = issuedDate;
    }


    /**
     * Gets the lifeTimeFlag value for this InstantIndividual.
     * 
     * @return lifeTimeFlag
     */
    public java.lang.String getLifeTimeFlag() {
        return lifeTimeFlag;
    }


    /**
     * Sets the lifeTimeFlag value for this InstantIndividual.
     * 
     * @param lifeTimeFlag
     */
    public void setLifeTimeFlag(java.lang.String lifeTimeFlag) {
        this.lifeTimeFlag = lifeTimeFlag;
    }


    /**
     * Gets the mailingPreference value for this InstantIndividual.
     * 
     * @return mailingPreference
     */
    public java.lang.String getMailingPreference() {
        return mailingPreference;
    }


    /**
     * Sets the mailingPreference value for this InstantIndividual.
     * 
     * @param mailingPreference
     */
    public void setMailingPreference(java.lang.String mailingPreference) {
        this.mailingPreference = mailingPreference;
    }


    /**
     * Gets the maritalStatus value for this InstantIndividual.
     * 
     * @return maritalStatus
     */
    public java.lang.String getMaritalStatus() {
        return maritalStatus;
    }


    /**
     * Sets the maritalStatus value for this InstantIndividual.
     * 
     * @param maritalStatus
     */
    public void setMaritalStatus(java.lang.String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }


    /**
     * Gets the mobileNo value for this InstantIndividual.
     * 
     * @return mobileNo
     */
    public java.lang.String getMobileNo() {
        return mobileNo;
    }


    /**
     * Sets the mobileNo value for this InstantIndividual.
     * 
     * @param mobileNo
     */
    public void setMobileNo(java.lang.String mobileNo) {
        this.mobileNo = mobileNo;
    }


    /**
     * Gets the nameLine1 value for this InstantIndividual.
     * 
     * @return nameLine1
     */
    public java.lang.String getNameLine1() {
        return nameLine1;
    }


    /**
     * Sets the nameLine1 value for this InstantIndividual.
     * 
     * @param nameLine1
     */
    public void setNameLine1(java.lang.String nameLine1) {
        this.nameLine1 = nameLine1;
    }


    /**
     * Gets the nameLine2 value for this InstantIndividual.
     * 
     * @return nameLine2
     */
    public java.lang.String getNameLine2() {
        return nameLine2;
    }


    /**
     * Sets the nameLine2 value for this InstantIndividual.
     * 
     * @param nameLine2
     */
    public void setNameLine2(java.lang.String nameLine2) {
        this.nameLine2 = nameLine2;
    }


    /**
     * Gets the nationality value for this InstantIndividual.
     * 
     * @return nationality
     */
    public java.lang.String getNationality() {
        return nationality;
    }


    /**
     * Sets the nationality value for this InstantIndividual.
     * 
     * @param nationality
     */
    public void setNationality(java.lang.String nationality) {
        this.nationality = nationality;
    }


    /**
     * Gets the nationality2 value for this InstantIndividual.
     * 
     * @return nationality2
     */
    public java.lang.String getNationality2() {
        return nationality2;
    }


    /**
     * Sets the nationality2 value for this InstantIndividual.
     * 
     * @param nationality2
     */
    public void setNationality2(java.lang.String nationality2) {
        this.nationality2 = nationality2;
    }


    /**
     * Gets the ncbConsentFlag value for this InstantIndividual.
     * 
     * @return ncbConsentFlag
     */
    public java.lang.String getNcbConsentFlag() {
        return ncbConsentFlag;
    }


    /**
     * Sets the ncbConsentFlag value for this InstantIndividual.
     * 
     * @param ncbConsentFlag
     */
    public void setNcbConsentFlag(java.lang.String ncbConsentFlag) {
        this.ncbConsentFlag = ncbConsentFlag;
    }


    /**
     * Gets the noOfDependent value for this InstantIndividual.
     * 
     * @return noOfDependent
     */
    public java.lang.String getNoOfDependent() {
        return noOfDependent;
    }


    /**
     * Sets the noOfDependent value for this InstantIndividual.
     * 
     * @param noOfDependent
     */
    public void setNoOfDependent(java.lang.String noOfDependent) {
        this.noOfDependent = noOfDependent;
    }


    /**
     * Gets the originatedUnit value for this InstantIndividual.
     * 
     * @return originatedUnit
     */
    public java.lang.String getOriginatedUnit() {
        return originatedUnit;
    }


    /**
     * Sets the originatedUnit value for this InstantIndividual.
     * 
     * @param originatedUnit
     */
    public void setOriginatedUnit(java.lang.String originatedUnit) {
        this.originatedUnit = originatedUnit;
    }


    /**
     * Gets the personalInfoSavedFlag value for this InstantIndividual.
     * 
     * @return personalInfoSavedFlag
     */
    public java.lang.String getPersonalInfoSavedFlag() {
        return personalInfoSavedFlag;
    }


    /**
     * Sets the personalInfoSavedFlag value for this InstantIndividual.
     * 
     * @param personalInfoSavedFlag
     */
    public void setPersonalInfoSavedFlag(java.lang.String personalInfoSavedFlag) {
        this.personalInfoSavedFlag = personalInfoSavedFlag;
    }


    /**
     * Gets the preScoreCash value for this InstantIndividual.
     * 
     * @return preScoreCash
     */
    public java.math.BigDecimal getPreScoreCash() {
        return preScoreCash;
    }


    /**
     * Sets the preScoreCash value for this InstantIndividual.
     * 
     * @param preScoreCash
     */
    public void setPreScoreCash(java.math.BigDecimal preScoreCash) {
        this.preScoreCash = preScoreCash;
    }


    /**
     * Gets the professionalCode value for this InstantIndividual.
     * 
     * @return professionalCode
     */
    public java.lang.String getProfessionalCode() {
        return professionalCode;
    }


    /**
     * Sets the professionalCode value for this InstantIndividual.
     * 
     * @param professionalCode
     */
    public void setProfessionalCode(java.lang.String professionalCode) {
        this.professionalCode = professionalCode;
    }


    /**
     * Gets the race value for this InstantIndividual.
     * 
     * @return race
     */
    public java.lang.String getRace() {
        return race;
    }


    /**
     * Sets the race value for this InstantIndividual.
     * 
     * @param race
     */
    public void setRace(java.lang.String race) {
        this.race = race;
    }


    /**
     * Gets the registerExtTelephoneNo value for this InstantIndividual.
     * 
     * @return registerExtTelephoneNo
     */
    public java.lang.String getRegisterExtTelephoneNo() {
        return registerExtTelephoneNo;
    }


    /**
     * Sets the registerExtTelephoneNo value for this InstantIndividual.
     * 
     * @param registerExtTelephoneNo
     */
    public void setRegisterExtTelephoneNo(java.lang.String registerExtTelephoneNo) {
        this.registerExtTelephoneNo = registerExtTelephoneNo;
    }


    /**
     * Gets the registerTelephoneNo value for this InstantIndividual.
     * 
     * @return registerTelephoneNo
     */
    public java.lang.String getRegisterTelephoneNo() {
        return registerTelephoneNo;
    }


    /**
     * Sets the registerTelephoneNo value for this InstantIndividual.
     * 
     * @param registerTelephoneNo
     */
    public void setRegisterTelephoneNo(java.lang.String registerTelephoneNo) {
        this.registerTelephoneNo = registerTelephoneNo;
    }


    /**
     * Gets the relationWithMain value for this InstantIndividual.
     * 
     * @return relationWithMain
     */
    public java.lang.String getRelationWithMain() {
        return relationWithMain;
    }


    /**
     * Sets the relationWithMain value for this InstantIndividual.
     * 
     * @param relationWithMain
     */
    public void setRelationWithMain(java.lang.String relationWithMain) {
        this.relationWithMain = relationWithMain;
    }


    /**
     * Gets the residentFlag value for this InstantIndividual.
     * 
     * @return residentFlag
     */
    public java.lang.String getResidentFlag() {
        return residentFlag;
    }


    /**
     * Sets the residentFlag value for this InstantIndividual.
     * 
     * @param residentFlag
     */
    public void setResidentFlag(java.lang.String residentFlag) {
        this.residentFlag = residentFlag;
    }


    /**
     * Gets the residentType value for this InstantIndividual.
     * 
     * @return residentType
     */
    public java.lang.String getResidentType() {
        return residentType;
    }


    /**
     * Sets the residentType value for this InstantIndividual.
     * 
     * @param residentType
     */
    public void setResidentType(java.lang.String residentType) {
        this.residentType = residentType;
    }


    /**
     * Gets the rmOccupation value for this InstantIndividual.
     * 
     * @return rmOccupation
     */
    public java.lang.String getRmOccupation() {
        return rmOccupation;
    }


    /**
     * Sets the rmOccupation value for this InstantIndividual.
     * 
     * @param rmOccupation
     */
    public void setRmOccupation(java.lang.String rmOccupation) {
        this.rmOccupation = rmOccupation;
    }


    /**
     * Gets the rmWealthFlag value for this InstantIndividual.
     * 
     * @return rmWealthFlag
     */
    public java.lang.String getRmWealthFlag() {
        return rmWealthFlag;
    }


    /**
     * Sets the rmWealthFlag value for this InstantIndividual.
     * 
     * @param rmWealthFlag
     */
    public void setRmWealthFlag(java.lang.String rmWealthFlag) {
        this.rmWealthFlag = rmWealthFlag;
    }


    /**
     * Gets the salutationCode value for this InstantIndividual.
     * 
     * @return salutationCode
     */
    public java.lang.String getSalutationCode() {
        return salutationCode;
    }


    /**
     * Sets the salutationCode value for this InstantIndividual.
     * 
     * @param salutationCode
     */
    public void setSalutationCode(java.lang.String salutationCode) {
        this.salutationCode = salutationCode;
    }


    /**
     * Gets the sameRegisterAddressFlag value for this InstantIndividual.
     * 
     * @return sameRegisterAddressFlag
     */
    public java.lang.String getSameRegisterAddressFlag() {
        return sameRegisterAddressFlag;
    }


    /**
     * Sets the sameRegisterAddressFlag value for this InstantIndividual.
     * 
     * @param sameRegisterAddressFlag
     */
    public void setSameRegisterAddressFlag(java.lang.String sameRegisterAddressFlag) {
        this.sameRegisterAddressFlag = sameRegisterAddressFlag;
    }


    /**
     * Gets the selfEmployInTotalIncome value for this InstantIndividual.
     * 
     * @return selfEmployInTotalIncome
     */
    public java.math.BigDecimal getSelfEmployInTotalIncome() {
        return selfEmployInTotalIncome;
    }


    /**
     * Sets the selfEmployInTotalIncome value for this InstantIndividual.
     * 
     * @param selfEmployInTotalIncome
     */
    public void setSelfEmployInTotalIncome(java.math.BigDecimal selfEmployInTotalIncome) {
        this.selfEmployInTotalIncome = selfEmployInTotalIncome;
    }


    /**
     * Gets the selfEmployIncomeExtraOther value for this InstantIndividual.
     * 
     * @return selfEmployIncomeExtraOther
     */
    public java.math.BigDecimal getSelfEmployIncomeExtraOther() {
        return selfEmployIncomeExtraOther;
    }


    /**
     * Sets the selfEmployIncomeExtraOther value for this InstantIndividual.
     * 
     * @param selfEmployIncomeExtraOther
     */
    public void setSelfEmployIncomeExtraOther(java.math.BigDecimal selfEmployIncomeExtraOther) {
        this.selfEmployIncomeExtraOther = selfEmployIncomeExtraOther;
    }


    /**
     * Gets the selfEmployIncomeExtraRental value for this InstantIndividual.
     * 
     * @return selfEmployIncomeExtraRental
     */
    public java.math.BigDecimal getSelfEmployIncomeExtraRental() {
        return selfEmployIncomeExtraRental;
    }


    /**
     * Sets the selfEmployIncomeExtraRental value for this InstantIndividual.
     * 
     * @param selfEmployIncomeExtraRental
     */
    public void setSelfEmployIncomeExtraRental(java.math.BigDecimal selfEmployIncomeExtraRental) {
        this.selfEmployIncomeExtraRental = selfEmployIncomeExtraRental;
    }


    /**
     * Gets the selfEmployIncomeExtraTotal value for this InstantIndividual.
     * 
     * @return selfEmployIncomeExtraTotal
     */
    public java.math.BigDecimal getSelfEmployIncomeExtraTotal() {
        return selfEmployIncomeExtraTotal;
    }


    /**
     * Sets the selfEmployIncomeExtraTotal value for this InstantIndividual.
     * 
     * @param selfEmployIncomeExtraTotal
     */
    public void setSelfEmployIncomeExtraTotal(java.math.BigDecimal selfEmployIncomeExtraTotal) {
        this.selfEmployIncomeExtraTotal = selfEmployIncomeExtraTotal;
    }


    /**
     * Gets the selfEmployIncomeTotal value for this InstantIndividual.
     * 
     * @return selfEmployIncomeTotal
     */
    public java.math.BigDecimal getSelfEmployIncomeTotal() {
        return selfEmployIncomeTotal;
    }


    /**
     * Sets the selfEmployIncomeTotal value for this InstantIndividual.
     * 
     * @param selfEmployIncomeTotal
     */
    public void setSelfEmployIncomeTotal(java.math.BigDecimal selfEmployIncomeTotal) {
        this.selfEmployIncomeTotal = selfEmployIncomeTotal;
    }


    /**
     * Gets the sourceFromCountry value for this InstantIndividual.
     * 
     * @return sourceFromCountry
     */
    public java.lang.String getSourceFromCountry() {
        return sourceFromCountry;
    }


    /**
     * Sets the sourceFromCountry value for this InstantIndividual.
     * 
     * @param sourceFromCountry
     */
    public void setSourceFromCountry(java.lang.String sourceFromCountry) {
        this.sourceFromCountry = sourceFromCountry;
    }


    /**
     * Gets the sourceOfRisk value for this InstantIndividual.
     * 
     * @return sourceOfRisk
     */
    public java.lang.String getSourceOfRisk() {
        return sourceOfRisk;
    }


    /**
     * Sets the sourceOfRisk value for this InstantIndividual.
     * 
     * @param sourceOfRisk
     */
    public void setSourceOfRisk(java.lang.String sourceOfRisk) {
        this.sourceOfRisk = sourceOfRisk;
    }


    /**
     * Gets the spBirthDate value for this InstantIndividual.
     * 
     * @return spBirthDate
     */
    public java.util.Calendar getSpBirthDate() {
        return spBirthDate;
    }


    /**
     * Sets the spBirthDate value for this InstantIndividual.
     * 
     * @param spBirthDate
     */
    public void setSpBirthDate(java.util.Calendar spBirthDate) {
        this.spBirthDate = spBirthDate;
    }


    /**
     * Gets the spIdNo value for this InstantIndividual.
     * 
     * @return spIdNo
     */
    public java.lang.String getSpIdNo() {
        return spIdNo;
    }


    /**
     * Sets the spIdNo value for this InstantIndividual.
     * 
     * @param spIdNo
     */
    public void setSpIdNo(java.lang.String spIdNo) {
        this.spIdNo = spIdNo;
    }


    /**
     * Gets the spIdType value for this InstantIndividual.
     * 
     * @return spIdType
     */
    public java.lang.String getSpIdType() {
        return spIdType;
    }


    /**
     * Sets the spIdType value for this InstantIndividual.
     * 
     * @param spIdType
     */
    public void setSpIdType(java.lang.String spIdType) {
        this.spIdType = spIdType;
    }


    /**
     * Gets the spSalutationCode value for this InstantIndividual.
     * 
     * @return spSalutationCode
     */
    public java.lang.String getSpSalutationCode() {
        return spSalutationCode;
    }


    /**
     * Sets the spSalutationCode value for this InstantIndividual.
     * 
     * @param spSalutationCode
     */
    public void setSpSalutationCode(java.lang.String spSalutationCode) {
        this.spSalutationCode = spSalutationCode;
    }


    /**
     * Gets the spThaiName value for this InstantIndividual.
     * 
     * @return spThaiName
     */
    public java.lang.String getSpThaiName() {
        return spThaiName;
    }


    /**
     * Sets the spThaiName value for this InstantIndividual.
     * 
     * @param spThaiName
     */
    public void setSpThaiName(java.lang.String spThaiName) {
        this.spThaiName = spThaiName;
    }


    /**
     * Gets the spThaiSurName value for this InstantIndividual.
     * 
     * @return spThaiSurName
     */
    public java.lang.String getSpThaiSurName() {
        return spThaiSurName;
    }


    /**
     * Sets the spThaiSurName value for this InstantIndividual.
     * 
     * @param spThaiSurName
     */
    public void setSpThaiSurName(java.lang.String spThaiSurName) {
        this.spThaiSurName = spThaiSurName;
    }


    /**
     * Gets the spTitleTypeCode value for this InstantIndividual.
     * 
     * @return spTitleTypeCode
     */
    public java.lang.String getSpTitleTypeCode() {
        return spTitleTypeCode;
    }


    /**
     * Sets the spTitleTypeCode value for this InstantIndividual.
     * 
     * @param spTitleTypeCode
     */
    public void setSpTitleTypeCode(java.lang.String spTitleTypeCode) {
        this.spTitleTypeCode = spTitleTypeCode;
    }


    /**
     * Gets the telephoneNo value for this InstantIndividual.
     * 
     * @return telephoneNo
     */
    public java.lang.String getTelephoneNo() {
        return telephoneNo;
    }


    /**
     * Sets the telephoneNo value for this InstantIndividual.
     * 
     * @param telephoneNo
     */
    public void setTelephoneNo(java.lang.String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }


    /**
     * Gets the thaiName value for this InstantIndividual.
     * 
     * @return thaiName
     */
    public java.lang.String getThaiName() {
        return thaiName;
    }


    /**
     * Sets the thaiName value for this InstantIndividual.
     * 
     * @param thaiName
     */
    public void setThaiName(java.lang.String thaiName) {
        this.thaiName = thaiName;
    }


    /**
     * Gets the thaiSalutationCode value for this InstantIndividual.
     * 
     * @return thaiSalutationCode
     */
    public java.lang.String getThaiSalutationCode() {
        return thaiSalutationCode;
    }


    /**
     * Sets the thaiSalutationCode value for this InstantIndividual.
     * 
     * @param thaiSalutationCode
     */
    public void setThaiSalutationCode(java.lang.String thaiSalutationCode) {
        this.thaiSalutationCode = thaiSalutationCode;
    }


    /**
     * Gets the thaiSurName value for this InstantIndividual.
     * 
     * @return thaiSurName
     */
    public java.lang.String getThaiSurName() {
        return thaiSurName;
    }


    /**
     * Sets the thaiSurName value for this InstantIndividual.
     * 
     * @param thaiSurName
     */
    public void setThaiSurName(java.lang.String thaiSurName) {
        this.thaiSurName = thaiSurName;
    }


    /**
     * Gets the titleTypeCode value for this InstantIndividual.
     * 
     * @return titleTypeCode
     */
    public java.lang.String getTitleTypeCode() {
        return titleTypeCode;
    }


    /**
     * Sets the titleTypeCode value for this InstantIndividual.
     * 
     * @param titleTypeCode
     */
    public void setTitleTypeCode(java.lang.String titleTypeCode) {
        this.titleTypeCode = titleTypeCode;
    }


    /**
     * Gets the workingAddrCopyFrom value for this InstantIndividual.
     * 
     * @return workingAddrCopyFrom
     */
    public java.lang.String getWorkingAddrCopyFrom() {
        return workingAddrCopyFrom;
    }


    /**
     * Sets the workingAddrCopyFrom value for this InstantIndividual.
     * 
     * @param workingAddrCopyFrom
     */
    public void setWorkingAddrCopyFrom(java.lang.String workingAddrCopyFrom) {
        this.workingAddrCopyFrom = workingAddrCopyFrom;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InstantIndividual)) return false;
        InstantIndividual other = (InstantIndividual) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accounts==null && other.getAccounts()==null) || 
             (this.accounts!=null &&
              java.util.Arrays.equals(this.accounts, other.getAccounts()))) &&
            ((this.addresses==null && other.getAddresses()==null) || 
             (this.addresses!=null &&
              java.util.Arrays.equals(this.addresses, other.getAddresses()))) &&
            ((this.age==null && other.getAge()==null) || 
             (this.age!=null &&
              this.age.equals(other.getAge()))) &&
            ((this.ageMonth==null && other.getAgeMonth()==null) || 
             (this.ageMonth!=null &&
              this.ageMonth.equals(other.getAgeMonth()))) &&
            ((this.benefitPlusSegment==null && other.getBenefitPlusSegment()==null) || 
             (this.benefitPlusSegment!=null &&
              this.benefitPlusSegment.equals(other.getBenefitPlusSegment()))) &&
            ((this.birthDate==null && other.getBirthDate()==null) || 
             (this.birthDate!=null &&
              this.birthDate.equals(other.getBirthDate()))) &&
            ((this.birthPlace==null && other.getBirthPlace()==null) || 
             (this.birthPlace!=null &&
              this.birthPlace.equals(other.getBirthPlace()))) &&
            ((this.bonusYearly==null && other.getBonusYearly()==null) || 
             (this.bonusYearly!=null &&
              this.bonusYearly.equals(other.getBonusYearly()))) &&
            ((this.businessSubType==null && other.getBusinessSubType()==null) || 
             (this.businessSubType!=null &&
              this.businessSubType.equals(other.getBusinessSubType()))) &&
            ((this.businessType==null && other.getBusinessType()==null) || 
             (this.businessType!=null &&
              this.businessType.equals(other.getBusinessType()))) &&
            ((this.caId==null && other.getCaId()==null) || 
             (this.caId!=null &&
              this.caId.equals(other.getCaId()))) &&
            ((this.cifId==null && other.getCifId()==null) || 
             (this.cifId!=null &&
              this.cifId.equals(other.getCifId()))) &&
            ((this.cifRelCode==null && other.getCifRelCode()==null) || 
             (this.cifRelCode!=null &&
              this.cifRelCode.equals(other.getCifRelCode()))) &&
            ((this.companyType==null && other.getCompanyType()==null) || 
             (this.companyType!=null &&
              this.companyType.equals(other.getCompanyType()))) &&
            ((this.contractEmpEndDate==null && other.getContractEmpEndDate()==null) || 
             (this.contractEmpEndDate!=null &&
              this.contractEmpEndDate.equals(other.getContractEmpEndDate()))) &&
            ((this.contractEmpStartDate==null && other.getContractEmpStartDate()==null) || 
             (this.contractEmpStartDate!=null &&
              this.contractEmpStartDate.equals(other.getContractEmpStartDate()))) &&
            ((this.contractEmployedFlag==null && other.getContractEmployedFlag()==null) || 
             (this.contractEmployedFlag!=null &&
              this.contractEmployedFlag.equals(other.getContractEmployedFlag()))) &&
            ((this.countryOfRegAddr==null && other.getCountryOfRegAddr()==null) || 
             (this.countryOfRegAddr!=null &&
              this.countryOfRegAddr.equals(other.getCountryOfRegAddr()))) &&
            ((this.creditCards==null && other.getCreditCards()==null) || 
             (this.creditCards!=null &&
              java.util.Arrays.equals(this.creditCards, other.getCreditCards()))) &&
            ((this.currentEmployerCode==null && other.getCurrentEmployerCode()==null) || 
             (this.currentEmployerCode!=null &&
              this.currentEmployerCode.equals(other.getCurrentEmployerCode()))) &&
            ((this.currentJobTitle==null && other.getCurrentJobTitle()==null) || 
             (this.currentJobTitle!=null &&
              this.currentJobTitle.equals(other.getCurrentJobTitle()))) &&
            ((this.custContactTime==null && other.getCustContactTime()==null) || 
             (this.custContactTime!=null &&
              this.custContactTime.equals(other.getCustContactTime()))) &&
            ((this.customerLevel==null && other.getCustomerLevel()==null) || 
             (this.customerLevel!=null &&
              this.customerLevel.equals(other.getCustomerLevel()))) &&
            ((this.customerRiskRM==null && other.getCustomerRiskRM()==null) || 
             (this.customerRiskRM!=null &&
              this.customerRiskRM.equals(other.getCustomerRiskRM()))) &&
            ((this.customerType==null && other.getCustomerType()==null) || 
             (this.customerType!=null &&
              this.customerType.equals(other.getCustomerType()))) &&
            ((this.discloseCustInfoFlag==null && other.getDiscloseCustInfoFlag()==null) || 
             (this.discloseCustInfoFlag!=null &&
              this.discloseCustInfoFlag.equals(other.getDiscloseCustInfoFlag()))) &&
            ((this.educationLevel==null && other.getEducationLevel()==null) || 
             (this.educationLevel!=null &&
              this.educationLevel.equals(other.getEducationLevel()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.emailStatementFlag==null && other.getEmailStatementFlag()==null) || 
             (this.emailStatementFlag!=null &&
              this.emailStatementFlag.equals(other.getEmailStatementFlag()))) &&
            ((this.employmentBizNature==null && other.getEmploymentBizNature()==null) || 
             (this.employmentBizNature!=null &&
              this.employmentBizNature.equals(other.getEmploymentBizNature()))) &&
            ((this.employmentDept==null && other.getEmploymentDept()==null) || 
             (this.employmentDept!=null &&
              this.employmentDept.equals(other.getEmploymentDept()))) &&
            ((this.employmentFaxNo==null && other.getEmploymentFaxNo()==null) || 
             (this.employmentFaxNo!=null &&
              this.employmentFaxNo.equals(other.getEmploymentFaxNo()))) &&
            ((this.employmentFinalTotalIncome==null && other.getEmploymentFinalTotalIncome()==null) || 
             (this.employmentFinalTotalIncome!=null &&
              this.employmentFinalTotalIncome.equals(other.getEmploymentFinalTotalIncome()))) &&
            ((this.employmentInfoSavedFlag==null && other.getEmploymentInfoSavedFlag()==null) || 
             (this.employmentInfoSavedFlag!=null &&
              this.employmentInfoSavedFlag.equals(other.getEmploymentInfoSavedFlag()))) &&
            ((this.employmentMonth==null && other.getEmploymentMonth()==null) || 
             (this.employmentMonth!=null &&
              this.employmentMonth.equals(other.getEmploymentMonth()))) &&
            ((this.employmentName==null && other.getEmploymentName()==null) || 
             (this.employmentName!=null &&
              this.employmentName.equals(other.getEmploymentName()))) &&
            ((this.employmentOccupation==null && other.getEmploymentOccupation()==null) || 
             (this.employmentOccupation!=null &&
              this.employmentOccupation.equals(other.getEmploymentOccupation()))) &&
            ((this.employmentProvince==null && other.getEmploymentProvince()==null) || 
             (this.employmentProvince!=null &&
              this.employmentProvince.equals(other.getEmploymentProvince()))) &&
            ((this.employmentStaffId==null && other.getEmploymentStaffId()==null) || 
             (this.employmentStaffId!=null &&
              this.employmentStaffId.equals(other.getEmploymentStaffId()))) &&
            ((this.employmentStatus==null && other.getEmploymentStatus()==null) || 
             (this.employmentStatus!=null &&
              this.employmentStatus.equals(other.getEmploymentStatus()))) &&
            ((this.employmentTelephoneDirectNo==null && other.getEmploymentTelephoneDirectNo()==null) || 
             (this.employmentTelephoneDirectNo!=null &&
              this.employmentTelephoneDirectNo.equals(other.getEmploymentTelephoneDirectNo()))) &&
            ((this.employmentTelephoneExtNo==null && other.getEmploymentTelephoneExtNo()==null) || 
             (this.employmentTelephoneExtNo!=null &&
              this.employmentTelephoneExtNo.equals(other.getEmploymentTelephoneExtNo()))) &&
            ((this.employmentTelephoneNo==null && other.getEmploymentTelephoneNo()==null) || 
             (this.employmentTelephoneNo!=null &&
              this.employmentTelephoneNo.equals(other.getEmploymentTelephoneNo()))) &&
            ((this.employmentYear==null && other.getEmploymentYear()==null) || 
             (this.employmentYear!=null &&
              this.employmentYear.equals(other.getEmploymentYear()))) &&
            ((this.excludeIncomeDebtFlag==null && other.getExcludeIncomeDebtFlag()==null) || 
             (this.excludeIncomeDebtFlag!=null &&
              this.excludeIncomeDebtFlag.equals(other.getExcludeIncomeDebtFlag()))) &&
            ((this.existingSavingTDAccount==null && other.getExistingSavingTDAccount()==null) || 
             (this.existingSavingTDAccount!=null &&
              this.existingSavingTDAccount.equals(other.getExistingSavingTDAccount()))) &&
            ((this.expiryDate==null && other.getExpiryDate()==null) || 
             (this.expiryDate!=null &&
              this.expiryDate.equals(other.getExpiryDate()))) &&
            ((this.extTelephoneNo==null && other.getExtTelephoneNo()==null) || 
             (this.extTelephoneNo!=null &&
              this.extTelephoneNo.equals(other.getExtTelephoneNo()))) &&
            ((this.fixNetAsset==null && other.getFixNetAsset()==null) || 
             (this.fixNetAsset!=null &&
              this.fixNetAsset.equals(other.getFixNetAsset()))) &&
            ((this.gender==null && other.getGender()==null) || 
             (this.gender!=null &&
              this.gender.equals(other.getGender()))) &&
            ((this.hostCifNo==null && other.getHostCifNo()==null) || 
             (this.hostCifNo!=null &&
              this.hostCifNo.equals(other.getHostCifNo()))) &&
            ((this.idIssueCtry1==null && other.getIdIssueCtry1()==null) || 
             (this.idIssueCtry1!=null &&
              this.idIssueCtry1.equals(other.getIdIssueCtry1()))) &&
            ((this.idNo1==null && other.getIdNo1()==null) || 
             (this.idNo1!=null &&
              this.idNo1.equals(other.getIdNo1()))) &&
            ((this.idType1==null && other.getIdType1()==null) || 
             (this.idType1!=null &&
              this.idType1.equals(other.getIdType1()))) &&
            ((this.idenPresentToBank==null && other.getIdenPresentToBank()==null) || 
             (this.idenPresentToBank!=null &&
              this.idenPresentToBank.equals(other.getIdenPresentToBank()))) &&
            ((this.inTotalExtraIncome==null && other.getInTotalExtraIncome()==null) || 
             (this.inTotalExtraIncome!=null &&
              this.inTotalExtraIncome.equals(other.getInTotalExtraIncome()))) &&
            ((this.inTotalFixedIncome==null && other.getInTotalFixedIncome()==null) || 
             (this.inTotalFixedIncome!=null &&
              this.inTotalFixedIncome.equals(other.getInTotalFixedIncome()))) &&
            ((this.inTotalIncome==null && other.getInTotalIncome()==null) || 
             (this.inTotalIncome!=null &&
              this.inTotalIncome.equals(other.getInTotalIncome()))) &&
            ((this.inTotalOtherIncome==null && other.getInTotalOtherIncome()==null) || 
             (this.inTotalOtherIncome!=null &&
              this.inTotalOtherIncome.equals(other.getInTotalOtherIncome()))) &&
            ((this.includeIncomeFlag==null && other.getIncludeIncomeFlag()==null) || 
             (this.includeIncomeFlag!=null &&
              this.includeIncomeFlag.equals(other.getIncludeIncomeFlag()))) &&
            ((this.incomeBankAccoutNumber==null && other.getIncomeBankAccoutNumber()==null) || 
             (this.incomeBankAccoutNumber!=null &&
              this.incomeBankAccoutNumber.equals(other.getIncomeBankAccoutNumber()))) &&
            ((this.incomeBankName==null && other.getIncomeBankName()==null) || 
             (this.incomeBankName!=null &&
              this.incomeBankName.equals(other.getIncomeBankName()))) &&
            ((this.incomeBasicSalary==null && other.getIncomeBasicSalary()==null) || 
             (this.incomeBasicSalary!=null &&
              this.incomeBasicSalary.equals(other.getIncomeBasicSalary()))) &&
            ((this.incomeDeclared==null && other.getIncomeDeclared()==null) || 
             (this.incomeDeclared!=null &&
              this.incomeDeclared.equals(other.getIncomeDeclared()))) &&
            ((this.incomeExtraOther==null && other.getIncomeExtraOther()==null) || 
             (this.incomeExtraOther!=null &&
              this.incomeExtraOther.equals(other.getIncomeExtraOther()))) &&
            ((this.incomeExtraRental==null && other.getIncomeExtraRental()==null) || 
             (this.incomeExtraRental!=null &&
              this.incomeExtraRental.equals(other.getIncomeExtraRental()))) &&
            ((this.incomeInfoSavedFlag==null && other.getIncomeInfoSavedFlag()==null) || 
             (this.incomeInfoSavedFlag!=null &&
              this.incomeInfoSavedFlag.equals(other.getIncomeInfoSavedFlag()))) &&
            ((this.incomeOtherCommAverage==null && other.getIncomeOtherCommAverage()==null) || 
             (this.incomeOtherCommAverage!=null &&
              this.incomeOtherCommAverage.equals(other.getIncomeOtherCommAverage()))) &&
            ((this.incomeOtherCommMonth1==null && other.getIncomeOtherCommMonth1()==null) || 
             (this.incomeOtherCommMonth1!=null &&
              this.incomeOtherCommMonth1.equals(other.getIncomeOtherCommMonth1()))) &&
            ((this.incomeOtherCommMonth2==null && other.getIncomeOtherCommMonth2()==null) || 
             (this.incomeOtherCommMonth2!=null &&
              this.incomeOtherCommMonth2.equals(other.getIncomeOtherCommMonth2()))) &&
            ((this.incomeOtherCommMonth3==null && other.getIncomeOtherCommMonth3()==null) || 
             (this.incomeOtherCommMonth3!=null &&
              this.incomeOtherCommMonth3.equals(other.getIncomeOtherCommMonth3()))) &&
            ((this.incomeOtherDiemAverage==null && other.getIncomeOtherDiemAverage()==null) || 
             (this.incomeOtherDiemAverage!=null &&
              this.incomeOtherDiemAverage.equals(other.getIncomeOtherDiemAverage()))) &&
            ((this.incomeOtherDiemMonth1==null && other.getIncomeOtherDiemMonth1()==null) || 
             (this.incomeOtherDiemMonth1!=null &&
              this.incomeOtherDiemMonth1.equals(other.getIncomeOtherDiemMonth1()))) &&
            ((this.incomeOtherDiemMonth2==null && other.getIncomeOtherDiemMonth2()==null) || 
             (this.incomeOtherDiemMonth2!=null &&
              this.incomeOtherDiemMonth2.equals(other.getIncomeOtherDiemMonth2()))) &&
            ((this.incomeOtherDiemMonth3==null && other.getIncomeOtherDiemMonth3()==null) || 
             (this.incomeOtherDiemMonth3!=null &&
              this.incomeOtherDiemMonth3.equals(other.getIncomeOtherDiemMonth3()))) &&
            ((this.incomeOtherFlightAverage==null && other.getIncomeOtherFlightAverage()==null) || 
             (this.incomeOtherFlightAverage!=null &&
              this.incomeOtherFlightAverage.equals(other.getIncomeOtherFlightAverage()))) &&
            ((this.incomeOtherFlightMonth1==null && other.getIncomeOtherFlightMonth1()==null) || 
             (this.incomeOtherFlightMonth1!=null &&
              this.incomeOtherFlightMonth1.equals(other.getIncomeOtherFlightMonth1()))) &&
            ((this.incomeOtherFlightMonth2==null && other.getIncomeOtherFlightMonth2()==null) || 
             (this.incomeOtherFlightMonth2!=null &&
              this.incomeOtherFlightMonth2.equals(other.getIncomeOtherFlightMonth2()))) &&
            ((this.incomeOtherFlightMonth3==null && other.getIncomeOtherFlightMonth3()==null) || 
             (this.incomeOtherFlightMonth3!=null &&
              this.incomeOtherFlightMonth3.equals(other.getIncomeOtherFlightMonth3()))) &&
            ((this.incomeOtherIncome==null && other.getIncomeOtherIncome()==null) || 
             (this.incomeOtherIncome!=null &&
              this.incomeOtherIncome.equals(other.getIncomeOtherIncome()))) &&
            ((this.incomeOtherOTAverage==null && other.getIncomeOtherOTAverage()==null) || 
             (this.incomeOtherOTAverage!=null &&
              this.incomeOtherOTAverage.equals(other.getIncomeOtherOTAverage()))) &&
            ((this.incomeOtherOTMonth1==null && other.getIncomeOtherOTMonth1()==null) || 
             (this.incomeOtherOTMonth1!=null &&
              this.incomeOtherOTMonth1.equals(other.getIncomeOtherOTMonth1()))) &&
            ((this.incomeOtherOTMonth2==null && other.getIncomeOtherOTMonth2()==null) || 
             (this.incomeOtherOTMonth2!=null &&
              this.incomeOtherOTMonth2.equals(other.getIncomeOtherOTMonth2()))) &&
            ((this.incomeOtherOTMonth3==null && other.getIncomeOtherOTMonth3()==null) || 
             (this.incomeOtherOTMonth3!=null &&
              this.incomeOtherOTMonth3.equals(other.getIncomeOtherOTMonth3()))) &&
            ((this.incomeSharedHolderPercent==null && other.getIncomeSharedHolderPercent()==null) || 
             (this.incomeSharedHolderPercent!=null &&
              this.incomeSharedHolderPercent.equals(other.getIncomeSharedHolderPercent()))) &&
            ((this.incomeType==null && other.getIncomeType()==null) || 
             (this.incomeType!=null &&
              this.incomeType.equals(other.getIncomeType()))) &&
            ((this.incometotalLastMthCreditAcct1==null && other.getIncometotalLastMthCreditAcct1()==null) || 
             (this.incometotalLastMthCreditAcct1!=null &&
              this.incometotalLastMthCreditAcct1.equals(other.getIncometotalLastMthCreditAcct1()))) &&
            ((this.incometotalLastMthCreditAcct2==null && other.getIncometotalLastMthCreditAcct2()==null) || 
             (this.incometotalLastMthCreditAcct2!=null &&
              this.incometotalLastMthCreditAcct2.equals(other.getIncometotalLastMthCreditAcct2()))) &&
            ((this.insiderCode==null && other.getInsiderCode()==null) || 
             (this.insiderCode!=null &&
              this.insiderCode.equals(other.getInsiderCode()))) &&
            ((this.issuedDate==null && other.getIssuedDate()==null) || 
             (this.issuedDate!=null &&
              this.issuedDate.equals(other.getIssuedDate()))) &&
            ((this.lifeTimeFlag==null && other.getLifeTimeFlag()==null) || 
             (this.lifeTimeFlag!=null &&
              this.lifeTimeFlag.equals(other.getLifeTimeFlag()))) &&
            ((this.mailingPreference==null && other.getMailingPreference()==null) || 
             (this.mailingPreference!=null &&
              this.mailingPreference.equals(other.getMailingPreference()))) &&
            ((this.maritalStatus==null && other.getMaritalStatus()==null) || 
             (this.maritalStatus!=null &&
              this.maritalStatus.equals(other.getMaritalStatus()))) &&
            ((this.mobileNo==null && other.getMobileNo()==null) || 
             (this.mobileNo!=null &&
              this.mobileNo.equals(other.getMobileNo()))) &&
            ((this.nameLine1==null && other.getNameLine1()==null) || 
             (this.nameLine1!=null &&
              this.nameLine1.equals(other.getNameLine1()))) &&
            ((this.nameLine2==null && other.getNameLine2()==null) || 
             (this.nameLine2!=null &&
              this.nameLine2.equals(other.getNameLine2()))) &&
            ((this.nationality==null && other.getNationality()==null) || 
             (this.nationality!=null &&
              this.nationality.equals(other.getNationality()))) &&
            ((this.nationality2==null && other.getNationality2()==null) || 
             (this.nationality2!=null &&
              this.nationality2.equals(other.getNationality2()))) &&
            ((this.ncbConsentFlag==null && other.getNcbConsentFlag()==null) || 
             (this.ncbConsentFlag!=null &&
              this.ncbConsentFlag.equals(other.getNcbConsentFlag()))) &&
            ((this.noOfDependent==null && other.getNoOfDependent()==null) || 
             (this.noOfDependent!=null &&
              this.noOfDependent.equals(other.getNoOfDependent()))) &&
            ((this.originatedUnit==null && other.getOriginatedUnit()==null) || 
             (this.originatedUnit!=null &&
              this.originatedUnit.equals(other.getOriginatedUnit()))) &&
            ((this.personalInfoSavedFlag==null && other.getPersonalInfoSavedFlag()==null) || 
             (this.personalInfoSavedFlag!=null &&
              this.personalInfoSavedFlag.equals(other.getPersonalInfoSavedFlag()))) &&
            ((this.preScoreCash==null && other.getPreScoreCash()==null) || 
             (this.preScoreCash!=null &&
              this.preScoreCash.equals(other.getPreScoreCash()))) &&
            ((this.professionalCode==null && other.getProfessionalCode()==null) || 
             (this.professionalCode!=null &&
              this.professionalCode.equals(other.getProfessionalCode()))) &&
            ((this.race==null && other.getRace()==null) || 
             (this.race!=null &&
              this.race.equals(other.getRace()))) &&
            ((this.registerExtTelephoneNo==null && other.getRegisterExtTelephoneNo()==null) || 
             (this.registerExtTelephoneNo!=null &&
              this.registerExtTelephoneNo.equals(other.getRegisterExtTelephoneNo()))) &&
            ((this.registerTelephoneNo==null && other.getRegisterTelephoneNo()==null) || 
             (this.registerTelephoneNo!=null &&
              this.registerTelephoneNo.equals(other.getRegisterTelephoneNo()))) &&
            ((this.relationWithMain==null && other.getRelationWithMain()==null) || 
             (this.relationWithMain!=null &&
              this.relationWithMain.equals(other.getRelationWithMain()))) &&
            ((this.residentFlag==null && other.getResidentFlag()==null) || 
             (this.residentFlag!=null &&
              this.residentFlag.equals(other.getResidentFlag()))) &&
            ((this.residentType==null && other.getResidentType()==null) || 
             (this.residentType!=null &&
              this.residentType.equals(other.getResidentType()))) &&
            ((this.rmOccupation==null && other.getRmOccupation()==null) || 
             (this.rmOccupation!=null &&
              this.rmOccupation.equals(other.getRmOccupation()))) &&
            ((this.rmWealthFlag==null && other.getRmWealthFlag()==null) || 
             (this.rmWealthFlag!=null &&
              this.rmWealthFlag.equals(other.getRmWealthFlag()))) &&
            ((this.salutationCode==null && other.getSalutationCode()==null) || 
             (this.salutationCode!=null &&
              this.salutationCode.equals(other.getSalutationCode()))) &&
            ((this.sameRegisterAddressFlag==null && other.getSameRegisterAddressFlag()==null) || 
             (this.sameRegisterAddressFlag!=null &&
              this.sameRegisterAddressFlag.equals(other.getSameRegisterAddressFlag()))) &&
            ((this.selfEmployInTotalIncome==null && other.getSelfEmployInTotalIncome()==null) || 
             (this.selfEmployInTotalIncome!=null &&
              this.selfEmployInTotalIncome.equals(other.getSelfEmployInTotalIncome()))) &&
            ((this.selfEmployIncomeExtraOther==null && other.getSelfEmployIncomeExtraOther()==null) || 
             (this.selfEmployIncomeExtraOther!=null &&
              this.selfEmployIncomeExtraOther.equals(other.getSelfEmployIncomeExtraOther()))) &&
            ((this.selfEmployIncomeExtraRental==null && other.getSelfEmployIncomeExtraRental()==null) || 
             (this.selfEmployIncomeExtraRental!=null &&
              this.selfEmployIncomeExtraRental.equals(other.getSelfEmployIncomeExtraRental()))) &&
            ((this.selfEmployIncomeExtraTotal==null && other.getSelfEmployIncomeExtraTotal()==null) || 
             (this.selfEmployIncomeExtraTotal!=null &&
              this.selfEmployIncomeExtraTotal.equals(other.getSelfEmployIncomeExtraTotal()))) &&
            ((this.selfEmployIncomeTotal==null && other.getSelfEmployIncomeTotal()==null) || 
             (this.selfEmployIncomeTotal!=null &&
              this.selfEmployIncomeTotal.equals(other.getSelfEmployIncomeTotal()))) &&
            ((this.sourceFromCountry==null && other.getSourceFromCountry()==null) || 
             (this.sourceFromCountry!=null &&
              this.sourceFromCountry.equals(other.getSourceFromCountry()))) &&
            ((this.sourceOfRisk==null && other.getSourceOfRisk()==null) || 
             (this.sourceOfRisk!=null &&
              this.sourceOfRisk.equals(other.getSourceOfRisk()))) &&
            ((this.spBirthDate==null && other.getSpBirthDate()==null) || 
             (this.spBirthDate!=null &&
              this.spBirthDate.equals(other.getSpBirthDate()))) &&
            ((this.spIdNo==null && other.getSpIdNo()==null) || 
             (this.spIdNo!=null &&
              this.spIdNo.equals(other.getSpIdNo()))) &&
            ((this.spIdType==null && other.getSpIdType()==null) || 
             (this.spIdType!=null &&
              this.spIdType.equals(other.getSpIdType()))) &&
            ((this.spSalutationCode==null && other.getSpSalutationCode()==null) || 
             (this.spSalutationCode!=null &&
              this.spSalutationCode.equals(other.getSpSalutationCode()))) &&
            ((this.spThaiName==null && other.getSpThaiName()==null) || 
             (this.spThaiName!=null &&
              this.spThaiName.equals(other.getSpThaiName()))) &&
            ((this.spThaiSurName==null && other.getSpThaiSurName()==null) || 
             (this.spThaiSurName!=null &&
              this.spThaiSurName.equals(other.getSpThaiSurName()))) &&
            ((this.spTitleTypeCode==null && other.getSpTitleTypeCode()==null) || 
             (this.spTitleTypeCode!=null &&
              this.spTitleTypeCode.equals(other.getSpTitleTypeCode()))) &&
            ((this.telephoneNo==null && other.getTelephoneNo()==null) || 
             (this.telephoneNo!=null &&
              this.telephoneNo.equals(other.getTelephoneNo()))) &&
            ((this.thaiName==null && other.getThaiName()==null) || 
             (this.thaiName!=null &&
              this.thaiName.equals(other.getThaiName()))) &&
            ((this.thaiSalutationCode==null && other.getThaiSalutationCode()==null) || 
             (this.thaiSalutationCode!=null &&
              this.thaiSalutationCode.equals(other.getThaiSalutationCode()))) &&
            ((this.thaiSurName==null && other.getThaiSurName()==null) || 
             (this.thaiSurName!=null &&
              this.thaiSurName.equals(other.getThaiSurName()))) &&
            ((this.titleTypeCode==null && other.getTitleTypeCode()==null) || 
             (this.titleTypeCode!=null &&
              this.titleTypeCode.equals(other.getTitleTypeCode()))) &&
            ((this.workingAddrCopyFrom==null && other.getWorkingAddrCopyFrom()==null) || 
             (this.workingAddrCopyFrom!=null &&
              this.workingAddrCopyFrom.equals(other.getWorkingAddrCopyFrom())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAccounts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccounts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAccounts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAddresses() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAddresses());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAddresses(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAge() != null) {
            _hashCode += getAge().hashCode();
        }
        if (getAgeMonth() != null) {
            _hashCode += getAgeMonth().hashCode();
        }
        if (getBenefitPlusSegment() != null) {
            _hashCode += getBenefitPlusSegment().hashCode();
        }
        if (getBirthDate() != null) {
            _hashCode += getBirthDate().hashCode();
        }
        if (getBirthPlace() != null) {
            _hashCode += getBirthPlace().hashCode();
        }
        if (getBonusYearly() != null) {
            _hashCode += getBonusYearly().hashCode();
        }
        if (getBusinessSubType() != null) {
            _hashCode += getBusinessSubType().hashCode();
        }
        if (getBusinessType() != null) {
            _hashCode += getBusinessType().hashCode();
        }
        if (getCaId() != null) {
            _hashCode += getCaId().hashCode();
        }
        if (getCifId() != null) {
            _hashCode += getCifId().hashCode();
        }
        if (getCifRelCode() != null) {
            _hashCode += getCifRelCode().hashCode();
        }
        if (getCompanyType() != null) {
            _hashCode += getCompanyType().hashCode();
        }
        if (getContractEmpEndDate() != null) {
            _hashCode += getContractEmpEndDate().hashCode();
        }
        if (getContractEmpStartDate() != null) {
            _hashCode += getContractEmpStartDate().hashCode();
        }
        if (getContractEmployedFlag() != null) {
            _hashCode += getContractEmployedFlag().hashCode();
        }
        if (getCountryOfRegAddr() != null) {
            _hashCode += getCountryOfRegAddr().hashCode();
        }
        if (getCreditCards() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCreditCards());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCreditCards(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCurrentEmployerCode() != null) {
            _hashCode += getCurrentEmployerCode().hashCode();
        }
        if (getCurrentJobTitle() != null) {
            _hashCode += getCurrentJobTitle().hashCode();
        }
        if (getCustContactTime() != null) {
            _hashCode += getCustContactTime().hashCode();
        }
        if (getCustomerLevel() != null) {
            _hashCode += getCustomerLevel().hashCode();
        }
        if (getCustomerRiskRM() != null) {
            _hashCode += getCustomerRiskRM().hashCode();
        }
        if (getCustomerType() != null) {
            _hashCode += getCustomerType().hashCode();
        }
        if (getDiscloseCustInfoFlag() != null) {
            _hashCode += getDiscloseCustInfoFlag().hashCode();
        }
        if (getEducationLevel() != null) {
            _hashCode += getEducationLevel().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getEmailStatementFlag() != null) {
            _hashCode += getEmailStatementFlag().hashCode();
        }
        if (getEmploymentBizNature() != null) {
            _hashCode += getEmploymentBizNature().hashCode();
        }
        if (getEmploymentDept() != null) {
            _hashCode += getEmploymentDept().hashCode();
        }
        if (getEmploymentFaxNo() != null) {
            _hashCode += getEmploymentFaxNo().hashCode();
        }
        if (getEmploymentFinalTotalIncome() != null) {
            _hashCode += getEmploymentFinalTotalIncome().hashCode();
        }
        if (getEmploymentInfoSavedFlag() != null) {
            _hashCode += getEmploymentInfoSavedFlag().hashCode();
        }
        if (getEmploymentMonth() != null) {
            _hashCode += getEmploymentMonth().hashCode();
        }
        if (getEmploymentName() != null) {
            _hashCode += getEmploymentName().hashCode();
        }
        if (getEmploymentOccupation() != null) {
            _hashCode += getEmploymentOccupation().hashCode();
        }
        if (getEmploymentProvince() != null) {
            _hashCode += getEmploymentProvince().hashCode();
        }
        if (getEmploymentStaffId() != null) {
            _hashCode += getEmploymentStaffId().hashCode();
        }
        if (getEmploymentStatus() != null) {
            _hashCode += getEmploymentStatus().hashCode();
        }
        if (getEmploymentTelephoneDirectNo() != null) {
            _hashCode += getEmploymentTelephoneDirectNo().hashCode();
        }
        if (getEmploymentTelephoneExtNo() != null) {
            _hashCode += getEmploymentTelephoneExtNo().hashCode();
        }
        if (getEmploymentTelephoneNo() != null) {
            _hashCode += getEmploymentTelephoneNo().hashCode();
        }
        if (getEmploymentYear() != null) {
            _hashCode += getEmploymentYear().hashCode();
        }
        if (getExcludeIncomeDebtFlag() != null) {
            _hashCode += getExcludeIncomeDebtFlag().hashCode();
        }
        if (getExistingSavingTDAccount() != null) {
            _hashCode += getExistingSavingTDAccount().hashCode();
        }
        if (getExpiryDate() != null) {
            _hashCode += getExpiryDate().hashCode();
        }
        if (getExtTelephoneNo() != null) {
            _hashCode += getExtTelephoneNo().hashCode();
        }
        if (getFixNetAsset() != null) {
            _hashCode += getFixNetAsset().hashCode();
        }
        if (getGender() != null) {
            _hashCode += getGender().hashCode();
        }
        if (getHostCifNo() != null) {
            _hashCode += getHostCifNo().hashCode();
        }
        if (getIdIssueCtry1() != null) {
            _hashCode += getIdIssueCtry1().hashCode();
        }
        if (getIdNo1() != null) {
            _hashCode += getIdNo1().hashCode();
        }
        if (getIdType1() != null) {
            _hashCode += getIdType1().hashCode();
        }
        if (getIdenPresentToBank() != null) {
            _hashCode += getIdenPresentToBank().hashCode();
        }
        if (getInTotalExtraIncome() != null) {
            _hashCode += getInTotalExtraIncome().hashCode();
        }
        if (getInTotalFixedIncome() != null) {
            _hashCode += getInTotalFixedIncome().hashCode();
        }
        if (getInTotalIncome() != null) {
            _hashCode += getInTotalIncome().hashCode();
        }
        if (getInTotalOtherIncome() != null) {
            _hashCode += getInTotalOtherIncome().hashCode();
        }
        if (getIncludeIncomeFlag() != null) {
            _hashCode += getIncludeIncomeFlag().hashCode();
        }
        if (getIncomeBankAccoutNumber() != null) {
            _hashCode += getIncomeBankAccoutNumber().hashCode();
        }
        if (getIncomeBankName() != null) {
            _hashCode += getIncomeBankName().hashCode();
        }
        if (getIncomeBasicSalary() != null) {
            _hashCode += getIncomeBasicSalary().hashCode();
        }
        if (getIncomeDeclared() != null) {
            _hashCode += getIncomeDeclared().hashCode();
        }
        if (getIncomeExtraOther() != null) {
            _hashCode += getIncomeExtraOther().hashCode();
        }
        if (getIncomeExtraRental() != null) {
            _hashCode += getIncomeExtraRental().hashCode();
        }
        if (getIncomeInfoSavedFlag() != null) {
            _hashCode += getIncomeInfoSavedFlag().hashCode();
        }
        if (getIncomeOtherCommAverage() != null) {
            _hashCode += getIncomeOtherCommAverage().hashCode();
        }
        if (getIncomeOtherCommMonth1() != null) {
            _hashCode += getIncomeOtherCommMonth1().hashCode();
        }
        if (getIncomeOtherCommMonth2() != null) {
            _hashCode += getIncomeOtherCommMonth2().hashCode();
        }
        if (getIncomeOtherCommMonth3() != null) {
            _hashCode += getIncomeOtherCommMonth3().hashCode();
        }
        if (getIncomeOtherDiemAverage() != null) {
            _hashCode += getIncomeOtherDiemAverage().hashCode();
        }
        if (getIncomeOtherDiemMonth1() != null) {
            _hashCode += getIncomeOtherDiemMonth1().hashCode();
        }
        if (getIncomeOtherDiemMonth2() != null) {
            _hashCode += getIncomeOtherDiemMonth2().hashCode();
        }
        if (getIncomeOtherDiemMonth3() != null) {
            _hashCode += getIncomeOtherDiemMonth3().hashCode();
        }
        if (getIncomeOtherFlightAverage() != null) {
            _hashCode += getIncomeOtherFlightAverage().hashCode();
        }
        if (getIncomeOtherFlightMonth1() != null) {
            _hashCode += getIncomeOtherFlightMonth1().hashCode();
        }
        if (getIncomeOtherFlightMonth2() != null) {
            _hashCode += getIncomeOtherFlightMonth2().hashCode();
        }
        if (getIncomeOtherFlightMonth3() != null) {
            _hashCode += getIncomeOtherFlightMonth3().hashCode();
        }
        if (getIncomeOtherIncome() != null) {
            _hashCode += getIncomeOtherIncome().hashCode();
        }
        if (getIncomeOtherOTAverage() != null) {
            _hashCode += getIncomeOtherOTAverage().hashCode();
        }
        if (getIncomeOtherOTMonth1() != null) {
            _hashCode += getIncomeOtherOTMonth1().hashCode();
        }
        if (getIncomeOtherOTMonth2() != null) {
            _hashCode += getIncomeOtherOTMonth2().hashCode();
        }
        if (getIncomeOtherOTMonth3() != null) {
            _hashCode += getIncomeOtherOTMonth3().hashCode();
        }
        if (getIncomeSharedHolderPercent() != null) {
            _hashCode += getIncomeSharedHolderPercent().hashCode();
        }
        if (getIncomeType() != null) {
            _hashCode += getIncomeType().hashCode();
        }
        if (getIncometotalLastMthCreditAcct1() != null) {
            _hashCode += getIncometotalLastMthCreditAcct1().hashCode();
        }
        if (getIncometotalLastMthCreditAcct2() != null) {
            _hashCode += getIncometotalLastMthCreditAcct2().hashCode();
        }
        if (getInsiderCode() != null) {
            _hashCode += getInsiderCode().hashCode();
        }
        if (getIssuedDate() != null) {
            _hashCode += getIssuedDate().hashCode();
        }
        if (getLifeTimeFlag() != null) {
            _hashCode += getLifeTimeFlag().hashCode();
        }
        if (getMailingPreference() != null) {
            _hashCode += getMailingPreference().hashCode();
        }
        if (getMaritalStatus() != null) {
            _hashCode += getMaritalStatus().hashCode();
        }
        if (getMobileNo() != null) {
            _hashCode += getMobileNo().hashCode();
        }
        if (getNameLine1() != null) {
            _hashCode += getNameLine1().hashCode();
        }
        if (getNameLine2() != null) {
            _hashCode += getNameLine2().hashCode();
        }
        if (getNationality() != null) {
            _hashCode += getNationality().hashCode();
        }
        if (getNationality2() != null) {
            _hashCode += getNationality2().hashCode();
        }
        if (getNcbConsentFlag() != null) {
            _hashCode += getNcbConsentFlag().hashCode();
        }
        if (getNoOfDependent() != null) {
            _hashCode += getNoOfDependent().hashCode();
        }
        if (getOriginatedUnit() != null) {
            _hashCode += getOriginatedUnit().hashCode();
        }
        if (getPersonalInfoSavedFlag() != null) {
            _hashCode += getPersonalInfoSavedFlag().hashCode();
        }
        if (getPreScoreCash() != null) {
            _hashCode += getPreScoreCash().hashCode();
        }
        if (getProfessionalCode() != null) {
            _hashCode += getProfessionalCode().hashCode();
        }
        if (getRace() != null) {
            _hashCode += getRace().hashCode();
        }
        if (getRegisterExtTelephoneNo() != null) {
            _hashCode += getRegisterExtTelephoneNo().hashCode();
        }
        if (getRegisterTelephoneNo() != null) {
            _hashCode += getRegisterTelephoneNo().hashCode();
        }
        if (getRelationWithMain() != null) {
            _hashCode += getRelationWithMain().hashCode();
        }
        if (getResidentFlag() != null) {
            _hashCode += getResidentFlag().hashCode();
        }
        if (getResidentType() != null) {
            _hashCode += getResidentType().hashCode();
        }
        if (getRmOccupation() != null) {
            _hashCode += getRmOccupation().hashCode();
        }
        if (getRmWealthFlag() != null) {
            _hashCode += getRmWealthFlag().hashCode();
        }
        if (getSalutationCode() != null) {
            _hashCode += getSalutationCode().hashCode();
        }
        if (getSameRegisterAddressFlag() != null) {
            _hashCode += getSameRegisterAddressFlag().hashCode();
        }
        if (getSelfEmployInTotalIncome() != null) {
            _hashCode += getSelfEmployInTotalIncome().hashCode();
        }
        if (getSelfEmployIncomeExtraOther() != null) {
            _hashCode += getSelfEmployIncomeExtraOther().hashCode();
        }
        if (getSelfEmployIncomeExtraRental() != null) {
            _hashCode += getSelfEmployIncomeExtraRental().hashCode();
        }
        if (getSelfEmployIncomeExtraTotal() != null) {
            _hashCode += getSelfEmployIncomeExtraTotal().hashCode();
        }
        if (getSelfEmployIncomeTotal() != null) {
            _hashCode += getSelfEmployIncomeTotal().hashCode();
        }
        if (getSourceFromCountry() != null) {
            _hashCode += getSourceFromCountry().hashCode();
        }
        if (getSourceOfRisk() != null) {
            _hashCode += getSourceOfRisk().hashCode();
        }
        if (getSpBirthDate() != null) {
            _hashCode += getSpBirthDate().hashCode();
        }
        if (getSpIdNo() != null) {
            _hashCode += getSpIdNo().hashCode();
        }
        if (getSpIdType() != null) {
            _hashCode += getSpIdType().hashCode();
        }
        if (getSpSalutationCode() != null) {
            _hashCode += getSpSalutationCode().hashCode();
        }
        if (getSpThaiName() != null) {
            _hashCode += getSpThaiName().hashCode();
        }
        if (getSpThaiSurName() != null) {
            _hashCode += getSpThaiSurName().hashCode();
        }
        if (getSpTitleTypeCode() != null) {
            _hashCode += getSpTitleTypeCode().hashCode();
        }
        if (getTelephoneNo() != null) {
            _hashCode += getTelephoneNo().hashCode();
        }
        if (getThaiName() != null) {
            _hashCode += getThaiName().hashCode();
        }
        if (getThaiSalutationCode() != null) {
            _hashCode += getThaiSalutationCode().hashCode();
        }
        if (getThaiSurName() != null) {
            _hashCode += getThaiSurName().hashCode();
        }
        if (getTitleTypeCode() != null) {
            _hashCode += getTitleTypeCode().hashCode();
        }
        if (getWorkingAddrCopyFrom() != null) {
            _hashCode += getWorkingAddrCopyFrom().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InstantIndividual.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "InstantIndividual"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accounts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "accounts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "Account"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addresses");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "addresses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "Address"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("age");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "age"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ageMonth");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "ageMonth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("benefitPlusSegment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "benefitPlusSegment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "birthDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("birthPlace");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "birthPlace"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bonusYearly");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "bonusYearly"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("businessSubType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "businessSubType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("businessType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "businessType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "caId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cifId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "cifId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cifRelCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "cifRelCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "companyType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contractEmpEndDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "contractEmpEndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contractEmpStartDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "contractEmpStartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contractEmployedFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "contractEmployedFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryOfRegAddr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "countryOfRegAddr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditCards");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "creditCards"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "InstantCreditCard"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentEmployerCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "currentEmployerCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentJobTitle");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "currentJobTitle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custContactTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "custContactTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "customerLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerRiskRM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "customerRiskRM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "customerType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("discloseCustInfoFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "discloseCustInfoFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("educationLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "educationLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailStatementFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "emailStatementFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentBizNature");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentBizNature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentDept");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentDept"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentFaxNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentFaxNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentFinalTotalIncome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentFinalTotalIncome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentInfoSavedFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentInfoSavedFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentMonth");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentMonth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentOccupation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentOccupation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentProvince");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentProvince"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentStaffId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentStaffId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentTelephoneDirectNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentTelephoneDirectNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentTelephoneExtNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentTelephoneExtNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentTelephoneNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentTelephoneNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("employmentYear");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "employmentYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excludeIncomeDebtFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "excludeIncomeDebtFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existingSavingTDAccount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "existingSavingTDAccount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expiryDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "expiryDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extTelephoneNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "extTelephoneNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fixNetAsset");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "fixNetAsset"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gender");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "gender"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hostCifNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "hostCifNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idIssueCtry1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "idIssueCtry1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idNo1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "idNo1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idType1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "idType1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idenPresentToBank");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "idenPresentToBank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inTotalExtraIncome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "inTotalExtraIncome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inTotalFixedIncome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "inTotalFixedIncome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inTotalIncome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "inTotalIncome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inTotalOtherIncome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "inTotalOtherIncome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("includeIncomeFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "includeIncomeFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeBankAccoutNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeBankAccoutNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeBankName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeBankName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeBasicSalary");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeBasicSalary"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeDeclared");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeDeclared"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeExtraOther");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeExtraOther"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeExtraRental");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeExtraRental"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeInfoSavedFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeInfoSavedFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherCommAverage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherCommAverage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherCommMonth1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherCommMonth1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherCommMonth2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherCommMonth2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherCommMonth3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherCommMonth3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherDiemAverage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherDiemAverage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherDiemMonth1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherDiemMonth1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherDiemMonth2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherDiemMonth2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherDiemMonth3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherDiemMonth3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherFlightAverage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherFlightAverage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherFlightMonth1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherFlightMonth1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherFlightMonth2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherFlightMonth2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherFlightMonth3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherFlightMonth3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherIncome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherIncome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherOTAverage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherOTAverage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherOTMonth1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherOTMonth1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherOTMonth2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherOTMonth2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeOtherOTMonth3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeOtherOTMonth3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeSharedHolderPercent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeSharedHolderPercent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incomeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incomeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incometotalLastMthCreditAcct1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incometotalLastMthCreditAcct1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incometotalLastMthCreditAcct2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "incometotalLastMthCreditAcct2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insiderCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "insiderCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("issuedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "issuedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lifeTimeFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "lifeTimeFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailingPreference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "mailingPreference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maritalStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "maritalStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobileNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "mobileNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameLine1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "nameLine1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameLine2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "nameLine2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nationality");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "nationality"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nationality2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "nationality2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ncbConsentFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "ncbConsentFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noOfDependent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "noOfDependent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originatedUnit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "originatedUnit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personalInfoSavedFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "personalInfoSavedFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preScoreCash");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "preScoreCash"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("professionalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "professionalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("race");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "race"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerExtTelephoneNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "registerExtTelephoneNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("registerTelephoneNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "registerTelephoneNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relationWithMain");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "relationWithMain"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("residentFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "residentFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("residentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "residentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rmOccupation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "rmOccupation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rmWealthFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "rmWealthFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salutationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "salutationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sameRegisterAddressFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "sameRegisterAddressFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selfEmployInTotalIncome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "selfEmployInTotalIncome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selfEmployIncomeExtraOther");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "selfEmployIncomeExtraOther"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selfEmployIncomeExtraRental");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "selfEmployIncomeExtraRental"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selfEmployIncomeExtraTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "selfEmployIncomeExtraTotal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selfEmployIncomeTotal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "selfEmployIncomeTotal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceFromCountry");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "sourceFromCountry"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceOfRisk");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "sourceOfRisk"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spBirthDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "spBirthDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spIdNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "spIdNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spIdType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "spIdType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spSalutationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "spSalutationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spThaiName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "spThaiName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spThaiSurName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "spThaiSurName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spTitleTypeCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "spTitleTypeCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telephoneNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "telephoneNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thaiName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "thaiName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thaiSalutationCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "thaiSalutationCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thaiSurName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "thaiSurName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titleTypeCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "titleTypeCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workingAddrCopyFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://individual.ob.common.sml.integrosys.com", "workingAddrCopyFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
