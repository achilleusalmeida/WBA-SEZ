/**
 * 
 */
package com.wba.services;

import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.wba.dbobjects.WbaRate;
import com.wba.logging.InjectableLogger;
import com.wba.managers.RateManager;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author achilleus.almeida
 *
 * Created On : Aug 5, 2019
 */

@Service
@Qualifier("WbaPrescriptionService")
public class WbaPrescriptionService 
{
	
	@InjectableLogger
	static Logger aLogger;
	
	@Autowired
	private RateManager aRateManager;
		
	/**
	 * @param pWbaRate
	 */
	public void 
	handlePrescriptionRequests(List<WbaRate> pWbaRate) 
	{
		Observable<WbaRate> lprescriptionObservable = Observable.fromIterable(pWbaRate);
		lprescriptionObservable
			.subscribe
				(lprescription -> aRateManager.process(lprescription), 		//Data Channel
					this::handleException, 		 		//Error Channel
						() -> aLogger.info("Done"));	//Complete Channel
	}
	
	/**
	 * @param pException
	 * @return
	 */
	private Throwable
	handleException(Throwable pException) 
	{
		return pException;
	}

	/**
	 * The below method is identical to the above one the only difference being that
	 * its response is Asynchronous.
	 * 
	 * @param pWbaRate
	 */
	@Async
	public void 
	handlePrescriptionRequestsAsync(List<WbaRate> pWbaRate) 
	{
		Observable<WbaRate> lprescriptionObservable = Observable.fromIterable(pWbaRate);
			lprescriptionObservable
				.subscribe
					(item -> aRateManager.process(item), 		//Data Channel
						this::handleException,  		 		//Error Channel
							() -> aLogger.info("Done"));	//Complete Channel
	}

	/**
	 * @param pWbaRate
	 */
	public void 
	handlePrescriptionRequestsException(List<WbaRate> pWbaRate) 
	{
		Observable<WbaRate> lprescriptionObservable = Observable.fromIterable(pWbaRate);
		lprescriptionObservable
		 .subscribeOn(Schedulers.io())
		 .map(lInput -> lInput.getMedName().toUpperCase())
		  .subscribe(inp -> System.out.println("Processing : " + inp + " on " + Thread.currentThread().getName()),
		    	error -> System.out.println("Error!")
		  );	
	}
}
