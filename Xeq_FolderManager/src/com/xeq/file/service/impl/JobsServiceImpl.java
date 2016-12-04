package com.xeq.file.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh.xep.entity.JobInfo;
import com.xeq.file.dao.JobsDao;
import com.xeq.file.service.JobsService;

@Service("jobsService")
public class JobsServiceImpl implements JobsService {
	@Autowired
	private JobsDao jobsDao;

	@Override
	public List<JobInfo> getJobList(String hql) {
		return jobsDao.getJobList(hql);
	}

	public JobsDao getJobsDao() {
		return jobsDao;
	}

	public void setJobsDao(JobsDao jobsDao) {
		this.jobsDao = jobsDao;
	}

	@Override
	public JobInfo getJobInfo(Integer id) {
		return jobsDao.getJobInfo(id);
	}

}
