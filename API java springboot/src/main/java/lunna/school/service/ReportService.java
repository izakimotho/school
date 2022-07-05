package lunna.school.service;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Map;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/11/22, Wednesday
 **/
public interface ReportService {
    public void init();
    Resource load(String filename);
    String generateReport(String fileName, String fileFormat, Map<String, Object> params)throws IOException;
    public void clear();

}
