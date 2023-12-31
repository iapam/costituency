package com.apam.constituencies.Security.Config;



import com.apam.constituencies.Security.PasswordEncode;
import com.apam.constituencies.Services.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DirectorService directorService;
    private final PasswordEncode passwordEncode;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/register")
                .permitAll()
                .requestMatchers("/registerPage","/dashboard","/edit_votes/{id}",
                        "/registerparlia",
                        "/registerPollingAgent","/registerpolllingPage","/registerpollingstation").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web)->web.ignoring().requestMatchers("/images/**").requestMatchers("/css/**").
                requestMatchers("/js/**");
    }

    public AuthenticationManagerBuilder authenticationManagerBuilder(AuthenticationManagerBuilder auth){
        return auth.authenticationProvider(daoAuthenticationProvider());
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(directorService);
        provider.setPasswordEncoder(passwordEncode.PasswordEncoder());
        return provider;
    }
}
