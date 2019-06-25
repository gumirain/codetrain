package jp.kelonos.dto;


import java.util.Date;


public class ProgressDto {
	private int progressId;
	private int userId;
	private int articleId;
	private int courseId;
	private Date progressGeneTime;
	private Date progressUpdateTime;
	private String courseName;
	private String articleTitle;


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

	public int getProgressId() {
		return progressId;
	}

	public void setProgressId(int progressId) {
		this.progressId = progressId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public Date getProgressGeneTime() {
		return progressGeneTime;
	}

	public void setProgressGeneTime(Date progressGeneTime) {
		this.progressGeneTime = progressGeneTime;
	}

	public Date getProgressUpdateTime() {
		return progressUpdateTime;
	}

	public void setProgressUpdateTime(Date progressUpdateTime) {
		this.progressUpdateTime = progressUpdateTime;
	}

}
