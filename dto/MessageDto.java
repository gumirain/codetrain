package jp.kelonos.dto;

import java.util.Date;

public class MessageDto {
	private int messageId;
	private String messageTitle;
	private String messageBody;
	private String repBody;
	private String repRepBody;
	private int categoryId;
	private int courseId;
	private int userId;
	private int comUserId;
	
	public int getComUserId() {
		return comUserId;
	}

	public void setComUserId(int comUserId) {
		this.comUserId = comUserId;
	}

	private int adUserId;
	private Date messageUpdateTime;
	private Date messageStartTime;

	public Date getMessageStartTime() {
		return messageStartTime;
	}

	public void setMessageStartTime(Date messageStartTime) {
		this.messageStartTime = messageStartTime;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getRepBody() {
		return repBody;
	}

	public void setRepBody(String repBody) {
		this.repBody = repBody;
	}

	public String getRepRepBody() {
		return repRepBody;
	}

	public void setRepRepBody(String repRepBody) {
		this.repRepBody = repRepBody;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAdUserId() {
		return adUserId;
	}

	public void setAdUserId(int adUserId) {
		this.adUserId = adUserId;
	}

	public Date getMessageUpdateTime() {
		return messageUpdateTime;
	}

	public void setMessageUpdateTime(Date messageUpdateTime) {
		this.messageUpdateTime = messageUpdateTime;
	}
}
