package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Collins K. Sang
 * 5/11/22 11:38 AM
 * school
 * RolesDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
public class RolesDto {
    private UUID role_id;
    private String role_name;
    private String description;
}
