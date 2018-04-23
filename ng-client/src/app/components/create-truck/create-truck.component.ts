import {Component, OnInit} from '@angular/core';
import {Truck} from "../../model/truck";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TruckService} from "../../services/trucks/truck.service";
import {Router} from "@angular/router";
import { Location } from '@angular/common';

@Component({
  selector: 'app-create-truck',
  templateUrl: './create-truck.component.html',
  styleUrls: ['./create-truck.component.css']
})
export class CreateTruckComponent implements OnInit {

  statusCode: number;
  processValidation = false;
  requestProcessing = false;

  date = new Date();
  today = new Date(this.date.getFullYear(), this.date.getMonth(), this.date.getDate());

  createForm = new FormGroup({
    truckCode: new FormControl('', Validators.required),
    date: new FormControl('', Validators.required),
    descriptions: new FormControl('', Validators.required)
  });

  constructor(private truckService: TruckService, private router: Router,
              private location: Location) {

  }

  ngOnInit() {
    this.truckService.getter();
  }

  saveForm() {
    this.processValidation = true;
    if (this.createForm.invalid) {
      return; //Validation failed, exit from method.
    }

// if we are here then all good
    this.preProcessConfigurations()
    let truckCode = this.createForm.get('truckCode').value.trim();
    //let date = this.createForm.get('date').value.trim();
    let description = this.createForm.get('descriptions').value.trim();

    let truck = new Truck(null, truckCode, this.today, description);

      this.truckService.createTruck(truck).subscribe(successCode => {
        this.statusCode = successCode;
        this.router.navigate(['/trucks']);
      }, errorCode => this.statusCode = errorCode);

  }

  preProcessConfigurations() {
    this.statusCode = null;
    this.requestProcessing = true;
  }

  back(): void{
    this.location.back()
  }

}
