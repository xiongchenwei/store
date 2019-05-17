
package com.xiaoxiong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.xiaoxiong.feign")
@EnableDiscoveryClient
@EnableFeignClients
public class AppMobile {

	public static void main(String[] args) {
		SpringApplication.run(AppMobile.class, args);
	}

}




