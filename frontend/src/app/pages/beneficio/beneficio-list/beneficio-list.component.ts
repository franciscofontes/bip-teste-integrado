import {Component, inject, signal} from '@angular/core';
import {CurrencyPipe} from '@angular/common';
import {RouterLink} from '@angular/router';
import {BeneficioService} from '../../../services/beneficio.service';
import {AlertService} from '../../../shared/alert/alert.service';
import {LoadingService} from '../../../services/loading.service';
import {LoadingComponent} from '../../../shared/loading/loading.component';
import {Page} from '../../../models/page.model';
import {Beneficio} from '../../../models/beneficio.model';
import {PageRequest} from '../../../models/page-request.model';

@Component({
  selector: 'app-beneficio-list',
  imports: [
    CurrencyPipe,
    RouterLink,
    LoadingComponent
  ],
  templateUrl: './beneficio-list.component.html',
  styleUrls: ['./beneficio-list.component.css']
})
export class BeneficioListComponent {
  beneficioService = inject(BeneficioService);
  alertService = inject(AlertService);
  loadingService = inject(LoadingService);
  pageBeneficios = signal<Page<Beneficio>>({number: 0, content: [], first: false, last: false, size: 0, totalElements: 0, totalPages: 0});
  beneficios = signal<Beneficio[]>([]);
  beneficio = signal<Beneficio>({id: 0, nome: '', descricao: '', valor: 0, ativo: true});
  loading = this.loadingService.loading;
  successAlertOptions = {autoClose: true, keepAfterRouteChange: true};

  ngOnInit(): void {
    this.findByPage();
  }

  get totalPages() {
    return Array(this.pageBeneficios().totalPages).fill(0).map((x, i) => i);
  }

  goToNextPage(): void {
    if (!this.pageBeneficios().last) {
      let nextPageNumber = this.pageBeneficios().number + 1;
      this.goToPage(nextPageNumber);
    }
  }

  goToPreviousPage(): void {
    if (!this.pageBeneficios().first) {
      let previousPageNumber = this.pageBeneficios().number - 1;
      this.goToPage(previousPageNumber);
    }
  }

  goToPage(page: number): void {
    let pageRequest = {number: page};
    this.findByPage(pageRequest);
  }

  findByPage(pageRequest?: PageRequest) {
    this.beneficioService.findByPage(pageRequest).subscribe((response) => {
      this.pageBeneficios.set(response);
    })
  }

  delete(id?: number) {
    if (id && confirm('Tem certeza?')) {
      this.beneficioService.delete(id).subscribe({
        next: (response) => {
          this.alertService.success("Beneficio deletado com sucesso", [], this.successAlertOptions);
          this.findByPage();
        },
        error: (err) => {
        }
      });
    }
  }
}
