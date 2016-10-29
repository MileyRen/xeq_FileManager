package com.xeq.file.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;

//@Entity
//@Table(name = "file_and_folder")
public class FileAndFolder_old implements java.io.Serializable {
	private static final long serialVersionUID = 577049976649031828L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "parentFolderId", nullable = false)
	private Integer parentFolderId;

	@Column(name = "name", nullable = false, length = 225)
	private String name;

	// @Temporal(TemporalType.DATE)
	@Column(name = "time", nullable = false)
	private Date time;

	@Column(name = "size", nullable = false, length = 225)
	private String size;

	@Column(name = "userId", nullable = false)
	private Integer userId;

	@Column(name = "type", nullable = false, length = 225)
	private String type;

	@Column(name = "folderPath", nullable = false, length = 16777216)
	private String folderPath;

	// 级联删除标识
	//@ManyToOne(cascade = CascadeType.REMOVE, optional = false)
	//@JoinColumn(name = "deleteFlag", referencedColumnName = "id")
	//private FileAndFolder deleteFlag;

//	@OneToMany(mappedBy = "file_and_folder", cascade = CascadeType.REMOVE)
	//private Set<FileAndFolder> deleteFlagSets = new HashSet<FileAndFolder>();

	// 映射的真实路径
	//@Column(name = "mappingPath", nullable = true, length = 225)
//	private String mappingPath;

	public FileAndFolder_old() {
		super();
	}

	public FileAndFolder_old(Integer id, Integer parentFolderId, String name, Date time, String size, Integer userId,
			String type, String folderPath) {
		super();
		this.id = id;
		this.parentFolderId = parentFolderId;
		this.name = name;
		this.time = time;
		this.size = size;
		this.userId = userId;
		this.type = type;
		this.folderPath = folderPath;
	//	this.deleteFlag = deleteFlag;
	//	this.deleteFlagSets = deleteFilags;
	//	this.mappingPath=mappingPath;
	}



	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(Integer parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
