import { Component, OnInit } from '@angular/core';
import { ValuesInterface } from '../../../../../shared/models/valuesInterface';
import { ServiceReadValuesService } from '../../../../../shared/services/service-read-values.service';
import { TranslateService } from '@ngx-translate/core';
import { GetLangService } from '../../../../../shared/services/get-lang.service';
@Component({
  selector: 'app-conductibity',
  templateUrl: './conductibity.component.html',
  styleUrls: ['./conductibity.component.css']
})
export class ConductibityComponent implements OnInit {
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
    if (arg === 'conductivity') {
      $('.conductivity').addClass('hidden');
      $('.conductivitycenter').removeAttr('hidden');
    } 
  }
  mostrar(arg) {
    if (arg === 'conductivity') {
      $('.conductivity').removeClass('hidden');
      $('.conductivitycenter').attr('hidden', 'true');
    }
  }
}
