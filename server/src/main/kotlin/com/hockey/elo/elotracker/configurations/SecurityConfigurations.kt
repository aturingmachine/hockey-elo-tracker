package com.hockey.elo.elotracker.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.context.SecurityContextPersistenceFilter
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
@Configuration
class SecurityConfigurations(
        private val restAuthenticationEntryPoint: RestAuthenticatedEntryPoint,
        private val mySuccessHandler: RequestAwareAuthenticationSuccessHandler,
        private val myFailureHandler: RequestAwareAuthenticationFailureHandler)
    : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
                .withUser("admin").password(encoder().encode("adminPass")).roles("ADMIN")
                .and()
                .withUser("user").password(encoder().encode("userPass")).roles("USER")
    }

    override fun configure(http: HttpSecurity?) {
        if (http == null) {
            return
        }

        http
            .cors()
            .and()
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint)
            .and()
            .authorizeRequests()
            .antMatchers("POST", "/login").permitAll()
            .antMatchers("/", "/api/v1/users/**").authenticated()
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .and()
            .addFilterBefore(SessionFilter(), SecurityContextPersistenceFilter::class.java)
            .formLogin()
            .successHandler(mySuccessHandler)
            .failureHandler(myFailureHandler)
            .and()
            .logout()
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = mutableListOf("http://localhost:9080")
        configuration.allowedMethods = mutableListOf("*")
        configuration.allowCredentials = true
        configuration.allowedHeaders = mutableListOf("Authorization", "Cache-Control", "Content-Type")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}
