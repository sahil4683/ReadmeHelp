import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  inputValue: string = '';

  onInputFocus() {
    // Implement any logic you need when the input is focused
  }

  onInputBlur() {
    // Implement any logic you need when the input loses focus
  }
}
