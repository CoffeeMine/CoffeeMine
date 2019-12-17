import {html, PolymerElement} from '@polymer/polymer';
import '@polymer/iron-collapse/iron-collapse.js';

class CollapsibleElement extends PolymerElement {
    static get template() {
        return html`
      <iron-collapse opened="{{opened}}" horizontal="{{horizontal}}" transitioning="{{transitioning}}" noAnimation="{{noAnimation}}">
        <slot></slot>
      </iron-collapse>
    `;
    }
}
customElements.define('collapsible-element', CollapsibleElement);