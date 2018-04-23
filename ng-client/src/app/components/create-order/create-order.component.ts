import {Component, Input, OnInit} from '@angular/core';
import {TruckService} from "../../services/trucks/truck.service";
import {OrderService} from "../../services/orders/order.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {Order} from "../../model/order";
import {Truck} from "../../model/truck";
import {Location} from "@angular/common";
import {TruckDetail} from "../../model/truckDetail";

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent implements OnInit {
   date = new Date();
   today = new Date(this.date.getFullYear(), this.date.getMonth(), this.date.getDate());

  statusCode: number;
  processValidation = false;
  requestProcessing = false;

  @Input() truck: TruckDetail;

  createOrderForm = new FormGroup({
    petrolQty: new FormControl(Validators.minLength(12),Validators.required)
  });

  /**
   *
   * @param {Router} router
   * @param {ActivatedRoute} route
   * @param {TruckService} truckService
   * @param {OrderService} orderService
   * @param {Location} location
   */
  constructor(private router: Router, private route: ActivatedRoute,
              private truckService: TruckService, private orderService: OrderService,
              private location: Location) { }

  ngOnInit() {
    /**
     * get truck id on init for order.
     */
    this.route.params
      .switchMap((params: Params) => this.truckService.getTruckById(+params['truckId']))
      .subscribe((truck) => {
        this.truck = truck;
        console.log("truck");
      });
  }

  /**
   * save order.
   */
  saveOrderForm() {

    this.processValidation = true;

    if (this.createOrderForm.invalid) {
      return; //Validation failed, exit from method.
    }

    this.preProcessConfigurations();

    let petrolQty = this.createOrderForm.get('petrolQty').value;

    let order = new Order(null, petrolQty, this.today, this.truck.truckId)

    this.orderService.createOrder(order).subscribe(successCode => {
      this.statusCode = successCode;
      this.router.navigate(['/trucks']);
    }, errorCode => this.statusCode = errorCode);
  }

  preProcessConfigurations() {
    this.statusCode = null;
    this.requestProcessing = true;
  }

  back(){
    this.location.back()
  }

}
