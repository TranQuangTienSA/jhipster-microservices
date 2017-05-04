import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { BusinessUnit } from './business-unit.model';
import { BusinessUnitService } from './business-unit.service';
@Injectable()
export class BusinessUnitPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private businessUnitService: BusinessUnitService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.businessUnitService.find(id).subscribe((businessUnit) => {
                this.businessUnitModalRef(component, businessUnit);
            });
        } else {
            return this.businessUnitModalRef(component, new BusinessUnit());
        }
    }

    businessUnitModalRef(component: Component, businessUnit: BusinessUnit): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.businessUnit = businessUnit;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
