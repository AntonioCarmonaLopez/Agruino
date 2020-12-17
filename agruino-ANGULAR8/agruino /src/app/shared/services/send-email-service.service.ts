import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SendEmailServiceService {

  constructor(private http: HttpClient) { }
  sendMessage(subject,mail,msg) {
    let body={
      subject,
      mail,
      msg
    }
    return this.http.post('http://localhost:3000/formulario', body);
    }
}
