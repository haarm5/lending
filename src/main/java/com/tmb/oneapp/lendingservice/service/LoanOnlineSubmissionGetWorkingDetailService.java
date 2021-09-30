package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.oneapp.lendingservice.constant.AddressTypeCode;
import com.tmb.oneapp.lendingservice.model.dropdown.Dropdowns;
import com.tmb.oneapp.lendingservice.model.loanonline.WorkingDetail;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionGetWorkingDetailService {
    private static final TMBLogger<LoanOnlineSubmissionGetWorkingDetailService> logger = new TMBLogger<>(LoanOnlineSubmissionGetWorkingDetailService.class);

    private final LoanOnlineSubmissionGetPersonalDetailService loanOnlineSubmissionGetPersonalDetailService;
    private final DropdownService dropdownService;

    public WorkingDetail getWorkingDetail(String crmId, Long caId) throws TMBCommonException, ServiceException, RemoteException, JsonProcessingException {
        Individual individual = loanOnlineSubmissionGetPersonalDetailService.getCustomer(caId);

        if (loanOnlineSubmissionGetPersonalDetailService.personalInfoSaved(individual)) {
            logger.info("Get personal info from [RSL]");
            return parseLoanSubmissionWorkingDetail(individual);
        } else {
            logger.info("Get personal info from [EC]");
            CustGeneralProfileResponse customerEc = loanOnlineSubmissionGetPersonalDetailService.getCustomerEC(crmId);
            return parseLoanSubmissionWorkingDetailEc(customerEc);
        }

    }

    private WorkingDetail parseLoanSubmissionWorkingDetail(Individual individual) {
        WorkingDetail workingDetail = new WorkingDetail();
        workingDetail.setEmploymentStatus(individual.getEmploymentStatus());
        workingDetail.setEmploymentYear(individual.getEmploymentYear());
        workingDetail.setEmploymentMonth(individual.getEmploymentMonth());
        workingDetail.setRmOccupation(individual.getRmOccupation());
        workingDetail.setOccupation(individual.getEmploymentOccupation());
        workingDetail.setContractEmployedFlag(individual.getContractEmployedFlag());
        workingDetail.setBusinessType(individual.getBusinessType());
        workingDetail.setBusinessSubType(individual.getBusinessSubType());
        workingDetail.setEmploymentName(individual.getEmploymentName());
        workingDetail.setAddress(parseWorkingAddress(individual.getAddresses()));
        workingDetail.setIncomeBasicSalary(individual.getIncomeBasicSalary());
        workingDetail.setIncomeOtherIncome(individual.getIncomeOtherIncome());
        workingDetail.setIncomeBank(individual.getIncomeBankName());
        workingDetail.setIncomeBankAccountNumber(individual.getIncomeBankAccoutNumber());
        workingDetail.setIncomeDeclared(individual.getIncomeDeclared());
        workingDetail.setIncometotalLastMthCreditAcct1(individual.getIncometotalLastMthCreditAcct1());
        workingDetail.setIncomeSharedHolderPercent(individual.getIncomeSharedHolderPercent());
        workingDetail.setIncomeType(individual.getIncomeType());
        workingDetail.setSciCountry(individual.getSourceFromCountry());
        workingDetail.setCardDelivery(individual.getMailingPreference());
        workingDetail.setEmailStatementFlag(individual.getEmailStatementFlag());

        return workingDetail;
    }

    private WorkingDetail parseLoanSubmissionWorkingDetailEc(CustGeneralProfileResponse customer) throws ServiceException, TMBCommonException, JsonProcessingException {
        String employmentStatus = dropdownService.getEmploymentStatus(customer.getOccupationCode());
        WorkingDetail workingDetail = new WorkingDetail();
        workingDetail.setEmploymentStatus(employmentStatus);
        workingDetail.setEmploymentYear(null);
        workingDetail.setEmploymentMonth(null);
        workingDetail.setRmOccupation(customer.getOccupationCode());
        workingDetail.setOccupation(null);
        workingDetail.setContractEmployedFlag(null);
        workingDetail.setBusinessType(customer.getBusinessTypeCode().substring(0,4));
        workingDetail.setBusinessSubType(customer.getBusinessTypeCode());
        workingDetail.setEmploymentName(customer.getWorkEmploymentName());
        workingDetail.setAddress(parseWorkingAddressEc(customer));
        workingDetail.setTel(customer.getWorkPhoneNo());
        workingDetail.setExTel(customer.getWorkPhoneNoExt());
        workingDetail.setIncomeBasicSalary(null);
        workingDetail.setIncomeOtherIncome(null);
        workingDetail.setIncomeBank(null);
        workingDetail.setIncomeBankAccountNumber(null);
        workingDetail.setIncomeDeclared(null);
        workingDetail.setIncometotalLastMthCreditAcct1(null);
        workingDetail.setIncomeSharedHolderPercent(null);
        workingDetail.setIncomeType(getDefaultIncomeType(employmentStatus));
        workingDetail.setCardDelivery(null);
        workingDetail.setEmailStatementFlag("Y");

        return workingDetail;
    }

    public Address parseWorkingAddressEc(CustGeneralProfileResponse customer) {
        Address address = new Address();
        address.setBuildingName(customer.getWorkAddrVillageOrbuilding());
        address.setNo(customer.getWorkAddrHouseNo());
        address.setFloor(customer.getWorkAddrFloorNo());
        address.setMoo(customer.getWorkAddrMoo());
        address.setStreetName(customer.getWorkAddrStreet());
        address.setPostalCode(customer.getWorkAddrZipcode());
        address.setProvince(customer.getWorkAddrProvinceNameTh());
        address.setCountry(customer.getNationality());
        address.setTumbol(customer.getWorkAddrSubDistrictNameTh());
        address.setRoad(customer.getWorkAddrStreet());
        address.setAmphur(customer.getWorkAddrdistrictNameTh());

        return address;
    }

    public Address parseWorkingAddress(com.tmb.common.model.legacy.rsl.common.ob.address.Address[] individualAddress) {
        Address address = new Address();
        Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> individualAddr = Arrays.stream(individualAddress).filter(addr -> AddressTypeCode.WORKING.getCode().equals(addr.getAddrTypCode())).findAny();
        if (individualAddr.isPresent()) {
            com.tmb.common.model.legacy.rsl.common.ob.address.Address workingAddress = individualAddr.get();
            address.setBuildingName(workingAddress.getBuildingName());
            address.setNo(workingAddress.getAddress());
            address.setFloor(workingAddress.getFloor());
            address.setMoo(workingAddress.getMoo());
            address.setStreetName(workingAddress.getStreetName());
            address.setPostalCode(workingAddress.getPostalCode());
            address.setProvince(workingAddress.getProvince());
            address.setCountry(workingAddress.getCountry());
            address.setTumbol(workingAddress.getTumbol());
            address.setRoad(workingAddress.getRoad());
            address.setAmphur(workingAddress.getAmphur());
        }
        return address;
    }

    private String getDefaultIncomeType(String employmentStatus) throws ServiceException, TMBCommonException, JsonProcessingException {
        List<Dropdowns.IncomeType> dropdownsIncomeType = dropdownService.getDropdownIncomeType(employmentStatus);
        return dropdownsIncomeType.get(0).getCode();
    }

}
