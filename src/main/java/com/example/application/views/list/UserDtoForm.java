package com.example.application.views.list;

import com.example.application.data.dto.UserDto;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class UserDtoForm extends FormLayout {

    TextField id = new TextField("id");
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField patronymic = new TextField("Patronymic");
    TextField birthday = new TextField("Birthday");
    EmailField email = new EmailField("Email");
    TextField phone = new TextField("Phone");
    TextField roles = new TextField("Roles");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");

    Binder<UserDto> binder = new BeanValidationBinder<>(UserDto.class);

    public UserDtoForm() {
        addClassName("user-form");
        binder.bindInstanceFields(this);
//        company.setItems(companies);
//        company.setItemLabelGenerator(Company::getName);
//        status.setItems(statuses);
//        status.setItemLabelGenerator(Status::getName);

        add(
                id,
                firstName,
                lastName,
                patronymic,
                birthday,
                email,
                phone,
                roles,
                createButtonsLayout()
        );
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);


        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new UserDtoForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new UserDtoForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    public void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new UserDtoForm.SaveEvent(this, binder.getBean()));
        }
    }

    public void setUserDto(UserDto user) {
        binder.setBean(user);
    }


    public static abstract class UserDtoFormEvent extends ComponentEvent<UserDtoForm> {
        private UserDto user;

        protected UserDtoFormEvent(UserDtoForm source, UserDto user) {
            super(source, false);
            this.user = user;
        }

        public UserDto getUser() {
            return user;
        }
    }

    public static class SaveEvent extends UserDtoFormEvent {
        SaveEvent(UserDtoForm source, UserDto user) {
            super(source, user);
        }
    }

    public static class DeleteEvent extends UserDtoFormEvent {
        DeleteEvent(UserDtoForm source, UserDto user) {
            super(source, user);
        }

    }

    public static class CloseEvent extends UserDtoFormEvent {
        CloseEvent(UserDtoForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<UserDtoForm.DeleteEvent> listener) {
        return addListener(UserDtoForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<UserDtoForm.SaveEvent> listener) {
        return addListener(UserDtoForm.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<UserDtoForm.CloseEvent> listener) {
        return addListener(UserDtoForm.CloseEvent.class, listener);
    }
//
//    TextField firstName = new TextField("First name");
//    TextField lastName = new TextField("Last name");
//    TextField firstName = new TextField("First name");
//    TextField lastName = new TextField("Last name");


//    private String secondName;
//    private String firstName;
//    private String patronymic;
//    private LocalDate birthday;
//    private String email;
//    private String phone;
}
