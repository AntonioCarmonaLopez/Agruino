import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { TranslateService } from '@ngx-translate/core';
import { GetLangService } from '../../shared/services/get-lang.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})

export class ForgotPasswordComponent implements OnInit {

  constructor(
    public authService: AuthService,
    private translate: TranslateService,
    private getLang: GetLangService
  ) { }

  ngOnInit() {
    this.translate.use(this.getLang.getLang());
    console.log('das' + this.getLang.getLang());
  }

}
