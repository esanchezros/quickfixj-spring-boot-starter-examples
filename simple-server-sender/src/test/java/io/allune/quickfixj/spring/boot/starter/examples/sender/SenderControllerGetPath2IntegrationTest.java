package io.allune.quickfixj.spring.boot.starter.examples.sender;

import static io.restassured.RestAssured.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppServer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SenderControllerGetPath2IntegrationTest {

	@Test
	public void canGetPath2() {
		// @formatter:off
		given()
			.contentType("application/json")
		.when()
			.get("/path2")
		.then()
			.assertThat()
		.statusCode(200);
		// @formatter:on
	}
}