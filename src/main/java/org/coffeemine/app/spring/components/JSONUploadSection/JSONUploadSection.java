package org.coffeemine.app.spring.components.JSONUploadSection;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("json-upload-section")
@JsModule("./src/JSONUploadSection.js")
public class JSONUploadSection extends PolymerTemplate<TemplateModel> {
    private FileBuffer file_buffer = new FileBuffer();

    @Id("upload")
    private Upload upload;

    public JSONUploadSection() {
        super();
        upload.setReceiver(file_buffer);
        upload.setAutoUpload(false);
        upload.setMaxFiles(1);
    }
}
