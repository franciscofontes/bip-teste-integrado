import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficioTransferComponent } from './beneficio-transfer.component';

describe('BeneficioTransferComponent', () => {
  let component: BeneficioTransferComponent;
  let fixture: ComponentFixture<BeneficioTransferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficioTransferComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BeneficioTransferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
