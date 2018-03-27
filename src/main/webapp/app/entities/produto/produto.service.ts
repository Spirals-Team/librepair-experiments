import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Produto } from './produto.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Produto>;

@Injectable()
export class ProdutoService {

    private resourceUrl =  SERVER_API_URL + 'api/produtos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/produtos';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(produto: Produto): Observable<EntityResponseType> {
        const copy = this.convert(produto);
        return this.http.post<Produto>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(produto: Produto): Observable<EntityResponseType> {
        const copy = this.convert(produto);
        return this.http.put<Produto>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Produto>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Produto[]>> {
        const options = createRequestOption(req);
        return this.http.get<Produto[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Produto[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Produto[]>> {
        const options = createRequestOption(req);
        return this.http.get<Produto[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Produto[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Produto = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Produto[]>): HttpResponse<Produto[]> {
        const jsonResponse: Produto[] = res.body;
        const body: Produto[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Produto.
     */
    private convertItemFromServer(produto: Produto): Produto {
        const copy: Produto = Object.assign({}, produto);
        copy.dataCadastro = this.dateUtils
            .convertLocalDateFromServer(produto.dataCadastro);
        return copy;
    }

    /**
     * Convert a Produto to a JSON which can be sent to the server.
     */
    private convert(produto: Produto): Produto {
        const copy: Produto = Object.assign({}, produto);
        copy.dataCadastro = this.dateUtils
            .convertLocalDateToServer(produto.dataCadastro);
        return copy;
    }
}
