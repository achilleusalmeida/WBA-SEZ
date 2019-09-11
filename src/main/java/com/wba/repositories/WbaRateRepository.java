/**
 * @author achilleus.almeida
 *
 * Created On : Aug 13, 2019
 */
package com.wba.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
import com.wba.dbobjects.WbaRate;


@Repository
public interface WbaRateRepository extends DocumentDbRepository<WbaRate, String> 
{
	List<WbaRate> findByProductid (String productid);
		
	@Query("select * from  com.wba.dbobjects.WbaRate wbaRate where medName=:medName")
	List<WbaRate> findByMedName(@Param("medName") String medName);
}
