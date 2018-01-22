package py.org.fundacionparaguaya.pspserver.common.pagination;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * 
 * @author bsandoval
 *
 * @param <T>
 */
public class PaginableList<T> {

	public PaginableList() {

	}
	
	public PaginableList(List<T> list) {
		this.list = list;
	}
	
	public PaginableList(Page<?> page, List<T> list) {
		this.list = list;
		this.totalPages = page.getTotalPages();
		this.totalRecords = page.getTotalElements();
		this.pageSize = page.getSize();
		// number of pages in spring is from 0 so we add 1
		this.currentPage = page.getNumber()+1;
	}

	public PaginableList(PaginableList<?> page, List<T> list) {
		this.list = list;
		this.totalPages = page.getTotalPages();
		this.totalRecords = page.getTotalRecords();
		this.currentPage = page.getCurrentPage();
		this.pageSize = page.getPageSize();
	}

	private int totalPages;
	private long totalRecords;
	private int currentPage;
	private int pageSize;

	private List<T> list;

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
