package org.coffeemine.app.spring.components.JSONUploadSection;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;

public class JSONUploadSection extends VerticalLayout {
    private FileBuffer file_buffer = new FileBuffer();

    public JSONUploadSection() {
        final var uploader = new Upload(file_buffer);
        uploader.setAutoUpload(false);
        uploader.setMaxFiles(1);
        add(uploader);
    }
}
