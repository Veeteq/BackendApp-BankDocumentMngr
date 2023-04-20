package com.veeteq.finance.bankdocument.dto;

import java.util.List;

public class PageResponse<T> {

    private List<T> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public List<T> getContent() {
        return content;
    }

    public PageResponse<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public PageResponse<T> setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageResponse<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public PageResponse<T> setTotalElements(long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public PageResponse<T> setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public boolean isLast() {
        return last;
    }

    public PageResponse<T> setLast(boolean last) {
        this.last = last;
        return this;
    }

}
