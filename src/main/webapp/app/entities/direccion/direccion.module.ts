import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TruequepSharedModule } from '../../shared';
import { TruequepAdminModule } from '../../admin/admin.module';
import {
    DireccionService,
    DireccionPopupService,
    DireccionComponent,
    DireccionDetailComponent,
    DireccionDialogComponent,
    DireccionPopupComponent,
    DireccionDeletePopupComponent,
    DireccionDeleteDialogComponent,
    direccionRoute,
    direccionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...direccionRoute,
    ...direccionPopupRoute,
];

@NgModule({
    imports: [
        TruequepSharedModule,
        TruequepAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DireccionComponent,
        DireccionDetailComponent,
        DireccionDialogComponent,
        DireccionDeleteDialogComponent,
        DireccionPopupComponent,
        DireccionDeletePopupComponent,
    ],
    entryComponents: [
        DireccionComponent,
        DireccionDialogComponent,
        DireccionPopupComponent,
        DireccionDeleteDialogComponent,
        DireccionDeletePopupComponent,
    ],
    providers: [
        DireccionService,
        DireccionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruequepDireccionModule {}
