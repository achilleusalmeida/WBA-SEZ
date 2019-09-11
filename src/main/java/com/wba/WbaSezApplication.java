/**
 * @author achilleus.almeida
 *
 * Created On : Aug 2, 2019
 */
package com.wba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import com.microsoft.azure.spring.data.cosmosdb.repository.config.EnableDocumentDbRepositories;

@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableDocumentDbRepositories(basePackages = "com.wba.repositories")
public class WbaSezApplication {
	public static void main(String[] args) {
		SpringApplication.run(WbaSezApplication.class, args);
	}
}
