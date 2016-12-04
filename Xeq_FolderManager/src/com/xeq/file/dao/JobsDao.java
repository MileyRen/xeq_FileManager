package com.xeq.file.dao;

import java.util.List;

import com.ssh.xep.entity.JobInfo;

/** 作业查询Dao */
public interface JobsDao {

	/** 查询JobInfo信息 */
	List<JobInfo> getJobList(String hql);

	/** 查询该Id对应的job */
	JobInfo getJobInfo(Integer id);
}
