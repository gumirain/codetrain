package jp.kelonos.dto;

import java.util.Date;

public class TxtDto {
	private int txtId;
	private String txtBody;
	private String articleTitle;
    private String courseName;
	private Date txtUpdateTime;
	private int categoryId;
	private int courseId;
	private int articleId;
	private String categoryName;
	
	
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getTxtId() {
		return txtId;
	}

	public void setTxtId(int txtId) {
		this.txtId = txtId;
	}

	public String getTxtBody() {
		return txtBody;
	}

	public void setTxtBody(String txtBody) {
		this.txtBody = txtBody;
	}

	public Date getTxtUpdateTime() {
		return txtUpdateTime;
	}

	public void setTxtUpdateTime(Date txtUpdateTime) {
		this.txtUpdateTime = txtUpdateTime;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	

}
