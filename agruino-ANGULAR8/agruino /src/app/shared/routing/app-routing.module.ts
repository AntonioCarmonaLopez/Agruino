import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
// Required components for which route services to be activated
import { SignInComponent } from '../../components/sign-in/sign-in.component';
import { SignUpComponent } from '../../components/sign-up/sign-up.component';
import { DashboardComponent } from '../../components/dashboard/dashboard.component';
import { ForgotPasswordComponent } from '../../components/forgot-password/forgot-password.component';
import { ValuesComponent } from '../../components/values/values.component';
import { UsersComponent } from '../../components/users/users.component';
import { UpdateProfileComponent } from '../../components/update-profile/update-profile.component';
import { SendEmailComponent } from '../../components/send-email/send-email.component';
import { MoistureComponent } from '../../components/values/readValues/moisture/moisture.component';
import { ConductibityComponent } from '../../components/values/readValues/conductivity/conductibity/conductibity.component';
import { TurbidityComponent } from '../../components/values/readValues/turbidity/turbidity/turbidity.component';
import { PhComponent } from '../../components/values/readValues/ph/ph/ph.component';
import { TempComponent } from '../../components/values/readValues/temp/temp/temp.component';
import { ValuesLogComponent } from '../../components/values/readValues/values-log/values-log.component';

// Import canActivate guard services
import { AuthGuard } from '../guard/secure-inner-pages.guard';
import { SecureInnerPagesGuard } from '../guard/auth-guard';
// Include route guard in routes array
const routes: Routes = [
  { path: '', redirectTo: '/sign-in', pathMatch: 'full'},
  { path: 'sign-in', component: SignInComponent},
  { path: 'register-user', component: SignUpComponent, canActivate: [SecureInnerPagesGuard, AuthGuard]},
  { path: 'dashboard', component: DashboardComponent, canActivate: [SecureInnerPagesGuard]},
  { path: 'forgot-password', component: ForgotPasswordComponent, canActivate: [SecureInnerPagesGuard]},
  { path: 'values', component: ValuesComponent, canActivate: [SecureInnerPagesGuard]},
  { path: 'users', component: UsersComponent, canActivate: [SecureInnerPagesGuard, AuthGuard]},
  { path: 'uprofile', component: UpdateProfileComponent, canActivate: [SecureInnerPagesGuard, AuthGuard]},
  { path: 'send-email/:email', component: SendEmailComponent, canActivate: [SecureInnerPagesGuard, AuthGuard]},
  { path: 'moisture/', component: MoistureComponent, canActivate: [SecureInnerPagesGuard, AuthGuard]},
  { path: 'conductibity/', component: ConductibityComponent, canActivate: [SecureInnerPagesGuard, AuthGuard]},
  { path: 'turbidity/', component: TurbidityComponent, canActivate: [SecureInnerPagesGuard, AuthGuard]},
  { path: 'ph/', component: PhComponent, canActivate: [SecureInnerPagesGuard, AuthGuard]},
  { path: 'temp/', component: TempComponent, canActivate: [SecureInnerPagesGuard, AuthGuard]},
  { path: 'values-log', component:  ValuesLogComponent, canActivate: [SecureInnerPagesGuard, AuthGuard]}

];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
