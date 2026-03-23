import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {BeneficioService} from '../../../services/beneficio.service';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {Beneficio} from '../../../models/beneficio.model';
import {AlertService} from '../../../shared/alert/alert.service';

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
  router = inject(Router);
  route = inject(ActivatedRoute);
  form!: FormGroup;
  fromId?: number;
  fromNome?: string;
  toId?: number;
  beneficio: Beneficio = this.beneficioService.beneficio();
  beneficios: Beneficio[] = this.beneficioService.beneficios();
  successAlertOptions = {autoClose: true, keepAfterRouteChange: true};
  warnAlertOptions = {autoClose: true, keepAfterRouteChange: false};

  ngOnInit() {
    this.form = this.fb.group({
      amount: ['', [Validators.required, Validators.min(1)]]
    });
    this.fill();
  }

  fill() {
    const fromId = this.route.snapshot.paramMap.get('fromId');
    const fromNome = this.route.snapshot.paramMap.get('fromNome');
    this.beneficioService.findAll();
    if (fromId) {
      this.fromId = +fromId;
      if (this.beneficio) this.form.patchValue(this.beneficio);
    }
    if (fromNome) {
      this.fromNome = fromNome;
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
        this.alertService.success("Ocorreu um erro no servidor. Não foi possível transferir", [], this.warnAlertOptions);
      }
    });
  }
}
