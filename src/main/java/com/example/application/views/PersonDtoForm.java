package com.example.application.views;

import com.example.application.data.dto.PersonDto;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class PersonDtoForm extends FormLayout {

    // настройка отображения CRUD-формы

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField patronymic = new TextField("Patronymic");
    DatePicker birthday = new DatePicker("Birthday");
    EmailField email = new EmailField("Email");
    TextField phone = new TextField("Phone");
    Checkbox isAdmin = new Checkbox("Admin");

    // создание кнопок управления CRUD-формой
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");

    Binder<PersonDto> binder = new BeanValidationBinder<>(PersonDto.class);

    // конструктор с добавлением полей в зависимости от прлей объекта на основе bing-га (автоматической привязки)

    public PersonDtoForm() {
        addClassName("user-form");
        isAdmin.addValueChangeListener(AbstractField.ComponentValueChangeEvent::getValue);
        binder.bindInstanceFields(this);
        binder.forField(isAdmin)
                .bind(PersonDto::isAdmin, PersonDto::setAdmin);

        add(
                firstName,
                lastName,
                patronymic,
                birthday,
                email,
                phone,
                isAdmin,
                createButtonsLayout()
        );
    }

    // привязка действий на CRUD-кнопки

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);


        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new PersonDtoForm.DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new PersonDtoForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    // валидация по установленным аннотациям jakarta

    public void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new PersonDtoForm.SaveEvent(this, binder.getBean()));
        }
    }

    public void setPersonDto(PersonDto personDto) {
        binder.setBean(personDto);
    }


    // создание внутреннего класса с настройкой событий по каждой CRUD кнопке

    public abstract static class PersonDtoFormEvent extends ComponentEvent<PersonDtoForm> {
        private final transient PersonDto personDto;

        protected PersonDtoFormEvent(PersonDtoForm source, PersonDto personDto) {
            super(source, false);
            this.personDto = personDto;
        }

        public PersonDto getPersonDto() {
            return personDto;
        }
    }

    public static class SaveEvent extends PersonDtoFormEvent {
        SaveEvent(PersonDtoForm source, PersonDto personDto) {
            super(source, personDto);
        }
    }

    public static class DeleteEvent extends PersonDtoFormEvent {
        DeleteEvent(PersonDtoForm source, PersonDto personDto) {
            super(source, personDto);
        }

    }

    public static class CloseEvent extends PersonDtoFormEvent {
        CloseEvent(PersonDtoForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<PersonDtoForm.DeleteEvent> listener) {
        return addListener(PersonDtoForm.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<PersonDtoForm.SaveEvent> listener) {
        return addListener(PersonDtoForm.SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<PersonDtoForm.CloseEvent> listener) {
        return addListener(PersonDtoForm.CloseEvent.class, listener);
    }
}
