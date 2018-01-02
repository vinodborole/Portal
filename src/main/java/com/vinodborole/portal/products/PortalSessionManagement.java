/**
 * 
 */
package com.vinodborole.portal.products;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinodborole.portal.persistence.dao.PortalAccountProductConfigRepository;
import com.vinodborole.portal.persistence.dao.PortalUserRepository;
import com.vinodborole.portal.persistence.model.PortalAccountProductConfig;
import com.vinodborole.portal.persistence.model.PortalUser;
import com.vinodborole.portal.persistence.model.token.PortalUserContext;
import com.vinodborole.portal.products.product1.Product1ProductSession;
import com.vinodborole.portal.products.product2.Product2ProductSession;
import com.vinodborole.portal.security.auth.PortalSession;

/**
 * @author vinodborole
 *
 */
@Component
public class PortalSessionManagement {

	private PortalSession portalSession;

	@Autowired
	private PortalUserRepository userRepository;

	@Autowired
	private PortalAccountProductConfigRepository accountProductConfigRepository;

	public Map<PortalProductType, IPortalProductSession> getProductSessions(PortalUserContext userContext)
			throws JsonParseException, JsonMappingException, IOException {
		Map<PortalProductType, IPortalProductSession> productSessions = new HashMap<PortalProductType, IPortalProductSession>();

		Product2ProductSession product2ProductSession = new Product2ProductSession();
		Product1ProductSession product1ProductSession = new Product1ProductSession();

		PortalUser user = userRepository.findByUsername(userContext.getUsername());

		List<PortalAccountProductConfig> productsConfigs = accountProductConfigRepository
				.findByAccounts(user.getAccount());

		for (PortalAccountProductConfig productsConfig : productsConfigs) {
			if (productsConfig.getProducts().getProductType() == PortalProductType.PRODUCT1) {
				Map<String, String> map = getProductConfig(productsConfig);
				product1ProductSession.setProductConfig(map);
			} else if (productsConfig.getProducts().getProductType() == PortalProductType.PRODUCT2) {
				Map<String, String> map = getProductConfig(productsConfig);
				product2ProductSession.setProductConfig(map);
			}
		}

		setProduct1ProductToken(userContext, product1ProductSession);
		setProduct2ProductToken(userContext, product2ProductSession);

		productSessions.put(PortalProductType.PRODUCT1, product1ProductSession);
		productSessions.put(PortalProductType.PRODUCT2, product2ProductSession);

		return productSessions;
	}

	private Map<String, String> getProductConfig(PortalAccountProductConfig productsConfig)
			throws IOException, JsonParseException, JsonMappingException {
		TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
		};
		Map<String, String> map = new ObjectMapper().readValue(productsConfig.getConfig(), typeRef);
		return map;
	}

	private void setProduct2ProductToken(PortalUserContext userContext, Product2ProductSession product2ProductSession) {
		PortalRestTemplate restTemplate = new PortalRestTemplate(product2ProductSession);
		ResponseEntity<?> loginResponse = loginProduct2(userContext, restTemplate);
		if (loginResponse.getStatusCode() == HttpStatus.OK) {
			List<String> tokenList = loginResponse.getHeaders().get("x-authentication-token");
			String authorizationKey = tokenList.get(0);
			System.out.println("HTTPStatus: " + loginResponse.getStatusCodeValue());
			System.out.println("Response Body: " + loginResponse.getBody());
			System.out.println("Authorization: " + authorizationKey);
			product2ProductSession.setToken(authorizationKey);
		} else if (loginResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
			ResponseEntity<?> response = registerProduct2(userContext, restTemplate);
			System.out.println("HTTPStatus: " + response.getStatusCodeValue());
			System.out.println("Response Body: " + response.getBody());
			if (response.getStatusCode() == HttpStatus.OK) {
				ResponseEntity<?> reloginResponse = loginProduct2(userContext, restTemplate);
				if (reloginResponse.getStatusCode() == HttpStatus.OK) {
					List<String> tokenList = reloginResponse.getHeaders().get("x-authentication-token");
					String authorizationKey = tokenList.get(0);
					System.out.println("HTTPStatus: " + reloginResponse.getStatusCodeValue());
					System.out.println("Response Body: " + reloginResponse.getBody());
					System.out.println("Authorization: " + authorizationKey);
					product2ProductSession.setToken(authorizationKey);
				}
			}
		}
	}

	private ResponseEntity<?> registerProduct2(PortalUserContext userContext, PortalRestTemplate restTemplate) {
		restTemplate.setContentType(MediaType.APPLICATION_JSON);
		try {
			Map<String, String> registerUsermap = new HashMap<String, String>();
			registerUsermap.put("username", userContext.getUsername());
			registerUsermap.put("password", "devOpsPasswd");
			String json = new ObjectMapper().writeValueAsString(registerUsermap);
			ResponseEntity<?> response = restTemplate.post("/api/registerUser", json);
			return response;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ResponseEntity<?> loginProduct2(PortalUserContext userContext, PortalRestTemplate restTemplate) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("username", userContext.getUsername());
		map.add("password", "product2Passwd");
		restTemplate.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		ResponseEntity<?> loginResponse = restTemplate.post("/api/login", map);
		return loginResponse;
	}

	private void setProduct1ProductToken(PortalUserContext userContext, Product1ProductSession product1ProductSession) {
		PortalRestTemplate restTemplate = new PortalRestTemplate(product1ProductSession);
		String authorizationKey = loginProduct1(userContext, restTemplate);
		product1ProductSession.setToken(authorizationKey);
	}

	private String loginProduct1(PortalUserContext userContext, PortalRestTemplate restTemplate) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("username", userContext.getUsername());
		map.add("password", "product1Passwd");
		restTemplate.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		ResponseEntity<?> loginResponse = restTemplate.post("/api/login", map);
		List<String> tokenList = loginResponse.getHeaders().get("x-authentication-token");
		String authorizationKey = tokenList.get(0);
		System.out.println("HTTPStatus: " + loginResponse.getStatusCodeValue());
		System.out.println("Response Body: " + loginResponse.getBody());
		System.out.println("Authorization: " + authorizationKey);
		return authorizationKey;
	}

	@Cacheable(value = "portalsession", key = "#userContext")
	public PortalSession getPortalSession(PortalUserContext userContext) {
		return portalSession;
	}

	public void setPortalSession(PortalSession cesSession) {
		this.portalSession = cesSession;
	}
}
