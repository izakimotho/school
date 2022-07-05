export interface IAppConfig {

    env: {
        name: string
    }

    apiServer: {
        link1:string,
        link2:string,
    }

    endpoint:{
        apiUrl:string,
        authport:string,
        adsport:string,
        vendorport:string,  
        bulksmsport:string,      
        appName:string,
        version:string,
        lang:[] 

    }
}
