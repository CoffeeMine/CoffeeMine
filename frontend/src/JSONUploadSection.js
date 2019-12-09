import {html, PolymerElement} from '@polymer/polymer';

function readFileAsync(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => {
            resolve(reader.result);
        };
        reader.onerror = reject;
        reader.readAsArrayBuffer(file);
    })
}

class JSONUploadSection extends PolymerElement {

    static get template() {
        return html`<vaadin-upload id="upload"></vaadin-upload><vaadin-button id="import">Import</vaadin-button>`;
    }

    static get is() {
        return 'json-upload-section';
    }

    constructor() {
        super();
        this.Module = {
            async file_exec(file, func) {
                const res = await readFileAsync(file);
                const raw_data = new Uint8Array(res, 0, res.byteLength);
                return func(raw_data, file.name);
            },
            _f_test: null,
            f_test: (raw) => {
                const input_ptr = this.Module._malloc(raw.length + 1);
                this.Module.HEAPU8.set(raw, input_ptr);
                const output_ptr = this.Module._malloc(512);
                const len = this.Module._f_test(input_ptr, raw.length, output_ptr);
//              const res_data = new Uint8Array(this.Module.HEAPU8.buffer, output_ptr, len);
                this.Module._free(output_ptr);
                this.Module._free(input_ptr);
                return [len/*, res_data.toString()*/];
            },
        };
    }

    ready() {
        super.ready();
        const http = new XMLHttpRequest();
        http.open("GET", "/JSONValidator.js");
        http.send();
        http.onload = () => this.Module = Function('Module', `
            Module.onRuntimeInitialized = () => Module._f_test = cwrap('test', 'number', ['number', 'number', 'number']);
            ${http.responseText}
            return Module;
        `)(this.Module);
        /* you saw nothing */

        this.$.upload._addFile = new Proxy(this.$.upload._addFile, {
            apply: async (target, self, args) => {
                console.log(args[0]);
                if((await this.Module.file_exec(args[0], this.Module.f_test))[0] === 0)
                    target.apply(self, args);
            }
        });
    }
}

customElements.define(JSONUploadSection.is, JSONUploadSection);
