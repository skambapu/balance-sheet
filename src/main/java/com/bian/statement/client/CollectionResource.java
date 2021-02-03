package com.bian.statement.client;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CollectionResource<T> {

    public static final CollectionResource EMPTY_COLLECTION;
    private List<T> content;
    private int count;
    private long total;

    public CollectionResource() {
        this.content = new LinkedList();
    }

    public CollectionResource(List<T> content, int count, long total) {
        this.content = content;
        this.count = count;
        this.total = total;
    }

    public List<T> getContent() {
        return this.content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    static {
        EMPTY_COLLECTION = new CollectionResource(Collections.EMPTY_LIST, 0, 0L);
    }
}
