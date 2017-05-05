import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { IrModel } from './ir-model.model';
import { IrModelPopupService } from './ir-model-popup.service';
import { IrModelService } from './ir-model.service';

@Component({
    selector: 'jhi-ir-model-dialog',
    templateUrl: './ir-model-dialog.component.html'
})
export class IrModelDialogComponent implements OnInit {

    irModel: IrModel;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private irModelService: IrModelService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['irModel']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.irModel.id !== undefined) {
            this.irModelService.update(this.irModel)
                .subscribe((res: IrModel) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.irModelService.create(this.irModel)
                .subscribe((res: IrModel) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: IrModel) {
        this.eventManager.broadcast({ name: 'irModelListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-ir-model-popup',
    template: ''
})
export class IrModelPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private irModelPopupService: IrModelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.irModelPopupService
                    .open(IrModelDialogComponent, params['id']);
            } else {
                this.modalRef = this.irModelPopupService
                    .open(IrModelDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
