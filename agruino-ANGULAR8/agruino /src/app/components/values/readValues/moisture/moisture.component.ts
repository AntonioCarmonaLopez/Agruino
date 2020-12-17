import { Component, OnInit } from '@angular/core';
import { ValuesInterface } from '../../../../shared/models/valuesInterface';
import { ServiceReadValuesService } from '../../../../shared/services/service-read-values.service';
import { TranslateService } from '@ngx-translate/core';
import { GetLangService } from '../../../../shared/services/get-lang.service';

@Component({
  selector: 'app-moisture',
  templateUrl: './moisture.component.html',
  styleUrls: ['./moisture.component.css']
})
export class MoistureComponent implements OnInit {
  values: ValuesInterface[];
  constructor(
    private serviceReadValuesService: ServiceReadValuesService,
    private translate: TranslateService,
    private getLang: GetLangService
  ) { }

  ngOnInit() {
    this.serviceReadValuesService.getValues().subscribe(values => {
      this.values = values;
      console.log(values);
    });
    this.translate.use(this.getLang.getLang());
    console.log('va' + this.getLang.getLang());
  }

  ocultar(arg) {
    if (arg === 'moisture') {
      $('.moisture').addClass('hidden');
      $('.moisturecenter').removeAttr('hidden');
    }
  }
  mostrar(arg) {
    if (arg === 'moisture') {
      $('.moisture').removeClass('hidden');
      $('.moisturecenter').attr('hidden', 'true');
    } 
  }
}
