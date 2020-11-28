package com.onlineshop.config;

import com.onlineshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
      extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
              .csrf()
              .disable()
              .authorizeRequests()
              //Доступ для всех пользователей
              .antMatchers("/registration").not().fullyAuthenticated()
              .antMatchers("/products").permitAll()
               .antMatchers("/ebooks").permitAll()
               .antMatchers("/headphones").permitAll()
               .antMatchers("/phones").permitAll()
               .antMatchers("/tablets").permitAll()
               .antMatchers("/watches").permitAll()
                .antMatchers("/products/search/*").permitAll()
                .antMatchers("/product/**").permitAll()

              //Доступ только для пользователей с ролью Администратор
              .antMatchers("/admin/**").hasRole("ADMIN")

              .antMatchers("/addproduct").hasRole( "ADMIN")
              //Доступ разрешен всем пользователей
              .antMatchers("/",
                           "/resources/**"
                          ).permitAll()
              //Все остальные страницы требуют аутентификации
              .anyRequest().authenticated()
              .and()
              //Настройка для входа в систему
              .formLogin()
              .loginPage("/login")
              //Перенарпавление на главную страницу после успешного входа
              .defaultSuccessUrl("/")
              .permitAll()
              .and()
              .logout()
              .permitAll()
              .logoutSuccessUrl("/");
    }
    
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
              .ignoring()
              .antMatchers("/resources/**",
                           "/static/**",
                           "/css/**",
                           "/js/**",
                           "/images/**",
                           "/webjars/**"
                          );
    }
}
