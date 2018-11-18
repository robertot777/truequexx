import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Valoracion } from './valoracion.model';
import { ValoracionService } from './valoracion.service';

@Component({
    selector: 'jhi-valoracion-detail',
    templateUrl: './valoracion-detail.component.html'
})
export class ValoracionDetailComponent implements OnInit, OnDestroy {

    valoracion: Valoracion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private valoracionService: ValoracionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInValoracions();
    }

    load(id) {
        this.valoracionService.find(id).subscribe((valoracion) => {
            this.valoracion = valoracion;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInValoracions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'valoracionListModification',
            (response) => this.load(this.valoracion.id)
        );
    }
}
