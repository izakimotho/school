import { CampaignObjectiveType } from './campaignobjectivetype';

export class CampaignObjective {
    id: string;
    name: string;
    campaignObjectiveType: CampaignObjectiveType;
    callToActionButtonTitle: string;
    viwableInStatus:boolean;
    viewableInHornBill:boolean;
    viewableInFireplace:boolean;
    hasCallToActionButton: boolean;
    hasMedia:boolean;
    campaignObjectiveAddFields:any;

    // objective: string;
    // description:string;
    // updated_at: Date;
    // created_at: Date

    constructor() {
    }
}
