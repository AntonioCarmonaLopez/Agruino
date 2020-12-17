import { Component, OnInit, NgZone  } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { UsersService } from '../../shared/services/users.service';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { GetLangService } from '../../shared/services/get-lang.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: any[];
  
  constructor(
    private usersService: UsersService,
    private authService: AuthService,
    private translate: TranslateService,
    private router: Router,
    private getLang: GetLangService
  ) {  console.log( 'Child :: Constructor' );
       this.usersService.getUsers().subscribe(users => {
    this.users = users;
    console.log(users);
  }); 

}
  ngOnInit() {
    this.translate.use(this.getLang.getLang());
    console.log('das' + this.getLang.getLang());
  }

  borrar(email) {
    let conf = confirm("Are you sure?"); 
    if(conf){ 
      if (email == null || email === '') {
        window.alert('Operation cancelled or empty email');
        return;
      } else if (this.authService.current(0, ' ').displayName !== 'Admin' ) {
        window.alert('Operation not allowed');
        return;
      }else{
      this.authService.getUser().subscribe((users: any) => {
        this.users = users;
        for (let user of users) {
          if (user.email === email) {
            console.log(user);
            //try {
              this.authService.deleteUser(user);
              //window.alert("user "+email+" has been deleted");
            //}catch(err){
              //window.alert("User not deleted->"+err.message);
            //}
          }
        }
      });
    }
  }
}
  // tslint:disable-next-line: use-lifecycle-interface
  ngOnDestroy() {

    console.log( 'Child :: ngOnDestroy' );
  }
}
