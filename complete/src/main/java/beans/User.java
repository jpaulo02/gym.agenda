package beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
public class User {
	
	private String userId;
	private String email;
	private String password;
	private boolean validated;
	private UserInfo userInfo;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isValidated() {
		return validated;
	}
	
	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	
}
