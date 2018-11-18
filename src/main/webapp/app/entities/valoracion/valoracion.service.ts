import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Valoracion } from './valoracion.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ValoracionService {

    private resourceUrl = SERVER_API_URL + 'api/valoracions';

    constructor(private http: Http) { }

    create(valoracion: Valoracion): Observable<Valoracion> {
        const copy = this.convert(valoracion);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(valoracion: Valoracion): Observable<Valoracion> {
        const copy = this.convert(valoracion);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Valoracion> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Valoracion.
     */
    private convertItemFromServer(json: any): Valoracion {
        const entity: Valoracion = Object.assign(new Valoracion(), json);
        return entity;
    }

    /**
     * Convert a Valoracion to a JSON which can be sent to the server.
     */
    private convert(valoracion: Valoracion): Valoracion {
        const copy: Valoracion = Object.assign({}, valoracion);
        return copy;
    }
}
