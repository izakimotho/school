import { IAcademicYear } from "./IAcademicYear";
import { ITerm } from "./ITerm"

export interface ITermDetails {
  term_details_id: string;
  terms: ITerm;
  name: string;
  term_description: string;
  date_from: Date;
  date_to: Date;
  academic_year: IAcademicYear;
  status: boolean;
}


