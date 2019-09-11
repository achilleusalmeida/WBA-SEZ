package com.wba.dbobjects;

import org.springframework.data.annotation.Id;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "product")
public class WbaRate 
{
	@Id
	private String id;
	
	private String productid;
	
	private String phUni;

	private String pharmCode;

	private String pharmType;

	private String medType;

	private String medName;

	private String prescribedBy;

	@Override
	public String toString() {
		return "WbaRate [id=" + id + ", productid=" + productid + ", phUni=" + phUni + ", pharmCode=" + pharmCode
				+ ", pharmType=" + pharmType + ", medType=" + medType + ", medName=" + medName + ", prescribedBy="
				+ prescribedBy + "]";
	}
	
}
