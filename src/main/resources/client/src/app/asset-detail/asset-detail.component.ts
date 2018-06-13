import { Component, OnInit, Input } from '@angular/core';
import { Asset } from 'src/app/asset/asset';
import { ActivatedRoute } from '@angular/router';
import { AssetService } from '../asset.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-asset-detail',
  templateUrl: './asset-detail.component.html',
  styleUrls: ['./asset-detail.component.css']
})
export class AssetDetailComponent implements OnInit {

  @Input() asset : Asset;

  constructor(
    private route: ActivatedRoute,
    private assetService: AssetService,
    private location: Location
  ) { }

  ngOnInit() {
    this.getAsset();
  }

  getAsset(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.assetService.getAsset(id)
    .subscribe(asset => this.asset = asset)
  }

  goBack(): void{
    this.location.back();
  }

  save(): void{
    this.assetService.updateAsset(this.asset)
    .subscribe(() => this.goBack());
  }

}
