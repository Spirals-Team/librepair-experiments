import { Component, OnInit } from '@angular/core';
import { Asset } from './asset';
import { AssetService } from '../asset.service';

@Component({
  selector: 'app-asset',
  templateUrl: './asset.component.html',
  styleUrls: ['./asset.component.css']
})
export class AssetComponent implements OnInit {

  assets: Asset[];

  constructor(private assetService : AssetService) { }

  ngOnInit() {
    this.getAssets();
  }

  getAssets(): void{
    this.assetService.getAssets()
    .subscribe(assets => this.assets = assets);
  }

  add(name: string){
    name = name.trim();
    if(!name){ return; }
    this.assetService.addAsset({ name } as Asset)
    .subscribe(asset => this.assets.push(asset));
  }

  delete(asset: Asset){

    this.assets = this.assets.filter(h => h !== asset);
    this.assetService.deleteAsset(asset).subscribe();
  }

}
