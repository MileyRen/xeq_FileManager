package com.xeq.file.service;

import java.util.List;

import com.ssh.xep.entity.JobInfo;

public interface JobsService {
	/** 查询JobInfo信息 */
	List<JobInfo> getJobList(String hql);

	/** 查询该Id对应的job */
	JobInfo getJobInfo(Integer id);
}
