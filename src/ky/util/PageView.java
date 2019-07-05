package ky.util;

import java.util.List;

public class PageView {

    // 用户指定/配置
    private int pageNum;// 当前页
    private int pageSize;// 每页显示的记录数量
    private Object searchBean;  //设置查询对象
    private String order;//排序的顺序(asc,desc)
    private String sort;//排序的字段


    // 从数据库中查询
    private int recordCount;// 总记录数
    private List recordList;// 本页的数据列表

    private int pageCount;// 总页数


    //传给dao包进行查询的

    public PageView(int pageNum, int pageSize, String order, String sort, Object searchBean) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.order = order;
        this.sort = isAcronym(sort);
        this.searchBean = searchBean;
    }

    //返给界面
    public PageView(int recordCount, List recordList) {
        this.recordCount = recordCount;
        this.recordList = recordList;
    }


    //转换前台传来的排序字段和数据库一致
    public String isAcronym(String word) {
        String sort = null;
        if (word != null) {
            sort = "";
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (Character.isUpperCase(c)) {
                    sort += ("_" + (c + "").toLowerCase());
                } else {
                    sort += c;
                }
            }
        }
        return sort;
    }


    public Object getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(Object searchBean) {
        this.searchBean = searchBean;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public List getRecordList() {
        return recordList;
    }

    public void setRecordList(List recordList) {
        this.recordList = recordList;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


}
