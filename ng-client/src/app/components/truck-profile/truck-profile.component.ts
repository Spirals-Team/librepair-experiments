import {Component, Input, OnInit} from '@angular/core';
import {TruckService} from "../../services/trucks/truck.service";
import {Location} from "@angular/common";
import {OrderService} from "../../services/orders/order.service";
import {Observable} from "rxjs/Observable";
import {Order} from "../../model/order";
import {Truck} from "../../model/truck";
import {ActivatedRoute, Params} from "@angular/router";
import {TruckDetail} from "../../model/truckDetail";

@Component({
  selector: 'app-truck-profile',
  templateUrl: './truck-profile.component.html',
  styleUrls: ['./truck-profile.component.css']
})
export class TruckProfileComponent implements OnInit {

  orders: Observable<Array<Order>>;
  @Input() truck: TruckDetail;

  constructor(private truckService: TruckService, private route: ActivatedRoute,
              private location: Location, private orderService: OrderService) {
  }

  ngOnInit() {
    this.route.params
      .switchMap((params: Params) => this.truckService.getTruckById(+params['truckId']))
      .subscribe((truck) => {
        this.truck = truck;
        console.log("truck");
      });
  }


  back(){
    this.location.back();
  }


}
