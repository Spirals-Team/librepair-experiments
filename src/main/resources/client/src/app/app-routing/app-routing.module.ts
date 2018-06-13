import { NgModule } from '@angular/core';
import { RouterModule, Routes} from '@angular/router';
import { AssetComponent } from '../asset/asset.component';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { AssetDetailComponent } from '../asset-detail/asset-detail.component';

const routes: Routes = [
  {path: 'assets', component: AssetComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'detail/:id', component: AssetDetailComponent},
  {path: '', redirectTo:'/dashboard', pathMatch:'full'},
];

@NgModule({
  imports : [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],  
})
export class AppRoutingModule { 
}
