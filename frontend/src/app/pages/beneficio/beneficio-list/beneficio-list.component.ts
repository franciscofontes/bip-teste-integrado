import {Component, inject} from '@angular/core';
import {CurrencyPipe} from '@angular/common';
import {RouterLink} from '@angular/router';
import {BeneficioService} from '../../../services/beneficio.service';
import {AlertService} from '../../../shared/alert/alert.service';

@Component({
  selector: 'app-beneficio-list',
  imports: [
    CurrencyPipe,
    RouterLink
  ],
  templateUrl: './beneficio-list.component.html',
  styleUrls: ['./beneficio-list.component.css']
})
export class BeneficioListComponent {
  beneficioService = inject(BeneficioService);
  alertService = inject(AlertService);
  pageBeneficios = this.beneficioService.pageBeneficios;
  beneficios = this.beneficioService.beneficios;
  successAlertOptions = {autoClose: true, keepAfterRouteChange: true};
  warnAlertOptions = {autoClose: true, keepAfterRouteChange: false};

  ngOnInit(): void {
    this.beneficioService.findByPage();
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
    this.beneficioService.findByPage(pageRequest);
  }

  delete(id?: number) {
    if (id && confirm('Tem certeza?')) {
      this.beneficioService.delete(id).subscribe({
        next: (response) => {
          this.alertService.success("Beneficio deletado com sucesso", [], this.successAlertOptions);
          this.beneficioService.findByPage();
        },
        error: (err) => {
          this.alertService.warn("Ocorreu um erro no servidor. Não foi possível deletar", [], this.warnAlertOptions);
        }
      });
    }
  }
}
