package com.yb.currencyexchangeservice.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CurrencyExchangeController {
	
//	Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class); // this.class(). @Slf4j ile gerek kalmadı 

	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange getExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		log.info("getExchangeValue called with from {} to {}", from, to);

//		CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
		
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("Unable to find data for " +from +" to " +to);
		}

		String port = environment.getProperty("local.server.port");				
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}
}
