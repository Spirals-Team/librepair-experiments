import { Component, OnInit } from '@angular/core';
import { Asset } from '../asset/asset';
import { AssetService } from '../asset.service';
import { MessageService } from '../message.service';
import { Observable, Subject } from 'rxjs';
import { debounce, debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-asset-search',
  templateUrl: './asset-search.component.html',
  styleUrls: ['./asset-search.component.css']
})
export class AssetSearchComponent implements OnInit {

  assets$: Observable<Asset[]>;

  private searchTerms = new Subject<string>();

  constructor(private assetService: AssetService){}

  search(term: string): void{
    this.searchTerms.next(term);
  }

  ngOnInit(): void{
    this.assets$ = this.searchTerms.pipe(
      // wait 300ms after each key stroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) =>
    this.assetService.searchAssets(term))
    );
  }

}
