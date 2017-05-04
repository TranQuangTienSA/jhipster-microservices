import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { BusinessUnit } from './business-unit.model';
import { BusinessUnitPopupService } from './business-unit-popup.service';
import { BusinessUnitService } from './business-unit.service';
import { Address, AddressService } from '../address';

@Component({
    selector: 'jhi-business-unit-dialog',
    templateUrl: './business-unit-dialog.component.html'
})
export class BusinessUnitDialogComponent implements OnInit {

    businessUnit: BusinessUnit;
    authorities: any[];
    isSaving: boolean;

    billtoaddresses: Address[];

    shiptoaddresses: Address[];

    parents: BusinessUnit[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private businessUnitService: BusinessUnitService,
        private addressService: AddressService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['businessUnit']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.addressService.query({filter: 'businessunit-is-null'}).subscribe((res: Response) => {
            if (!this.businessUnit.billToAddressId) {
                this.billtoaddresses = res.json();
            } else {
                this.addressService.find(this.businessUnit.billToAddressId).subscribe((subRes: Address) => {
                    this.billtoaddresses = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.addressService.query({filter: 'businessunit-is-null'}).subscribe((res: Response) => {
            if (!this.businessUnit.shipToAddressId) {
                this.shiptoaddresses = res.json();
            } else {
                this.addressService.find(this.businessUnit.shipToAddressId).subscribe((subRes: Address) => {
                    this.shiptoaddresses = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.businessUnitService.query({filter: 'businessunit-is-null'}).subscribe((res: Response) => {
            if (!this.businessUnit.parentId) {
                this.parents = res.json();
            } else {
                this.businessUnitService.find(this.businessUnit.parentId).subscribe((subRes: BusinessUnit) => {
                    this.parents = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.businessUnit.id !== undefined) {
            this.businessUnitService.update(this.businessUnit)
                .subscribe((res: BusinessUnit) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.businessUnitService.create(this.businessUnit)
                .subscribe((res: BusinessUnit) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: BusinessUnit) {
        this.eventManager.broadcast({ name: 'businessUnitListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackAddressById(index: number, item: Address) {
        return item.id;
    }

    trackBusinessUnitById(index: number, item: BusinessUnit) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-business-unit-popup',
    template: ''
})
export class BusinessUnitPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private businessUnitPopupService: BusinessUnitPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.businessUnitPopupService
                    .open(BusinessUnitDialogComponent, params['id']);
            } else {
                this.modalRef = this.businessUnitPopupService
                    .open(BusinessUnitDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
