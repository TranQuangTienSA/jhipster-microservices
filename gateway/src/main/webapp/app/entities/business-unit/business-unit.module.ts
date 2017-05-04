import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    BusinessUnitService,
    BusinessUnitPopupService,
    BusinessUnitComponent,
    BusinessUnitDetailComponent,
    BusinessUnitDialogComponent,
    BusinessUnitPopupComponent,
    BusinessUnitDeletePopupComponent,
    BusinessUnitDeleteDialogComponent,
    businessUnitRoute,
    businessUnitPopupRoute,
    BusinessUnitResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...businessUnitRoute,
    ...businessUnitPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BusinessUnitComponent,
        BusinessUnitDetailComponent,
        BusinessUnitDialogComponent,
        BusinessUnitDeleteDialogComponent,
        BusinessUnitPopupComponent,
        BusinessUnitDeletePopupComponent,
    ],
    entryComponents: [
        BusinessUnitComponent,
        BusinessUnitDialogComponent,
        BusinessUnitPopupComponent,
        BusinessUnitDeleteDialogComponent,
        BusinessUnitDeletePopupComponent,
    ],
    providers: [
        BusinessUnitService,
        BusinessUnitPopupService,
        BusinessUnitResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayBusinessUnitModule {}
