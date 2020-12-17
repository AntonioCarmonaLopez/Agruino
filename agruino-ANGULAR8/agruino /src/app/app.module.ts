import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

// Reactive Form
import { ReactiveFormsModule } from '@angular/forms';

// App routing modules
import { AppRoutingModule } from './shared/routing/app-routing.module';

// App components
import { AppComponent } from './app.component';
import { SignInComponent } from './components/sign-in/sign-in.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { ValuesComponent } from './components/values/values.component';
import { NavbarUserComponent } from './components/navbar-user/navbar-user.component';
import { NavbarNoUserComponent } from './components/navbar-no-user/navbar-no-user.component';

// Firebase services + enviorment module
import { AngularFireModule } from '@angular/fire';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFirestore, AngularFirestoreModule } from '@angular/fire/firestore';
import { environment } from '../environments/environment';
import { AngularFireStorageModule } from '@angular/fire/storage';

// Auth, values service && send-email-service
import { AuthService } from './shared/services/auth.service';
import { ServiceReadValuesService } from './shared/services/service-read-values.service';
import { SendEmailServiceService } from './shared/services/send-email-service.service';

// Form module
import { FormsModule } from '@angular/forms';

// import ngx-translate and the http loader
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { UsersComponent } from './components/users/users.component';
import { UpdateProfileComponent } from './components/update-profile/update-profile.component';
import { SendEmailComponent } from './components/send-email/send-email.component';
import { MoistureComponent } from './components/values/readValues/moisture/moisture.component';
import { ConductibityComponent } from './components/values/readValues/conductivity/conductibity/conductibity.component';
import { TurbidityComponent } from './components/values/readValues/turbidity/turbidity/turbidity.component';
import { PhComponent } from './components/values/readValues/ph/ph/ph.component';
import { TempComponent } from './components/values/readValues/temp/temp/temp.component';
import { ValuesLogComponent } from './components/values/readValues/values-log/values-log.component';
import { ReadValuesLogComponent } from './components/values/readValues/values-log/read-values-log/read-values-log.component';


@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    SignUpComponent,
    DashboardComponent,
    ForgotPasswordComponent,
    ValuesComponent,
    NavbarUserComponent,
    NavbarNoUserComponent,
    UsersComponent,
    UpdateProfileComponent,
    SendEmailComponent,
    MoistureComponent,
    ConductibityComponent,
    TurbidityComponent,
    PhComponent,
    TempComponent,
    ValuesLogComponent,
    ReadValuesLogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFireAuthModule,
    AngularFirestoreModule,
    AngularFireStorageModule,
    ReactiveFormsModule,
    FormsModule,
      // ngx-translate and the loader module
      HttpClientModule,
      TranslateModule.forRoot({
          loader: {
              provide: TranslateLoader,
              useFactory: HttpLoaderFactory,
              deps: [HttpClient]
          }
      })
  ],
  providers: [AuthService, ServiceReadValuesService, SendEmailServiceService],
  bootstrap: [AppComponent]
})

export class AppModule {
  constructor(public afs: AngularFirestore) {
    afs.firestore.settings({
      timestampsInSnapshots: true,
    });
    afs.firestore.enablePersistence();
  }
}
// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}
