package com.gateway.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Model1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class DemoController {

    private static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Value("${fetcher.url}")
    private String fetcherUrl;

    private ObjectMapper mapper;

    private RestTemplate restTemplate;

    public DemoController(ObjectMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(name = "name") String name) {
        logger.info("Performing greeting request on {}", fetcherUrl);
        ResponseEntity<String> resp = restTemplate.getForEntity(fetcherUrl + "/hello?name=" + name, String.class);
        return resp.getStatusCode() == HttpStatus.OK ? Util.tryOr(mapper, new Model1(resp.getBody(), fetcherUrl), () -> "{s}") : "{}";
    }

} 