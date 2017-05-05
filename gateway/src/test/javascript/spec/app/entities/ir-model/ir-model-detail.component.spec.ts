import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { GatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { IrModelDetailComponent } from '../../../../../../main/webapp/app/entities/ir-model/ir-model-detail.component';
import { IrModelService } from '../../../../../../main/webapp/app/entities/ir-model/ir-model.service';
import { IrModel } from '../../../../../../main/webapp/app/entities/ir-model/ir-model.model';

describe('Component Tests', () => {

    describe('IrModel Management Detail Component', () => {
        let comp: IrModelDetailComponent;
        let fixture: ComponentFixture<IrModelDetailComponent>;
        let service: IrModelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [IrModelDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    IrModelService,
                    EventManager
                ]
            }).overrideComponent(IrModelDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IrModelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IrModelService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new IrModel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.irModel).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
