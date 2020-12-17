import { Injectable, NgZone } from '@angular/core';
import { User } from '../models/user';
import { AngularFireAuth } from '@angular/fire/auth';
import { AngularFirestore, AngularFirestoreDocument, AngularFirestoreCollection } from '@angular/fire/firestore';
import { Router } from '@angular/router';
import * as firebase from 'firebase';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  userData: any; // Save logged in user data
  // collection where stored users from firebase
  userCollection: AngularFirestoreCollection<any>;
  // observable where we stored new user read from arduino
  user: Observable<any[]>;
  userDoc: AngularFirestoreDocument<any>;


  constructor(
    public afs: AngularFirestore,   // Inject Firestore service
    public afAuth: AngularFireAuth, // Inject Firebase auth service
    public router: Router,
    public ngZone: NgZone // NgZone service to remove outside scope warning
  ) {
    this.user = afs.collection('users').valueChanges();
    /* Saving user data in localstorage when
    logged in and setting up null when logged out */
    this.afAuth.authState.subscribe(user => {

      if (user) {

        this.userData = user;
        localStorage.setItem('user', JSON.stringify(this.userData));
        JSON.parse(localStorage.getItem('user'));
      } else {
        sessionStorage.setItem('user', null);
        JSON.parse(localStorage.getItem('user'));
      }
    });
  }

  // Sign in with email/password
  signIn(email, password) {
    return this.afAuth.auth.signInWithEmailAndPassword(email, password)
      .then((result) => {
        this.setUserData(result.user);
        console.log('user',this.userData)

        this.ngZone.run(() => {
          this.router.navigate(['dashboard']);
        });

      }).catch((error) => {
        window.alert(error.message);
      });
  }

  // Sign up with email/password
  signUp(email, password, profile) {
    return this.afAuth.auth.createUserWithEmailAndPassword(email, password)
      .then((result) => {
        /* Call the SendVerificaitonMail() function when new user sign
        up and returns promise */
        this.sendVerificationMail();
        window.alert('User created please check your email, you must confirm it');
        result.user.updateProfile({
          displayName: profile
        }).then(function() {
          this.setUserData(result.user);
          this.ngZone.run(() => {
            this.router.navigate(['sign-in']);
          });
        }, ((error) => {
          console.log(error);
        }));

        console.log(profile);
      }).catch((error) => {
        window.alert(error.message);
      });
  }

  // Send email verfificaiton when new user sign up
  sendVerificationMail() {
    return this.afAuth.auth.currentUser.sendEmailVerification()
    .then(() => {
      this.router.navigate(['verify-email-address']);
    });
  }

  // Reset Forggot password
  forgotPassword(passwordResetEmail) {
    const user = JSON.parse(localStorage.getItem('user'));
    if (passwordResetEmail === user.email) {
      return this.afAuth.auth.sendPasswordResetEmail(passwordResetEmail)
      .then(() => {
        window.alert('Password reset email sent, check your inbox.');
      }).catch((error) => {
        window.alert(error);
      });
    } else {
      window.alert('Email must be under your property');
    }
  }

  // Returns true when user is looged in and email is verified
  get isLoggedIn(): boolean {
    const user = JSON.parse(localStorage.getItem('user'));
    return (user !== null && user.emailVerified !== false) ? true : false;
  }

  /* Setting up user data when sign in with username/password and
  sign up with username/password in Firestore database using
  AngularFirestore + AngularFirestoreDocument service */
  setUserData(user) {
    const userRef: AngularFirestoreDocument<any> = this.afs.doc(`users/${user.uid}`);
    const userData: User = {
      uid: user.uid,
      email: user.email,
      photoURL: user.photoURL,
      displayName: user.displayName,
      emailVerified: user.emailVerified,
    };
    return userRef.set(userData, {
      merge: true
    });
  }

  // Sign out
  signOut() {
    return this.afAuth.auth.signOut().then(() => {
      localStorage.removeItem('user');
      this.router.navigate(['sign-in']);
      window.alert('Session will be closed!');
    });
  }

  // catch current logged user
  current(op, url) {
    const user = JSON.parse(localStorage.getItem('user'));
    if (op === 1) {
      const profile = firebase.auth().currentUser;
      profile.updateProfile({
        photoURL: url,
        //displayName: 'Admin'
      }).then(function(response) {
        window.alert('Profile img has been updated');
        this.setUserData(user);
      }, ((error) => {
        console.log(error);
      }));
    } else {
      return user;
    }
  }
    // get user
    getUser() {
      return this.user;
    }
    // delete user
    deleteUser(user) {
      user.del
      return this.afs.collection('users').doc(user.uid).delete()
      .catch((error) => {
        console.log(error);
      });
  }


}
