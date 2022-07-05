package lunna.school.model;

import lombok.Getter;

/**
 * IntelliJ IDEA
 * school
 * GenderEnum
 *
 * @author Collins K. Sang
 * 2021/07/21 12:04
 **/
@Getter
public enum GenderEnum {
    MALE("Male"),
    FEMALE("Female"),
    UNKNOWN("Prefer not to say");
    public final String label;

    GenderEnum(String label) {
        this.label = label;
    }
}
