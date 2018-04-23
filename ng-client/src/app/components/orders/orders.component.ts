import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {Order} from "../../model/order";
import {OrderService} from "../../services/orders/order.service";
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})

export class OrdersComponent implements OnInit {

  orders: Observable<Array<Order>>;
  status: number;
  start: Date;
  end: Date;
  order: Order;

  dateForm = new FormGroup({
    date1: new FormControl('', Validators.required),
    date2: new FormControl('', Validators.required)
   });

  statusCode: number;

  constructor(private orderService: OrderService, private router: Router) {
  }

  ngOnInit() {
    this.orders = this.getOrders();
  }

  private getOrdersFilter() {
    if (this.dateForm.invalid) {
      return;
    }
    this.start = this.dateForm.get('date1').value.trim();
    this.end = this.dateForm.get('date2').value.trim();

    this.orders = this.orderService.getOrders(this.start, this.end)
  }

  private getOrders() {
   return this.orderService.getOrdersWithoutDate()
  }

  deleteOrder(order) {
    return this.orderService.deleteOrderById(order.orderId)
      .subscribe((order) => {
          this.router.navigate(['/orders'])
          console.log(order);
        },
        errorCode => this.status = errorCode)
  }

  updateOrder(order) {
    this.orderService.setter(order)
    this.router.navigate(['/editOrders'])
  }

}
