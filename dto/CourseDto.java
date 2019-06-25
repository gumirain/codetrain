package jp.kelonos.dto;


import java.util.Date;

public class CourseDto {
	private int courseId;
	private String courseName;
	private String courseProperty;
	private String coursePremise;
	private String courseGoal;
	private String courseTime;
	private int categoryId;
	private Date courseUpdateTime;
	private String categoryName;
	private int progress;
	private int courseFreeFlag;

	

	public int getCourseFreeFlag() {
		return courseFreeFlag;
	}

	public void setCourseFreeFlag(int courseFreeFlag) {
		this.courseFreeFlag = courseFreeFlag;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseProperty() {
		return courseProperty;
	}

	public void setCourseProperty(String courseProperty) {
		this.courseProperty = courseProperty;
	}

	public String getCoursePremise() {
		return coursePremise;
	}

	public void setCoursePremise(String coursePremise) {
		this.coursePremise = coursePremise;
	}

	public String getCourseGoal() {
		return courseGoal;
	}

	public void setCourseGoal(String courseGoal) {
		this.courseGoal = courseGoal;
	}

	public String getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Date getCourseUpdateTime() {
		return courseUpdateTime;
	}

	public void setCourseUpdateTime(Date courseUpdateTime) {
		this.courseUpdateTime = courseUpdateTime;
	}

}
