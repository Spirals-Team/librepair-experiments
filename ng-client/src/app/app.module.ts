import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {Router } from "@angular/router";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

import { AppComponent } from './app.component';
import {OrdersComponent, TrucksComponent ,PageNotFoundComponent ,EditTruckComponent} from './components';
import {NavigationComponent} from "./components/navigation/navigation.component";
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {OrderService} from "./services/orders/order.service";
import {TruckService} from "./services/trucks/truck.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TruckProfileComponent} from "./components/truck-profile/truck-profile.component";
import { CreateTruckComponent } from './components';
import { CreateOrderComponent } from './components';

@NgModule({
  declarations: [
    AppComponent,
    TrucksComponent,
    OrdersComponent,
    PageNotFoundComponent,
    NavigationComponent,
    EditTruckComponent,
    TruckProfileComponent,
    CreateTruckComponent,
    CreateOrderComponent
  ],

  imports: [
    NgbModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [OrderService,TruckService],
  bootstrap: [AppComponent]
})

 export class AppModule {
   constructor(router: Router) {
     console.log('Routes: ', JSON.stringify(router.config, undefined, 2));
    }
 }
