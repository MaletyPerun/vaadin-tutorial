package com.example.application.security;

import com.example.application.data.model.Person;
import com.example.application.data.model.Role;
import com.example.application.data.repository.PersonRepository;
import com.example.application.views.list.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends VaadinWebSecurity {

    // настройка конфигурации на основе изменненого UserDetailsService

    private final PersonRepository personRepository;

    // отображение страницы логина и пароля при первом обращении к приложению

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    // игнорирования ресурсов всем пользователям

    @Override
    protected void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/images/**");
        super.configure(web);
    }

//    https://stackoverflow.com/questions/49847791/java-spring-security-user-withdefaultpasswordencoder-is-deprecated

    // столкнулся с deprecated способов авторизации пользователя
    // свой кастомный не получилось настроить
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Optional<Person> optionalUser = personRepository.findByEmailIgnoreCase(email);
            Person authPerson = optionalUser.orElseThrow(
                    () -> new UsernameNotFoundException("Person '" + email + "' was not found"));
            System.out.println(authPerson.getRoles().contains(Role.ADMIN));
            User authUser = (User) User.withDefaultPasswordEncoder()
                    .username(authPerson.getEmail())
                    .password(authPerson.getPassword())
                    .roles(authPerson.getRolesForSecurity())
                    .build();
            log.info("authUser.getPassword() = {}", authUser.getPassword());
            log.info("authPerson.getPassword() = {}", authPerson.getPassword());
            return authUser;
        };
    }
}


