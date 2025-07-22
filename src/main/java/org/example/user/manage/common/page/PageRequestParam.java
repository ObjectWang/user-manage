package org.example.user.manage.common.page;

import lombok.Data;

@Data
public class PageRequestParam {
    protected Integer pageSize;
    protected Integer pageNum;

    public void check(){
        if(this.pageNum == null || this.getPageNum() < 0){
            setPageNum(1);
        }
        if(this.pageSize == null || this.getPageSize() < 0){
            setPageSize(10);
        }
    }
}
