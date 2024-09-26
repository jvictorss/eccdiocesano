package br.com.verbum.eccdiocesano.config;

import br.com.verbum.eccdiocesano.domain.services.UserDetailService;
import br.com.verbum.eccdiocesano.security.AuthEntryPointJwt;
import br.com.verbum.eccdiocesano.security.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    final UserDetailService userDetailService;
    final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Autowired
    public WebSecurityConfig(UserDetailService userDetailService, AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailService = userDetailService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterAppChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Configurando o CSRF com a nova API lambda
                .cors(AbstractHttpConfigurer::disable) // Desabilitando o CORS com a nova API
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/**", "/v1/login","/v1/usuarios/create/**","/verbum-ecc/**", "/proxy/**", "/v3/api-docs/**",
                                "/images/**", "/templates/**", "/swagger-ui.html/**",
                                "/css/**", "/swagger-ui/**",
                                "/swagger-resources/**", "/swagger-resources").permitAll() // Permitindo todas essas rotas
                        .anyRequest().authenticated()) // Configuração das autorizações de requisição
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Gerenciamento de sessões com a nova API
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(unauthorizedHandler)); // Tratamento de exceções atualizado

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/assets/**", "/h2/**", "/swagger-ui.html/**",
                        "/todoapp/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/swagger-resources",
                        "/v3/api-docs/**", "/proxy/**");
    }

}
