package com.example.ejb.model;

import java.util.List;

public class Page<T> {

    private List<T> content;
    private int pageNumber;
    private boolean first;
    private boolean last;
    private int pageSize;
    private int totalPages;
    private int totalElements;

    public Page() {
    }

    public Page(List<T> content, int pageNumber, boolean first, boolean last, int pageSize, int totalPages, int totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.first = first;
        this.last = last;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
