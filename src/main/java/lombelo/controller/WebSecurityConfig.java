package lombelo.controller;

import lombelo.model.Account;
import lombelo.model.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * @author Niklas Wünsche
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserAccountRepository accounts;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        Optional<Account> foundAccount = StreamSupport.stream(accounts.findAll().spliterator(), false).findAny();
        String userName = foundAccount.isPresent() ? foundAccount.get().getUserName() : "user";
        String password = foundAccount.isPresent() ? foundAccount.get().getPassword() : "password";

        auth
                .inMemoryAuthentication()
                .withUser(userName).password(password).roles("USER");
    }

}