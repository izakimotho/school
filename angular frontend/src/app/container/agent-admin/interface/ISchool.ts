import { IEducationSystem } from "src/app/Shared/Interfaces/school/IEducationSystem";

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

export interface ICounty {
  county_id: number;
  county_name: string;
  code: string;
}

export interface ISubCounty {
  sub_county_id: number;
  sub_county_name: string;
  sub_code: string;
  county: ICounty;
}

export interface IWard {
  ward_id: number;
  ward_name: string;
  ward_code: string;
  sub_county: ISubCounty;
}


export interface ISponsor {
  school_sponsor_id: string;
  school_sponsor_name: string;
}

export interface ISchoolLevel {
  school_level_id: string;
  school_level_name: string;
}

export interface ISchoolCategory {
  school_category_id: string;
  school_category_name: string;
}

export interface ICluster {
  cluster_id: string;
  cluster_name: string;
}

export interface IGender {
  school_gender_id: string;
  school_gender_name: string;
}

export interface ISchoolType {
  school_type_id: string;
  school_type_name: string;
}
