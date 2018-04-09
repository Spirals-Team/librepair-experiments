import { rootReducer } from './reducers';
import { NgReduxModule, NgRedux, DevToolsExtension } from '@angular-redux/store';
import { provideReduxForms } from '@angular-redux/form';
import { NgModule } from '@angular/core';
import { IGlobalState } from '../state/globalState';

@NgModule({
  imports: [NgReduxModule],
})
export class StoreModule {
  constructor(
    public store: NgRedux<IGlobalState>,
    devTools: DevToolsExtension,
  ) {
    store.configureStore(
      rootReducer,
      {},
      [],
      devTools.isEnabled() ? [devTools.enhancer()] : []
    );

    provideReduxForms(store);
  }
}
