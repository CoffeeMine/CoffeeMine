package org.coffeemine.app.spring.components.JSONUploadSection;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import org.springframework.lang.NonNull;

@Tag("wasm-holder-element")
class WasmHolder extends Component {
    WasmHolder(@NonNull String part_name) {
        // Definitely not a hack™
        UI.getCurrent().getPage().executeJs(
                "(async () => {\n" +
                "  const scriptPromise = new Promise((resolve, reject) => {\n" +
                "    const script = document.createElement('script');\n" +
                "    document.body.appendChild(script);\n" +
                "    script.onload = resolve;\n" +
                "    script.onerror = reject;\n" +
                "    script.async = true;\n" +
                "    script.src = './wasm-parts/" + part_name +".js';\n" +
                "  });\n" +
                "  scriptPromise.then(() => (Module.onRuntimeInitialized = () => {" +
                "    const f_test = cwrap('test', 'number', ['number']);\n" +
                "    console.log(f_test(9));\n" +
                "  ;}));" +
                "})();");
    }
}

public class JSONUploadSection extends Div {
    private WasmHolder wh = new WasmHolder("JSONValidator");
    private FileBuffer file_buffer = new FileBuffer();
    private Upload upload = new Upload();

    public JSONUploadSection() {
        upload.setReceiver(file_buffer);
        upload.setAutoUpload(false);
        upload.setMaxFiles(1);
        add(upload, wh);
    }
}
