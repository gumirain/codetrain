package jp.kelonos.dto;

public class ArticleDto {
	private int articleId;
	private int categoryId;
	private int courseId;
	private String articleTitle;
	private String categoryName;
	private String courseName;
	private int articleFreeFlag;
	private int courseFreeFlag;
	

	public int getCourseFreeFlag() {
		return courseFreeFlag;
	}

	public void setCourseFreeFlag(int courseFreeFlag) {
		this.courseFreeFlag = courseFreeFlag;
	}

	public int getArticleFreeFlag() {
		return articleFreeFlag;
	}

	public void setArticleFreeFlag(int articleFreeFlag) {
		this.articleFreeFlag = articleFreeFlag;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
