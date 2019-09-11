/**
 * 
 */
package com.wba.transfer;

import java.util.List;

import com.wba.dbobjects.WbaRate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author achilleus.almeida
 *
 * Created On : Aug 7, 2019
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WbaRateRequest 
{
	private List<WbaRate> wbaRateList;
}
