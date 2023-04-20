package br.com.ezschedule.apischedule.authentication;

import br.com.ezschedule.apischedule.service.DetailAdministratorServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class JWTconfigurationAdministrator extends WebSecurityConfigurerAdapter {
    private final DetailAdministratorServiceImpl detailAdministratorService;
    private final PasswordEncoder passwordEncoder;

    public JWTconfigurationAdministrator(DetailAdministratorServiceImpl detailAdministratorService, PasswordEncoder passwordEncoder) {
        this.detailAdministratorService = detailAdministratorService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailAdministratorService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests().
                antMatchers(HttpMethod.POST, "/**").permitAll().
                antMatchers(HttpMethod.PUT, "/**").permitAll().
                antMatchers(HttpMethod.GET, "/**").permitAll().
                antMatchers(HttpMethod.DELETE,"/**").permitAll().
                anyRequest().authenticated().
                and().
                addFilter(new JWTauthenticateFilterAdministrator(authenticationManager())).
                addFilter(new JWTvalidationFilterAdministrator(authenticationManager())).
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
