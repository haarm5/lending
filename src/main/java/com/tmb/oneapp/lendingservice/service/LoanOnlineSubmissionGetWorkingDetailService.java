package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.address.Province;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.CustomerServiceClient;
import com.tmb.oneapp.lendingservice.constant.AddressTypeCode;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.dropdown.Dropdowns;
import com.tmb.oneapp.lendingservice.model.loanonline.CommonProvinceRequest;
import com.tmb.oneapp.lendingservice.model.loanonline.WorkingDetail;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetApplicationInfoRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetCustomerInfoRequest;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionGetWorkingDetailService {

    private final RslService rslService;
    private final CustomerServiceClient customerServiceClient;
    private final DropdownService dropdownService;
    private final CommonServiceFeignClient commonServiceFeignClient;

    public WorkingDetail getWorkingDetail(String crmId, String caId) throws TMBCommonException, ServiceException, RemoteException, JsonProcessingException {
        Individual customerInfoRsl = getCustomerInfoRsl(caId);
        CustGeneralProfileResponse customerInfoEc = getCustomerInfoEc(crmId);
        return parseLoanSubmissionWorkingDetail(customerInfoRsl, customerInfoEc, caId);
    }

    private WorkingDetail parseLoanSubmissionWorkingDetail(Individual customerInfoRsl, CustGeneralProfileResponse customerInfoEc, String caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        WorkingDetail workingDetail = new WorkingDetail();
        String employmentStatus = prepareData(customerInfoRsl.getEmploymentStatus(), getEmploymentStatusEc(customerInfoEc));
        workingDetail.setEmploymentStatus(employmentStatus);
        workingDetail.setEmploymentYear(prepareData(customerInfoRsl.getEmploymentYear(), null));
        workingDetail.setEmploymentMonth(prepareData(customerInfoRsl.getEmploymentMonth(), null));
        workingDetail.setRmOccupation(prepareData(customerInfoRsl.getRmOccupation(), customerInfoEc.getOccupationCode()));
        workingDetail.setEmploymentOccupation(prepareData(customerInfoRsl.getEmploymentOccupation(), null));
        workingDetail.setProfessional(prepareData(customerInfoRsl.getProfessionalCode(), null));
        workingDetail.setContractEmployedFlag(prepareData(customerInfoRsl.getContractEmployedFlag(), null));
        workingDetail.setBusinessType(prepareBusinessType(customerInfoRsl, customerInfoEc));
        workingDetail.setBusinessSubType(prepareData(customerInfoRsl.getBusinessSubType(), customerInfoEc.getBusinessTypeCode()));
        workingDetail.setEmploymentName(prepareData(customerInfoRsl.getEmploymentName(), customerInfoEc.getWorkEmploymentName()));
        workingDetail.setAddress(prepareWorkingAddress(customerInfoRsl.getAddresses(), customerInfoEc));
        workingDetail.setIncomeBasicSalary(prepareData(customerInfoRsl.getIncomeBasicSalary(), null));
        workingDetail.setIncomeOtherIncome(prepareData(customerInfoRsl.getIncomeOtherIncome(), null));
        workingDetail.setIncomeBank(prepareData(customerInfoRsl.getIncomeBankName(), null));
        workingDetail.setIncomeBankAccountNumber(prepareData(customerInfoRsl.getIncomeBankAccoutNumber(), null));
        workingDetail.setIncomeDeclared(prepareData(customerInfoRsl.getIncomeDeclared(), null));
        workingDetail.setIncometotalLastMthCreditAcct1(prepareData(customerInfoRsl.getIncometotalLastMthCreditAcct1(), null));
        workingDetail.setIncomeSharedHolderPercent(prepareData(customerInfoRsl.getIncomeSharedHolderPercent(), null));
        workingDetail.setIncomeType(prepareIncomeType(customerInfoRsl.getIncomeType(), employmentStatus));
        workingDetail.setSciCountry(prepareData(customerInfoRsl.getSourceFromCountry(), customerInfoEc.getCountryOfIncome()));
        workingDetail.setCardDelivery(prepareData(customerInfoRsl.getMailingPreference(), null));
        workingDetail.setEmailStatementFlag(prepareData(customerInfoRsl.getEmailStatementFlag(), "Y"));
        workingDetail.setTel(prepareData(customerInfoRsl.getEmploymentTelephoneNo(), customerInfoEc.getWorkPhoneNo()));
        workingDetail.setExTel(prepareData(customerInfoRsl.getEmploymentTelephoneExtNo(), customerInfoEc.getWorkPhoneNoExt()));
        workingDetail.setWaiveDoc(getStatusIsWaiveDoc(caId));
        return workingDetail;
    }

    public String getEmploymentStatusEc(CustGeneralProfileResponse customerInfoEc) throws TMBCommonException {
        return dropdownService.getEmploymentStatus(customerInfoEc.getOccupationCode());
    }

    private String prepareBusinessType(Individual customerInfoRsl, CustGeneralProfileResponse customerInfoEc) {
        String businessTypeCodeEc = "";
        if (!StringUtils.isEmpty(customerInfoEc.getBusinessTypeCode())) {
            businessTypeCodeEc = customerInfoEc.getBusinessTypeCode().substring(0, 4);
        }
        return prepareData(customerInfoRsl.getBusinessType(), businessTypeCodeEc);
    }

    public Address parseWorkingAddressEc(CustGeneralProfileResponse customer) {
        Address address = new Address();
        address.setBuildingName(fixedMaxLengthAddress(customer.getWorkAddrVillageOrbuilding(), 90));
        address.setNo(fixedMaxLengthAddress(customer.getWorkAddrHouseNo(), 25));
        address.setFloor(fixedMaxLengthAddress(customer.getWorkAddrFloorNo(), 3));
        address.setMoo(fixedMaxLengthAddress(customer.getWorkAddrMoo(), 20));
        address.setStreetName(fixedMaxLengthAddress(customer.getWorkAddrStreet(), 100));
        address.setPostalCode(fixedMaxLengthAddress(customer.getWorkAddrZipcode(), 20));
        address.setProvince(fixedMaxLengthAddress(customer.getWorkAddrProvinceNameTh(), 100));
        address.setCountry(fixedMaxLengthAddress(customer.getNationality(), 40));
        address.setTumbol(fixedMaxLengthAddress(customer.getWorkAddrSubDistrictNameTh(), 20));
        address.setRoad(fixedMaxLengthAddress(customer.getWorkAddrStreet(), 25));
        address.setAmphur(fixedMaxLengthAddress(customer.getWorkAddrdistrictNameTh(), 30));

        return address;
    }

    public Address prepareWorkingAddress(com.tmb.common.model.legacy.rsl.common.ob.address.Address[] addressRsl, CustGeneralProfileResponse customerEc) {
        Address address = new Address();
        Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> individualAddr = Arrays.stream(addressRsl).filter(addr -> AddressTypeCode.WORKING.getCode().equals(addr.getAddrTypCode())).findAny();
        if (individualAddr.isPresent()) {
            com.tmb.common.model.legacy.rsl.common.ob.address.Address workingAddress = individualAddr.get();
            address.setBuildingName(fixedMaxLengthAddress(prepareData(workingAddress.getBuildingName(), customerEc.getWorkAddrVillageOrbuilding()), 90));
            address.setNo(fixedMaxLengthAddress(prepareData(workingAddress.getAddress(), customerEc.getWorkAddrHouseNo()), 25));
            address.setFloor(fixedMaxLengthAddress(prepareData(workingAddress.getFloor(), customerEc.getWorkAddrFloorNo()), 3));
            address.setMoo(fixedMaxLengthAddress(prepareData(workingAddress.getMoo(), customerEc.getWorkAddrMoo()), 20));
            address.setStreetName(fixedMaxLengthAddress(prepareData(workingAddress.getStreetName(), customerEc.getWorkAddrStreet()), 100));
            address.setPostalCode(fixedMaxLengthAddress(prepareData(workingAddress.getPostalCode(), customerEc.getWorkAddrZipcode()), 20));
            address.setProvince(fixedMaxLengthAddress(getProvinceName(prepareData(workingAddress.getPostalCode(), customerEc.getWorkAddrZipcode())), 100));
            address.setCountry(fixedMaxLengthAddress(prepareData(workingAddress.getCountry(), customerEc.getNationality()), 40));
            address.setTumbol(fixedMaxLengthAddress(prepareData(workingAddress.getTumbol(), customerEc.getWorkAddrSubDistrictNameTh()), 20));
            address.setRoad(fixedMaxLengthAddress(prepareData(workingAddress.getRoad(), customerEc.getWorkAddrStreet()), 25));
            address.setAmphur(fixedMaxLengthAddress(prepareData(workingAddress.getAmphur(), customerEc.getWorkAddrdistrictNameTh()), 30));
        } else {
            return parseWorkingAddressEc(customerEc);
        }
        return address;
    }

    private String fixedMaxLengthAddress(String value, int length) {
        if (Objects.isNull(value) || value.isEmpty()) {
            return "";
        }
        return org.apache.commons.lang3.StringUtils.left(value, length);
    }

    private String prepareIncomeType(String rslIncomeType, String employmentStatus) throws JsonProcessingException {
        if (StringUtils.isEmpty(rslIncomeType)) {
            return rslIncomeType;
        }
        List<Dropdowns.IncomeType> dropdownsIncomeType = dropdownService.getDropdownIncomeType(employmentStatus);
        return dropdownsIncomeType.get(0).getCode();
    }

    private Individual getCustomerInfoRsl(String caId) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        LoanSubmissionGetCustomerInfoRequest request = new LoanSubmissionGetCustomerInfoRequest();
        request.setCaId(caId);
        ResponseIndividual response = rslService.getLoanSubmissionCustomerInfo(request);
        if (ObjectUtils.isEmpty(response.getBody()) || ObjectUtils.isEmpty(response.getBody().getIndividuals()[0])) {
            throw new TMBCommonException(response.getHeader().getResponseCode(),
                    "Customer info on rsl are empty.", ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response.getBody().getIndividuals()[0];

    }

    private boolean getStatusIsWaiveDoc(String caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        LoanSubmissionGetApplicationInfoRequest request = new LoanSubmissionGetApplicationInfoRequest();
        request.setCaId(caId);
        ResponseApplication response = rslService.getLoanSubmissionApplicationInfo(request);
        if (ObjectUtils.isEmpty(response.getBody())) {
            throw new TMBCommonException(response.getHeader().getResponseCode(),
                    "Application info on rsl are empty.", ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response.getBody().getNatureOfRequest().equals("04") || response.getBody().getNatureOfRequest().equals("12");
    }

    private CustGeneralProfileResponse getCustomerInfoEc(String crmId) throws TMBCommonException {
        TmbOneServiceResponse<CustGeneralProfileResponse> response = customerServiceClient.getCustomers(crmId).getBody();
        if (ObjectUtils.isEmpty(Objects.requireNonNull(response).getData())) {
            throw new TMBCommonException(response.getStatus().getCode(),
                    "Customer info on ec are empty.", ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response.getData();
    }

    private <T> T prepareData(T rslData, T ecData) {
        if (!ObjectUtils.isEmpty(rslData)) {
            return rslData;
        }
        return ecData;
    }

    private String getProvinceName(String postCode) {
        CommonProvinceRequest req = new CommonProvinceRequest();
        req.setField("postcode");
        req.setSearch(postCode);
        ResponseEntity<TmbOneServiceResponse<List<Province>>> response = commonServiceFeignClient.getProvince(req);
        if (!ResponseCode.SUCCESS.getCode().equals(Objects.requireNonNull(response.getBody()).getStatus().getCode())) {
            return Strings.EMPTY;
        }
        return response.getBody().getData().get(0).getProvinceNameTh();
    }

}
