package com.lc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lc.model.entity.Goods;
import com.lc.model.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    IPage<GoodsVo> selectAllGoods(IPage<GoodsVo> page);

    IPage<GoodsVo> queryGoods(IPage<GoodsVo> page, @Param("searchTxt")String searchTxt);

}
