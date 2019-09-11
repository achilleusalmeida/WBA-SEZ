/**
 * @author achilleus.almeida
 *
 * Created On : Sep 5, 2019
 */
package com.wba.kafka;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.wba.dbobjects.WbaRate;
import com.wba.logging.InjectableLogger;

/*@RestController
@RequestMapping("kafka")*/
@Service
public class KafkaProducer 
{
	@Autowired
	KafkaTemplate<String, WbaRate> aKafkaTemplate;
	
	@InjectableLogger
	private static Logger aLogger;

	private static final String TOPIC = "WbaRatesTopic";

	// @GetMapping("/publish/{name}")
	public void 
	post(/* @PathVariable("name") final */WbaRate pWbaRate) 
	{
		aKafkaTemplate.send(TOPIC, pWbaRate);	
		aLogger.info("Message posted on to topic : {}", TOPIC);
	}
}
