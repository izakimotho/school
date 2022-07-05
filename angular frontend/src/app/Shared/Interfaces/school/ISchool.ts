import { ICluster } from "./ICluster";
import { ICounty } from "./ICounty";
import { IEducationSystem } from "./IEducationSystem";
import { IGender } from "./IGender";
import { ISchoolCategory } from "./ISchoolCategory";
import { ISchoolLevel } from "./ISchoolLevel";
import { ISchoolType } from "./ISchoolType";
import { ISponsor } from "./ISponsor";
import { ISubCounty } from "./ISubCounty";
import { IWard } from "./IWard";


export interface ISchool {
  org_id: string;
  org_name: string;
  code: string;
  county: ICounty;
  sub_county: ISubCounty;
  ward: IWard;
  education_system: IEducationSystem;
  school_sponsor: ISponsor;
  school_level: ISchoolLevel;
  school_category: ISchoolCategory;
  cluster: ICluster;
  gender: IGender;
  school_type: ISchoolType;
  logo: any;
  motto: string;
  email_address: string;
  postal_address: string;
  phone_numbers: any[];
  mobile_phone_numbers: any[];
  school_history: string;
}

 