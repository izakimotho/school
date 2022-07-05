package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lunna.school.model.Organization;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/19/22 9:22 AM
 * school
 * TermsDto
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class TermsDto {
    private String term_id;
    private String name;
    private String description;
    private SchoolDto organization;

    public TermsDto(){}

    public TermsDto(String term_id, String name, String description, SchoolDto organization) {
        this.term_id = term_id;
        this.name = name;
        this.description = description;
        this.organization = organization;
    }
}
