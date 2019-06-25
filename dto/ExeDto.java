package jp.kelonos.dto;

import java.util.Date;

public class ExeDto {
	private int exeId;
	private String exeBody;
	private int categoryId;
	private String categoryName;
	private Date exeUpdateTime;
	private String articleTitle;
    private String courseName;
    
    
	
	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Date getExeUpdateTime() {
		return exeUpdateTime;
	}

	public void setExeUpdateTime(Date exeUpdateTime) {
		this.exeUpdateTime = exeUpdateTime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	private int courseId;
	private int articleId;

	public int getExeId() {
		return exeId;
	}

	public void setExeId(int exeId) {
		this.exeId = exeId;
	}

	public String getExeBody() {
		return exeBody;
	}

	public void setExeBody(String exeBody) {
		this.exeBody = exeBody;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

}
