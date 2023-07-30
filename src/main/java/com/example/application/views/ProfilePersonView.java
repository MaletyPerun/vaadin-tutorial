package com.example.application.views;

import com.example.application.data.dto.PersonDto;
import com.example.application.data.service.PersonService;
import com.example.application.data.security.SecurityService;
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
import lombok.extern.slf4j.Slf4j;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Person panel")
@PermitAll
@Slf4j
public class ProfilePersonView extends VerticalLayout {

    // настройка отображения таблицы и CRUD-формы
    transient SecurityService securityService;
    transient PersonService service;
    Grid<PersonDto> grid = new Grid<>(PersonDto.class);
    TextField filterText = new TextField();
    PersonDtoForm personDtoForm;

    public ProfilePersonView(PersonService service, SecurityService securityService) {
        this.securityService = securityService;
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    // добавление в отображение таблицы с CRUD-формы

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
        personDtoForm.addDeleteListener(this::deletePersonDto);
        personDtoForm.addCloseListener(event -> closeEditor());
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

    // CRUD-операции в зависимости от нажатой кнопки

    private void savePersonDto(PersonDtoForm.SaveEvent event) {
        service.savePerson(event.getPersonDto(), securityService.getAuthenticatedUser());
        updateList();
        closeEditor();
    }

    private void deletePersonDto(PersonDtoForm.DeleteEvent event) {
        service.deletePerson(event.getPersonDto().getId());
        updateList();
        closeEditor();
    }

    public void editPersonDto(PersonDto personDto) {
        if (personDto == null) {
            closeEditor();
        } else {
            personDtoForm.setPersonDto(personDto);
            personDtoForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void addPersonDto() {
        grid.asSingleSelect().clear();
        editPersonDto(PersonDto.builder().build());
    }

    private void closeEditor() {
        personDtoForm.setPersonDto(null);
        personDtoForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.getAllPersons(filterText.getValue(), securityService.getAuthenticatedUser()));
    }

}
