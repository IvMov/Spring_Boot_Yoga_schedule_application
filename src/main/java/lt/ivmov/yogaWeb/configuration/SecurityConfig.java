package lt.ivmov.yogaWeb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login/**", "/public/**", "/**/logout").permitAll()
                .antMatchers("/private/**").authenticated()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/public/schedule", true)
                .failureUrl("/login?error=true")
                .and()
            .logout()
                .logoutSuccessUrl("/public")
                .permitAll();

        http.headers()
                .frameOptions()
                .sameOrigin();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT email, password, TRUE as enabled FROM students WHERE email = ?")
//                .authoritiesByUsernameQuery("SELECT authority, email FROM students WHERE username = ?");
//
        //            .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
//            .authoritiesByUsernameQuery("");
//            .passwordEncoder(passwordEncoder());
//
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("pass"))
//                .roles("USER")
//            .and()
//                .withUser("admin")
//                .password(passwordEncoder().encode("123"))
//                .roles("USER", "ADMIN");
//
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

}
