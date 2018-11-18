import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Comuna } from './comuna.model';
import { ComunaService } from './comuna.service';

@Component({
    selector: 'jhi-comuna-detail',
    templateUrl: './comuna-detail.component.html'
})
export class ComunaDetailComponent implements OnInit, OnDestroy {

    comuna: Comuna;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private comunaService: ComunaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInComunas();
    }

    load(id) {
        this.comunaService.find(id).subscribe((comuna) => {
            this.comuna = comuna;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInComunas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'comunaListModification',
            (response) => this.load(this.comuna.id)
        );
    }
}
