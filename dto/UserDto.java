package jp.kelonos.dto;

import java.util.Date;

public class UserDto {
	
	private int userId;
	private String userEmail;
	private String userPassword;
	private String userName;
	private int userStatus;
	private int userDelFlg;
	private Date userStartTime;
	private Date userUpdateTime;
	private int companyId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public int getUserDelFlg() {
		return userDelFlg;
	}

	public void setUserDelFlg(int userDelFlg) {
		this.userDelFlg = userDelFlg;
	}

	public Date getUserStartTime() {
		return userStartTime;
	}

	public void setUserStartTime(Date userStartTime) {
		this.userStartTime = userStartTime;
	}

	public Date getUserUpdateTime() {
		return userUpdateTime;
	}

	public void setUserUpdateTime(Date userUpdateTime) {
		this.userUpdateTime = userUpdateTime;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getUserReason() {
		return userReason;
	}

	public void setUserReason(String userReason) {
		this.userReason = userReason;
	}

	private String userReason;
}
