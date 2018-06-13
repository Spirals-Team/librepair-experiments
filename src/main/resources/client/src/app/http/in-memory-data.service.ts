import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Asset } from '../asset/asset';

@Injectable({
  providedIn: 'root'
})
export class InMemoryDataService implements InMemoryDbService{

  createDb(){
    
    const assets: Asset[] = [
      {id:1, name: 'Lenovo Laptop', category: 'Computers', value: 35600},
      {id:2, name: 'Paper Shredder', category: 'Electronics', value: 8900},
      {id:3, name: 'AC Unit', category: 'Fittings', value: 35600},
      {id:4, name: 'KAZ 256F', category: 'Motor Vehicles', value: 420130},
      {id:5, name: 'Water Dispenser', category: 'Elctronics', value: 5600},
      {id:6, name: 'Officer Chair', category: 'Furniture', value: 2500},
      {id:7, name: 'Coffee Maker', category: 'Electronics', value: 6500},
      {id:8, name: 'IBM XS340', category: 'Computers', value: 560456},
  ];
    return {assets};
  }
}
