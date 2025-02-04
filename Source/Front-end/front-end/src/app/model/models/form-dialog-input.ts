import {ValidatorFn} from "@angular/forms";
import {InputType} from "./input-type";

export class FormDialogInput {
  type: InputType;
  label: string;
  header: string;
  value?: string;
  selectOptions?: string[];
  validator: ValidatorFn[];
  min?: number;
  max?: number;

  constructor(type: InputType, label: string, header: string, validator: ValidatorFn[], min?: number, max?: number, selectOptions?: string[], value?: string) {
    this.type = type;
    this.label = label;
    this.header = header;
    this.validator = validator;
    this.min = min;
    this.max = max;
    this.selectOptions = selectOptions;
    this.value = value;
  }
}
