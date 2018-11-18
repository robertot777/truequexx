import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TruequepSharedModule } from '../../shared';
import { TruequepAdminModule } from '../../admin/admin.module';
import {
    OfertaService,
    OfertaPopupService,
    OfertaComponent,
    OfertaDetailComponent,
    OfertaDialogComponent,
    OfertaPopupComponent,
    OfertaDeletePopupComponent,
    OfertaDeleteDialogComponent,
    ofertaRoute,
    ofertaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ofertaRoute,
    ...ofertaPopupRoute,
];

@NgModule({
    imports: [
        TruequepSharedModule,
        TruequepAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OfertaComponent,
        OfertaDetailComponent,
        OfertaDialogComponent,
        OfertaDeleteDialogComponent,
        OfertaPopupComponent,
        OfertaDeletePopupComponent,
    ],
    entryComponents: [
        OfertaComponent,
        OfertaDialogComponent,
        OfertaPopupComponent,
        OfertaDeleteDialogComponent,
        OfertaDeletePopupComponent,
    ],
    providers: [
        OfertaService,
        OfertaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruequepOfertaModule {}
