package group.msg.sapbpmcpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Spring service forming the MCP server tool
 */
@Service
public class BusinessPartnerMCPService {

	/** The client to make a call against the API endpoint */
	private BusinessPartnerClient bpClient;
	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(BusinessPartnerMCPService.class);

	/**
	 * Creates the instance of the service
	 * 
	 */
	public BusinessPartnerMCPService(BusinessPartnerClient bpClient) {
		// Store injection
		this.bpClient = bpClient;
	}

	/**
	 * The MCP server tool. This is the method finally called by the MCP client
	 * 
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	@Tool(description = "Create a partner in SAP-BP with first and last name")
	public String createBPpartner(String firstName, String lastName) {

		// Create partner DTO and JSON
		BusinessPartner businessPartner = new BusinessPartner();
		businessPartner.setFirstName(firstName).setLastName(lastName).setBusinessPartnerCategory("1");

		// Create partner in SAP BP
		try {
			String businessPartnerId = bpClient.createPartner(new ObjectMapper().writeValueAsString(businessPartner));
			return String.format("Partner %s created successfuly", businessPartnerId);
		} catch (Exception e) {
			logger.error("Unable to create new partner: {}", e.getMessage(), e);
			return "Unable to create partner";
		}

	}
}
