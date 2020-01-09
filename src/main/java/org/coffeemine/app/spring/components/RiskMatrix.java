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
import org.coffeemine.app.spring.view.View;

import java.util.*;

public class RiskMatrix extends View {

    private List<Risk> riskList;
    private ListDataProvider<Risk> dataProvider;

    public RiskMatrix(){

        riskList = new ArrayList<>();
        dataProvider = new ListDataProvider<>(riskList);

        riskList.add(new Risk ("No or poor business case", "Commercial", "Select", "Select"));
        riskList.add(new Risk ("More than one customer", "Commercial", "Select", "Select"));
        riskList.add(new Risk ("Inappropriate contract type", "Commercial", "Select", "Select"));
        riskList.add(new Risk ("Penalties for non-performance", "Commercial", "Select", "Select"));
        riskList.add(new Risk ("Ill-defined scope", "Commercial", "Select", "Select"));
        riskList.add(new Risk ("Unclear payment schedule", "Commercial", "Select", "Select"));
        riskList.add(new Risk ("Payments not linked to deliverables", "Commercial", "Select", "Select"));
        riskList.add(new Risk ("Unclear customer structure", "Relationship", "Select", "Select"));
        riskList.add(new Risk ("Poor access to stakeholders", "Relationship", "Select", "Select"));
        riskList.add(new Risk ("Internal customer politics", "Relationship", "Select", "Select"));
        riskList.add(new Risk ("Multiple stakeholders", "Relationship", "Select", "Select"));
        riskList.add(new Risk ("Users not committed to project", "Relationship", "Select", "Select"));
        riskList.add(new Risk ("Unwillingness to change", "Relationship", "Select", "Select"));
        riskList.add(new Risk ("Management and users disagree", "Relationship", "Select", "Select"));
        riskList.add(new Risk ("Requirements not agreed", "Requirements", "Select", "Select"));
        riskList.add(new Risk ("Requirements incomplete", "Requirements", "Select", "Select"));
        riskList.add(new Risk ("Requirements not detailed enough", "Requirements", "Select", "Select"));
        riskList.add(new Risk ("Ambiguity in requirements", "Requirements", "Select", "Select"));
        riskList.add(new Risk ("No single document of requirements", "Requirements", "Select", "Select"));
        riskList.add(new Risk ("Stringent non-functional requirements", "Requirements", "Select", "Select"));
        riskList.add(new Risk ("Acceptance criteria not agreed", "Requirements", "Select", "Select"));
        riskList.add(new Risk ("Project manager not involved in initial planning", "Planning and Resource", "Select", "Select"));
        riskList.add(new Risk ("Project very large with quick build-up", "Planning and Resource", "Select", "Select"));
        riskList.add(new Risk ("Estimates not based on metrics", "Planning and Resource", "Select", "Select"));
        riskList.add(new Risk ("Excessive reliance on key staff", "Planning and Resource", "Select", "Select"));
        riskList.add(new Risk ("Developers lack key skills", "Planning and Resource", "Select", "Select"));
        riskList.add(new Risk ("Inexperience in business area", "Planning and Resource", "Select", "Select"));
        riskList.add(new Risk ("Inexperience of technology", "Planning and Resource", "Select", "Select"));
        riskList.add(new Risk ("Environment new to developers", "Technical", "Select", "Select"));
        riskList.add(new Risk ("Development and live environment differs", "Technical", "Select", "Select"));
        riskList.add(new Risk ("Restricted access to environment", "Technical", "Select", "Select"));
        riskList.add(new Risk ("Unfamiliar system software", "Technical", "Select", "Select"));
        riskList.add(new Risk ("Lack of technical support", "Technical", "Select", "Select"));
        riskList.add(new Risk ("Unfamiliar tools/methods/standards", "Technical", "Select", "Select"));
        riskList.add(new Risk ("New/unproven technology used", "Technical", "Select", "Select"));
        riskList.add(new Risk ("No/little experience of suppliers", "Subcontract", "Select", "Select"));
        riskList.add(new Risk ("Suppliers in poor financial state", "Subcontract", "Select", "Select"));
        riskList.add(new Risk ("Difficult to stage tests of items", "Subcontract", "Select", "Select"));
        riskList.add(new Risk ("No choice of supplier", "Subcontract", "Select", "Select"));
        riskList.add(new Risk ("Use of proprietary products", "Subcontract", "Select", "Select"));
        riskList.add(new Risk ("Subcontracts not 'back-to-back' with main contract", "Subcontract", "Select", "Select"));

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
            riskList.add(new Risk("New Risk", "Commercial", "Select", "Select"));
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
            ListDataProvider<Risk> listDataProvider = (ListDataProvider<Risk>) grid
                    .getDataProvider();
            listDataProvider.getItems().remove(risk);
            listDataProvider.refreshAll();
        });
        return button;
    }

}
