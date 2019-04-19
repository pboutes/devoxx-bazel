package com.gateway.demo;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Info;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("dev")
public class DemoApplicationTests {

	@Value("${fetcher.url}")
	private String url;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MockMvc mvc;

	private MockRestServiceServer mockServer;

	private ObjectMapper mapper = new ObjectMapper();

	@Before
	public void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	public void shouldGreetWithName() throws Exception {
		mockServer
				.expect(ExpectedCount.once(), requestTo(new URI(url + "/hello?name=Devoxx")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK).body("Hello Devoxx!"));


		mvc.perform(MockMvcRequestBuilders.get("/greet?name=Devoxx").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo(mapper.writeValueAsString(Info.of("Hello Devoxx!", url)))));
	}

	@Test
	public void shouldReturn400() throws Exception {
		mockServer
				.expect(ExpectedCount.once(), requestTo(new URI(url + "/hello?name=Devoxx")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK).body("Hello Devoxx!"));


		mvc.perform(MockMvcRequestBuilders.get("/greet").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

}
