/**
 * 
 */
package com.wba.managers;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.wba.dbobjects.WbaRate;
import com.wba.logging.InjectableLogger;

/**
 * @author achilleus.almeida
 *
 * Created On : Aug 6, 2019
 */
@Component
public class RateManager 
{
	@InjectableLogger
	static Logger aLogger;
	
	public void 
	process(WbaRate pWbaRate) 
	{
		aLogger.info("Processing Rate",pWbaRate);
	}

}
