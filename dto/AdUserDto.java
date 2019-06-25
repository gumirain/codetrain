package jp.kelonos.dto;

import java.util.Date;

public class AdUserDto {
	private int adUserId;
	private String adUserName;
	private String adUserPassword;
	private int adUserAdFlg;
	private Date adUserUpdateTime;
	private int adUserDelFlg;

	public int getAdUserDelFlg() {
		return adUserDelFlg;
	}

	public void setAdUserDelFlg(int adUserDelFlg) {
		this.adUserDelFlg = adUserDelFlg;
	}

	public int getAdUserId() {
		return adUserId;
	}

	public void setAdUserId(int adUserId) {
		this.adUserId = adUserId;
	}

	public String getAdUserName() {
		return adUserName;
	}

	public void setAdUserName(String adUserName) {
		this.adUserName = adUserName;
	}

	public String getAdUserPassword() {
		return adUserPassword;
	}

	public void setAdUserPassword(String adUserPassword) {
		this.adUserPassword = adUserPassword;
	}

	public int getAdUserAdFlg() {
		return adUserAdFlg;
	}

	public void setAdUserAdFlg(int adUserAdFlg) {
		this.adUserAdFlg = adUserAdFlg;
	}

	public Date getAdUserUpdateTime() {
		return adUserUpdateTime;
	}

	public void setAdUserUpdateTime(Date adUserUpdateTime) {
		this.adUserUpdateTime = adUserUpdateTime;
	}

	public String getAdUserEmail() {
		return adUserEmail;
	}

	public void setAdUserEmail(String adUserEmail) {
		this.adUserEmail = adUserEmail;
	}

	private String adUserEmail;
}
