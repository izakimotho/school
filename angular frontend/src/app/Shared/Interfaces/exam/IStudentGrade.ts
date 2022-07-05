import { IHostel } from "../school/IHostel";
import { IPosition } from "../school/IPositions";
import { ISchool } from "../school/ISchool";
import { ISchoolStream } from "../school/ISchoolStream"; 
import { IGradePosting } from "./IGradePosting";

export interface  IStudentGrade {
   
  student_id: string;
  staff_id: string;
  first_name: string;
  middle_name: string;
  surname: string;  
  school_stream:ISchoolStream;
  gradePosting:IGradePosting ; 
  // grade: string,
  // remarks: string, 
  }
  
 


  