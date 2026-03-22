import {inject, Injectable, signal} from '@angular/core';
import {Beneficio} from '../models/beneficio.model';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Page} from '../models/page.model';
import {PageRequest} from '../models/page-request.model';

@Injectable({providedIn: 'root'})
export class BeneficioService {

  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/api/v1/beneficios';
  private pageRequest: PageRequest | undefined;

  pageBeneficios = signal<Page<Beneficio>>(
    {
      number: 0, content: [], first: false, last: false, size: 0, totalElements: 0, totalPages: 0
    }
  );
  beneficios = signal<Beneficio[]>([]);

  constructor() {
  }

  findAll() {
    this.http.get<Beneficio[]>(this.apiUrl).subscribe(data => {
      this.beneficios.set(data);
    });
  }

  findByPage(pageRequest?: PageRequest) {
    this.pageRequest = pageRequest;
    let params = this.fillPageParams(pageRequest);
    this.http.get<Page<Beneficio>>(this.apiUrl + '/page', {params}).subscribe(pageResponse => {
      this.pageBeneficios.set(pageResponse);
    });
  }

  add(beneficio: Beneficio) {
    this.http.post<Beneficio>(this.apiUrl, beneficio).subscribe(() => {
      this.findByPage(this.pageRequest);
    });
  }

  update(beneficio: Beneficio) {
    this.http.put(this.apiUrl + '/' + beneficio.id, beneficio).subscribe(() => {
      this.findByPage(this.pageRequest);
    });
  }

  delete(id: number) {
    this.http.delete(this.apiUrl + '/' + id).subscribe(() => {
      this.findByPage(this.pageRequest);
    });
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
