package net.tinyset.customer.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "net.tinyset.customer")
public class FeignConfiguration {

}
