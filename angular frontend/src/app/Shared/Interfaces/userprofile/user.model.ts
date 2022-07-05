export class User  {
  id: null | number;
  phone_number: null | number;
  email:string;
  password: string;
  handle:string;
  bio:string;
  fixedUsername:string;
  username: string;
  profileUrl: string;
  wallpaperUrl:string;
  accountPrivate: boolean;
  roleid: number;
  roleName: string;
  userType: number=0;  
  followersCount: number=0;
  followingCount: number=0;
  passwordExpiry: null | number;
  createdAt: Date;
  constructor() {
  }
}