import { Component, OnInit } from '@angular/core';
import { Asset } from '../asset/asset';
import { AssetService } from '../asset.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  assets: Asset[] = [];

  constructor(private assetService: AssetService) { }

  ngOnInit() {

    this.getAssets();
  }

  getAssets(): void{
    this.assetService.getAssets()
    .subscribe(assets => this.assets = assets.slice(1,5));
  }

}
