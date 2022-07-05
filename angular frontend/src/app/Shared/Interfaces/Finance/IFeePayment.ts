import { ITermDetails } from "../school/ITermDetails"
import { IStudent } from "../student/IStudent" 
export interface IFeePayment { 
  term: ITermDetails;
  student: IStudent;
  fee_amount: number;
}
