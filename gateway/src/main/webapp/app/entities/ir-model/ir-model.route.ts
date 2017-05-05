import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { IrModelComponent } from './ir-model.component';
import { IrModelDetailComponent } from './ir-model-detail.component';
import { IrModelPopupComponent } from './ir-model-dialog.component';
import { IrModelDeletePopupComponent } from './ir-model-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class IrModelResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const irModelRoute: Routes = [
  {
    path: 'ir-model',
    component: IrModelComponent,
    resolve: {
      'pagingParams': IrModelResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'gatewayApp.irModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'ir-model/:id',
    component: IrModelDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'gatewayApp.irModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const irModelPopupRoute: Routes = [
  {
    path: 'ir-model-new',
    component: IrModelPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'gatewayApp.irModel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'ir-model/:id/edit',
    component: IrModelPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'gatewayApp.irModel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'ir-model/:id/delete',
    component: IrModelDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'gatewayApp.irModel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
