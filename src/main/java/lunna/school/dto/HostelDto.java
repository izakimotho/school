package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.Organization;
import lunna.school.model.Staff;
import lunna.school.model.Student;

import java.util.Date;
import java.util.UUID;

/**
 * IntelliJ IDEA
 * school
 * HostelDto
 *
 * @author Collins K. Sang
 * 2021/07/02 11:29
 **/
@Getter
@Setter
@ToString
public class HostelDto {
    private UUID hostel_id;
    private String hostel_name;
    private Integer hostel_capacity;
    private String description;
    private Organization org_id;
    private String created_by;
    private Date created_at;
    private Student hostel_captain;
    private Staff hostel_master;


    public HostelDto() {
    }

    public HostelDto(UUID hostel_id, String hostel_name, Integer hostel_capacity, String description, Organization org_id, String created_by, Date created_at, Student hostel_captain, Staff hostel_master) {
        this.hostel_id = hostel_id;
        this.hostel_name = hostel_name;
        this.hostel_capacity = hostel_capacity;
        this.description = description;
        this.org_id = org_id;
        this.created_by = created_by;
        this.created_at = created_at;
        this.hostel_captain = hostel_captain;
        this.hostel_master = hostel_master;
    }
}
