package com.platform.model;

import java.util.Date;

public class StepNumHistory {
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getShouhuan_id() {
		return shouhuan_id;
	}
	public void setShouhuan_id(String shouhuan_id) {
		this.shouhuan_id = shouhuan_id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getStepNum() {
		return stepNum;
	}
	public void setStepNum(Integer stepNum) {
		this.stepNum = stepNum;
	}
	private Integer id;
	private String shouhuan_id;
	private Date time;
	private Integer stepNum;
}
