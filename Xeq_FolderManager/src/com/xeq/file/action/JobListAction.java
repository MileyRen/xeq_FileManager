package com.xeq.file.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gene.utils.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.ssh.xep.entity.JobInfo;
import com.xeq.file.domain.JobCss;
import com.xeq.file.domain.JobStep;
import com.xeq.file.service.JobsService;

@Namespace("/")
@ParentPackage("struts-default")
@Controller("jobListAction")
@Scope("prototype")
public class JobListAction extends ActionSupport
		implements SessionAware, ServletRequestAware, ModelDriven<JobInfo>, Preparable {
	private static final long serialVersionUID = -8335181902779435263L;
	private static Logger logger = Logger.getLogger(JobListAction.class);
	private Map<String, Object> session;
	private HttpServletRequest request;
	@Autowired
	private JobsService jobsService;
	private Integer id;
	private JobInfo jobInfo;

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
		String createTime = request.getParameter("createTime");
		long fTime = jobsService.getTime(request.getParameter("fTime"));
		long tTime = jobsService.getTime(request.getParameter("tTime"));
		String sort = request.getParameter("sort");
		String sortByTime = request.getParameter("sortByTime");
		String sortDA = request.getParameter("sortDA");

		StringBuffer sf = new StringBuffer();
		sf.append(hql);

		if (jobInfo.getStatus() != null && !jobInfo.getStatus().equals("ALL")) {
			sf.append(jobInfo.getStatus());
		}
		if (createTime != null && createTime.equals("select")) {
			sf.append(" and bgTime between " + fTime + " and " + tTime + " ");
		}
		if (sort != null && sort.equals("select")) {
			sf.append(sortByTime + sortDA);
		}
		String HQL = sf.toString();

		logger.info("hql=="+HQL);
		List<JobInfo> jList = jobsService.getJobList(HQL);
		List<JobCss> jcList = jobsService.getJobCss(jList);

		for (JobInfo jobInfo : jList) {
			try {
				jobInfo.setProcessInfo(URLDecoder.decode(jobInfo.getProcessInfo(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		session.put("jcList", jcList);
		session.put("jList", jList);
		return SUCCESS;
	}

	@Action(value = "jobInfo", results = { @Result(name = "success", location = "/jobInfoList/jobStep.jsp"),
			@Result(name = "input", location = "login.jsp"),
			@Result(name = "noStart", location = "/jobInfoList/jobStep.jsp") })
	public String getJobsInfo() {
		logger.info("-----------------------查询JOB的详细信息------------------------");
		User user = (User) session.get("user");
		if (user == null) {
			return INPUT;
		}
		int userId = user.getId();
		logger.info("id=" + id);
		JobInfo jf = jobsService.getJobInfo(id);
		/** 获取processInfo内容 */
		String processInfo = jf.getProcessInfo();

		session.put("jobStep", jf);

		/***** 解析processInfo *****/
		Document document;
		try {
			document = DocumentHelper.parseText(processInfo);
			Element rootElement = document.getRootElement();
			List<JobStep> processLists = new ArrayList<JobStep>();
			processLists = jobsService.getNodes(rootElement, processLists);
			for (JobStep jobStep : processLists) {
				System.out.println(jobStep.toString());
			}
			session.put("processInfo", processLists);
		} catch (DocumentException e) {
			logger.info("processInfo为空。。。。。。。");
			session.put("processInfo", null);
			return "noStart";
		}
		/***** 解析processInfo结束 *****/

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

	public void setJobInfo(JobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}

	public JobInfo getJobInfo() {
		return jobInfo;
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

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
