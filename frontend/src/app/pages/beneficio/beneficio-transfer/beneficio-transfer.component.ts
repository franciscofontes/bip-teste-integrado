import {Component, inject, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {BeneficioService} from '../../../services/beneficio.service';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {Beneficio} from '../../../models/beneficio.model';
import {AlertService} from '../../../shared/alert/alert.service';
import {concatMap} from 'rxjs';
import {LoadingService} from '../../../services/loading.service';

@Component({
  selector: 'app-beneficio-transfer',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './beneficio-transfer.component.html',
  styleUrl: './beneficio-transfer.component.css',
})
export class BeneficioTransferComponent {
  fb = inject(FormBuilder);
  beneficioService = inject(BeneficioService);
  alertService = inject(AlertService);
  loadingService = inject(LoadingService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  loading = this.loadingService.loading;
  localLoading = false;
  form!: FormGroup;
  fromId?: number;
  toId?: number;
  beneficioOrigem = signal<Beneficio>({id: 0, nome: '', descricao: '', valor: 0, ativo: true});
  beneficiosDestino = signal<Beneficio[]>([]);
  successAlertOptions = {autoClose: true, keepAfterRouteChange: true};

  ngOnInit() {
    this.form = this.fb.group({
      amount: ['', [Validators.required, Validators.min(1)]]
    });
    this.fill();
  }

  fill() {
    const fromId = this.route.snapshot.paramMap.get('fromId');
    if (fromId) {
      this.localLoading = true;
      this.fromId = +fromId;
      this.beneficioService.findById(this.fromId).pipe(
        concatMap(beneficio => {
          this.beneficioOrigem.set(beneficio);
          return this.beneficioService.findAll();
        })
      ).subscribe(beneficios => {
        this.localLoading = false;
        this.beneficiosDestino.set(beneficios);
      });
    }
  }

  select(event: Event) {
    const value = (event.target as HTMLSelectElement).value;
    this.toId = +value;
  }

  save() {
    if (this.form.invalid) return;
    if (!this.fromId) return;
    if (!this.toId) return;
    const transfer = {...this.form.value, fromId: this.fromId, toId: this.toId};
    this.beneficioService.transfer(transfer).subscribe({
      next: (response) => {
        this.alertService.success("Transferência realizada com sucesso", [], this.successAlertOptions);
        this.router.navigate(['..'], {relativeTo: this.route}).then(r => '');
      },
      error: (err) => {
      }
    });
  }
}
