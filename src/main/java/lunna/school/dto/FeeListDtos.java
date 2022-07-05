package lunna.school.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * Collins K. Sang
 * 5/17/22 10:49 AM
 * school
 * FeeListDtos
 * IntelliJ IDEA
 **/
@Getter
@Setter
@ToString
public class FeeListDtos {
   private UUID student_fee_id;
   private StudentDto student_id;
   private SchoolDto org_id;
   private ClassModelDto class_id;
   private SchoolStreamDto school_stream_id;
   private AcademicYearDto year;
   private TermDetailsDtos terms;
   private float fee_amount;
   private float balance;
   private UserDto created_by;

   public FeeListDtos() {
   }

   public FeeListDtos(UUID student_fee_id, StudentDto student_id, SchoolDto org_id, ClassModelDto class_id, SchoolStreamDto school_stream_id, AcademicYearDto year, TermDetailsDtos terms, float fee_amount, float balance, UserDto created_by) {
      this.student_fee_id = student_fee_id;
      this.student_id = student_id;
      this.org_id = org_id;
      this.class_id = class_id;
      this.school_stream_id = school_stream_id;
      this.year = year;
      this.terms = terms;
      this.fee_amount = fee_amount;
      this.balance = balance;
      this.created_by = created_by;
   }
}
