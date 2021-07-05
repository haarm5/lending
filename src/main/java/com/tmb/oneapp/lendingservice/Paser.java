package com.tmb.oneapp.lendingservice;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.loan.EligibleProductResponse;

public class Paser {
    public EligibleProductResponse parseAbc(ServiceResponse response) throws TMBCommonException {
        if(response==null) {
            throw  new TMBCommonException("Error");
        }
        return new EligibleProductResponse();
    }
}
