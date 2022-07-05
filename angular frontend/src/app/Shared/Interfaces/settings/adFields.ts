import { AdFieldsSubItems } from './adFieldsSubItems';

export class AdFieldsItems {
    id: number | null;
    name: string;
    callToActionButtonTitle: any=false;
    viwableInStatus:boolean=false;
    viewableInHornBill:boolean=false;
    viewableInFireplace:boolean=false;
    viewableInConnect:boolean=false;
    hasCallToActionButton:boolean=false;
    viewableInTicker:boolean=false;
    hasMedia:boolean=false;
    campaignType
    campaignObjectiveAddFields: AdFieldsSubItems; 
         
    constructor() {
    }
}