package ky.util;

import java.util.List;

public class PageViewSum {
    private int pageNum;
    private int pageSize;
    private Object searchBean;
    private String order;
    private String sort;
    private int recordCount;
    private List recordList;
    private List recordSumList;
    private int pageCount;

    public PageViewSum() {
    }

    public PageViewSum(int pageNum, int pageSize, String order, String sort,
                       Object searchBean) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.order = order;
        this.sort = isAcronym(sort);
        this.searchBean = searchBean;
    }

    public PageViewSum(int recordCount, List recordList, List recordSumList) {
        this.recordCount = recordCount;
        this.recordList = recordList;
        this.recordSumList = recordSumList;
    }

    public String isAcronym(String word) {
        String sort = null;
        if (word != null) {
            sort = "";
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (Character.isUpperCase(c)) {
                    sort = sort
                            + "_"
                            + new StringBuilder(String.valueOf(c)).toString()
                            .toLowerCase();
                } else
                    sort = sort + c;
            }
        }

        return sort;
    }

    public List getRecordSumList() {
        return recordSumList;
    }

    public void setRecordSumList(List recordSumList) {
        this.recordSumList = recordSumList;
    }

    public Object getSearchBean() {
        return this.searchBean;
    }

    public void setSearchBean(Object searchBean) {
        this.searchBean = searchBean;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public List getRecordList() {
        return this.recordList;
    }

    public void setRecordList(List recordList) {
        this.recordList = recordList;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}

/*
 * Location: C:\Users\Administrator\Desktop\ky_pms\WEB-INF\classes\ Qualified
 * Name: ky.util.PageView JD-Core Version: 0.6.0
 */