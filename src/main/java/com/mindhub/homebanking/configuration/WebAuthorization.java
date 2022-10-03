package com.mindhub.homebanking.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity

@Configuration

class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override

    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                //permisos para el administrador
                .antMatchers(HttpMethod.POST, "/api/clients", "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards", "/api/transactions").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers("/web/**").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers("/api/clients/current/**", "/api/clients/current/accounts", "/api/loans", "/api/clients/current/cards/{id}", "/api/accounts/{id}").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers("/api/**", "/rest/**", "/h2-console", "/manager/**").hasAuthority("ADMIN")
                .antMatchers("/scripts/**", "/assets/**", "/img/**").permitAll()
                .antMatchers("/index.html", "/login.html").permitAll()
                .antMatchers("/**").hasAuthority("ADMIN");

        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");


        http.logout().logoutUrl("/api/logout");

        // desactivar la comprobación de tokens CSRF
        http.csrf().disable();

        //deshabilitar frameOptions para que se pueda acceder a h2-console
        http.headers().frameOptions().disable();

        // si el usuario no está autenticado, simplemente envíe una respuesta de falla de autenticación
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        //si el inicio de sesión es exitoso, simplemente borre las banderas que solicitan autenticación
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // si falla el inicio de sesión, simplemente envíe una respuesta de falla de autenticación
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // si el cierre de sesión es exitoso, simplemente envíe una respuesta exitosa
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }
}