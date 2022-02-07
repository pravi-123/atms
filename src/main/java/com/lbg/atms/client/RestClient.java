package com.lbg.atms.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient<T> {
    private final RestTemplate restTemplate;

    public RestClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public T getObject(String uri, Class<T> responseObject) {
        return restTemplate.getForObject("https://api.lloydsbank.com/open-banking/v2.2/atms",responseObject);
    }

}
