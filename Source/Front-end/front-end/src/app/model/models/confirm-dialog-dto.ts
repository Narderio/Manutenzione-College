export class ConfirmDialogDto {
  title: string;
  message: string;
  sendButton: string;

  constructor(title: string, message: string, sendButton: string) {
    this.title = title;
    this.message = message;
    this.sendButton = sendButton;
  }
}
