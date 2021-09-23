package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class JasperReportService {

    private static TMBLogger<JasperReportService> logger = new TMBLogger<>(JasperReportService.class);

    private String reportFileName;
    private JasperReport jasperReport;
    private JasperPrint jasperPrint;
    private Map<String, Object> parameters;

    public JasperReportService() {
        parameters = new HashMap<>();
    }

    public void compileReport() {
        try {
            InputStream reportStream = getClass().getResourceAsStream("/jasper/".concat(reportFileName));
            jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
        } catch (JRException ex) {
            logger.error("Error during compile report {}", reportFileName);
        }
    }

    public void fillReport() {
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        } catch (JRException ex) {
            logger.error("Error during filling report {}", reportFileName);
        }
    }

    public ByteArrayOutputStream convertReportToOutputStream() throws TMBCommonException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint, os);
        } catch (JRException e) {
            throw new TMBCommonException(ResponseCode.JASPER_REPORT_ERROR.getCode(),
                    ResponseCode.JASPER_REPORT_ERROR.getMessage(),
                    ResponseCode.JASPER_REPORT_ERROR.getService(),
                    HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
        return os;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }
}
