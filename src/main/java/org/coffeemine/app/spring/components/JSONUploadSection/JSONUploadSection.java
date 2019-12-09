package org.coffeemine.app.spring.components.JSONUploadSection;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.io.IOException;

@Tag("json-upload-section")
@JsModule("./src/JSONUploadSection.js")
public class JSONUploadSection extends PolymerTemplate<TemplateModel> {
    private MemoryBuffer memory_buffer = new MemoryBuffer();

    @Id("upload")
    private Upload upload;
    @Id("import")
    private Button button;

    public JSONUploadSection() {
        super();
        upload.setReceiver(memory_buffer);
        upload.setMaxFiles(1);
        button.addClickListener(e -> {
            try {
                NitriteDBProvider.getInstance().importJSONProject(new String(memory_buffer.getInputStream().readAllBytes()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
