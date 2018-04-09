import {NgForm} from '@angular/forms';

export const getValuesFromForm = <T>(form: NgForm): T => form.form.value;
