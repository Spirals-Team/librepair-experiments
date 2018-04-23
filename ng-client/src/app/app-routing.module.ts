import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {OrdersComponent, TrucksComponent, PageNotFoundComponent} from "./components";
import {EditTruckComponent} from "./components";
import {TruckProfileComponent} from "./components/truck-profile/truck-profile.component";
import {CreateTruckComponent} from "./components/create-truck/create-truck.component";
import {CreateOrderComponent} from "./components/create-order/create-order.component";

const routes: Routes = [
  {
    path: 'orders',
    component: OrdersComponent
  },
  {
    path: 'trucks',
    component: TrucksComponent
  },
  {
    path: 'editsTrucks/:truckId',
    component: EditTruckComponent
  },
  {
    path: 'newTruck',
    component: CreateTruckComponent
  },
  {
    path: 'newOrder/:truckId',
    component: CreateOrderComponent
  },
  {
    path: 'truckProfile/:truckId',
    component: TruckProfileComponent
  },
  {
    path: '',
    redirectTo: '/trucks',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

