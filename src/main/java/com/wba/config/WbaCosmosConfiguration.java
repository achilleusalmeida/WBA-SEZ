package com.wba.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.azure.data.cosmos.CosmosKeyCredential;
import com.microsoft.azure.spring.data.cosmosdb.config.AbstractDocumentDbConfiguration;
import com.microsoft.azure.spring.data.cosmosdb.config.DocumentDBConfig;

@Configuration
public class WbaCosmosConfiguration extends AbstractDocumentDbConfiguration
{
	@Value("${azure.cosmosdb.uri}") 
    private String uri;

    @Value("${azure.cosmosdb.key}")
    private String key;

    //@Value("${azure.cosmosdb.secondaryKey}")
    //private String secondaryKey;

    @Value("${azure.cosmosdb.database}")
    private String dbName;
    
    //private CosmosKeyCredential cosmosKeyCredential;

    public DocumentDBConfig getConfig() {
    	CosmosKeyCredential lCosmosKeyCredential = new CosmosKeyCredential(key);
        return DocumentDBConfig.builder(uri, lCosmosKeyCredential.key(), dbName).build();
    }
    
	/*
	 * public void switchToSecondaryKey() {
	 * this.cosmosKeyCredential.key(secondaryKey); }
	 */
}
