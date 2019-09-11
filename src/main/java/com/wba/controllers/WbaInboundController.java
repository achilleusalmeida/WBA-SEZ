/**
 * @author achilleus.almeida
 *
 * Created On : August 2, 2019
 */
package com.wba.controllers;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wba.dbobjects.WbaRate;
import com.wba.logging.InjectableLogger;
import com.wba.repositories.WbaRateRepository;
import com.wba.services.WbaPrescriptionService;
import com.wba.services.WbaProductService;
import com.wba.transfer.WbaRateRequest;

/*	
 * @CrossOrigin was used to allow requests 
 * to come in through the front End (Angular) 
 * from the specified port
 * 
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class WbaInboundController 
{
	@Autowired
	private WbaPrescriptionService aWbaPrescriptionService;
	
	@InjectableLogger
	private static Logger aLogger;
	
	@Autowired
	private WbaRateRepository aWbaRateRepository;

	@Autowired
	private WbaProductService aWbaProductService;
	
	/**
	 * The below end point responds with the name of the user passed on in the request body
	 * @param pUserName
	 * @return
	 */
	@PostMapping(value = "${api-endpoint-config.user}")
	public ResponseEntity<String> 
	helloUser(@RequestBody String pUserName) 
	{
		return new ResponseEntity<>("Hi " + pUserName, HttpStatus.OK);
	}
		
	/**
	 * The below endpoint is used to process a rate by converting it into an observable
	 * @param pWbaRateRequest
	 * @return
	 */
	@PostMapping(value = "${api-endpoint-config.phRate}")
	public ResponseEntity<WbaRateRequest> 
	getPhRate(@RequestBody WbaRateRequest pWbaRateRequest) 
	{
		aWbaPrescriptionService.handlePrescriptionRequests(pWbaRateRequest.getWbaRateList());
		return new ResponseEntity<>(pWbaRateRequest, HttpStatus.OK);
	}
	
	/**
	 * @param pWbaRate
	 * @return
	 */
	@PostMapping(value = "${api-endpoint-config.phRateAsync}")
	public ResponseEntity<List<WbaRate>> 
	getPhRateAsync(@RequestBody List<WbaRate> pWbaRate) 
	{
		aWbaPrescriptionService.handlePrescriptionRequestsAsync(pWbaRate);
		List<WbaRate> lRetrievedRecords = (List<WbaRate>) aWbaRateRepository.findAll();
		return new ResponseEntity<>(lRetrievedRecords, HttpStatus.OK);
	}
	
	/**
	 * The below end point is used to list all the products currently in the Azure Cosmos DB
	 * @param pWbaRate
	 * @return
	 */
	@PostMapping(value = "${api-endpoint-config.phRateException}")
	public ResponseEntity<List<WbaRate>> 
	getPhRateExceptionScenario(@RequestBody List<WbaRate> pWbaRate) 
	{
		aWbaPrescriptionService.handlePrescriptionRequestsException(pWbaRate);
		List<WbaRate> lRetrievedRecords = (List<WbaRate>) aWbaRateRepository.findAll();
		return new ResponseEntity<>(lRetrievedRecords, HttpStatus.OK);
	}
	
	/**
	 * The below end point is used to save a new product to Azure Cosmos DB
	 * @param pWbaRate
	 * @return
	 */
	@PostMapping(value = "${api-endpoint-config.saveProduct}")
	public ResponseEntity<WbaRate> 
	saveProduct(@RequestBody WbaRate pWbaRate) 
	{
		aLogger.info("Beginning of Inbound Controller saveProduct : {}", pWbaRate);
		aWbaProductService.saveProduct(pWbaRate);
		return new ResponseEntity<>(pWbaRate, HttpStatus.OK);
	}
	
	/**
	 * The below end point is used to save a list of products into Azure Cosmos DB
	 * @param pWbaRate
	 * @return
	 */
	@PostMapping(value = "${api-endpoint-config.saveProducts}")
	public ResponseEntity<String> 
	saveProducts(@RequestBody List<WbaRate> pWbaRate) 
	{
		aLogger.info("Beginning of Inbound Controller save List of Products : {}", pWbaRate);
		aWbaRateRepository.saveAll(pWbaRate);
		return new ResponseEntity<>("Records Successfully inserted", HttpStatus.OK);
	}
	
	/**
	 * The below end point is used to save a list of products into Azure Cosmos DB
	 * @param pWbaRate
	 * @return
	 */
	@PostMapping(value = "${api-endpoint-config.updateProduct}")
	public ResponseEntity<String> 
	updateProduct(@RequestBody WbaRate pWbaRate) 
	{
		aLogger.info("Beginning of Inbound Controller updateProduct : {}", pWbaRate);
		List<WbaRate> lTargetWbaRate = aWbaRateRepository.findByProductid(pWbaRate.getId());
		BeanUtils.copyProperties(pWbaRate, lTargetWbaRate.get(0));
		aWbaRateRepository.save(lTargetWbaRate.get(0));
		return new ResponseEntity<>("Records Updated successfully", HttpStatus.OK);
	}
	
	/**
	 * The below end point is used to delete a  product from Azure Cosmos DB
	 * @param pWbaRate
	 * @return
	 */
	@PostMapping(value = "${api-endpoint-config.deleteProductById}")
	public ResponseEntity<String> 
	deleteProductById(@RequestBody String pProductId) 
	{
		/*
		 * TODO : Try and derive why the native findById method does not work there
		 * seems to be some problem with the partition key there is an exception which
		 * is thrown "partition key needs to be provided"
		 */
		List<WbaRate> lProductsList = aWbaRateRepository.findByProductid(pProductId);
		aWbaRateRepository.deleteAll(lProductsList);
		return new ResponseEntity<>("Record successfully deleted", HttpStatus.OK);
	}
	
	
	/**
	 * The below end point is used to list all the products from Azure Cosmos DB
	 * @return
	 */
	@GetMapping(value = "${api-endpoint-config.listAllProducts}")
	public ResponseEntity<List<WbaRate>> 
	listAllProducts() 
	{
		aLogger.info("Beginning of Inbound Controller listAllProducts");
		return new ResponseEntity<>(aWbaProductService.listAllProducts(), HttpStatus.OK);
	}
	
	
	/**
	 * The below end point is used to find a product from Azure Cosmos DB using its ID
	 * @param pId
	 * @return
	 */
	@PostMapping(value = "${api-endpoint-config.findProductById}")
	public ResponseEntity<List<WbaRate>> 
	findProductById(@RequestBody String pId) 
	{
		List<WbaRate> lProductsList = aWbaRateRepository.findByProductid(pId);
		return new ResponseEntity<>(lProductsList, HttpStatus.OK);
	}
	
	
	/**
	 * The below end point is used to find a product from Azure Cosmos DB using the product Name
	 * @param pMedName
	 * @return
	 */
	@PostMapping(value = "${api-endpoint-config.findByMedName}")
	public ResponseEntity<List<WbaRate>> 
	findByMedName(@RequestBody String pMedName) 
	{
		List<WbaRate> lProductsList = aWbaRateRepository.findByMedName(pMedName);
		aLogger.info("List size is : {}", lProductsList.size());
		return new ResponseEntity<>(lProductsList, HttpStatus.OK);
	}
	
	/**
	 * The below end point is used to list all the products from Azure Cosmos DB
	 * @return
	 */
	@GetMapping(value = "${api-endpoint-config.fetchMedName}")
	public ResponseEntity<Set<String>> 
	fetchProductIds() 
	{
		aLogger.info("Beginning of Inbound Controller fetchMedName");
		return new ResponseEntity<>(aWbaProductService.fetchMedName(), HttpStatus.OK);
	}
	
	/**
	 * The below end point is used to list all the products from Azure Cosmos DB
	 * @return
	 */
	@GetMapping(value = "${api-endpoint-config.testCache}")
	public ResponseEntity<List<WbaRate>>  
	testCache() 
	{
		aLogger.info("Beginning of Inbound Controller listAllProducts");
		aWbaProductService.listAllProducts();
		aWbaProductService.listAllProducts();
		List<WbaRate> lRateList = aWbaProductService.listAllProducts();
		return new ResponseEntity<>(lRateList, HttpStatus.OK);
	}
}

