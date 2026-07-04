package com.lc.model.Param;

import lombok.Data;

@Data
public class GoodsSearchParam {
    private String searchTxt;
    private Integer pageNum;
    private Integer pageSize;
}
