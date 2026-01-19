package group.msg.sapbpmcpserver;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class McpServerApplicationTest {

	@Autowired
	/** Injected class with the connection details */
	private EnvironmentSetup envSetup;
	@Autowired
	/** The client to make a call against the API endpoint */
	private BusinessPartnerClient bpClient;
	/** Logger */
	private static final Logger logger = LoggerFactory.getLogger(McpServerApplicationTest.class);

	@Test
	void readConfig() {
		System.out.println(envSetup.getUser());
	}

	@Test
	void checkConnection() {
		// Create partner DTO and JSON
		BusinessPartner businessPartner = new BusinessPartner();
		businessPartner.setFirstName("Created").setLastName("ByTestExec").setBusinessPartnerCategory("1");

		// Create partner
		try {
			bpClient.createPartner(new ObjectMapper().writeValueAsString(businessPartner));
		} catch (JsonProcessingException | BusinessPartnerException e) {
			logger.error("Unable to create new partner: {}", e.getMessage(), e);
			fail("Unable to create partner", e);
		}

	}
}
