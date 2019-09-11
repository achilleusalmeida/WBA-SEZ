package com.wba.services;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.wba.logging.InjectableLogger;

@Service
@Qualifier("WbaRegistrationService")
public class WbaRegistrationService 
{
	@InjectableLogger
	static Logger aLogger;

	public boolean 
	registerUsers(String pUser) 
	{
		if (!StringUtils.isEmpty(pUser)) {
			aLogger.info("User successfully registered");
			return true;
		} else {
			return false;
		}
	}
}
