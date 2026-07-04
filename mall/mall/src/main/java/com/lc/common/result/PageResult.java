package com.lc.common.result;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageResult<T> {

    //结果数组集合
    private List<T> records;

    //结果总数
    private Long total;

    //页面大小
    private Long size;

    public PageResult(Page<T> data) {
        this.records = data.getRecords();
        this.total = data.getTotal();
        this.size = data.getSize();
    }
}
