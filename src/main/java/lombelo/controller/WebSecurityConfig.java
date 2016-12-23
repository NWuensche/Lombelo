package lombelo.controller;

import lombelo.model.Account;
import lombelo.model.AccountRepository;
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

    @Autowired AccountRepository accounts;

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
        Account account;
        if(!foundAccount.isPresent()) {
            account = new Account("user", "password");
            accounts.save(account);
        }
        else {
            account = foundAccount.get();
        }

        auth
                .inMemoryAuthentication()
                .withUser(account.getUserName()).password(account.getPassword()).roles("USER");
    }

}