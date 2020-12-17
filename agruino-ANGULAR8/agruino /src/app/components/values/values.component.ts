import { Component, OnInit, ViewEncapsulation} from '@angular/core';
// New imports for implementing our values interface and our service
import { ValuesInterface } from '../../shared/models/valuesInterface';
import { ServiceReadValuesService } from '../../shared/services/service-read-values.service';
import { AuthService } from '../../shared/services/auth.service';
import { TranslateService } from '@ngx-translate/core';
import { GetLangService } from '../../shared/services/get-lang.service';

@Component({
  selector: 'app-values',
  templateUrl: './values.component.html',
  styleUrls: ['./values.component.css'],
  encapsulation: ViewEncapsulation.None
})

export class ValuesComponent implements OnInit {
  values: ValuesInterface[];

// Inject to a constructor our authservice, service read values, translate service & getlangservice
  constructor(
    public authService: AuthService,
    private serviceReadValuesService: ServiceReadValuesService,
    private translate: TranslateService,
    private getLang: GetLangService
    ) { }
    activeLang: string;

  // Read at start new values
  ngOnInit() {
    this.serviceReadValuesService.getValues().subscribe(values => {
      this.values = values;
      console.log(values);
    });
    this.translate.use(this.getLang.getLang());
    console.log('va' + this.getLang.getLang());
  }

}
