package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.address.Province;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.CustomerServiceClient;
import com.tmb.oneapp.lendingservice.constant.AddressTypeCode;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.dropdown.Dropdowns;
import com.tmb.oneapp.lendingservice.model.loanonline.CommonProvinceRequest;
import com.tmb.oneapp.lendingservice.model.loanonline.WorkingDetail;
import com.tmb.oneapp.lendingservice.model.personal.Address;
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
        CustGeneralProfileResponse customerInfoEc = getCustomerInfoFromRslEc(crmId);
        return parseLoanSubmissionWorkingDetail(customerInfoRsl, customerInfoEc);
    }

    private WorkingDetail parseLoanSubmissionWorkingDetail(Individual customerInfoRsl, CustGeneralProfileResponse customerInfoEc) throws ServiceException, TMBCommonException, JsonProcessingException {
        WorkingDetail workingDetail = new WorkingDetail();
        String employmentStatus = prepareData(customerInfoRsl.getEmploymentStatus(), getEmploymentStatusEc(customerInfoEc));
        workingDetail.setEmploymentStatus(employmentStatus);
        workingDetail.setEmploymentYear(prepareData(customerInfoRsl.getEmploymentYear(), null));
        workingDetail.setEmploymentMonth(prepareData(customerInfoRsl.getEmploymentMonth(), null));
        workingDetail.setRmOccupation(prepareData(customerInfoRsl.getRmOccupation(), customerInfoEc.getOccupationCode()));
        workingDetail.setOccupation(prepareData(customerInfoRsl.getEmploymentOccupation(), null));
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
        workingDetail.setIncomeType(prepareData(customerInfoRsl.getIncomeType(), null));
        workingDetail.setSciCountry(prepareIncomeType(customerInfoRsl.getIncomeType(), employmentStatus));
        workingDetail.setCardDelivery(prepareData(customerInfoRsl.getMailingPreference(), null));
        workingDetail.setEmailStatementFlag(prepareData(customerInfoRsl.getEmailStatementFlag(), "Y"));
        workingDetail.setTel(prepareData(customerInfoRsl.getEmploymentTelephoneNo(), customerInfoEc.getWorkPhoneNo()));
        workingDetail.setExTel(prepareData(customerInfoRsl.getEmploymentTelephoneExtNo(), customerInfoEc.getWorkPhoneNoExt()));

        return workingDetail;
    }

    private String getEmploymentStatusEc(CustGeneralProfileResponse customerInfoEc) throws ServiceException, TMBCommonException, JsonProcessingException {
        return dropdownService.getEmploymentStatus(customerInfoEc.getOccupationCode());
    }

    private String prepareBusinessType(Individual customerInfoRsl, CustGeneralProfileResponse customerInfoEc) {
        String businessTypeCodeEc = "";
        if(!StringUtils.isEmpty(customerInfoEc.getBusinessTypeCode())) {
            businessTypeCodeEc = customerInfoEc.getBusinessTypeCode().substring(0, 4);
        }
        return prepareData(customerInfoRsl.getBusinessType(), businessTypeCodeEc);
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

    public Address prepareWorkingAddress(com.tmb.common.model.legacy.rsl.common.ob.address.Address[] addressRsl, CustGeneralProfileResponse customerEc) {
        Address address = new Address();
        Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> individualAddr = Arrays.stream(addressRsl).filter(addr -> AddressTypeCode.WORKING.getCode().equals(addr.getAddrTypCode())).findAny();
        if (individualAddr.isPresent()) {
            com.tmb.common.model.legacy.rsl.common.ob.address.Address workingAddress = individualAddr.get();
            address.setBuildingName(prepareData(workingAddress.getBuildingName(), customerEc.getWorkAddrVillageOrbuilding()));
            address.setNo(prepareData(workingAddress.getAddress(), customerEc.getWorkAddrHouseNo()));
            address.setFloor(prepareData(workingAddress.getFloor(), customerEc.getWorkAddrFloorNo()));
            address.setMoo(prepareData(workingAddress.getMoo(), customerEc.getWorkAddrMoo()));
            address.setStreetName(prepareData(workingAddress.getStreetName(), customerEc.getWorkAddrStreet()));
            address.setPostalCode(prepareData(workingAddress.getPostalCode(), customerEc.getWorkAddrZipcode()));
            address.setProvince(getProvinceName(prepareData(workingAddress.getPostalCode(), customerEc.getWorkAddrZipcode())));
            address.setCountry(prepareData(workingAddress.getCountry(), customerEc.getNationality()));
            address.setTumbol(prepareData(workingAddress.getTumbol(), customerEc.getWorkAddrSubDistrictNameTh()));
            address.setRoad(prepareData(workingAddress.getRoad(), customerEc.getWorkAddrStreet()));
            address.setAmphur(prepareData(workingAddress.getAmphur(), customerEc.getWorkAddrdistrictNameTh()));
        } else {
            return parseWorkingAddressEc(customerEc);
        }
        return address;
    }

    private String prepareIncomeType(String rslIncomeType, String employmentStatus) throws ServiceException, TMBCommonException, JsonProcessingException {
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

    private CustGeneralProfileResponse getCustomerInfoFromRslEc(String crmId) throws TMBCommonException {
        TmbOneServiceResponse<CustGeneralProfileResponse> response = customerServiceClient.getCustomers(crmId).getBody();
        if (ObjectUtils.isEmpty(response.getData())) {
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
        if (!ResponseCode.SUCCESS.getCode().equals(response.getBody().getStatus().getCode())) {
            return Strings.EMPTY;
        }
        return response.getBody().getData().get(0).getProvinceNameTh();
    }

}
