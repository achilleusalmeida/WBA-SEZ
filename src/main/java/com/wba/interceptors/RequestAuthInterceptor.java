/**
 * 
 */
package com.wba.interceptors;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wba.logging.InjectableLogger;

/**
 * @author achilleus.almeida
 *
 * Created On : Aug 5, 2019
 */
public class RequestAuthInterceptor extends HandlerInterceptorAdapter
{	
	@Value("${wbauser}")
	private String aUsername;
	
	 @Value("${wbapass}")
	 private String aPassword;
	
	@InjectableLogger
	static Logger aLogger;
	
	public static final String AUTHENTICATION = "Authorization";
	
	public static final String OPTIONS = "OPTIONS";
	
	@Override
	public boolean 
	preHandle(HttpServletRequest pRequest, HttpServletResponse pResponse, Object pHandler)
	throws Exception 
	{
		aLogger.info("WBA preHandle for basic authentication");
		if (StringUtils.equals(OPTIONS, pRequest.getMethod())) 
		{
			pResponse.setStatus(HttpServletResponse.SC_OK);
		} 
		else {
				try 
				{
					String lHeaderAuth = pRequest.getHeader(AUTHENTICATION);
					boolean lReqValid = validateReq(lHeaderAuth);
					if (!lReqValid) 
					{
					aLogger.info("Authentication Failed");
					pResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
					return false;
					}
				}
				catch (Exception e) 
				{
					aLogger.info("Error occurred while authenticating request", e);
					aLogger.info("Authentication Failed-Error");
					pResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
					return false;
				}
			}
		return true;
	}
	
	private boolean 
	validateReq(String headerAuth) 
	{
		boolean reqValid = false;
		if (headerAuth != null && headerAuth.toLowerCase().startsWith("basic")) {
			// Authorization: Basic base64credentials
			String lBase64Credentials = headerAuth.substring("Basic".length()).trim();
			byte[] lCredDecoded = Base64.getDecoder().decode(lBase64Credentials);
			String lCredentials = new String(lCredDecoded, StandardCharsets.UTF_8);
			// credentials = username:password
			final String[] splitHeaderAuth = lCredentials.split(":", 2);
			if (splitHeaderAuth.length != 2) {
				aLogger.info("Incorrect format for Authentication! Format should be username:password");
				reqValid = false;
			}

			else {
				String username = splitHeaderAuth[0];
				String password = splitHeaderAuth[1];

				if (StringUtils.equals(aUsername, username) && StringUtils.equals(aPassword, password)) {

					aLogger.info("Request Authenticated successfully");
					reqValid = true;

				} else {
					aLogger.info("Authentication Error! Invalid Credentials");
					reqValid = false;
				}
			}
		}
		else {
			aLogger.info("Not Authorised! No Authentication passed in header");
			reqValid = false;
		}
		return reqValid;
	}
}
