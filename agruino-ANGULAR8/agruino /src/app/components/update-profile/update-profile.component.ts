import { Component, OnInit, NgZone  } from '@angular/core';
import { User } from '../../shared/models/user';
import { Profile } from '../../shared/models/profile';
import { AuthService } from '../../shared/services/auth.service';
import { AngularFirestore } from '@angular/fire/firestore';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { GetLangService } from '../../shared/services/get-lang.service';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {

  profiles: Profile[] = [
    { id: 1, profile: 'User' },
    { id: 2, profile: 'Admin' },
  ];
  users: User[];
  user: User;
  confirmed: boolean = true;
  selected: any;
  filtered: any;
  emailPattern = '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$';
  rSelect = false;
  rEmail = false;
  sSelect = false;
  sEmail = false;
  public saveUsername: boolean;

  constructor(
    public afs: AngularFirestore,   // Inject Firestore service
    private authService: AuthService,
    private translate: TranslateService,
    private getLang: GetLangService,
    public router: Router,
    public ngZone: NgZone // NgZone service to remove outside scope warning

  ) {  console.log( 'Child :: Constructor' );
       const email = prompt('Email:', 'x@x.com');
       if(!email)
        this.router.navigate(['dashboard'])
       switch(email){
        case 'x@x.com':
          this.router.navigate(['dashboard']);
          break;
        default:
          this.authService.getUser().subscribe((users) => {
            this.users = users;
         for (const us of users) {
           if (us.email === email) {
             console.log(us);
             return this.user = us;
           } 
         }
       })
       }
      }
 FieldsChange(values:any){
  console.log(values.currentTarget.checked);
  this.confirmed=values.currentTarget.checked;
  }
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
    this.rEmail = true;
    this.sEmail = false;
    return;
  }
}

updateProfile(profile, email) {
  if (typeof this.selected === 'undefined') {
    window.alert('Must choose a profile');
    this.rSelect = false;
    this.disableCheckSelect();
    return false;
  } else if ($.trim(email) === '') {
      $('#email').css('background', 'pink');
      $('#okEmail').remove();
      return false;
  } else if (!$.trim(email).match(this.emailPattern)) {
      window.alert('Bad format of the email adress');
      return false;
  }
  return this.afs.collection('users').doc(this.user.uid).update({
    uid: this.user.uid,
    displayName: profile,
    email: email,
    emailVerified: this.confirmed
  }).then((result) => {
    this.ngZone.run(() => {
      this.router.navigate(['dashboard']);
      window.alert('Profile user has been updated');
    });
  }).catch((error) => {
    window.alert(error.message);
  });
}
  ngOnInit() {

    this.translate.use(this.getLang.getLang());
    //console.log('das' + this.getLang.getLang());
  }
  // tslint:disable-next-line: use-lifecycle-interface
  ngOnDestroy() {

    console.log( 'Child :: ngOnDestroy' );

}
}
