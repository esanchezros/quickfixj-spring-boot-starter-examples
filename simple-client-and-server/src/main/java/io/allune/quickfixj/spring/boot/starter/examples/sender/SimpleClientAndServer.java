/*
 * Copyright 2017-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.allune.quickfixj.spring.boot.starter.examples.sender;

import io.allune.quickfixj.spring.boot.starter.examples.sender.config.ApplicationContextConfiguration;
import io.allune.quickfixj.spring.boot.starter.examples.sender.controller.SenderController;
import io.allune.quickfixj.spring.boot.starter.template.QuickFixJTemplate;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class SimpleClientAndServer {

	public static void main(String[] args) {
		SpringApplicationBuilder parentBuilder
				= new SpringApplicationBuilder(ApplicationContextConfiguration.class)
					.web(WebApplicationType.NONE);
		parentBuilder.run(args);

		parentBuilder.child(QuickFixJClientContextConfiguration.class)
				.properties("spring.config.name=client")
				.web(WebApplicationType.SERVLET).run(args);
		parentBuilder.child(QuickFixJServerContextConfiguration.class)
				.properties("spring.config.name=server")
				.web(WebApplicationType.SERVLET).run(args);
	}

	@EnableAutoConfiguration
	@Configuration
	public static class QuickFixJClientContextConfiguration {
		// override beans if required
		@Bean
		public SenderController senderController(QuickFixJTemplate quickFixJTemplate) {
			return new SenderController(quickFixJTemplate);
		}
	}

	@EnableAutoConfiguration
	@Configuration
	public static class QuickFixJServerContextConfiguration {
		// override beans if required
		@Bean
		public SenderController senderController(QuickFixJTemplate quickFixJTemplate) {
			return new SenderController(quickFixJTemplate);
		}
	}
}
