import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RespuestaOferta } from './respuesta-oferta.model';
import { RespuestaOfertaService } from './respuesta-oferta.service';

@Component({
    selector: 'jhi-respuesta-oferta-detail',
    templateUrl: './respuesta-oferta-detail.component.html'
})
export class RespuestaOfertaDetailComponent implements OnInit, OnDestroy {

    respuestaOferta: RespuestaOferta;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private respuestaOfertaService: RespuestaOfertaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRespuestaOfertas();
    }

    load(id) {
        this.respuestaOfertaService.find(id).subscribe((respuestaOferta) => {
            this.respuestaOferta = respuestaOferta;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRespuestaOfertas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'respuestaOfertaListModification',
            (response) => this.load(this.respuestaOferta.id)
        );
    }
}
