# SAP-LLM Integration Showcase with Model Context Protocol

> Experience seamless AI-powered enterprise integration. This innovative showcase demonstrates how Large Language Models can intelligently interact with SAP Business Partner data through the cutting-edge [Model Context Protocol](https://modelcontextprotocol.io/) (MCP). Users can effortlessly create new business partners directly through natural language conversations with their LLM of choice. Built on robust [Java](https://adoptopenjdk.net/) foundation with Spring's advanced [MCP implementation](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-overview.html).

![Animation](https://github.com/user-attachments/assets/81e65e52-edb3-4882-8ec2-8cfa3b088f4f)

### [Architecture Overview](#architecture-overview) ⚫ [Prerequisites](#prerequisites) ⚫ [Setup & Configuration](#setup--configuration) ⚫ [Building from Source](#building-from-source) ⚫ [Usage](#usage) ⚫ [Troubleshooting](#troubleshooting) ⚫ [License](#license) ⚫ [Contributing & Support](#contributing--support)

## Architecture Overview

This showcase leverages several key technologies:

- **Spring AI MCP Framework** - Provides the MCP server implementation
- **SAP OData APIs** - Allow direct integration with SAP Business Partner services  
- **Natural Language Processing** - Intelligent parsing of conversational input using AI and LLM
- **Robust Error Handling** - Comprehensive validation and error recovery

## Prerequisites
Before diving into this next-generation integration experience, ensure you have the following components ready:

- **LLM with MCP Support** - A Large Language Model that supports the Model Context Protocol (see [supported clients](https://modelcontextprotocol.io/clients))
- **SAP System** - SAP environment with an activated Business Partner creation service (details see below)
- **Java Runtime** - Java 17 or higher for optimal performance
- **Maven** - For building the project from source (version 3.6+ recommended)

## Setup & Configuration

### 1. Configure your SAP Server
Ensure your SAP system has the [Business Partner creation API](https://api.sap.com/api/API_BUSINESS_PARTNER/path/post_A_BusinessPartner) properly activated and accessible. The API endpoint must be reachable from your application environment, and you'll need a user with valid credentials and appropriate authorization for Business Partner creation operations.
**Required SAP Authorizations:**
- `S_SERVICE` - For OData service access
- `F_BUPA_*` - Business Partner maintenance authorizations
  
### 2. Choose your LLM
Choose and install an LLM capable of interacting with the MCP. A comprehensive list of LLMs can be found [here](https://modelcontextprotocol.io/clients).

### 3. Download the Application
Download the latest JAR file from the [releases section](../../releases) of this repository, or build from source using the instructions below.

### 4. Configure the Application
Copy and edit the application configuration file _environmentSetup.properties.template_ to match your environment:

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
See the [MCP documentation](https://modelcontextprotocol.io/docs/develop/connect-local-servers) for detailed guidance on connecting to MCP servers.

## Building from Source
For developers who want to customize the showcase and build it on their own:

```bash
# Clone the repository
git clone https://github.com/yourusername/sap-bp-mcp-showcase.git
cd sap-bp-mcp-showcase

# Configure your environment
cp environmentSetup.properties.template environmentSetup.properties
# Edit environmentSetup.properties with your SAP credentials

# Build the project
mvn clean install
```

This compiles the source code, runs tests, and packages the application into a deployable JAR file in the `target` directory.

**⚠️ Important Note:** During the build process, an integration test will execute that creates an actual Business Partner record in your configured SAP system. Ensure your test environment is properly set up to handle these operations.

## Usage

Once configured, simply start a conversation with your LLM and begin creating SAP Business Partners using natural language. The MCP integration seamlessly handles all SAP API interactions behind the scenes, providing an intuitive conversational experience.

Example conversation:
```
"Create a new business partner Jane Doe in SAP"
```

## Troubleshooting

**Common Issues:**

- **Connection Refused**: Verify your SAP endpoint URL and network connectivity
- **Authentication Failed**: Check your SAP credentials and user authorizations
- **MCP Server Not Found**: Ensure Java path is correct in LLM configuration
- **Business Partner Creation Failed**: Verify required fields and SAP customization settings

For all errors, please check the log file _sap-bp-mcp-server.log_

**Debug Mode:**
For a detailed analysis, please consider using the MCP [debugging capabilities](https://modelcontextprotocol.io/legacy/tools/debugging).

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Contributing & Support

This is a showcase project demonstrating the powerful potential of MCP integration with enterprise systems. Please feel free to open a GitHub issue if you have questions, suggestions, or encounter any challenges while exploring this integration approach.

---

*Transforming enterprise data interaction through intelligent conversation.*
