import { IAcademicYear } from "../school/IAcademicYear"
import { IClasses } from "../school/IClasses"
import { ITermDetails } from "../school/ITermDetails"
import { IVoteHead } from "./IVoteHead"

export interface IFeeStructure {
  fee_structure_id: string;
  voteHead: IVoteHead;
  classModel: IClasses;
  termDetails: ITermDetails;
  academicYear: IAcademicYear;
  amount: number;
}

