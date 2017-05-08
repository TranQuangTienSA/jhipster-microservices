package net.tinyset.customer.config;

import net.tinyset.customer.aop.authorization.AuthorizationAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AuthorizationAspectConfiguration {
    @Bean
    public AuthorizationAspect authorizationAspect() {
        return new AuthorizationAspect();
    }
}
