export class Menu {
    id: number ;
    linktext: string;
    iconclass:string;
    icon: string;
    child: boolean;
    submenu: Submenu[];
    constructor() {
    }
  }

  export class Submenu {
    id: number;
    linktext: string;
    link:string; 
    constructor() {
    }
  }
 