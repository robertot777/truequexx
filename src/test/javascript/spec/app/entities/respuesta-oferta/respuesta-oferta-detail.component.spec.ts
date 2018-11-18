/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TruequepTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RespuestaOfertaDetailComponent } from '../../../../../../main/webapp/app/entities/respuesta-oferta/respuesta-oferta-detail.component';
import { RespuestaOfertaService } from '../../../../../../main/webapp/app/entities/respuesta-oferta/respuesta-oferta.service';
import { RespuestaOferta } from '../../../../../../main/webapp/app/entities/respuesta-oferta/respuesta-oferta.model';

describe('Component Tests', () => {

    describe('RespuestaOferta Management Detail Component', () => {
        let comp: RespuestaOfertaDetailComponent;
        let fixture: ComponentFixture<RespuestaOfertaDetailComponent>;
        let service: RespuestaOfertaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TruequepTestModule],
                declarations: [RespuestaOfertaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RespuestaOfertaService,
                    JhiEventManager
                ]
            }).overrideTemplate(RespuestaOfertaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RespuestaOfertaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RespuestaOfertaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RespuestaOferta(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.respuestaOferta).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
