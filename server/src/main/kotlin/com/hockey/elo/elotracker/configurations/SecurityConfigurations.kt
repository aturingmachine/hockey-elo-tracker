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
import javax.sql.DataSource

@EnableWebSecurity
@Configuration
class SecurityConfigurations(
        private val restAuthenticationEntryPoint: RestAuthenticatedEntryPoint,
        private val mySuccessHandler: RequestAwareAuthenticationSuccessHandler,
        private val myFailureHandler: RequestAwareAuthenticationFailureHandler,
        private val dataSource: DataSource)
    : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("select username, authority from authorities where username = ?")
    }

    override fun configure(http: HttpSecurity?) {
        if (http == null) {
            return
        }

        http
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/", "/api/v1/users/**").authenticated()
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .and()
            .cors()
            .and()
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint)
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
        configuration.allowedOrigins = mutableListOf("http://localhost:9080", "*")
        configuration.allowedMethods = mutableListOf("*")
        configuration.allowCredentials = true
        configuration.allowedHeaders = mutableListOf("Authorization", "Cache-Control", "Content-Type")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}
