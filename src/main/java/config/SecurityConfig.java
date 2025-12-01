package config;

import com.security.TryingToMakeCorrectRealizationOfJWT.jwt.CustomUserDetailService;
import com.security.TryingToMakeCorrectRealizationOfJWT.jwt.JWTFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTFilter filter;
    private final CustomUserDetailService uds;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Полностью отключаем CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Разрешаем ВСЕ запросы
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // Разрешаем iframe
                );
//        http
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/h2-console/**") // Отключаем CSRF для H2 Console
//                        .disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/**").permitAll()
//                        .requestMatchers("/h2-console/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .exceptionHandling(exception -> exception
//                        .authenticationEntryPoint(
//                                (request, response, authException) ->
//                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
//                        )
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                );
//
//        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(uds);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}