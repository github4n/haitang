package com.bmn.haitang.demo.pig.vo;

import java.util.List;

/**
 * @author zhangyuqiang02@playcrab.com
 * @date 2019/6/26
 */
public class PageVO {

    private int total;
    private List<?> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
