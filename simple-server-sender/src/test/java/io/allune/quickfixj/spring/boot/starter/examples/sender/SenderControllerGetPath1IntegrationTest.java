package io.allune.quickfixj.spring.boot.starter.examples.sender;

import static io.restassured.RestAssured.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppServer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SenderControllerGetPath1IntegrationTest {

	@Test
	public void canGetPath1() {
		// @formatter:off
		given()
			.contentType("application/json")
		.when()
			.get("/path1")
		.then()
			.assertThat()
		.statusCode(200);
		// @formatter:on
	}
}