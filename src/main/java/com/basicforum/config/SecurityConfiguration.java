package com.basicforum.config;
import com.basicforum.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf->csrf
                        .ignoringRequestMatchers ("/api/v1/auth/**")
                )
                .authorizeHttpRequests(authorize->
                        authorize
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/v1/post/createPost").hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST,"/api/v1/user/**").hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .securityContext((securityContext) -> securityContext
                        .requireExplicitSave(true)
                ).securityContext(security->
                        security.requireExplicitSave(true)
                ).sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}