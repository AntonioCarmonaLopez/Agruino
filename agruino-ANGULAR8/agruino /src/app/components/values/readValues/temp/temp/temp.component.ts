import { Component, OnInit } from '@angular/core';
import { ValuesInterface } from '../../../../../shared/models/valuesInterface';
import { ServiceReadValuesService } from '../../../../../shared/services/service-read-values.service';
import { TranslateService } from '@ngx-translate/core';
import { GetLangService } from '../../../../../shared/services/get-lang.service';

@Component({
  selector: 'app-temp',
  templateUrl: './temp.component.html',
  styleUrls: ['./temp.component.css']
})
export class TempComponent implements OnInit {
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
    console.log(arg)
    if (arg === 'temp') {
      $('.temp').addClass('hidden');
      $('.tempcenter').removeAttr('hidden');
    } 
  }

  mostrar(arg) {
    if (arg === 'temp') {
      $('.temp').removeClass('hidden');
      $('.tempcenter').attr('hidden', 'true');
    } 
  }
}
