/**
 * This is the order class for ng.
 */
export class Order {


  constructor(public orderId :number,
  public petrolQty :number,
  public orderDate : Date,
  public truckId: string) {
  }

}
