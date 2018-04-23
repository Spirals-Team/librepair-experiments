/**
 * This is the truck class for ng used to get basic truck detail.
 */
export class Truck {

  constructor (
  public truckId: string,
  public truckCode : string,
  public purchasedDate: Date,
  public descriptions : string ) {
  }

}
