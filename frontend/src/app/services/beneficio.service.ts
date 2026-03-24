import {inject, Injectable, signal} from '@angular/core';
import {Beneficio} from '../models/beneficio.model';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Page} from '../models/page.model';
import {PageRequest} from '../models/page-request.model';
import {Transfer} from '../models/transfer.model';

@Injectable({providedIn: 'root'})
export class BeneficioService {

  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/v1/beneficios';
  pageRequest: PageRequest | undefined;

  constructor() {
  }

  findAll() {
    return this.http.get<Beneficio[]>(this.apiUrl);
  }

  findByPage(pageRequest?: PageRequest) {
    this.pageRequest = pageRequest;
    let params = this.fillPageParams(pageRequest);
    return this.http.get<Page<Beneficio>>(this.apiUrl + '/page', {params});
  }

  findById(id: number) {
    return this.http.get<Beneficio>(this.apiUrl + "/" + id);
  }

  create(beneficio: Beneficio) {
    return this.http.post<Beneficio>(this.apiUrl, beneficio);
  }

  update(beneficio: Beneficio) {
    return this.http.put(this.apiUrl + '/' + beneficio.id, beneficio);
  }

  delete(id: number) {
    return this.http.delete(this.apiUrl + '/' + id);
  }

  transfer(transfer: Transfer) {
    return this.http.post<Beneficio>(this.apiUrl + '/transfer', transfer);
  }

  private fillPageParams(pageRequest?: PageRequest): HttpParams {
    let params = new HttpParams();
    if (pageRequest?.number) {
      params = params.append('number', pageRequest.number.toString());
    }
    if (pageRequest?.size) {
      params = params.append('size', pageRequest.size.toString());
    }
    if (pageRequest?.orderBy) {
      params = params.append('orderBy', pageRequest.orderBy);
    }
    if (pageRequest?.direction) {
      params = params.append('direction', pageRequest.direction.toUpperCase());
    }
    return params;
  }
}
