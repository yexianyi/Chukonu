package net.yxy.chukonu.spring.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    
//    @Autowired
//    private UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint()) // entrypoint for anonymous 
                .accessDeniedHandler(new CustomAccessDeineHandler()) // handler for logged in users but no enough permission
                .and()
                .headers().frameOptions().disable() // resolve 'X-Frame-Options' to 'deny' issue
                .and()
                .authorizeRequests()
                .antMatchers("/login.html").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .failureUrl("/login.html?error=Invalid Username or Password.")
                .defaultSuccessUrl("/index.html")
                .and()
                //the “never” option will ensure that Spring Security itself will not create any session; 
                //however, if the application creates one, then Spring Security will make use of it.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                                    .maximumSessions(1).maxSessionsPreventsLogin(true) // a second login will cause the first to be invalidated.
                                    .expiredUrl("/login.html?error=Session has been expired, please relogin.").and()
                                    .invalidSessionUrl("/login.html?error=Invalid session, please relogin.")
                                    // Session Fixation Protection with Spring Security
                                    // when “none” is set, the original session will not be invalidated
                                    // when “newSession” is set, a clean session will be created without any of the attributes from the old session being copied over
                                    .sessionFixation().newSession()
                .and().logout().deleteCookies("JSESSIONID");

    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication().dataSource(dataSource)
//          .usersByUsernameQuery() // use Default Query
//          .authoritiesByUsernameQuery() // use Default Query
          .passwordEncoder(new BCryptPasswordEncoder()); // The default is to use plain text.
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        // config UserDetail Implementation class to handle login verification
//        authProvider.setUserDetailsService(userService); 
//        authProvider.setPasswordEncoder(encoder());
//        return authProvider;
//    }
    
//    @Bean
//    public PasswordEncoder encoder() {
//        return new PasswordEncoder() {
//
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return DigestUtils.md5Hex(rawPassword.toString().getBytes());
//            }
//
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return encodedPassword.equals(DigestUtils.md5Hex(rawPassword.toString().getBytes()));
//            }
//        };
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**");
//        web.ignoring().antMatchers("/v2/api-docs", 
//                                   "/swagger-resources", 
//                                   "/swagger-resources/**", 
//                                   "/configuration/ui", 
//                                   "/configuration/security", 
//                                   "/swagger-ui.html", 
//                                   "/webjars/**");
    }

}
