package com.xeq.file.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ssh.xep.entity.JobInfo;
import com.xeq.file.dao.JobsDao;

@Repository("jobsDaoImpl")
public class JobsDaoImpl extends BaseDao implements JobsDao {

	@Override
	public List<JobInfo> getJobList(String hql) {
		Query query = getSession().createQuery(hql);
		List<JobInfo> list = query.list();
		return list;
	}

	@Override
	public JobInfo getJobInfo(Integer id) {
		String hql = "FROM JobInfo WHERE id=" + id;
		Query query = getSession().createQuery(hql);
		JobInfo jobInfo = (JobInfo) query.uniqueResult();
		return jobInfo;
	}

}
