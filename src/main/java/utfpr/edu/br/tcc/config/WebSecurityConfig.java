package utfpr.edu.br.tcc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import utfpr.edu.br.tcc.repository.UsuarioRepository;
import utfpr.edu.br.tcc.service.UsuarioService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public WebSecurityConfig(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @Override//autorizaçao de acesso
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().exceptionHandling().accessDeniedPage("/403")
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/")
                .failureUrl("/login?error=bad_credentials").permitAll()
                .and().authorizeRequests()
                .antMatchers("/usuarios/**").permitAll()
                .antMatchers("/index/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/estados/**").hasRole("ADMIN")
                .antMatchers("/cidades/**").hasRole("ADMIN")
                .antMatchers("/emails/**").hasRole("ADMIN")
                .antMatchers("/marcas/**").hasRole("ADMIN")
                .antMatchers("/veiculos/**").hasRole("ADMIN")
                .antMatchers("/relatorios/**").hasRole("ADMIN")
                .antMatchers("/tipo_Seguros/**").hasRole("ADMIN")
                .antMatchers("/seguros/**").hasRole("ADMIN")
                .antMatchers("/relatorio/**").hasRole("ADMIN")
                .antMatchers("/**").hasAnyRole("USER", "ADMIN");

        http.headers().frameOptions().sameOrigin();
    }

    @Override//ignora autenticaçao
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/fragments/**")
                .antMatchers("/images/**")
                .antMatchers("/webjars/**")
                .antMatchers("/assets/**")
                .antMatchers("/css/**")
                .antMatchers("/static/**")
                .antMatchers("../resources/static/js/**")
                .antMatchers("/layout/**");
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UsuarioService(usuarioRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Override//fazer autenticaçao do usuario
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.usuarioService);
        return daoAuthenticationProvider;
    }
}
