/**
 * @author achilleus.almeida
 *
 * Created On : Sep 5, 2019
 */
package com.wba.kafka;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.wba.dbobjects.WbaRate;
import com.wba.logging.InjectableLogger;
import com.wba.repositories.WbaRateRepository;


@Service
public class KafkaConsumer 
{
	@Autowired
	private WbaRateRepository aWbaRateRepository;
	
	@InjectableLogger
	private static Logger aLogger;
	
	@KafkaListener(topics = "test", groupId ="group_id")
	public void 
	consume(String pMessage) 
	{
		aLogger.info("Consumed Message : {}", pMessage);
	}
	
	@KafkaListener(topics = "WbaRatesTopic", groupId = "group_json", containerFactory = "kafkaListenerContainerFactoryWbaRate")
	public void 
	consumeJson(WbaRate pWbaRate) 
	{
		aLogger.info("Consumed Message : {}", pWbaRate);
		aWbaRateRepository.save(pWbaRate);
		aLogger.info("Message Saved : {}", pWbaRate);
	}
}
