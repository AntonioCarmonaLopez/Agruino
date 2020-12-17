import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { Profile } from '../../shared/models/profile';
import { TranslateService } from '@ngx-translate/core';
import { GetLangService } from '../../shared/services/get-lang.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})

export class SignUpComponent implements OnInit {

  constructor(
    public authService: AuthService,
    private translate: TranslateService,
    private getLang: GetLangService
  ) { }
  selected: any;
  filtered: any;
  emailPattern = '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$';
  passPattern = '(?=^.{6,}$)((?=.*\d)(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|(?=.*\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))^.*';
  profiles: Profile[] = [
    { id: 1, profile: 'User' },
    { id: 2, profile: 'Admin' },
  ];
  rSelect = false;
  rEmail = false;
  rPass = false;
  rRePass = false;
  sSelect = false;
  sEmail = false;
  sPass = false;
  sRePass = false;

// validations
onOptionsSelected() {
  console.log(this.selected);
  if (typeof this.selected === 'undefined') {
    window.alert('Must choose a profile');
    this.rSelect = false;
    this.disableCheckSelect();
    return;
  }
  this.rSelect = true;
  this.enableCheckSelect();
  this.enableButton();
  return;
}
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
    window.alert('Email adress can not be emty');
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
    window.alert('Password can not be emty');
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
rePass(str, restr) {
  if ($.trim(str) !== $.trim(restr)) {
    this.rRePass = false;
    this.disableCheckRePass();
    return;
  }
  this.rRePass = true;
  this.enableCheckRePass();
  this.enableButton();
  return;
}
rePasslost(str, restr) {
  if ($.trim(str) !== $.trim(restr)) {
    window.alert('Password are differents');
    this.rRePass = false;
    this.disableCheckRePass();
    return;
  }
  this.rRePass = true;
  this.enableCheckRePass();
  this.enableButton();
  return;
}
enableCheckSelect() {
  if (this.rSelect) {
    $('#select').css('background', 'white');
    $('#checkSelect').append('&nbsp;<img id="okEmail" src="../../../assets/img/green-check-icon-png-10.jpg" alt="imagen ok">');
    this.rSelect = false;
    this.sSelect = true;
    return;
  }
}
disableCheckSelect() {
  if (!this.rSelect) {
    $('#select').css('background', 'pink');
    $('#okSelect').remove();
    this.rSelect = true;
    this.sSelect = false;
    return;
  }
}
enableButton() {
  if (this.sSelect && this.sEmail && this.sPass && this.sRePass) {
    $('#submit').removeAttr('disabled');
    return;
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
    let checkEmail = document.querySelector('#imgPass') as HTMLElement;
    checkEmail.innerHTML = '&nbsp;<img id="okPass" src="../../../assets/img/green-check-icon-png-10.jpg" alt="imagen ok">';
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
    this.sRePass = false;
    this.rRePass = false;
    this.disableCheckRePass();
    return;
  }
}
enableCheckRePass() {
  if (this.rRePass) {
    $('#repass').css('background', 'white');
    $('#okRePass').remove();
    let checkRePass = document.querySelector('#imgRePass') as HTMLElement;
    checkRePass.innerHTML = '&nbsp;<img id="#okRePass" src="../../../assets/img/green-check-icon-png-10.jpg" alt="imagen ok">';
    this.sRePass = true;
    return;
  }
}

disableCheckRePass() {
  if (!this.rRePass)  {
    $('#repass').css('background', 'pink');
    $('#okRePass').remove();
    this.sRePass = false;
    return;
  }
}

ngOnInit() {
  this.translate.use(this.getLang.getLang());
  console.log('das' + this.getLang.getLang());
}
}


