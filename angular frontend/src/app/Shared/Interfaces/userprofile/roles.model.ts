import { Permission } from './permission.model';
export class Roles  {
    id: number;
    name: string;
    permissions: Array<Permission>;
    constructor() {
    }
  }