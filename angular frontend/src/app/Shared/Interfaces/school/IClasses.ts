import { IEducationSystem } from "./IEducationSystem";
import { ISchoolLevel } from "./ISchoolLevel";

export interface IClasses {
    class_id: string;
    class_name: string;
    description: string;
    education_system: IEducationSystem;
    school_level: ISchoolLevel;
  }
  
 