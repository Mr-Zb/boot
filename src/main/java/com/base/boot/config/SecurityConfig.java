package com.base.boot.config;

import com.base.boot.common.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author zbang
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 需要放行的URL
     */
    private static final String[] AUTH_WHITELIST = {
            // -/sys/use/register
            "/sys/menu/aa",
            "/sys/user/register",
            "/duid/*",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
            // other public endpoints of your API may be appended to this array
    };

    /**
     * 登陆失败
     */
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    /**
     * 登陆成功
     */
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    /**
     * 登出
     */
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;
    /**
     * 匿名用户访问无权限资源时权限校验
     */
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    /**
     * 认证过的用户访问无权限资源时权限校验
     */
    @Autowired
    private MyAccessDeineHandler myAccessDeniedHandler;

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtAuthorizationTokenFilter authenticationTokenFilter;

    /**
     * 添加验证码校验过滤器
     */
//    @Autowired
//    private ValidateCodeFilter validateCodeFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                /**
                 *方便后面写前后端分离的时候前端过来的第一次验证请求
                 */
                .antMatchers(HttpMethod.OPTIONS, "/**").anonymous()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated();
        http.formLogin()
                .loginProcessingUrl("/login")
                .permitAll()//允许所有用户
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler);
        http.logout()
                .permitAll()
                .logoutSuccessUrl("/logout").logoutSuccessHandler(myLogoutSuccessHandler);
        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint)
                .accessDeniedHandler(myAccessDeniedHandler);
        http.csrf().disable();
        //我们用token所以把Session禁止掉
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
