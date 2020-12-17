import { Component, OnInit } from '@angular/core';
import { TranslateService} from '@ngx-translate/core';
import { GetLangService } from '../app/shared/services/get-lang.service';
import { AuthService } from '../app/shared/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(
    public translate: TranslateService,
    public getLang: GetLangService,
    public authService: AuthService,
    public router: Router) {
}

ngOnInit() {
  this.translate.setDefaultLang('es');
  this.router.events.subscribe((url:any) => console.log(url));
      console.log("ruta",this.router.url); 
 }
  title = 'agruino';
  useLanguage(language: string) {
    if (language !== 'undefined') {
      this.getLang.setLang(language);
      this.translate.use(language);
      console.log('app' + this.getLang.getLang());
      return;
    }
    this.translate.use(this.getLang.getLang());
  }
}
