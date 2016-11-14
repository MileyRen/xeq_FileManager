package com.xeq.file.domain;

import org.springframework.stereotype.Component;

import com.xeq.file.dao.impl.BaseDao;

/** 分页的bean */
@Component("pageSource")
public class PageSource {
	private int totalRows = 0; // 记录总数
	private int totalPages = 0; // 总页数
	private int pageSize = (new BaseDao()).pageSize(); // 每页显示数据条数，默认为10条记录
	private int currentPage = 1; // 当前页数
	private boolean hasPrevious = false; // 是否有上一页
	private boolean hasNext = false; // 是否有下一页

	public PageSource() {

	}

	public void init(int totalRows, int pageSize) {
		this.totalRows = totalRows;
		this.pageSize = pageSize;
		totalPages = (totalRows % pageSize == 0) ? (totalRows / pageSize) : (totalRows / pageSize) + 1;
		refresh(); // 刷新当前页面信息
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		refresh();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		refresh();
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
		refresh();
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		refresh();
	}

	// 跳到第一页
	public void first() {
		currentPage = 1;
		this.setHasPrevious(false);
		refresh();
	}

	// 取得上一页（重新设定当前页面即可）
	public void previous() {
		currentPage--;
		refresh();
	}

	// 取得下一页
	public void next() {
		System.out.println("next: totalPages: " + totalPages + " currentPage : " + currentPage);
		if (currentPage < totalPages) {
			currentPage++;
		}
		refresh();
	}

	// 跳到最后一页
	public void last() {
		currentPage = totalPages;
		this.setHasNext(false);
		refresh();
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public boolean isHasPrevious() {
		return hasPrevious;
	}
	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}
	// 刷新当前页面信息
	public void refresh() {
		if (totalPages <= 1) {
			hasPrevious = false;
			hasNext = false;
		} else if (currentPage == 1) {
			hasPrevious = false;
			hasNext = true;
		} else if (currentPage == totalPages) {
			hasPrevious = true;
			hasNext = false;
		} else {
			hasPrevious = true;
			hasNext = true;
		}
	}

	@Override
	public String toString() {
		return "PageSource [totalRows=" + totalRows + ", totalPages=" + totalPages + ", pageSize=" + pageSize
				+ ", currentPage=" + currentPage + ", hasPrevious=" + hasPrevious + ", hasNext=" + hasNext + "]";
	}
	
	
}