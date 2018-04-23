
import {AbstractControl} from "@angular/forms";


export class TruckCodeValidation {


  static validate(truckCode: number) {

    return (control: AbstractControl): {[key: string]: boolean} => {
      if (!control.value || 0 === control.value.length) {
        return null;
      }

      if (control.value.length() >= truckCode && control.value.length() < 7 ) {
        return null;
      }
      return {"incorrect input": true};
    };
  }
}
