import {Component, Input} from '@angular/core';
import {Subscription} from 'rxjs';
import {Alert, AlertType} from './alert.model';
import {NavigationStart, Router} from '@angular/router';
import {AlertService} from './alert.service';

@Component({
  selector: 'app-alert',
  imports: [],
  templateUrl: './alert.component.html',
  styleUrl: './alert.component.css',
})
export class AlertComponent {
  @Input() id = 'default-alert';
  @Input() fade = true;

  onlyOne: boolean = true;
  alerts: Alert[] = [];
  alertSubscription!: Subscription;
  routeSubscription!: Subscription;

  constructor(private router: Router, private alertService: AlertService) { }

  ngOnInit() {

    this.alertSubscription = this.alertService.onAlert(this.id).subscribe(alert => {

      if (!alert.message) {
        this.alerts = this.alerts.filter(x => x.keepAfterRouteChange);
        this.alerts.forEach(x => delete x.keepAfterRouteChange);
        return;
      }

      if (this.onlyOne) {
        if (this.alerts.length === 0) {
          this.alerts.push(alert);
        }
      } else {
        this.alerts.push(alert);
      }

      if (alert.autoClose) {
        setTimeout(() => this.removeAlert(alert), 3000);
      }
    });

    this.routeSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        this.alertService.clear(this.id);
      }
    });
  }

  ngOnDestroy() {
    this.alertSubscription.unsubscribe();
    this.routeSubscription.unsubscribe();
  }

  removeAlert(alert: Alert) {

    if (!this.alerts.includes(alert)) return;

    if (this.fade) {

      let alertFound = this.alerts.find(x => x === alert);
      if (alertFound) {
        alertFound.fade = true;
      }

      setTimeout(() => {
        this.alerts = this.alerts.filter(x => x !== alert);
      }, 250);
    } else {
      this.alerts = this.alerts.filter(x => x !== alert);
    }
  }

  cssClass(alert: Alert) {
    if (!alert) return;

    const classes = ['notification notification-fixed-bottom-right', ''];

    const alertTypeClass = {
      [AlertType.Success]: 'is-success',
      [AlertType.Error]: 'is-danger',
      [AlertType.Info]: 'is-info',
      [AlertType.Warning]: 'is-warning'
    }

    classes.push(alertTypeClass[alert.type]);

    if (alert.fade) {
      classes.push('fade');
    }

    return classes.join(' ');
  }
}
