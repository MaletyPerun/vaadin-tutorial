package com.example.application.views.list;

import com.example.application.data.dto.UserDto;
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

@Route(value = "admin", layout = MainLayout.class)
@PageTitle("Admin panel")
//@RequiredArgsConstructor
@PermitAll
public class AdminView extends VerticalLayout {
    CrmService service;
    Grid<UserDto> grid = new Grid<>(UserDto.class);
    TextField filterText = new TextField();
    UserDtoForm userForm;

    public AdminView(CrmService service) {
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
        HorizontalLayout content = new HorizontalLayout(grid, userForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, userForm);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        userForm = new UserDtoForm();
        userForm.setWidth("25em");
        userForm.addSaveListener(this::saveUser);
        userForm.addDeleteListener(this::deleteUser);
        userForm.addCloseListener(event -> closeEditor());

    }

    private void saveUser(UserDtoForm.SaveEvent event) {
        // TODO: 28.07.2023 стоит ли все загонять без update?
        service.create(event.getUser());
        updateList();
        closeEditor();
    }
//
    private void deleteUser(UserDtoForm.DeleteEvent event) {
        service.delete(event.getUser().getId());
        updateList();
        closeEditor();
    }
//
//
    private void configureGrid() {
//        firstName,
//                lastName,
//                patronymic,
//                birthday,
//                email,
//                phone,
        grid.addClassNames("user-grid");
        grid.setSizeFull();
        grid.setColumns("id", "firstName", "lastName", "patronymic", "birthday", "email", "phone", "roles");
//        grid.addColumn(userDto -> userDto.getStatus().getName()).setHeader("Status");
//        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editUserDto(event.getValue()));
    }
//
    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(event -> addUserDto());

        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
//
    public void editUserDto(UserDto user) {
        if (user == null) {
            closeEditor();
        } else {
            userForm.setUserDto(user);
            userForm.setVisible(true);
            addClassName("editing");
        }
    }
//
    private void closeEditor() {
        userForm.setUserDto(null);
        userForm.setVisible(false);
        removeClassName("editing");
    }
//
    private void addUserDto() {
        grid.asSingleSelect().clear();
        editUserDto(UserDto.builder().build());
    }
//
    private void updateList() {
        grid.setItems(service.getAllUsers(filterText.getValue()));
//        grid.setItems(service.getAllUsers(filterText.getValue()));
//        grid.setItems(service.findAllContacts(filterText.getValue()));
    }

}
