package com.tmb.oneapp.lendingservice;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.ServiceResponseImp;
import com.tmb.oneapp.lendingservice.model.loan.EligibleProductResponse;
import com.tmb.oneapp.lendingservice.util.ThrowTmbExceptionFunction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.function.Supplier;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.tmb.oneapp", "com.tmb.common"})
public class LendingServiceApplication {


    public static <T, R> R fetchSoap(Supplier<T> supplier, ThrowTmbExceptionFunction<T, R> parser) throws TMBCommonException {

        T response = supplier.get();

        R result = parser.apply(response);




        return result;
    }


    public static void main(String[] args) throws TMBCommonException {

//        Supplier<ServiceResponse> ss = () -> {
//            return new ServiceResponseImp();
//        };
//        Paser pp = new Paser();
//        EligibleProductResponse result = LendingServiceApplication.fetchSoap(ss, serviceResponse -> pp.parseAbc(serviceResponse));
//        System.out.println(result);
        SpringApplication.run(LendingServiceApplication.class);
        setCertificate();


    }


    static void setCertificate() {
        String keyStoreFile = "oneapp-dev.tmbbank.local.jks";
        if (null == System.getProperty("javax.net.ssl.keyStore")) {
            System.setProperty("javax.net.ssl.keyStore", keyStoreFile);
            System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
        }
        if (null == System.getProperty("javax.net.ssl.trustStore")) {
            System.setProperty("javax.net.ssl.trustStore", keyStoreFile);
            System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        }
    }
}

