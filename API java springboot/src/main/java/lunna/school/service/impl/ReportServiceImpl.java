package lunna.school.service.impl;

import lombok.RequiredArgsConstructor;
import lunna.school.exception.ExpectationFailedException;
import lunna.school.reports.ReportExporter;
import lunna.school.reports.ReportFiller;
import lunna.school.repository.ReportCardViewRepository;
import lunna.school.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Map;
import java.util.Set;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/11/22, Wednesday
 **/
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    @Autowired
    ReportFiller reportFiller;
    @Autowired
    ReportExporter exporter;
    //    private final ReportCardViewRepository repository;
    private final Path root = Paths.get(System.getProperty("java.io.tmpdir") + File.separator + "school");

    @Override
    public void init() {
        System.out.println(root.toUri());
        if (!Files.exists(root)) {
            try {
                Files.createDirectory(root);
                System.out.println("Directory created");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            System.out.println("Directory already exists");
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    @Override
    public String generateReport(String fileName, String fileFormat, Map<String, Object> params) throws IOException {
        reportFiller.setReportFileName(fileName + ".jrxml");
        reportFiller.compileReport();
        reportFiller.setParameters(params);
        reportFiller.fillReport();
        exporter.setJasperPrint(reportFiller.getJasperPrint());
        String finalName = root + "/" + fileName + "_" + System.currentTimeMillis();
        String reportFilePath = null;

        switch (fileFormat) {
            case "pdf":
                reportFilePath = finalName + ".pdf";
                exporter.exportToPdf(reportFilePath, "Koryr");
                break;
            case "html":
                reportFilePath = finalName + ".html";
                exporter.exportToHtml(reportFilePath);
                break;
            case "xlsx":
                reportFilePath = finalName + ".xlsx";
                exporter.exportToXlsx(reportFilePath, "Report Data");
                break;
            case "csv":
                reportFilePath = finalName + ".csv";
                exporter.exportToCsv(reportFilePath);
                break;


        }
        return reportFilePath;
    }

    @Override
    public void clear() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

}
