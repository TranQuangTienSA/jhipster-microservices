import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { IrModel } from './ir-model.model';
import { IrModelPopupService } from './ir-model-popup.service';
import { IrModelService } from './ir-model.service';

@Component({
    selector: 'jhi-ir-model-delete-dialog',
    templateUrl: './ir-model-delete-dialog.component.html'
})
export class IrModelDeleteDialogComponent {

    irModel: IrModel;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private irModelService: IrModelService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['irModel']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.irModelService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'irModelListModification',
                content: 'Deleted an irModel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ir-model-delete-popup',
    template: ''
})
export class IrModelDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private irModelPopupService: IrModelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.irModelPopupService
                .open(IrModelDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
