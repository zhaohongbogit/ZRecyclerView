package org.zhzephi.recycler.page;

/**
 * Created by ZHZEPHI on 2015/10/16.
 */
public class Page {

    private int pageIndex = 1;

    public int getFirstPage() {
        return pageIndex = 1;
    }

    public int getNextPage() {
        return pageIndex+1;
    }

    public void setPageIndex() {
        pageIndex++;
    }
}
