package jp.kelonos.dto;

import java.util.Date;

public class CategoryDto {
	private int categoryId;
	private String categoryName;
	private Date categoryUpdateTime;
	private int progressRate;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getCategoryUpdateTime() {
		return categoryUpdateTime;
	}

	public void setCategoryUpdateTime(Date categoryUpdateTime) {
		this.categoryUpdateTime = categoryUpdateTime;
	}

	public int getProgressRate() {
		return progressRate;
	}

	public void setProgressRate(int progressRate) {
		this.progressRate = progressRate;
	}
	
}
