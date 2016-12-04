package com.xeq.file.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gene.utils.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ssh.xep.entity.JobInfo;
import com.xeq.file.service.JobsService;

@Namespace("/")
@ParentPackage("struts-default")
@Controller("jobListAction")
@Scope("prototype")
public class JobListAction extends ActionSupport implements SessionAware, ModelDriven<JobInfo>, Preparable {
	private static final long serialVersionUID = -8335181902779435263L;
	private static Logger logger = Logger.getLogger(JobListAction.class);
	private Map<String, Object> session;
	@Autowired
	private JobsService jobsService;
	// @Autowired
	// private JobInfo jobInfo;
	private Integer id;

	@Action(value = "jobsList", results = { @Result(name = "success", location = "/jobInfoList/JobInfoList.jsp"),
			@Result(name = "input", location = "login.jsp") })
	public String getJobs() {
		logger.info("-----------------------查询用户的所有Job------------------------");
		User user = (User) session.get("user");
		if (user == null) {
			return INPUT;
		}
		int userId = user.getId();
		String hql = "FROM JobInfo where userId=" + userId;
		List<JobInfo> jList = jobsService.getJobList(hql);
		for (JobInfo jobInfo : jList) {
			try {
				jobInfo.setProcessInfo(URLDecoder.decode(jobInfo.getProcessInfo(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		session.put("jList", jList);
		return SUCCESS;
	}

	@Action(value = "jobInfo", results = { @Result(name = "success", location = "/jobInfoList/jobStep.jsp"),
			@Result(name = "input", location = "login.jsp") })
	public String getJobsInfo() {
		logger.info("-----------------------查询JOB的详细信息------------------------");
		User user = (User) session.get("user");
		if (user == null) {
			return INPUT;
		}
		int userId = user.getId();
		logger.info("id=" + id);
		JobInfo jf = jobsService.getJobInfo(id);
		String processInfo = jf.getProcessInfo();
		logger.info("process============================" + processInfo);
		session.put("jobStep", jf);
		return SUCCESS;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public JobsService getJobsService() {
		return jobsService;
	}

	public void setJobsService(JobsService jobsService) {
		this.jobsService = jobsService;
	}

	@Override
	public void prepare() throws Exception {
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	private JobInfo model;

	@Override
	public JobInfo getModel() {
		return model;
	}

}
