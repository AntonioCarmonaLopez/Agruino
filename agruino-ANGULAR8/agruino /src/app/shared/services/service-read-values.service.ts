//service for read values from firebase and return a values collection
import { Injectable } from "@angular/core";
import {
  AngularFirestore,
  AngularFirestoreCollection,
  AngularFirestoreDocument,
} from "@angular/fire/firestore";
import { ValuesInterface } from "../models/valuesInterface";
import { ValueLog } from "../models/valueLog";
import { Observable } from "rxjs";
import { ValuesHistory } from '../models/valuesHistory';

@Injectable({
  providedIn: "root",
})
export class ServiceReadValuesService {
  //collection where stored values from firebase
  valuesCollection: AngularFirestoreCollection<ValuesInterface>;
  valuesHistoryCollection: AngularFirestoreCollection<ValuesHistory>;
  valuesLogCollection: AngularFirestoreCollection<ValueLog>;
  //array where we stored new values read from arduino
  values: Observable<ValuesInterface[]>;
  valuesDoc: AngularFirestoreDocument<ValuesInterface>;
  valuesHistory: Observable<ValuesHistory[]>;
  valuesHistoryDoc: AngularFirestoreDocument<ValuesHistory>;
  valuesLog: Observable<ValueLog[]>;
  valuesLogDoc: AngularFirestoreDocument<ValueLog>;

  constructor(public afs: AngularFirestore) {
    this.values = afs.collection("values").valueChanges();
    console.log(this.values);

    this.valuesHistory = afs.collection("values_history").valueChanges();
    console.log(this.valuesHistory);

    this.valuesLog = afs.collection("values_log").valueChanges();
    console.log(this.valuesLog);
  }
  //methods
  //method for cath values
  getValues() {
    return this.values;
  }

    //method for cath values
    getValuesHistory() {
      return this.valuesHistory;
    }

  //method for cath values
  getValuesLog() {
    return this.valuesLog;
  }
}
