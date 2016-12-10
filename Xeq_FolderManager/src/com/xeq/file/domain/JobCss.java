package com.xeq.file.domain;

import java.text.SimpleDateFormat;

public class JobCss {
	private int id;
	private int userId;
	private int flowBasicInfoId;
	private String bpmn;
	private String processInfo;
	private String bgTime;
	private String edTime;
	private String status;

	// 样式
	private String css;
	private String label;

	public JobCss() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFlowBasicInfoId() {
		return flowBasicInfoId;
	}

	public void setFlowBasicInfoId(int flowBasicInfoId) {
		this.flowBasicInfoId = flowBasicInfoId;
	}

	public String getBpmn() {
		return bpmn;
	}

	public void setBpmn(String bpmn) {
		this.bpmn = bpmn;
	}

	public String getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(String processInfo) {
		this.processInfo = processInfo;
	}

	public String getBgTime() {
		return bgTime;
	}

	public void setBgTime(String bgTime) {
		if (bgTime == "0" || bgTime.equals("0")) {
			this.bgTime = "";
		} else {
			SimpleDateFormat ss = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
			this.bgTime = ss.format(Long.parseLong(bgTime));
		}

	}

	public String getEdTime() {
		return edTime;
	}

	public void setEdTime(String edTime) {
		if (edTime == "0" || edTime.equals("0")) {
			this.edTime = "";
		} else {
			SimpleDateFormat ss = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
			this.edTime = ss.format(Long.parseLong(edTime));
		}

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
