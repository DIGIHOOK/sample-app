import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SampleAppAccountJEDIModule } from './account-jedi/account-jedi.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SampleAppAccountJEDIModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SampleAppEntityModule {}
