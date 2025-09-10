package group.msg.sapbpmcpserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:environmentSetup.properties")
public class EnvironmentSetup {

	@Value("${connection.endpoint.createpartner}")
	private String createPartnerEndpoint;

	@Value("${connection.user}")
	private String user;

	@Value("${connection.password}")
	private String password;

	public String getCreatePartnerEndpoint() {
		return createPartnerEndpoint;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

}
