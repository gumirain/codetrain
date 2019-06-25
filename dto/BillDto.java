package jp.kelonos.dto;

public class BillDto {

	private int billId;
	private int billNormal;
	private int billRest;
	private int billTotal;
	private int companyId;
	private String billDate;
	public int getBillId() {
		return billId;
	}
	public int setBillId(int billId) {
		return this.billId = billId;
	}
	public int getBillNormal() {
		return billNormal;
	}
	public void setBillNormal(int billNormal) {
		this.billNormal = billNormal;
	}
	public int getBillRest() {
		return billRest;
	}
	public void setBillRest(int billRest) {
		this.billRest = billRest;
	}
	public int getBillTotal() {
		return billTotal;
	}
	public void setBillTotal(int billTotal) {
		this.billTotal = billTotal;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	
}

