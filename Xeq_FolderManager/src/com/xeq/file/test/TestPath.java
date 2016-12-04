package com.xeq.file.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ssh.xep.entity.JobInfo;
import com.xeq.file.dao.FolderOperate;
import com.xeq.file.domain.FileAndFolder;
import com.xeq.file.service.FolderService;
import com.xeq.file.service.JobsService;

public class TestPath {
	private FolderService folderService;
	private FolderOperate folderOperate;
	private JobsService jobsService;
	ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	@Test
	public void testP() {
		folderService = (FolderService) context.getBean("FolderService");
		List<FileAndFolder> lists = new ArrayList<FileAndFolder>();
		lists = folderService.getAll("From FileAndFolder where userId=1");
		for (FileAndFolder fileAndFolder : lists) {
			System.out.println(
					fileAndFolder.getId() + ";" + fileAndFolder.getName() + ";" + fileAndFolder.getParentFolderId());
		}
	}

	@Test
	public void test() {
		jobsService = (JobsService) context.getBean("jobService");
		String hql = "FROM JobInfo where userId=1";
		List<JobInfo> jList = jobsService.getJobList(hql);
		for (JobInfo jobInfo : jList) {
			System.out.println(jobInfo.getId() + "," + jobInfo.getBgTime());
		}
	}
}
