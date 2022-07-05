import { IAcademicYear } from "../school/IAcademicYear"
import { IClasses } from "../school/IClasses"
import { ITermDetails } from "../school/ITermDetails"
import { IVoteHead } from "./IVoteHead"

export interface IFeeStructureDTO {
  fee_structure_id: string;
  fee_total_amount: number;
  class_model: IClasses; 
  academic_year: IAcademicYear;
  term_fee: ITerm_Fee_Details[];
}


  export interface ITerm_Fee_Details {
    term_name: string;
    term_total_amount: number;
    vote_heads: Ivote_heads[];
  }
  export interface Ivote_heads {
    fee_structure_id: string;
    amount: number;
    vote_head_name: string;    
  }

//   "term_fee": [
//     {
//         "term_name": "term One",
//         "term_total_amount": 0.0,
//         "vote_heads": [
//             {
//                 "fee_structure_id": "725141e3-5f83-468e-a7c2-b0ea5610ab84",
//                 "vote_head_name": "fee-vote",
//                 "amount": 0.0
//             }
//         ]
//     }
// ]

