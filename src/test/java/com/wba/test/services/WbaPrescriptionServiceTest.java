/**
 * 
 */
package com.wba.test.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.wba.WbaSezApplicationTests;
import com.wba.dbobjects.WbaRate;
import com.wba.managers.RateManager;
import com.wba.services.WbaPrescriptionService;

/**
 * @author achilleus.almeida
 *
 * Created On : Aug 6, 2019
 */

@Profile("test")
@WebAppConfiguration
public class WbaPrescriptionServiceTest extends WbaSezApplicationTests
{
	@Autowired
	WbaPrescriptionService aWbaPrescriptionService;
	
	@Mock
	RateManager aRateManager;
	
	@Test
	public void 
	handlePrescriptionRequestsTest() 
	throws Exception 
	{
		ReflectionTestUtils.setField(aWbaPrescriptionService, "aRateManager", aRateManager);
		doNothing().when(aRateManager).process(any(WbaRate.class));
		aWbaPrescriptionService.handlePrescriptionRequests(getWbaRateList());
	}

	private List<WbaRate> 
	getWbaRateList() 
	{
		List<WbaRate> lWbaRateList = new ArrayList<>();
		lWbaRateList.add(WbaRate
							.builder()
								.medName("Test")
									.pharmType("Test")
										.build());
		return lWbaRateList;
	}
}
