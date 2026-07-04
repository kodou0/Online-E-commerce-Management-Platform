package com.lc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.model.Param.GoodsSearchParam;
import com.lc.model.Param.UploadGoodsPicParam;
import com.lc.model.entity.Goods;
import com.lc.model.vo.GoodsVo;

public interface GoodsService {

    Page<GoodsVo> getAllGoods(Integer pageNum, Integer pageSize);

    Page<GoodsVo> queryGoods(GoodsSearchParam goodsSearchParam);

    Goods uploadPic(UploadGoodsPicParam uploadGoodsPicParam);





}
