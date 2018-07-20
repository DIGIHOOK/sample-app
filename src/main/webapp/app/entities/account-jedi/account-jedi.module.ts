import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SampleAppSharedModule } from 'app/shared';
import {
    AccountJEDIComponent,
    AccountJEDIDetailComponent,
    AccountJEDIUpdateComponent,
    AccountJEDIDeletePopupComponent,
    AccountJEDIDeleteDialogComponent,
    accountJEDIRoute,
    accountJEDIPopupRoute
} from './';

const ENTITY_STATES = [...accountJEDIRoute, ...accountJEDIPopupRoute];

@NgModule({
    imports: [SampleAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AccountJEDIComponent,
        AccountJEDIDetailComponent,
        AccountJEDIUpdateComponent,
        AccountJEDIDeleteDialogComponent,
        AccountJEDIDeletePopupComponent
    ],
    entryComponents: [AccountJEDIComponent, AccountJEDIUpdateComponent, AccountJEDIDeleteDialogComponent, AccountJEDIDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SampleAppAccountJEDIModule {}
