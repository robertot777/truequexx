import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TruequepSharedModule } from '../../shared';
import {
    ComunaService,
    ComunaPopupService,
    ComunaComponent,
    ComunaDetailComponent,
    ComunaDialogComponent,
    ComunaPopupComponent,
    ComunaDeletePopupComponent,
    ComunaDeleteDialogComponent,
    comunaRoute,
    comunaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...comunaRoute,
    ...comunaPopupRoute,
];

@NgModule({
    imports: [
        TruequepSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ComunaComponent,
        ComunaDetailComponent,
        ComunaDialogComponent,
        ComunaDeleteDialogComponent,
        ComunaPopupComponent,
        ComunaDeletePopupComponent,
    ],
    entryComponents: [
        ComunaComponent,
        ComunaDialogComponent,
        ComunaPopupComponent,
        ComunaDeleteDialogComponent,
        ComunaDeletePopupComponent,
    ],
    providers: [
        ComunaService,
        ComunaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TruequepComunaModule {}
