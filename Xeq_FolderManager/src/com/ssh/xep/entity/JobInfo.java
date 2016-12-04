package com.ssh.xep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component("jobInfo")
@Entity
@Table(name = "jobInfo", catalog = "xep")
public class JobInfo implements Serializable {

	private static final long serialVersionUID = -1971978444096079412L;

	private int id;
	private int userId;
	private int flowBasicInfoId;
	private String bpmn;
	private String processInfo;
	private long bgTime;
	private long edTime;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (bgTime == 0 && edTime == 0) {
			this.status = "Pending...";
		} else if (bgTime != 0 && edTime == 0) {
			this.status = "Running or Error";

		} else if (bgTime != 0 && edTime != 0) {
			this.status = "success";
		}
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "userId", nullable = false)
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "flowBasicInfoId", nullable = false)
	public int getFlowBasicInfoId() {
		return flowBasicInfoId;
	}

	public void setFlowBasicInfoId(int flowBasicInfoId) {
		this.flowBasicInfoId = flowBasicInfoId;
	}

	@Column(name = "bpmn")
	public String getBpmn() {
		return bpmn;
	}

	public void setBpmn(String bpmn) {
		this.bpmn = bpmn;
	}

	@Column(name = "processInfo")
	public String getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(String processInfo) {
		this.processInfo = processInfo;
	}

	@Column(name = "bgTime")
	public long getBgTime() {
		return bgTime;
	}

	public void setBgTime(long bgTime) {
		this.bgTime = bgTime;
	}

	@Column(name = "edTime")
	public long getEdTime() {
		return edTime;
	}

	public void setEdTime(long edTime) {
		this.edTime = edTime;
	}
}
