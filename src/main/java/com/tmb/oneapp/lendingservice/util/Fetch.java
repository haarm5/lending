package com.tmb.oneapp.lendingservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.http.HttpStatus;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * Fetch provides functionality for fetching and handle network error.
 */
public class Fetch {

    private Fetch() {

    }

    /**
     * Fetches data from OneApp services.
     * @param supplier
     * @param <T>
     * @return
     * @throws TMBCommonException
     */
    public static <T> T fetch(Supplier<TmbOneServiceResponse> supplier) throws TMBCommonException {
        TmbOneServiceResponse oneAppResponse = supplier.get();
        TmbStatus tmbStatus = oneAppResponse.getStatus();
        if (!ResponseCode.SUCCESS.getCode().equals(tmbStatus.getCode())) {
            throw new TMBCommonException(tmbStatus.getCode(), tmbStatus.getMessage(), tmbStatus.getService(), HttpStatus.BAD_REQUEST, null);
        }
        return (T) oneAppResponse.getData();
    }

    /**
     * Fetches dat from Soap service
     * @param supplier
     * @param supplierFunction
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<T> fetchFuture(ThrowSoapServiceExceptionFunction<T> supplier,TmbExceptionSupplierFunction<T> supplierFunction) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return Fetch.fetch(supplier::get,supplierFunction::apply);
            } catch (TMBCommonException e) {
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Waits for all future complete.
     * @param cfs
     * @return
     * @throws TMBCommonException
     */
    public static List<Object> allOf(CompletableFuture<?>... cfs) throws TMBCommonException {
        CompletableFuture.allOf(cfs);
        List<Object> results = new ArrayList<>();
        try {
            for (CompletableFuture f : cfs) {
                results.add(f.get());
            }
            return results;

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.BAD_REQUEST, null);
        } catch (CompletionException e) {
            throw (TMBCommonException) e.getCause();

        }
    }

    /**
     * Fetch data from Soap service and parses data
     * @param supplier
     * @param supplierFunction
     * @param <T>
     * @return
     * @throws TMBCommonException
     */
    public static <T> T fetch(ThrowSoapServiceExceptionFunction<T> supplier,TmbExceptionSupplierFunction<T> supplierFunction) throws TMBCommonException {
        T response = null;
        try {
            response = supplier.get();
            return supplierFunction.apply(response);
        } catch (RemoteException | ServiceException | JsonProcessingException e) {
            throw new TMBCommonException("soap network error");
        }
    }
}
