package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.data.TrackItem;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CreateTrackItem extends Dialog {

    public CreateTrackItem(Consumer<TrackItem> callback) {
        final var type = new ComboBox<>("Type");
        type.setItems(Arrays.stream(TrackItem.Type.values()).map(Enum::name).collect(Collectors.toList()));
        type.setRequired(true);
        final var name = new TextField("Title: ");
        name.setRequired(true);
        final var desc = new TextField("Description: ");
        desc.setRequired(true);
        final var submit = new Button("Submit", e -> {
            callback.accept(new TrackItem(-1,
                    name.getValue(),
                    desc.getValue(),
                    CurrentUser.get().getId(),
                    -1,
                    TrackItem.Type.valueOf((String) type.getValue()),
                    false,
                    TrackItem.Status.OPEN,
                    TrackItem.Resolution.UNRESOLVED,
                    LocalDateTime.now(),
                    null));
            close();
        });
        add(type, name, desc, submit);
        open();
    }

}
