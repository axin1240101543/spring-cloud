package com.micro.darren.gateway;

import com.micro.darren.gateway.zuulfilter.DistributedPreRateLimitZuulFilter;
import com.micro.darren.gateway.zuulfilter.PreRateLimitZuulFilter;
import com.micro.darren.gateway.zuulfilter.PreRequestLogZuulFitler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@EnableFeignClients
//@EnableAsync
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 限流过滤器
     * @return
     */
//    @Bean
//    public PreRateLimitZuulFilter rateLimitZuulFilter(){
//        return new PreRateLimitZuulFilter();
//    }

    /**
     * 分布式限流过滤器
     * @return
     */
    @Bean
      public DistributedPreRateLimitZuulFilter distributedPreRateLimitZuulFilter(){
          return new DistributedPreRateLimitZuulFilter();
      }

    /**
     * 日志过滤器
     * @return
     */
    @Bean
    public PreRequestLogZuulFitler preRequestLogZuulFitler(){
        return new PreRequestLogZuulFitler();
    }

}

