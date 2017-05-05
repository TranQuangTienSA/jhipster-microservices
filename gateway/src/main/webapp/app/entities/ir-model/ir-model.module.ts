import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    IrModelService,
    IrModelPopupService,
    IrModelComponent,
    IrModelDetailComponent,
    IrModelDialogComponent,
    IrModelPopupComponent,
    IrModelDeletePopupComponent,
    IrModelDeleteDialogComponent,
    irModelRoute,
    irModelPopupRoute,
    IrModelResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...irModelRoute,
    ...irModelPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        IrModelComponent,
        IrModelDetailComponent,
        IrModelDialogComponent,
        IrModelDeleteDialogComponent,
        IrModelPopupComponent,
        IrModelDeletePopupComponent,
    ],
    entryComponents: [
        IrModelComponent,
        IrModelDialogComponent,
        IrModelPopupComponent,
        IrModelDeleteDialogComponent,
        IrModelDeletePopupComponent,
    ],
    providers: [
        IrModelService,
        IrModelPopupService,
        IrModelResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayIrModelModule {}
