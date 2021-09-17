package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.LogAround;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.util.TMBUtils;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.documnet.*;
import com.tmb.oneapp.lendingservice.service.UploadDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Api(tags = "UploadDocumentController")
@RequestMapping("/document")
@RestController
public class UploadDocumentController {

    private final UploadDocumentService uploadDocumentService;

    @ApiOperation("Upload documents")
    @PostMapping(value = "/upload")
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<UploadDocumentResponse>> uploadDocument(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @Valid @RequestBody UploadDocumentRequest request
    ) throws TMBCommonException {
        TmbOneServiceResponse<UploadDocumentResponse> response = new TmbOneServiceResponse<>();

        try {
            UploadDocumentResponse uploadDocumentResponse = uploadDocumentService.upload(crmId, request);
            response.setData(uploadDocumentResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @ApiOperation("Submit documents")
    @PostMapping(value = "/submit")
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<SubmitDocumentResponse>> submitDocument(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @Valid @RequestBody SubmitDocumentRequest request
    ) throws TMBCommonException {
        TmbOneServiceResponse<SubmitDocumentResponse> response = new TmbOneServiceResponse<>();

        try {
            SubmitDocumentResponse submitDocumentResponse = uploadDocumentService.submit(crmId, request);
            response.setData(submitDocumentResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @ApiOperation("Delete documents")
    @DeleteMapping(value = "/{caId}/{docCode}/{fileType}/{fileName}")
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<DeleteDocumentResponse>> deleteDocument(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @ApiParam(value = "caId", required = true)
            @Valid @PathVariable("caId") String caId,
            @ApiParam(value = "docCode", required = true)
            @Valid @PathVariable("docCode") String docCode,
            @ApiParam(value = "fileType", required = true)
            @Valid @PathVariable("fileType") String fileType,
            @ApiParam(value = "fileName", required = true)
            @Valid @PathVariable("fileName") String fileName
    ) throws TMBCommonException {
        TmbOneServiceResponse<DeleteDocumentResponse> response = new TmbOneServiceResponse<>();

        try {
            DeleteDocumentResponse deleteDocumentResponse = uploadDocumentService.delete(crmId, caId, docCode, fileType, fileName);
            response.setData(deleteDocumentResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
