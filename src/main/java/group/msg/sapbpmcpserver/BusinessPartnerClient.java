package group.msg.sapbpmcpserver;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class to encapsulate calls to the SAP BP ODATA endpoint
 */
@Component
public class BusinessPartnerClient {

	/** Header for the csrf token */
	private static final String XSRF_HEADER = "x-csrf-token";
	/** Header fetch */
	private static final String FETCH = "Fetch";
	/** String bearing the received access token */
	private String token;
	/** Auth header bearing username and password for HTTP basic auth */
	private String authHeader;
	/** Injected class with the connection details */
	private final EnvironmentSetup environmentSetup;
	/** The HTTP client */
	private final HttpClient client;
	/** HTTP cookie manager */
	private final CookieManager cookieManager;
	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(BusinessPartnerClient.class);
	/** JSON object mapper */
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Creates a new instance
	 * 
	 * @param environmentSetup injected setup variables
	 */
	public BusinessPartnerClient(EnvironmentSetup environmentSetup) {
		// Store injection
		this.environmentSetup = environmentSetup;

		// Prepare authorization header content
		String auth = environmentSetup.getUser() + ":" + environmentSetup.getPassword();
		String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
		authHeader = "Basic " + encodedAuth;

		// Init cookie manager
		this.cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(cookieManager);
		this.client = HttpClient.newBuilder().cookieHandler(cookieManager).build();
	}

	/**
	 * Get the CSRF token
	 * 
	 * @return
	 * @throws BusinessPartnerException
	 */
	private String getCSRFToken() throws BusinessPartnerException {
		// Obtain if not already done
		if (token == null) {
			obtainCSRFToken();
		}

		// Returm the token
		return token;
	}

	/**
	 * Obtain the CSRF token using a HTTP call
	 * 
	 * @throws BusinessPartnerException
	 */
	private void obtainCSRFToken() throws BusinessPartnerException {
		// Prepare request
		HttpRequest request = HttpRequest.newBuilder().version(HttpClient.Version.HTTP_1_1)
				.uri(URI.create(environmentSetup.getCreatePartnerEndpoint())).header(XSRF_HEADER, FETCH)
				.header("Authorization", authHeader).header("Content-Type", "application/json").header("Accept", "*/*")
				.GET().build();

		try {
			// Execute request
			HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
			token = response.headers().firstValue(XSRF_HEADER).get();
		} catch (Exception e) {
			logger.error("Unable to obtain CSRF ticket: {}", e.getMessage(), e);
			throw new BusinessPartnerException("Unable to obtain CSRF ticket", e);
		}
	}

	/**
	 * Method to create the partner in the SAP system
	 * 
	 * @param jsonBody
	 * @return
	 * @throws BusinessPartnerException
	 */
	public String createPartner(String jsonBody) throws BusinessPartnerException {

		try {
			// Create request
			HttpRequest request = HttpRequest.newBuilder().version(HttpClient.Version.HTTP_1_1)
					.uri(URI.create(environmentSetup.getCreatePartnerEndpoint())).header(XSRF_HEADER, getCSRFToken())
					.header("Authorization", authHeader).header("Content-Type", "application/json")
					.header("Accept", "application/json").POST(BodyPublishers.ofString(jsonBody)).build();

			// Execute request
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			String partnerKey = mapper.readTree(response.body()).get("d").get("BusinessPartner").toString();

			// Log
			logger.info(String.format("Partner %s created successfuly", partnerKey));

			// Return number
			return partnerKey;
		} catch (IOException | InterruptedException e) {
			logger.error("Unable to create partner: {}", e.getMessage(), e);
			throw new BusinessPartnerException("Unable to create partner", e);
		}

	}

}
