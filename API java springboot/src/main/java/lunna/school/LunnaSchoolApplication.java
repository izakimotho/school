package lunna.school;

import lunna.school.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class LunnaSchoolApplication implements CommandLineRunner {
    @Autowired
    ReportService reportService;
    public static void main(String[] args) {
        SpringApplication.run(LunnaSchoolApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
//        reportService.clear();
        reportService.init();
    }
}
