/**
 * @author achilleus.almeida
 *
 * Created On : Aug 2, 2019
 */
package com.wba.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.wba.dbobjects.WbaRate;
import com.wba.kafka.KafkaProducer;
import com.wba.logging.InjectableLogger;
import com.wba.repositories.WbaRateRepository;



@Service
@Qualifier("WbaProductService")
public class WbaProductService {

	@Autowired
	private WbaRateRepository aWbaRateRepository;
	
	@InjectableLogger
	private static Logger aLogger;
	
	@Autowired
	private KafkaProducer aKafkaProducer;
	
	/**
	 * The below method returns all the products currently present in the Database
	 * 
	 * @return
	 */
	@Cacheable(value="listofProducts")
	public List<WbaRate> 
	listAllProducts() 
	{
		aLogger.info("List All Products methods called");
		return (List<WbaRate>) aWbaRateRepository.findAll();
	}

	/**
	 * The below service method is used to save a product to the Database
	 * 
	 * @param pWbaRate
	 */
	@Async
	public void 
	saveProduct(WbaRate pWbaRate) 
	{
		aLogger.info("Publishing message on to Kafka : {}", pWbaRate);
		aKafkaProducer.post(pWbaRate);
		aLogger.info("Message posted on to Kafka : {}", pWbaRate);
	}
	
	/**
	 * The below method returns all the products currently present in the Database
	 * 
	 * @return
	 */
	public Set<String> 
	fetchMedName() 
	{
		List<WbaRate> lRateList = (List<WbaRate>) aWbaRateRepository.findAll();
		return lRateList 
					.stream() 
						.map(WbaRate::getMedName)
							.collect(Collectors.toSet());
	}
}

