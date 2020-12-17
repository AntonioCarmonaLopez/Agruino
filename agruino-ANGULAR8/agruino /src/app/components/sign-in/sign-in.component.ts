import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { TranslateService } from '@ngx-translate/core';
import { GetLangService } from '../../shared/services/get-lang.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})

export class SignInComponent implements OnInit {

  constructor(
    public authService: AuthService,
    private translate: TranslateService,
    private getLang: GetLangService
  ) { }
  emailPattern = '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$';
  // tslint:disable-next-line: max-line-length
  passPattern = '(?=^.{6,}$)((?=.*\d)(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))^.*';
  rEmail = false;
  rPass = false;
  sEmail = false;
  sPass = false;
  email(str) {
    if ($.trim(str) === '') {
      this.rEmail = false;
      this.disableCheckEmail();
      return;
    } else if (!$.trim(str).match(this.emailPattern)) {
      this.rEmail = false;
      this.disableCheckEmail();
      return;
    }
    this.rEmail = true;
    this.sEmail = true;
    this.enableCheckEmail();
    this.enableButton();
    return;
  }
  emaillost(str) {
    if ($.trim(str) === '') {
      window.alert('Email adress can not be empty');
      this.rEmail = false;
      this.disableCheckEmail();
      return;
    } else if (!$.trim(str).match(this.emailPattern)) {
      window.alert('Bad format of the email adress');
      this.rEmail = false;
      this.disableCheckEmail();
      return;
    }
    this.rEmail = true;
    this.enableCheckEmail();
    this.enableButton();
    return;
  }
  pass(str) {
    if ($.trim(str) === '') {
      this.rPass = false;
      this.disableCheckPass();
      return;
    } else if (!$.trim(str).match(this.passPattern)) {
      this.rPass = false;
      this.disableCheckPass();
      return;
    }
    this.rPass = true;
    this.enableCheckPass();
    this.enableButton();
    return;
  }
  passlost(str) {
    if ($.trim(str) === '') {
      window.alert('Password can not be empty');
      this.rPass = false;
      this.disableCheckPass();
      return;
    } else if (!$.trim(str).match(this.passPattern)) {
      window.alert('Bad format of the pass');
      this.rPass = false;
      this.disableCheckPass();
      return;
    }
    this.rPass = true;
    this.enableCheckPass();
    this.enableButton();
    return;
  }
  enableButton() {
    if (this.sEmail && this.sPass) {
      $('#submit').removeAttr('disabled');
    }
  }
  enableCheckEmail() {
    if (this.rEmail) {
      $('#email').css('background', 'white');
      $('#okEmail').remove();
      let checkEmail = document.querySelector('#imgEmail') as HTMLElement;
      checkEmail.innerHTML = '&nbsp;<img id="okEmail" src="../../../assets/img/green-check-icon-png-10.jpg" alt="imagen ok">';
      this.rEmail = false;
      this.sEmail = true;
      return;
    }
  }
  disableCheckEmail() {
    if (!this.rEmail) {
      $('#email').css('background', 'pink');
      $('#okEmail').remove();
      $('input:button').attr('disabled', 'disabled');
      this.rEmail = true;
      this.sEmail = false;
      return;
    }
  }
  enableCheckPass() {
    if (this.rPass) {
      $('#pass').css('background', 'white');
      $('#okPass').remove();
      let checkPass = document.querySelector('#imgPass') as HTMLElement;
      checkPass.innerHTML = '&nbsp;<img id="okPass" src="../../../assets/img/green-check-icon-png-10.jpg" alt="imagen ok">';
      $('#repass').removeAttr('disabled');
      this.rPass = false;
      this.sPass = true;
      return;
   }
  }
  disableCheckPass() {
    if (!this.rPass) {
      $('#pass').css('background', 'pink');
      $('#okPass').remove();
      this.rPass = true;
      this.sPass = false;
      return;
    }
  }
  ngOnInit() {
    this.translate.use(this.getLang.getLang());
    console.log('das' + this.getLang.getLang());
    $('.btnSpace2').addClass('hidden');

   }

}
