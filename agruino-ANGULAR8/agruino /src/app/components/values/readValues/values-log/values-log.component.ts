import { Component, OnInit } from "@angular/core";
import { ValueLog } from "../../../../shared/models/valueLog";
import { ServiceReadValuesService } from "../../../../shared/services/service-read-values.service";
import { AuthService } from "../../../../shared/services/auth.service";
import { TranslateService } from "@ngx-translate/core";
import { GetLangService } from "../../../../shared/services/get-lang.service";

@Component({
  selector: "app-values-log",
  templateUrl: "./values-log.component.html",
  styleUrls: ["./values-log.component.css"],
})
export class ValuesLogComponent implements OnInit {

  constructor(
    public authService: AuthService,
    private translate: TranslateService,
    private getLang: GetLangService
  ) {}
  activeLang: string;
  ngOnInit() {

    this.translate.use(this.getLang.getLang());
    console.log("va" + this.getLang.getLang());
  }
}
