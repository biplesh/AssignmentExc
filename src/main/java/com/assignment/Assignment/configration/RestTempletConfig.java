package com.assignment.Assignment.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTempletConfig {
	
	//@Bean
    public RestTemplate template() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(4000);
        httpRequestFactory.setConnectTimeout(4000);
        httpRequestFactory.setReadTimeout(4000);
        return new RestTemplate(httpRequestFactory);
    }

	public String getForObject(String uRL, Class<String> class1) {
		// TODO Auto-generated method stub
		return null;
	} 

	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
