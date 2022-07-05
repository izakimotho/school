package lunna.school.service;

import io.minio.MinioClient;
import io.minio.messages.Bucket;
import lunna.school.model.Filez;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;
import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URLConnection;
import java.util.List;
/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 17. Aug 2021 9:13 AM
 **/

@Service
public class MinioAdapter {

    @Autowired
    MinioClient minioClient;

    @Value("${spring.minio.bucket}")
    String defaultBucketName;

    @Value("${minio.default.folder}")
    String defaultBaseFolder;

    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


    public Object uploadFile(String name, byte[] content) {
        long millis = System.currentTimeMillis();

        String rndchars = RandomStringUtils.randomAlphanumeric(10);
        String extension = FilenameUtils.getExtension(name);
        String filename = rndchars + "_" + millis+"."+extension;

        File file = new File("/tmp/" + filename);
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        file.canWrite();
        file.canRead();
        try {
            FileOutputStream iofs = new FileOutputStream(file);
            iofs.write(content);

            System.out.println(file.getAbsolutePath()+" "+mimeType);
            minioClient.putObject(defaultBucketName, defaultBaseFolder + filename, file.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        Filez filez = new Filez();
        filez.setFile_ext(extension);
        filez.setFile_name(filename);
        filez.setFile_type(mimeType);
        return filez;

    }

    public byte[] getFile(String key) {
        try {
            InputStream obj = minioClient.getObject(defaultBucketName, defaultBaseFolder + "/" + key);

            byte[] content = IOUtils.toByteArray(obj);
            obj.close();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostConstruct
    public void init() {
    }
}
