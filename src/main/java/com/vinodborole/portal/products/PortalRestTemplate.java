/**
 * 
 */
package com.vinodborole.portal.products;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author vinodborole
 *
 */
public class PortalRestTemplate<T> {

	private IPortalProductSession productSession;
	private RestTemplate rest;
	private HttpHeaders headers;
	private HttpStatus status;
	private String server;

	public PortalRestTemplate(IPortalProductSession productSession) {
		this.productSession = productSession;
		this.rest = new RestTemplate();
		this.headers = new HttpHeaders();
		headers.add("Accept", "*/*");
		if (productSession.getProductToken() != null)
			headers.add("Authorization", "Bearer " + productSession.getProductToken());
		if (productSession.getProductConfig() != null)
			this.server = productSession.getProductConfig().get("host");
	}

	public void setContentType(MediaType mediaType) {
		headers.setContentType(mediaType);
	}

	public void addHeader(String key, String value) {
		headers.add(key, value);
	}

	public ResponseEntity get(String uri) {
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.GET, requestEntity,
				String.class);
		this.setStatus(responseEntity.getStatusCode());
		return responseEntity;
	}

	public ResponseEntity post(String uri, T entity) {
		HttpEntity<T> request = new HttpEntity<T>(entity, headers);
		ResponseEntity<String> response = rest.postForEntity(server + uri, request, String.class);
		return response;
	}

	public ResponseEntity put(String uri, String json) {
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.PUT, requestEntity,
				String.class);
		return responseEntity;
	}

	public ResponseEntity delete(String uri) {
		HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
		ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.DELETE, requestEntity,
				String.class);
		return responseEntity;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
