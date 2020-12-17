import { Component, OnInit } from '@angular/core';
import { SendEmailServiceService } from '../../shared/services/send-email-service.service';
import { ActivatedRoute } from '@angular/router';
import swal from 'sweetalert'
import { TranslateService } from '@ngx-translate/core';
import { GetLangService } from '../../shared/services/get-lang.service';
import { AuthService } from '../../shared/services/auth.service';
import { from } from 'rxjs';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-send-email',
  templateUrl: './send-email.component.html',
  styleUrls: ['./send-email.component.css']
})
export class SendEmailComponent implements OnInit {
  users: any;
  email = "";
  rsubject = false;
  rmsg = false;

  constructor(
    private activedRoute: ActivatedRoute,
    private sendEmailService: SendEmailServiceService,
    private translate: TranslateService,
    private getLang: GetLangService,
    public authService: AuthService,
    private cdRef:ChangeDetectorRef
    ) { }

    subject(str){
      if ($.trim(str) === '') {
        this.rsubject=false;
        return;
      }
      this.rsubject=true;
      this.enableButton();
    }

    subjectlost(str){
      if ($.trim(str) === '') {
        window.alert('Subject is empty')
        this.rsubject=false;
        return;
      }
      this.rsubject=true;
      this.enableButton();
    }

    msg(str){
      if ($.trim(str) === '') {
        this.rmsg=false;
        return;
      }
      this.rmsg=true;
      this.enableButton();
    }

    msglost(str){
      if ($.trim(str) === '') {
        window.alert('Message cannot be empty')
        this.rmsg=false;
        return;
      }
      this.rmsg=true;
      this.enableButton();
    }

    enableButton() {
      if (this.rsubject && this.rmsg ) {
        console.log('ok');
        
        $('#submit').removeAttr('disabled');
      }
    }

  ngOnInit() {
    this.translate.use(this.getLang.getLang());
    this.email = this.activedRoute.snapshot.params.email;
  }

  contactForm(subject,mail,msg) {
    if(mail==='all'){
      this.authService.getUser().subscribe((users: any) => {
        this.users = users;
        for (let user of users) {
         this.sendEmailService.sendMessage(subject,user.email,msg).subscribe(() => {
          console.log(user.email);
          this.cdRef.detectChanges();
         });  
        }
        swal("Formulario de contacto", "Mensaje enviado correctamente", 'success');
      });
    } else {
      this.sendEmailService.sendMessage(subject,mail,msg).subscribe(() => {
        swal("Formulario de contacto", "Mensaje enviado correctamente", 'success');
        });
        
      }
    }
}
