package lunna.school.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 17. Aug 2021 9:12 AM
 **/

@Configuration
public class MinioConfig {
    @Value("${spring.minio.access-key}")
    String accessKey;
    @Value("${spring.minio.secret-key}")
    String accessSecret;
    @Value("${spring.minio.url}")
    String minioUrl;

    @Bean
    public MinioClient generateMinioClient() {
        try {
            MinioClient client = new MinioClient(minioUrl, accessKey, accessSecret);
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}