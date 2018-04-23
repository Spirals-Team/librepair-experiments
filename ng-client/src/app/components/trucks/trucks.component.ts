import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {Truck} from "../../model/truck";
import {TruckService} from "../../services/trucks/truck.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-trucks',
  templateUrl: './trucks.component.html',
  styleUrls: ['./trucks.component.css']
})

export class TrucksComponent implements OnInit {
  truck:Truck;
  statusCode: number;
  trucks: Observable<Array<Truck>>;
  requestProcessing = false;

  constructor(private truckService: TruckService, private router: Router, private route: ActivatedRoute) {
   }

  ngOnInit() {
    this.trucks = this.getTrucksDtos();
  }

  getTrucksDtos() {
    return this.truckService.getTrucks();
  }

  deleteTruck(truck) {
    this.truckService.deleteTruckById(truck.truckId)
      .subscribe(successCode => {
          this.statusCode = successCode;
          this.router.navigate(['/trucks'])
        },
        errorCode => this.statusCode = errorCode);
  }

  updateTruck(truck) {
    this.truckService.setter(truck)
    this.router.navigate(['/editsTrucks/'+ truck.truckId])
  }

  //this gets us edit page with blank forms.
  newTruck() {
    let truck = new Truck(null, null, null, null)
    this.truckService.setter(truck)
    this.router.navigate(['/editsTrucks/' + truck.truckId])
  }

}

