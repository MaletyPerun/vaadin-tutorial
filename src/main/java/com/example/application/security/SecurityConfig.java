package com.example.application.security;

import com.example.application.data.entity.Person;
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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends VaadinWebSecurity {

    private final PersonRepository personRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Override
    protected void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/images/**");
        super.configure(web);
    }

//    https://stackoverflow.com/questions/49847791/java-spring-security-user-withdefaultpasswordencoder-is-deprecated

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Optional<Person> optionalUser = personRepository.findByEmailIgnoreCase(email);
            Person authPerson = optionalUser.orElseThrow(
                    () -> new UsernameNotFoundException("User '" + email + "' was not found"));
            return User.withDefaultPasswordEncoder()
                    .username(authPerson.getEmail())
                    .password(authPerson.getPassword())
                    .roles(authPerson.getRoles().toString())
                    .build();
//            log.debug("Authenticating '{}'", email);
//            return new InMemoryUserDetailsManager(user);
//            return (User) User.withUsername(authPerson.getEmail())
//                    .password(authPerson.getPassword())
//                    .roles(authPerson.getRoles().toString())
//                    .build();


//            System.out.println(authUser);
//            return authUser;
        };
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
////            log.debug("Authenticating '{}'", email);
//        return new InMemoryUserDetailsManager(User.withUsername("user")
//                .password("{noop}userpass")
//                .password("userpass").passwordEncoder()
//                .roles("USER")
//                .build());
//    }


//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
}
