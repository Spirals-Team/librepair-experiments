import { Component, ViewChild } from '@angular/core';
import { NavController, NavParams, AlertController, Navbar, LoadingController } from 'ionic-angular';
import { RulesService } from '../../services/rules.service';
import { Rule } from '../../api/Rule';
import { RulesCustomAutoCompleteService } from '../../services/rulesCustomAutoComplete.service';
import { AutoCompleteComponent } from 'ionic2-auto-complete';
import { RulesStaticPage } from '../rules-static/rulesStatic.component';
import { RulesStaticAutoCompleteService } from '../../services/rulesStaticAutoComplete.service';

@Component({
  selector: 'page-rules-custom',
  templateUrl: 'rulesCustom.component.html',
})
export class RulesCustomPage extends RulesStaticPage {

  @ViewChild(AutoCompleteComponent) autocomplete: AutoCompleteComponent;
  @ViewChild(Navbar) navBar: Navbar;
  selectedRule: Rule;
  rules: Rule[];
  custom: boolean = true;

  constructor(public navCtrl: NavController,
    public navParams: NavParams,
    public loadingCtrl: LoadingController,
    public rulesCustomAutoCompleteService: RulesCustomAutoCompleteService,
    public rulesStaticAutoCompleteService: RulesStaticAutoCompleteService,
    public alertCtrl: AlertController,
    public rulesService: RulesService) {

    super(navCtrl, navParams, loadingCtrl, rulesCustomAutoCompleteService, rulesStaticAutoCompleteService, alertCtrl, rulesService);
  }

  registerRulesChangedListener() {
    this.rulesService.rulesChangedObservable.subscribe(rule => {
      this.loadRules();
    });
  }

  releaseRule(rule: Rule) {
    this.rulesService.createRule(rule, false).subscribe(rules => {
      this.rulesService.deleteRule(rule.id, true).subscribe(rules => {
        this.loadRules();
      });
    });
  }
}
