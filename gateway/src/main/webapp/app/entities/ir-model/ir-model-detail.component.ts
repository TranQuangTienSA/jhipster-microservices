import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { IrModel } from './ir-model.model';
import { IrModelService } from './ir-model.service';

@Component({
    selector: 'jhi-ir-model-detail',
    templateUrl: './ir-model-detail.component.html'
})
export class IrModelDetailComponent implements OnInit, OnDestroy {

    irModel: IrModel;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private irModelService: IrModelService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['irModel']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIrModels();
    }

    load(id) {
        this.irModelService.find(id).subscribe((irModel) => {
            this.irModel = irModel;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIrModels() {
        this.eventSubscriber = this.eventManager.subscribe('irModelListModification', (response) => this.load(this.irModel.id));
    }
}
