package cn.louyu.config;

import cn.louyu.service.security.MyAuthenctiationFailureHandler;
import cn.louyu.service.security.MyAuthenticationSuccessHandler;
import cn.louyu.service.security.MyPasswordEncode;
import cn.louyu.service.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启security注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许所有用户访问"/"
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                //其他地址的访问均需验证权限
                .anyRequest().authenticated()
                .and()
                .formLogin()// 表单登录
                //指定登录页是"/login"
                .loginPage("/login")
                .failureUrl("/login?error=true")
                //登录成功后默认跳转到"/Home"
                .defaultSuccessUrl("/Home/Index")
                .permitAll()
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenctiationFailureHandler)
                .and()
                .logout() //开启csrf后必须要post请求才能退出登录
                .logoutSuccessUrl("/login")//退出登录后的默认url是"/home"
                .permitAll();

        //解决非thymeleaf的form表单提交被拦截问题
        http.csrf().disable();
        //解决中文乱码问题
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(gteMyPasswordEncode());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/layuiadmin/**","/js/**","/css/**","/img/**");
    }

    @Bean
    public PasswordEncoder gteMyPasswordEncode(){
        return new MyPasswordEncode();
    }
}
