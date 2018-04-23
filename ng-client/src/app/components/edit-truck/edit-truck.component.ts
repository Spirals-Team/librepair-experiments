import {Component, Input, OnInit} from '@angular/core';
import {Truck} from "../../model/truck";
import {TruckService} from "../../services/trucks/truck.service";
import 'rxjs/add/operator/switchMap';
import {ActivatedRoute, Params, Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Location} from "@angular/common";

@Component({
  selector: 'app-edit-truck',
  templateUrl: './edit-truck.component.html',
  styleUrls: ['./edit-truck.component.css']
})

export class EditTruckComponent implements OnInit {

  @Input() truck: Truck;
  processValidation = false;
  statusCode: number;
  requestProcessing = false;


  truckForm = new FormGroup({
    truckCode: new FormControl('', Validators.required),
    date: new FormControl('', Validators.required),
    descriptions: new FormControl('', Validators.required)
  });

  constructor(private truckService: TruckService,
              private router: Router, private route: ActivatedRoute, private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .switchMap((params: Params) => this.truckService.getTruckById(+params['truckId']))
      .subscribe((truck) => {
        this.truck = truck;
        console.log("truck");
      });
  }


  back() {
    this.location.back()
  }

  processForm() {

    this.processValidation = true;
    if (this.truckForm.invalid) {
      return; //Validation failed, exit from method.
    }

    // if we are here then all good
    this.preProcessConfigurations()

    let truckCode = this.truckForm.get('truckCode').value.trim();
    let date = this.truckForm.get('date').value.trim();
    let description = this.truckForm.get('descriptions').value.trim();

    //this.dateString = new Date(this.truck.purchasedDate);

    let truck = new Truck(this.truck.truckId, truckCode, date , description);

    this.truckService.updateTrucks(truck).subscribe(successCode => {
      this.statusCode = successCode;
      this.router.navigate(['/trucks']);
    }, errorCode => this.statusCode = errorCode);


  }

  //Perform preliminary processing configurations
  preProcessConfigurations() {
    this.statusCode = null;
    this.requestProcessing = true;
  }


}
