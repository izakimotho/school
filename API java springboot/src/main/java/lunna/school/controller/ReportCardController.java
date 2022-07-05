package lunna.school.controller;

import lombok.RequiredArgsConstructor;
import lunna.school.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/11/22, Wednesday
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class ReportCardController {
    @Autowired
    ReportService reportService;

    @PostMapping("/report-card")
    public ResponseEntity<Resource> generateReport(@RequestParam("date") String date,
                                 @RequestParam("student_id") String student_id,
                                 @RequestParam("fileFormat") String fileFormat) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("student_id", student_id);

        String fileName = "report_card";
        String reportPath  = reportService.generateReport(fileName, fileFormat,params);
        Path uploadPath = Paths.get(reportPath);
        if (!Files.exists(uploadPath)) {
            System.out.println("file not found");
        }
        String url = MvcUriComponentsBuilder
                .fromMethodName(ReportCardController.class, "getFile", uploadPath.getFileName().toString()).build().toString();

        Resource file = reportService.load(uploadPath.getFileName().toString());
        System.out.println(url);
        return ResponseEntity.ok()
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);


    }

    @PostMapping("/report-card/class/{class_id}")
    public ResponseEntity<Resource> generateClassReport(@RequestParam("term") String term,
                                                   @PathVariable("class_id") String class_id,
                                                   @RequestParam("fileFormat") String fileFormat) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("class_id", class_id);
        params.put("term", term);

        String fileName = "class_report_card";
        String reportPath  = reportService.generateReport(fileName, fileFormat,params);
        Path uploadPath = Paths.get(reportPath);
        if (!Files.exists(uploadPath)) {
            System.out.println("file not found");
        }
        String url = MvcUriComponentsBuilder
                .fromMethodName(ReportCardController.class, "getFile", uploadPath.getFileName().toString()).build().toString();

        Resource file = reportService.load(uploadPath.getFileName().toString());
        System.out.println(url);
        return ResponseEntity.ok()
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    /**
     * Get Class Exam Time Table
     * @param class_id
     * @param fileFormat
     * @return
     * @throws IOException
     */

    @GetMapping("/time-table/class/{class_id}")
    public ResponseEntity<Resource> generateTimeTable(@PathVariable("class_id") String class_id,
                                                      @RequestParam("exam_type_id") String exam_type_id,
                                                      @RequestParam("fileFormat") String fileFormat) throws IOException{
        Map<String, Object> params = new HashMap<>();
        params.put("class_id", class_id);
        params.put("exam_type_id", exam_type_id);

        String fileName = "exam_time_table";
        String reportPath  = reportService.generateReport(fileName, fileFormat,params);
        Path uploadPath = Paths.get(reportPath);
        Resource file = reportService.load(uploadPath.getFileName().toString());
        return ResponseEntity.ok()
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = reportService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
