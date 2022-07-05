import { ISchool } from "src/app/container/agent-admin/interface/ISchool";
import { IGradePosting } from "../exam/IGradePosting";
import { IHostel } from "../school/IHostel";
import { IPosition } from "../school/IPositions";
import { ISchoolStream } from "../school/ISchoolStream"; 

export class IStudent {
    student_id: string;
    staff_id: string;
    first_name: string;
    middle_name: string;
    surname: string;
    gender: string;
    dob: string;
    guardians_name: string;
    guardian_phone: string;
    next_of_kin: string;
    kin_phone: string;
    kin_relationship: string;
    is_boarder: boolean;
    _boarder: boolean;
    admission_date: string;
    school_stream:ISchoolStream;
    hostel :IHostel;
    organization: ISchool;
    positions: IPosition;
    position_id;
    avatar: string;
    constructor() {
    }
  }
  



{
    
    
   
   
    
}
