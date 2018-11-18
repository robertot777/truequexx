/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TruequepTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OfertaDetailComponent } from '../../../../../../main/webapp/app/entities/oferta/oferta-detail.component';
import { OfertaService } from '../../../../../../main/webapp/app/entities/oferta/oferta.service';
import { Oferta } from '../../../../../../main/webapp/app/entities/oferta/oferta.model';

describe('Component Tests', () => {

    describe('Oferta Management Detail Component', () => {
        let comp: OfertaDetailComponent;
        let fixture: ComponentFixture<OfertaDetailComponent>;
        let service: OfertaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TruequepTestModule],
                declarations: [OfertaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OfertaService,
                    JhiEventManager
                ]
            }).overrideTemplate(OfertaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OfertaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OfertaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Oferta(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.oferta).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
