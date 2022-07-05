# Lunna SMIS
npm i "flatpickr": "^4.6.3",
npm i "angularx-flatpickr": "^6.2.0",


# LunnaSMIS
This project was generated with [Angular CLI] version 11.2.10. 
It is based on Bootstrap 5 CSS and Angular UI Kit.



## Table of Contents

* [Versions](#versions)
* [Demo](#demo)
* [Documentation](#documentation)
* [Get Started](#getstarted)
* [Quick Start](#quick-start)
* [Files and folders](#files-and-folders)
* [Browser Support](#browser-support)
* [Reporting Issues](#reporting-issues)
* [Licensing](#licensing)
* [Useful Links](#useful-links)
* [Resources](#resources)
* [Plugins](#plugins) 


## Versions
 ![version](https://img.shields.io/badge/version-1.1.0-blue.svg) ![license](https://img.shields.io/badge/license-MIT-blue.svg) <a href="#" target="_blank">

 ## Demo

| Dashboard Page | Icons Page | User Profile Page  | Tables Page | Login Page | Register Page  |
| --- | --- | ---  | --- | --- | ---  |
|


## Documentation
The documentation for the Lunna Portal is hosted at our [website](https://app.lunna.chat).


### Get Started

- Install NodeJS **LTS** version from NodeJs Official Page</a>
- Download the product on this page
- Unzip the downloaded file to a folder in your computer
- Open Terminal
- Go to your file project (where you’ve unzipped the product)
- (If you are on a linux based terminal) Simply run `npm run install:clean`
- (If not) Run in terminal `npm install`
- (If not) Run in terminal `npm run build` 
- (If not) Run in terminal `npm start`
- (If not) Run in terminal `npm install clean`

- Navigate to https://localhost:4200
   ng serve --host 0.0.0.0 --port 4200 -o


## Files and Folder

This is the project structure that you will get upon the download:
 
This is the project structure that you will get upon the download:
```
IED 
.
├── CHANGELOG.md
├── ISSUE_TEMPLATE.md
├── Dockerfile
├── Dockerfile.bk.apache
├── Dockerfile.bk
├── Dockerfile.bk
├── LICENSE.md
├── README.md
├── angular.json
├── browserslist
├── e2e
│   ├── protractor.conf.js
│   ├── src
│   │   ├── app.e2e-spec.ts
│   │   └── app.po.ts
│   └── tsconfig.json
├── certs
│   ├── cert.pm
│   └── chain.pm
│   └── fullchain.pm
│   └── privkey.pm
│   └── httpd.conf.pm
│   ├── certsbk23042021
│   │   ├── cert.pm
│   │   └── chain.pm
│   │   └── fullchain.pm
│   │   └── privkey.pm
│   ├── src
│   │   ├── app.e2e-spec.ts
│   │   └── app.po.ts
│   └── tsconfig.json
├── karma.conf.js
├── package.json
├── src
│   ├── app
│   │   ├── app-routing.module.ts
│   │   ├── app.component.html
│   │   ├── app.component.ts
│   │   ├── app.module.ts
│   │   ├── Shared
│   │   │   ├── layouts
│   │   │   │   ├── adminLayout
│   │   │   │   │   ├── adminLayout.component.html
│   │   │   │   │   └── adminLayout.component.ts
│   │   │   │   ├── basicLayout
│   │   │   │   │   ├── basicLayout.component.html
│   │   │   │   │   └── basicLayout.component.ts
│   │   │   │   ├── blankLayout
│   │   │   │   │   ├── blankLayout.component.html
│   │   │   │   │   └── blankLayout.component.ts
│   │   │   │   ├── footer
│   │   │   │   │   ├── footer.component.html
│   │   │   │   │   └── footer.component.ts
│   │   │   │   ├── menubar
│   │   │   │   │   ├── menubar.component.html
│   │   │   │   │   └── menubar.component.ts
│   │   │   │   ├── navbar
│   │   │   │   │   ├── navbar.component.html
│   │   │   │   │   └── navbar.component.ts
│   │   │   │   ├── sidebar
│   │   │   │   │   ├── sidebar.component.html
│   │   │   │   │   └── sidebar.component.ts
│   │   │   │   └── layouts.module.ts
│   │   │   ├── loading
│   │   │   │   ├── loading.component.html
│   │   │   │   └── loading.component.ts
│   │   │   │   └── loading.service.ts
│   │   │   ├── messages
│   │   │   │   ├── messages.component.html
│   │   │   │   └── messages.component.ts
│   │   │   │   └── messages.service.ts
│   │   │   ├── pipes
│   │   │   │   ├── safe-url.pipe.ts 
│   │   │   ├── Interfaces
│   │   │   │   ├── userprofile
│   │   │   │   │   ├── departments.ts
│   │   │   │   │   ├── permission.model.ts
│   │   │   │   │   ├── roles.model.ts
│   │   │   │   │   ├── user.model.ts 
│   │   │   │   ├── app-config.ts 
│   │   │   │   ├── cam-details.ts 
│   │   │   │   ├── location_details.ts 
│   │   │   │   ├── photo.ts 
│   │   │   │   ├── targetProfile.ts 
│   │── Modules
│   │   ├── admin
│   │   │   ├── admin-routing.modules.ts
│   │   │   ├── admin.modules.ts
│   │   │   │   ├── dashboard
│   │   │   │   │   ├── dashboard.component.html
│   │   │   │   │   └── dashboard.component.ts
│   │   │   │   ├── settings
│   │   │   │   │   ├── settings.component.html
│   │   │   │   │   └── settings.component.ts
│   │   │   │   └── tables
│   │   │   │       ├── tables.component.html
│   │   │   │       └── tables.component.ts
│   │   ├── bulk-sms
│   │   │   ├── bulk-sms-routing.modules.ts
│   │   │   ├── bulk-sms.modules.ts
│   │   │   │   ├── dashboard
│   │   │   │   │   ├── dashboard.component.html
│   │   │   │   │   └── dashboard.component.ts
│   │   │   │   ├── contacts
│   │   │   │   │   ├── contacts.component.html
│   │   │   │   │   └── contacts.component.ts
│   │   │   │   └── topup
│   │   │   │       ├── topup.component.html
│   │   │   │       └── topup.component.ts
│   │   ├── auth
│   │   │   ├── login
│   │   │   │   ├── login.component.html
│   │   │   │   └── login.component.ts
│   │   │   └── register
│   │   │       ├── register.component.html
│   │   │       └── register.component.ts
│   │   ├── index
│   │   │   ├── index.component.html
│   │   │   └── index.component.ts
│   │   ├── landing
│   │   │   ├── landing.component.html
│   │   │   └── landing.component.ts
│   │   └── profile
│   │       ├── profile.component.html
│   │       └── profile.component.ts
│   │── Services
│   │   ├── guard
│   │   │   ├── auth-guard.guard.ts
│   │   │   ├── can-deactivate.guard.ts
│   │   ├── api.auth.services.ts
│   │   ├── api.services.ts 
│   │   ├── app-config.service.ts 
│   │   ├── selective-preloading-strategy.service.ts 
│   ├── assets
│   │   ├── config
│   │   │   ├── app.config.json
│   │   │   └── test_app.config.json
│   │   ├── fonts
│   │   │   ├── nucleo
│   │   │   │   ├── nucleo-icons.eot
│   │   │   │   └── nucleo-icons.svg
│   │   │   │   └── nucleo-icons.ttf
│   │   │   │   └── nucleo-icons.woff
│   │   │   │   └── nucleo-icons.woff2
│   │   ├── images
│   │   │   ├── brand..
│   │   │   ├── icons..
│   │   │   ├── theme.. 
│   │   ├── nucleo
│   │   │   ├── nucleo-icons.eot
│   │   │   └── nucleo-icons.svg
│   │   │   └── nucleo-icons.ttf
│   │   │   └── nucleo-icons.woff
│   │   │   └── nucleo-icons.woff2
│   │   └── scss
│   │   |   ├── lunna.css
│   │   |   └── custom..
│   │   │   │   |   ├── ..
│   │   |   └── core..
│   │   │   │   |   ├── ..
│   │   |   └── angular-differences..
│   │   │   │   |   ├── ..
│   │   └── vendor
│   │       ├── index.css
│   │       └── tailwind.css
│   ├── environments
│   │   ├── environment.prod.ts
│   │   └── environment.ts
│   ├── favicon.ico
│   ├── index.html
│   ├── main.ts
│   ├── polyfills.ts
│   ├── styles.css
│   └── test.ts
├── tailwind.config.js
├── tsconfig.app.json
├── tsconfig.json
├── tsconfig.spec.json
└── tslint.json
```
## Browser Support

At present, we officially aim to support the last two versions of the following browsers:

| Chrome | Firefox | Edge | Safari | Opera |
|:---:|:---:|:---:|:---:|:---:|


## Reporting Issues

We use GitHub Issues as the official bug tracker for the Notus Angular. Here are some advices for our users that want to report an issue:

1. Make sure that you are using the latest version of the Notus Angular. Check the CHANGELOG from your dashboard on our <a href="#/?ref=na-readme" target="_blank">website</a>.
2. Providing us reproducible steps for the issue will shorten the time it takes for it to be fixed.
3. Some issues may be browser specific, so specifying in what browser you encountered the issue might help.

## Licensing

- Copyright 2021 <a href="#" target="_blank">Smoke</a>

- Licensed under <a href="#" target="_blank">IED</a>

## Useful Links


- <a href="#" target="_blank">More products</a> from Smoke
- Check our Bundles <a href="#" target="_blank">here</a>
- Check our awesome builder <a href="#" target="_blank">here</a>
- Check Technology in the project behind this product <a href="#" target="_blank">here</a>

### Social Media

Twitter: <a href="https://twitter.com#" target="_blank">https://twitter.com/#</a>

Facebook: <a href="https://www.facebook.com/#" target="_blank">https://www.facebook.com/#</a>

Dribbble: <a href="https://dribbble.com/#" target="_blank">https://dribbble.com/#</a>

Instagram: <a href="https://www.instagram.com/#/" target="_blank">https://www.instagram.com/#/</a>


## Resources
- Demo: <a href="#" target="_blank">#Demo</a>
- Download Page: <a href="#" target="_blank">#/product/IED</a>
- Documentation: <a href="#" target="_blank">#/overview</a>
- License Agreement: <a href="#/" target="_blank">#/license?ref=na-readme</a>
- Support: <a href="" target="_blank">#/contact-us</a>
- Issues: <a href="#" target="_blank">Issues Page</a>



## Plugins

### sweet alert
npm install --save sweetalert2

### Install Ngx-ui-loader with npm
npm install --save ngx-ui-loader

### Install Charts with npm
npm install chart.js --save
###  Install ng-image-slider

### Install  datatable 
npm install jquery --save
npm install datatables.net --save
npm install datatables.net-dt --save
npm install angular-datatables --save
npm install @types/jquery --save-dev
npm install @types/datatables.net --save-dev 
 
### If you want to export excel files
npm install jszip --save
### JS file
npm install datatables.net-buttons --save
### CSS file
npm install datatables.net-buttons-dt --save
### Typings
npm install @types/datatables.net-buttons --save-dev
 

### google maps install
- npm install 
 --save
- npm install --save @types/googlemaps
- npm i @agm/core@1.1.0

npm install ng2-file-upload --save

###  ADD BELOW CODE INTO YOU ANGULAR.JSON FILE for Datatable
```...
    "styles": [
                ...
                "node_modules/datatables.net-dt/css/jquery.dataTables.css",
                "node_modules/bootstrap/dist/css/bootstrap.min.css",
                ],
                "scripts": [
                "node_modules/jquery/dist/jquery.js",
                "node_modules/datatables.net/js/jquery.dataTables.js",
                "node_modules/bootstrap/dist/js/bootstrap.js",
                
                "node_modules/jszip/dist/jszip.js",
                "node_modules/datatables.net-buttons/js/dataTables.buttons.js",
                "node_modules/datatables.net-buttons/js/buttons.colVis.js",
                "node_modules/datatables.net-buttons/js/buttons.flash.js",
                "node_modules/datatables.net-buttons/js/buttons.html5.js",
                "node_modules/datatables.net-buttons/js/buttons.print.js"
                ]
    ...
```


### Install compodoc with npm
npm install -g @compodoc/compodoc

###  Generate Source Code Documentation Using CLI

compodoc allows you to generate the documentation within seconds and can create a complete documentation report and architecture of your project.
Bash

### Run compodoc in your project (generated with Angular CLI for example) and serve it
compodoc -p src/tsconfig.app.json -s

 

