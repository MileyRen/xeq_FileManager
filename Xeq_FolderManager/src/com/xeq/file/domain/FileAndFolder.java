package com.xeq.file.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "file_and_folder")
public class FileAndFolder implements java.io.Serializable {
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

	@ManyToOne(optional = true)
	@JoinColumn(name = "deleteFlag", referencedColumnName = "id", nullable = true, unique = false)
	private FileAndFolder deleteFlag;

	@OneToMany(targetEntity = FileAndFolder.class, orphanRemoval = true)
	@JoinColumn(name = "deleteFlag")
	@Cascade(value = { CascadeType.SAVE_UPDATE, CascadeType.DELETE, CascadeType.ALL ,CascadeType.SAVE_UPDATE})
	private Set<FileAndFolder> deleteFlagSets = new HashSet<FileAndFolder>();

	/*
	 * // 级联删除标识
	 * 
	 * @ManyToOne(cascade = CascadeType.ALL, optional = true)
	 * 
	 * @JoinColumn(name = "deleteFlag", referencedColumnName =
	 * "id",nullable=true) private FileAndFolder deleteFlag;
	 * 
	 * @OneToMany(mappedBy = "id", cascade = CascadeType.ALL) private
	 * Set<FileAndFolder> deleteFlagSets = new HashSet<FileAndFolder>();
	 */

	// 映射的真实路径
	@Column(name = "mappingPath", nullable = true, length = 225)
	private String mappingPath;

	public FileAndFolder() {
		super();
	}

	public FileAndFolder(Integer id, Integer userId) {
		super();
		this.id = id;
		this.userId = userId;
	}

	public FileAndFolder(Integer id, Integer parentFolderId, String name, Date time, String size, Integer userId,
			String type, String folderPath, FileAndFolder deleteFlag, Set<FileAndFolder> deleteFlagSets,
			String mappingPath) {
		super();
		this.id = id;
		this.parentFolderId = parentFolderId;
		this.name = name;
		this.time = time;
		this.size = size;
		this.userId = userId;
		this.type = type;
		this.folderPath = folderPath;
		this.deleteFlag = deleteFlag;
		this.deleteFlagSets = deleteFlagSets;
		this.mappingPath = mappingPath;
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

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public FileAndFolder getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(FileAndFolder deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Set<FileAndFolder> getDeleteFlagSets() {
		return deleteFlagSets;
	}

	public void setDeleteFlagSets(Set<FileAndFolder> deleteFlagSets) {
		this.deleteFlagSets = deleteFlagSets;
	}

	public String getMappingPath() {
		return mappingPath;
	}

	public void setMappingPath(String mappingPath) {
		this.mappingPath = mappingPath;
	}

	@Override
	public String toString() {
		return "FileAndFolder [id=" + id + ", parentFolderId=" + parentFolderId + ", name=" + name + ", time=" + time
				+ ", size=" + size + ", userId=" + userId + ", type=" + type + ", folderPath=" + folderPath
				+ ", deleteFlag=" + deleteFlag + ", mappingPath=" + mappingPath + "]";
	}

}
