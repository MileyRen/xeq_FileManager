package com.xeq.file.domain;

import java.text.SimpleDateFormat;

public class JobStep {
	private String id;
	private String name;
	private String ret;
	private String pid;
	private String bgTime;
	private String edTime;
	private String state;

	//样式
	private String css;
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getBgTime() {
		return bgTime;
	}

	public void setBgTime(String bgTime) {
		SimpleDateFormat ss = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		this.bgTime = ss.format(Long.parseLong(bgTime));
	}

	public String getEdTime() {
		return edTime;
	}

	public void setEdTime(String edTime) {
		SimpleDateFormat ss = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		this.edTime = ss.format(Long.parseLong(edTime));
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public JobStep() {
		super();
	}

	public JobStep(String id, String name, String ret, String pid, String bgTime, String edTime, String state,
			String css) {
		super();
		this.id = id;
		this.name = name;
		this.ret = ret;
		this.pid = pid;
		this.bgTime = bgTime;
		this.edTime = edTime;
		this.state = state;
		this.css = css;
	}

	@Override
	public String toString() {
		return "JobStep [id=" + id + ", name=" + name + ", ret=" + ret + ", pid=" + pid + ", bgTime=" + bgTime
				+ ", edTime=" + edTime + ", state=" + state + "]";
	}

}
