import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {AlertComponent} from './shared/alert/alert.component';
import {HeaderComponent} from './shared/header/header.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AlertComponent, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  protected readonly title = signal('frontend');
}
