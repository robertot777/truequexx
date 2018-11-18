import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Comuna } from './comuna.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ComunaService {

    private resourceUrl = SERVER_API_URL + 'api/comunas';

    constructor(private http: Http) { }

    create(comuna: Comuna): Observable<Comuna> {
        const copy = this.convert(comuna);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(comuna: Comuna): Observable<Comuna> {
        const copy = this.convert(comuna);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Comuna> {
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
     * Convert a returned JSON object to Comuna.
     */
    private convertItemFromServer(json: any): Comuna {
        const entity: Comuna = Object.assign(new Comuna(), json);
        return entity;
    }

    /**
     * Convert a Comuna to a JSON which can be sent to the server.
     */
    private convert(comuna: Comuna): Comuna {
        const copy: Comuna = Object.assign({}, comuna);
        return copy;
    }
}
