/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TruequepTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ValoracionDetailComponent } from '../../../../../../main/webapp/app/entities/valoracion/valoracion-detail.component';
import { ValoracionService } from '../../../../../../main/webapp/app/entities/valoracion/valoracion.service';
import { Valoracion } from '../../../../../../main/webapp/app/entities/valoracion/valoracion.model';

describe('Component Tests', () => {

    describe('Valoracion Management Detail Component', () => {
        let comp: ValoracionDetailComponent;
        let fixture: ComponentFixture<ValoracionDetailComponent>;
        let service: ValoracionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TruequepTestModule],
                declarations: [ValoracionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ValoracionService,
                    JhiEventManager
                ]
            }).overrideTemplate(ValoracionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ValoracionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ValoracionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Valoracion(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.valoracion).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
