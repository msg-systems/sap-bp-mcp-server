package group.msg.sapbpmcpserver;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO class for a business partner
 */
public class BusinessPartner {

	@JsonProperty("FirstName")
	private String firstName;

	@JsonProperty("LastName")
	private String lastName;

	@JsonProperty("BusinessPartnerCategory")
	private String businessPartnerCategory;

	public String getFirstName() {
		return firstName;
	}

	public BusinessPartner setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public BusinessPartner setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getBusinessPartnerCategory() {
		return businessPartnerCategory;
	}

	public void setBusinessPartnerCategory(String businessPartnerCategory) {
		this.businessPartnerCategory = businessPartnerCategory;
	}

}
