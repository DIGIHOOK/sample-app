import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountJEDI } from 'app/shared/model/account-jedi.model';

type EntityResponseType = HttpResponse<IAccountJEDI>;
type EntityArrayResponseType = HttpResponse<IAccountJEDI[]>;

@Injectable({ providedIn: 'root' })
export class AccountJEDIService {
    private resourceUrl = SERVER_API_URL + 'api/account-jedis';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/account-jedis';

    constructor(private http: HttpClient) {}

    create(accountJEDI: IAccountJEDI): Observable<EntityResponseType> {
        return this.http.post<IAccountJEDI>(this.resourceUrl, accountJEDI, { observe: 'response' });
    }

    update(accountJEDI: IAccountJEDI): Observable<EntityResponseType> {
        return this.http.put<IAccountJEDI>(this.resourceUrl, accountJEDI, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAccountJEDI>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAccountJEDI[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAccountJEDI[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
