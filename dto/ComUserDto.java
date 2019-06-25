package jp.kelonos.dto;

import java.util.Date;

public class ComUserDto {
	private int comUserId;
	private String comUserName;
	private String comUserEmail;
	private String comUserPassword;
	private String comUserResPosition;
	private Date comUserUpdateTime;
	private int companyId;
	private int comUserDelFlg;

	public int getComUserDelFlg() {
		return comUserDelFlg;
	}

	public void setComUserDelFlg(int comUserDelFlg) {
		this.comUserDelFlg = comUserDelFlg;
	}

	public int getComUserId() {
		return comUserId;
	}

	public void setComUserId(int comUserId) {
		this.comUserId = comUserId;
	}

	public String getComUserName() {
		return comUserName;
	}

	public void setComUserName(String comUserName) {
		this.comUserName = comUserName;
	}

	public String getComUserEmail() {
		return comUserEmail;
	}

	public void setComUserEmail(String comUserEmail) {
		this.comUserEmail = comUserEmail;
	}

	public String getComUserPassword() {
		return comUserPassword;
	}

	public void setComUserPassword(String comUserPassword) {
		this.comUserPassword = comUserPassword;
	}

	public String getComUserResPosition() {
		return comUserResPosition;
	}

	public void setComUserResPosition(String comUserResPosition) {
		this.comUserResPosition = comUserResPosition;
	}

	public Date getComUserUpdateTime() {
		return comUserUpdateTime;
	}

	public void setComUserUpdateTime(Date comUserUpdateTime) {
		this.comUserUpdateTime = comUserUpdateTime;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
}
