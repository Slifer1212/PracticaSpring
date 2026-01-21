package com.inventory.todoproject.domain.pagination;

public class PageRequest {

    private final int page;
    private final int size;
    private final String sortBy;
    private final String direction;

    public PageRequest(int page, int size) {
        this(page, size, null, "ASC");
    }

    public PageRequest(int page, int size, String sortBy, String direction) {
        this.page = Math.max(0, page);
        this.size = Math.min(Math.max(1, size), 10);
        this.sortBy = sortBy;
        this.direction = direction != null ? direction.toUpperCase() : "ASC";
    }

    // Getters
    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public int getOffset() {
        return page * size;
    }

}
