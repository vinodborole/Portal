/**
 * 
 */
package com.vinodborole.portal.products;

import java.util.Map;

/**
 * @author vinodborole
 *
 */
public interface IPortalProductSession {

	public PortalProductType getProductType();

	public String getProductToken();

	public Map<String, String> getProductConfig();
}
