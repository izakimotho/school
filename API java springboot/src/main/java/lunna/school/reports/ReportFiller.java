package lunna.school.reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/11/22, Wednesday
 **/
@Component
public class ReportFiller {
    private String reportFileName;

    private JasperReport jasperReport;

    private JasperPrint jasperPrint;

    @Autowired
    private DataSource dataSource;

    private Map<String, Object> parameters;

    public ReportFiller() {
        parameters = new HashMap<>();
    }

    public void prepareReport() {
        compileReport();
        fillReport();
    }

    public void compileReport() {
        try {
            String resourceLocation = "classpath:reports/";
            File file = ResourceUtils.getFile(resourceLocation);
            System.out.println(file.getAbsolutePath()+"/"+reportFileName.replace(".jrxml", ".jasper"));
            jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath()+"/"+reportFileName);
            String jasper = reportFileName = reportFileName.replace(".jrxml", ".jasper");
            JRSaver.saveObject(jasperReport, file.getAbsolutePath()+"/"+jasper);
        } catch (JRException ex) {
            Logger.getLogger(ReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void fillReport() {
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
        } catch (JRException | SQLException ex) {
            Logger.getLogger(ReportFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }
}
