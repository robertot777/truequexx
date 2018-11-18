/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TruequepTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ComunaDetailComponent } from '../../../../../../main/webapp/app/entities/comuna/comuna-detail.component';
import { ComunaService } from '../../../../../../main/webapp/app/entities/comuna/comuna.service';
import { Comuna } from '../../../../../../main/webapp/app/entities/comuna/comuna.model';

describe('Component Tests', () => {

    describe('Comuna Management Detail Component', () => {
        let comp: ComunaDetailComponent;
        let fixture: ComponentFixture<ComunaDetailComponent>;
        let service: ComunaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TruequepTestModule],
                declarations: [ComunaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ComunaService,
                    JhiEventManager
                ]
            }).overrideTemplate(ComunaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ComunaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComunaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Comuna(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.comuna).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
