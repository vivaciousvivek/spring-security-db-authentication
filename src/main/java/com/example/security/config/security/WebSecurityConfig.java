package com.example.security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration about which resource to be protected and which not can be configured
 *
 * @author VIVEK KUMAR SINGH
 * @since (2018-04-30 12:44:32)
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) /*for method level security*/
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private UserDetailsService userDetailsService;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    /* Allow every request should be fully authenticated */
    http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();

    /**
     * If u want specific request to be authorized, use antMatchers and then authenticated(){will
     * ask spring security for from where these url should authenticated, then spring will send this
     * to userdetailservice}.
     *
     * <p>If user is not authenticated it will move to the login page using formLogin(){spring
     * security default login page, if you need to provide custom login page then use
     * loginPage("/name of login")}
     *
     * any request which is not for "/secure" then allow it using anyRequest().permitAll()
     */
    http.authorizeRequests()
        .antMatchers("**/secured/**")
        .authenticated()
        .anyRequest()
        .permitAll()
        .and()
        .formLogin()
        //            .loginPage("/login")
        .permitAll();

    http.csrf().disable();
  }

  /**
   * Database Authentication using Custom UserDetailsService service
   *
   * @param auth
   * @throws Exception
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }
}
