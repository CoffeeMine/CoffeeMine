package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.apache.commons.lang3.StringUtils;
import org.coffeemine.app.spring.data.Risk;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.view.View;

import java.util.*;
import java.util.stream.Collectors;

public class RiskMatrix extends View {

    private List<Risk> riskList;
    private ListDataProvider<Risk> dataProvider;

    public RiskMatrix(){

        riskList = NitriteDBProvider.getInstance().getRisks().collect(Collectors.toList());
        dataProvider = new ListDataProvider<>(riskList);

        ArrayList<String> types = new ArrayList<>();
        types.add("Commercial");
        types.add("Relationship");
        types.add("Requirements");
        types.add("Planning and Resource");
        types.add("Technical");
        types.add("Subcontract");

        ArrayList<String> impacts = new ArrayList<>();
        impacts.add("Select");
        impacts.add("Large");
        impacts.add("Moderate");
        impacts.add("Small");

        ArrayList<String> likelihoods = new ArrayList<>();
        likelihoods.add("Select");
        likelihoods.add("High");
        likelihoods.add("Medium");
        likelihoods.add("Low");

        Grid<Risk> riskMatrix = new Grid<>();
        riskMatrix.setItems(riskList);
        riskMatrix.setDataProvider(dataProvider);

        Grid.Column<Risk> descriptionColumn = riskMatrix
                .addColumn(Risk::getDescription).setHeader("Description");
        Grid.Column<Risk> typeColumn = riskMatrix
                .addColumn(Risk::getType).setHeader("Type");
        Grid.Column<Risk> impactColumn = riskMatrix
                .addColumn(Risk::getImpact).setHeader("Impact");
        Grid.Column<Risk> likelihoodColumn = riskMatrix
                .addColumn(Risk::getLikelihood).setHeader("Likelihood");

        Binder<Risk> binder = new Binder<>(Risk.class);
        Editor<Risk> editor = riskMatrix.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField descriptionField = new TextField();
        binder.bind(descriptionField, "description");
        descriptionColumn.setEditorComponent(descriptionField);

        Select<String> typeSelect = new Select<>();
        typeSelect.setItems(types);
        binder.bind(typeSelect, "type");
        typeColumn.setEditorComponent(typeSelect);

        Select<String> impactSelect = new Select<>();
        impactSelect.setItems(impacts);
        binder.bind(impactSelect, "impact");
        impactColumn.setEditorComponent(impactSelect);

        Select<String> likelihoodSelect = new Select<>();
        likelihoodSelect.setItems(likelihoods);
        binder.bind(likelihoodSelect, "likelihood");
        likelihoodColumn.setEditorComponent(likelihoodSelect);

        Collection<Button> editButtons = Collections
                .newSetFromMap(new WeakHashMap<>());

        Grid.Column<Risk> editorColumn = riskMatrix
                .addComponentColumn(risk -> {
                    Button edit = new Button("Edit");
                    edit.addClassName("edit");
                    edit.addClickListener(e -> {
                        editor.editItem(risk);
                        descriptionField.focus();

                    });
                    edit.setEnabled(!editor.isOpen());
                    editButtons.add(edit);
                    return edit;
                });


        editor.addOpenListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));
        editor.addCloseListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));

        Button save = new Button("Save", e -> editor.save());
        save.addClassName("save");

        Button cancel = new Button("Cancel", e -> editor.cancel());
        cancel.addClassName("cancel");

        riskMatrix.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");

        Button addButton = new Button("Add New Risk", event -> {
            riskList.add(new Risk(0,"New Risk", "Commercial", "Select", "Select"));
            NitriteDBProvider.getInstance().addRisk(new Risk(0,"New Risk", "Commercial", "Select", "Select"));
            riskMatrix.getDataProvider().refreshAll();
        });

        riskMatrix.addComponentColumn(item -> createRemoveButton(riskMatrix, item));

        Div buttons = new Div(save, cancel);
        editorColumn.setEditorComponent(buttons);

        FooterRow footerRow = riskMatrix.appendFooterRow();
        footerRow.getCell(descriptionColumn).setComponent(addButton);

        HeaderRow filterRow = riskMatrix.appendHeaderRow();

        TextField descriptionFilter = new TextField();
        descriptionFilter.addValueChangeListener(event -> dataProvider.addFilter(
                risk -> StringUtils.containsIgnoreCase(risk.getDescription(),
                        descriptionFilter.getValue())));

        descriptionFilter.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(descriptionColumn).setComponent(descriptionFilter);
        descriptionFilter.setSizeFull();
        descriptionFilter.setPlaceholder("Search");

        TextField typeFilter = new TextField();
        typeFilter.addValueChangeListener(event -> dataProvider.addFilter(
                risk -> StringUtils.containsIgnoreCase(risk.getType(),
                        typeFilter.getValue())));

        typeFilter.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(typeColumn).setComponent(typeFilter);
        typeFilter.setSizeFull();
        typeFilter.setPlaceholder("Search");

        TextField impactFilter = new TextField();
        impactFilter.addValueChangeListener(event -> dataProvider.addFilter(
                risk -> StringUtils.containsIgnoreCase(risk.getImpact(),
                        impactFilter.getValue())));

        impactFilter.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(impactColumn).setComponent(impactFilter);
        impactFilter.setSizeFull();
        impactFilter.setPlaceholder("Search");

        TextField likelihoodFilter = new TextField();
        likelihoodFilter.addValueChangeListener(event -> dataProvider.addFilter(
                risk -> StringUtils.containsIgnoreCase(risk.getLikelihood(),
                        likelihoodFilter.getValue())));

        likelihoodFilter.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(likelihoodColumn).setComponent(likelihoodFilter);
        likelihoodFilter.setSizeFull();
        likelihoodFilter.setPlaceholder("Search");

        this.add(riskMatrix);

    }

    private Button createRemoveButton(Grid<Risk> grid, Risk risk) {
        @SuppressWarnings("unchecked")
        Button button = new Button("Remove", clickEvent -> {
            NitriteDBProvider.getInstance().removeRisk(risk.getId());
            ListDataProvider<Risk> listDataProvider = (ListDataProvider<Risk>) grid
                    .getDataProvider();
            listDataProvider.getItems().remove(risk);
            listDataProvider.refreshAll();
        });
        return button;
    }

}
