import {FormDialogInput} from "./form-dialog-input";


export class FormDialogDto {
  title: string;
  form: FormDialogInput[];
  sendButton: string;

  constructor(title: string, sendButton: string, form: FormDialogInput[]) {
    this.title = title;
    this.form = form;
    this.sendButton = sendButton;
  }
}
