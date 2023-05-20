package com.example.OrderService.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

      @Bean
        WebClient webClient(WebClient.Builder builder) {return builder.build();}

  /*  @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

   */

   /* @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }*/
}
