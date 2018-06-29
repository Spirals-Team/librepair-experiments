import {AdminPageComponent} from './../pages/admin-page/admin-page.component';
import {MarketplacePageComponent} from '../pages/marketplace-page/marketplace-page.component';
import {QuestPageComponent} from './../pages/quest-page/quest-page.component';
import {AdventurePageComponent} from '../pages/adventure-page/adventure-page.component';
import {StartPageComponent} from './../pages/start-page/start-page.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MyAvatarPageComponent} from '../pages/my-avatar-page/my-avatar-page.component';
import {GamemasterPageComponent} from '../pages/gamemaster-page/gamemaster-page.component';
import {AuthenticationGuard} from '../login/authentication.guard';
import {EmptyPageComponent} from '../pages/empty-page/empty-page.component';
import {RoutingUrls} from './routing-urls';


const appRoutes: Routes = [
  {path: '', redirectTo: '/empty', pathMatch: 'full'},
  {path: RoutingUrls.empty, component: EmptyPageComponent},
  {path: RoutingUrls.start, component: StartPageComponent, canActivate: [AuthenticationGuard]},
  {path: RoutingUrls.myAvatar, component: MyAvatarPageComponent, canActivate: [AuthenticationGuard]},
  {path: RoutingUrls.adventures, component: AdventurePageComponent, canActivate: [AuthenticationGuard]},
  {path: RoutingUrls.quests, component: QuestPageComponent, canActivate: [AuthenticationGuard]},
  {path: RoutingUrls.marketplace, component: MarketplacePageComponent, canActivate: [AuthenticationGuard]},
  {path: RoutingUrls.gamemaster, component: GamemasterPageComponent, canActivate: [AuthenticationGuard]},
  {path: RoutingUrls.admin, component: AdminPageComponent, canActivate: [AuthenticationGuard]},
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
