
// service for read values from firebase and return a values collection
import { Injectable } from '@angular/core';
import { AngularFirestore, AngularFirestoreCollection, AngularFirestoreDocument } from '@angular/fire/firestore';
import { User } from '../models/user';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class  UsersService {
  // collection where stored values from firebase
  usersCollection: AngularFirestoreCollection<any>;
  // array where we stored new values read from arduino
  users: Observable<any[]>;
  usersDoc: AngularFirestoreDocument<any>;

  constructor(public afs: AngularFirestore) {
    //this.users = afs.collection('users').valueChanges();
  }
  // methods
  // method for cath users
  getUsers() {
    return this.afs.collection('users').valueChanges();
  }

}
