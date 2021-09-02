package com.tmb.oneapp.lendingservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.loan.stagingbar.LoanStagingbar;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;

import lombok.AllArgsConstructor;

/**
 * Provides service to fetchLoanStagingBar
 */
@Service
@AllArgsConstructor
public class LoanStagingBarService {

	private static final TMBLogger<LoanStagingBarService> logger = new TMBLogger<>(LoanStagingBarService.class);
	private final CommonServiceFeignClient commonServiceFeignClient;

	public LoanStagingbar fetchLoanStagingBar(String correlationId, String crmId, LoanStagingbar request)
			throws TMBCommonException {
		try {
			TmbOneServiceResponse<LoanStagingbar> loanStagingbar = commonServiceFeignClient
					.fetchLoanStagingBar(correlationId, crmId, request);
			if (!ResponseCode.SUCCESS.getCode().equals(loanStagingbar.getStatus().getCode())) {
				String errorMessage = String.format("[%s] %s", loanStagingbar.getStatus().getCode(),
						loanStagingbar.getStatus().getMessage());
				throw new TMBCommonException(ResponseCode.DATA_NOT_FOUND.getCode(), errorMessage,
						ResponseCode.DATA_NOT_FOUND.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
			}
			return loanStagingbar.getData();
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw e;
		}
	}

}
