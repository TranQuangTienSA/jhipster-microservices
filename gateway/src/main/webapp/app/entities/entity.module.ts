import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GatewayAddressModule } from './address/address.module';
import { GatewayBusinessUnitModule } from './business-unit/business-unit.module';
import { GatewayCustomerModule } from './customer/customer.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GatewayAddressModule,
        GatewayBusinessUnitModule,
        GatewayCustomerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayEntityModule {}
