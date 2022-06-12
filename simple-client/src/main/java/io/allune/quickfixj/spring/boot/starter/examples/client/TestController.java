package io.allune.quickfixj.spring.boot.starter.examples.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/fix")
@RestController
public class TestController {

	@GetMapping("/test")
	public String test() throws Exception {
		log.info("test");
		return "success";
	}
}
