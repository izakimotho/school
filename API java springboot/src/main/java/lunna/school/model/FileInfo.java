package lunna.school.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * @author : HAron Korir
 * @mailto : koryrh@gmail.com
 * @created : 5/12/22, Thursday
 **/
@Getter
@Setter
public class FileInfo {
    private String name;
    private String url;
    public FileInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
