# SAP-LLM Integration Showcase with Model Context Protocol

> Experience seamless AI-powered enterprise integration. This innovative showcase demonstrates how Large Language Models can intelligently interact with SAP Business Partner data through the cutting-edge [Model Context Protocol](https://modelcontextprotocol.io/) (MCP). Users can effortlessly create new business partners directly through natural language conversations with their LLM of choice. Built on robust [Java](https://adoptopenjdk.net/) foundation with Spring's advanced [MCP implementation](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-overview.html).

![Animation](https://github.com/user-attachments/assets/81e65e52-edb3-4882-8ec2-8cfa3b088f4f)

**TODO** Need to add a toc

## Prerequisites
Before diving into this next-generation integration experience, ensure you have the following components ready:

- **LLM with MCP Support** - A Large Language Model that supports the Model Context Protocol
- **SAP System** - SAP environment with an activated Business Partner creation service (details see below)
- **Java Runtime** - Java 17 or higher for optimal performance
- **Maven** - For building the project from source

## Setup & Configuration

### 1. Configure Your SAP Server
Ensure your SAP system has the [Business Partner creation API](https://api.sap.com/api/API_BUSINESS_PARTNER/path/post_A_BusinessPartner) properly activated and accessible. Configure the necessary endpoints and authentication credentials.

### 2. Choose your LLM
Choose and install an LLM capable of interacting with the MCP. A list LLMs can be found [here](https://modelcontextprotocol.io/clients).

### 3. Download the Application
Download the latest JAR file from the releases section of this repository.

### 4. Configure the Application
Edit the application configuration _environmentSetup.properties_ to match your environment:

```properties
# SAP Connection Configuration
connection.endpoint.createpartner=http://mysapserver.com:8000/sap/opu/odata/sap/API_BUSINESS_PARTNER/A_BusinessPartner
connection.user=userName
connection.password=secretPassword
```
### 5. Configure Your LLM
Add the MCP server to your LLM configuration to enable seamless integration:

```json
{
	"mcpServers": {
		"sap-bp-mcp-showcase": {
			"command": "java",
			"args": [
				"-Dspring.ai.mcp.server.transport=STDIO",
				"-jar",
				"Path\\To\\sap-bp-mcp-server-1.0.0-SNAPSHOT.jar"
			]
		}		
	}
}
```
See the [mcp documentation](https://modelcontextprotocol.io/docs/develop/connect-local-servers) for reference how to connect to a MCP server.


## Building from Source
To customize the solution and build on your own execute:

```bash
mvn clean install
```
This will compile the source code, run tests, and package the application into a deployable JAR file in the `target` directory.

## Usage

Once configured, simply start a conversation with your LLM and begin creating SAP Business Partners using natural language. The MCP integration handles the SAP API interactions behind the scenes, providing a smooth, intuitive experience.

Example conversation:
```
"Create a new business partner John Doe in SAP"
```

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Contributing & Support

This is a showcase project demonstrating the powerful potential of MCP integration with enterprise systems. Feel free to open a GitHub issue if you have questions, suggestions, or encounter any challenges while exploring this integration approach.

---

*Transforming enterprise data interaction through intelligent conversation.*
