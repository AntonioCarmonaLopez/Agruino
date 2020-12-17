import { Component, OnInit, NgZone } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import * as $ from 'jquery';
import { TranslateService } from '@ngx-translate/core';
import { GetLangService } from '../../shared/services/get-lang.service';
import { AngularFireStorage } from '@angular/fire/storage';
import { finalize } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  users: any[];
  constructor(
    public authService: AuthService,
    private storage: AngularFireStorage,
    private translate: TranslateService,
    private getLang: GetLangService
  ) { }
  url: string;
  activeLang: string;
  uploadPercent: Observable<number>;
  urlImage: Observable<string>;

  updatePhoto()  {
    const value = $('#text').val()
    if(value != '')
      this.authService.current(1, value);
      $('#boton').attr('disabled','disabled');
  }

uploadPhoto(event){
  const id = Math.random().toString(36).substring(2);
  const file = event.target.files[0];
  const filePath = `uploads/profile_${id}`;
  const ref = this.storage.ref(filePath);
  const task = this.storage.upload(filePath, file);
  this.uploadPercent = task.percentageChanges();
  //task.snapshotChanges().pipe(finalize(() => this.urlImage = ref.getDownloadURL())).subscribe();
  task.snapshotChanges().pipe(
    finalize(() => {
        this.urlImage = ref.getDownloadURL();
        this.uploadPercent = null;
        console.log('done');
    })
).subscribe();
}
  borrar() {
    const email = prompt('Email:', 'x@x.com');
    let conf = confirm("Are you sure?"); 
    if(conf){
      if (email == null || email === '') {
        window.alert('Operation cancelled or empty email');
        return;
      } else if (this.authService.current(0, ' ').displayName !== 'Admin' ) {
        window.alert('Operation not allowed');
        return;
      }
      this.authService.getUser().subscribe((users: any) => {
        this.users = users;
        for (let user of users) {
          if (user.email === email) {
            console.log(user);
            //try {
              this.authService.deleteUser(user);
              window.alert("user "+email+" has been deleted");
            //}catch(err){
              //window.alert("User not deleted->"+err.message);
            //}
          }
        }
      });
  }
}
ngOnInit() {
    this.translate.use(this.getLang.getLang());
  }
}
