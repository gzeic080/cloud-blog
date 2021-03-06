package cn.xiaojunyun.cloud.blog.data.page;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author:萧竣匀
 * @desc:分页实体类
 * @createTime:2018年5月28日上午9:43:39
 */
public class Page<E> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4054624457276530231L;
    /**
     * 当前页
     */
    private int pageCurrent=1;
    /**
     * 页面大小
     */
    private int pageSize=20;
    /**
     * 总条数
     */
    private int total=0;
    /**
     * 总页数
     */
    private int pageCount;
    /**
     * 数据
     */
    private List<E> rows;

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<E> getRows() {
        return rows;
    }

    public void setRows(List<E> rows) {
        this.rows = rows;
    }

}
