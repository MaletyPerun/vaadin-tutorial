package com.example.application.views.list;

import com.example.application.data.dto.PersonDto;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Admin panel")
//@RequiredArgsConstructor
@PermitAll
public class AdminPersonView extends VerticalLayout {
    CrmService service;
    Grid<PersonDto> grid = new Grid<>(PersonDto.class);
    TextField filterText = new TextField();
    PersonDtoForm personDtoForm;

    public AdminPersonView(CrmService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, personDtoForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, personDtoForm);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        personDtoForm = new PersonDtoForm();
        personDtoForm.setWidth("25em");
        personDtoForm.addSaveListener(this::savePersonDto);
        personDtoForm.addDeleteListener(this::deletePerson);
        personDtoForm.addCloseListener(event -> closeEditor());

    }

    private void savePersonDto(PersonDtoForm.SaveEvent event) {
        // TODO: 28.07.2023 стоит ли все загонять без update?
        service.createPerson(event.getPersonDto());
        updateList();
        closeEditor();
    }

    private void deletePerson(PersonDtoForm.DeleteEvent event) {
        service.deletePerson(event.getPersonDto().getId());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("person-grid");
        grid.setSizeFull();
        grid.setColumns("id", "firstName", "lastName", "patronymic", "birthday", "email", "phone");
        grid.addColumn(PersonDto::isAdmin).setHeader("Admin");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editPersonDto(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(event -> addPersonDto());

        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editPersonDto(PersonDto user) {
        if (user == null) {
            closeEditor();
        } else {
            personDtoForm.setPersonDto(user);
            personDtoForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        personDtoForm.setPersonDto(null);
        personDtoForm.setVisible(false);
        removeClassName("editing");
    }

    private void addPersonDto() {
        grid.asSingleSelect().clear();
        editPersonDto(PersonDto.builder().build());
    }

    private void updateList() {
        grid.setItems(service.getAllPersons(filterText.getValue()));
    }

}
