import {Directive, ElementRef, HostListener} from '@angular/core';

    const DISABLE_TIME = 800;

@Directive({
  selector: 'button[d-submit]'
})
export class ButtonSubmitDirective {
  constructor(private elementRef: ElementRef) { }
  @HostListener('click', ['$event'])
  clickEvent() {
      this.elementRef.nativeElement.setAttribute('disabled', 'true');
      setTimeout(() => this.elementRef.nativeElement.removeAttribute('disabled'), DISABLE_TIME);
  }
}
