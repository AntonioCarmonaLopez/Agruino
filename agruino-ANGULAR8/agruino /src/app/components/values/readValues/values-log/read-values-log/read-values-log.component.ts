import { Component, OnInit } from "@angular/core";
import { ValuesInterface } from "../../../../../shared/models/valuesInterface";
import { ValuesHistory } from "../../../../../shared/models/valuesHistory";
import { ValueLog } from "../../../../../shared/models/valueLog";
import { ServiceReadValuesService } from "../../../../../shared/services/service-read-values.service";
import { TranslateService } from "@ngx-translate/core";
import { GetLangService } from "../../../../../shared/services/get-lang.service";

@Component({
  selector: "app-read-values-log",
  templateUrl: "./read-values-log.component.html",
  styleUrls: ["./read-values-log.component.css"],
})
export class ReadValuesLogComponent implements OnInit {
  valueModel: String;
  valuesArray = ["All", "moisture", "conductivity", "turbidity", "ph", "temp"];
  values: ValuesInterface[];
  valuesLog: ValueLog[];
  valuesHistory: ValuesHistory[];
  modeAll = true;
  value;
  constructor(
    private serviceReadValuesService: ServiceReadValuesService,
    private translate: TranslateService,
    private getLang: GetLangService
  ) {}

  ngOnInit() {
    this.serviceReadValuesService.getValues().subscribe((values) => {
      this.values = values;
      console.log(values);
    });
    this.serviceReadValuesService
      .getValuesHistory()
      .subscribe((valuesHistory) => {
        this.valuesHistory = valuesHistory;
        console.log(valuesHistory);
      });
    this.serviceReadValuesService.getValuesLog().subscribe((valuesLog) => {
      this.valuesLog = valuesLog;
      console.log(valuesLog);
    });
    this.translate.use(this.getLang.getLang());

    console.log("va" + this.getLang.getLang());
  }
  valuesFilter(env, value) {
    this.value = value;
    console.log(value);
    if (value === "All") {
      this.modeAll = true;
    } else {
      this.modeAll = false;
    }
  }
  getDate() {
    let num1 = (document.getElementById("date") as HTMLInputElement).value;
    //this.milliseconds = new Date(num1).getTime() / 100;
    console.log(new Date(num1.toString()));
    //console.log(typeof milliseconds)
    return new Date(num1).getTime();
  }
  convertToDate(date) {
    console.log(date);

    return date;
  }
  calcDay() {
    let now = new Date();
    let now2 = Number(now);
    let start = new Date(now.getFullYear(), 0, 0);
    let start2 = Number(start);
    let diff = now2 - start2;
    let oneDay = 1000 * 60 * 60 * 24;
    let day = Math.floor(diff / oneDay);
    return day;
  }
}
