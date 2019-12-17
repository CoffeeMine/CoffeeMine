import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';

class HorizontalScroll extends PolymerElement {
  static get template() {
    return html`
            <style>
                #container {
                    overflow-x:scroll;
                    overflow-y:hidden;
                    scrollbar-width: none;
                    padding: 10px;
                    box-shadow: inset 0px -180px 140px -140px rgba(0,0,0,0.75);
                }
                #container::-webkit-scrollbar {
                    display: none;
                }
                
            </style>
            <vaadin-horizontal-layout id="container" on-wheel=scroll><slot></slot></vaadin-horizontal-layout>
            `;
  }
  static get is() {
    return 'horizontal-scroll';
  }

  scroll(e) {
    // TODO: add kinetic scrolling
    this.$.container.scrollLeft += e.deltaY;
  }
}


customElements.define(HorizontalScroll.is, HorizontalScroll);