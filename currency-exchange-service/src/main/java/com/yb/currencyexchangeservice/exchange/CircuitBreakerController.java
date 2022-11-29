package com.yb.currencyexchangeservice.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/sample-api")
//	@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")	// Default olarak 3 kere tekrar eder.
//	@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
	@RateLimiter(name = "default")
	@Bulkhead(name = "default")
	public String sampleApi() {
		
		logger.info("Sample Api call recieved");
		
		 /* Ratelimiter için kaldırıldı
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);
		return forEntity.getBody(); */
		
		return "Sample API";
	}
	
	public String hardcodedResponse(Exception ex) {
		
		return "fallback method";
	}
}
