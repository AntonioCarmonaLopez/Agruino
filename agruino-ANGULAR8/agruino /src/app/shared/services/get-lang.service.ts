import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GetLangService {

  activeLang: any;

  constructor() { }

  getLang(): any {
    return localStorage.getItem('lang');
  }
  setLang(language: any) {
        this.activeLang = language;
        localStorage.setItem('lang', this.activeLang);
    }

}
