package com.abb.ventyx.utilities;

public class TestLoginCredentials {

	private String username;
	private String password;

	public TestLoginCredentials(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestLoginCredentials other = (TestLoginCredentials) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	// public TestLoginCredentials merge(TestLoginCredentials overrides) {
	//
	// return new TestLoginCredentials(
	// isNotBlank(overrides.getUsername()) ? overrides.getUsername() :
	// getUsername(),
	// isNotBlank(overrides.getPassword()) ? overrides.getPassword() :
	// getPassword(),
	// isNotBlank(overrides.getDistrict()) ? overrides.getDistrict() :
	// getDistrict(),
	// isNotBlank(overrides.getPosition()) ? overrides.getPosition() :
	// getPosition());
	// }

}
