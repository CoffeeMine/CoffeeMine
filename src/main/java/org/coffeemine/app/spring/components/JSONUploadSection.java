package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Span;
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

    @Id("error")
    private Span error;

    public JSONUploadSection(Button button) {
        super();
        upload.setReceiver(memory_buffer);
        upload.setMaxFiles(1);
        upload.setAcceptedFileTypes("application/json");

        error.setText("Invalid file.");
        error.setVisible(false);

        button.setEnabled(false);

        button.addClickListener(e -> {
            try {
                NitriteDBProvider.getInstance()
                        .importJSONProject(new String(memory_buffer.getInputStream().readAllBytes()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        this.addDetachListener(e -> {
            upload.getStyle().clear();
            error.setVisible(false);
        });
        upload.addSucceededListener(e -> {
            upload.getStyle().clear();
            error.setVisible(false);
            button.setEnabled(true);
        });
        upload.getElement().addEventListener("file-remove", e -> {
            button.setEnabled(false);
        });
    }

    @ClientCallable
    void errorNotify() {
        upload.getStyle().set("border-color", "var(--material-error-color)");
        error.getStyle().set("color", "var(--material-error-color)");
        error.getStyle().set("padding-left", "15px");
        error.setVisible(true);
    }
}
