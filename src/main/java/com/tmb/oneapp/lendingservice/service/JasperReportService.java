package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.logger.TMBLogger;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class JasperReportService {

    private static TMBLogger<JasperReportService> logger = new TMBLogger<>(JasperReportService.class);

    private String reportFileName;
    private JasperReport jasperReport;
    private JasperPrint jasperPrint;
    private Map<String, Object> parameters;

    private JasperExportManager jasperExportManager;

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

    public void exportToPdf(JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        jasperExportManager.exportToPdfStream(jasperPrint, outputStream);
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
