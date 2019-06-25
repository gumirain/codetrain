package jp.kelonos.dto;

import java.util.Date;

public class CompanyDto {

	private int companyId;
	private String companyName;
	private String companyDomain;
	private String companyBillingAdd;
	private Date companyUpdateTime;
	private String companyComuser;
	private String companyEmail;
	private int companyDelFlg;
	
	public int getCompanyDelFlg() {
		return companyDelFlg;
	}
	public void setCompanyDelFlg(int companyDelFlg) {
		this.companyDelFlg = companyDelFlg;
	}
	private String comComuserResPosi;
	
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyDomain() {
		return companyDomain;
	}
	public void setCompanyDomain(String companyDomain) {
		this.companyDomain = companyDomain;
	}
	public String getCompanyBillingAdd() {
		return companyBillingAdd;
	}
	public void setCompanyBillingAdd(String companyBillingAdd) {
		this.companyBillingAdd = companyBillingAdd;
	}
	public Date getCompanyUpdateTime() {
		return companyUpdateTime;
	}
	public void setCompanyUpdateTime(Date companyUpdateTime) {
		this.companyUpdateTime = companyUpdateTime;
	}
	public String getCompanyComuser() {
		return companyComuser;
	}
	public void setCompanyComuser(String companyComuser) {
		this.companyComuser = companyComuser;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	public String getComComuserResPosi() {
		return comComuserResPosi;
	}
	public void setComComuserResPosi(String comComuserResPosi) {
		this.comComuserResPosi = comComuserResPosi;
	}	
}
