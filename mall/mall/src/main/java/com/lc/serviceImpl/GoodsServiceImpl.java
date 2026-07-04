package com.lc.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.mapper.GoodsMapper;
import com.lc.mapper.GoodsTypeMapper;
import com.lc.model.Param.GoodsSearchParam;
import com.lc.model.Param.UploadGoodsPicParam;
import com.lc.model.entity.Goods;
import com.lc.model.vo.GoodsVo;
import com.lc.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public Page<GoodsVo> getAllGoods(Integer pageNum, Integer pageSize) {
        Page<GoodsVo> page = new Page<>(pageNum,pageSize);
        IPage<GoodsVo> goodsVoIPage = goodsMapper.selectAllGoods(page);
        return (Page<GoodsVo>) goodsVoIPage;
    }

    @Override
    public Page<GoodsVo> queryGoods(GoodsSearchParam goodsSearchParam) {
        Page<GoodsVo> page = new Page<>(goodsSearchParam.getPageNum(),goodsSearchParam.getPageSize());
        IPage<GoodsVo> goodsVoIPage = goodsMapper.queryGoods(page, goodsSearchParam.getSearchTxt());
        return (Page<GoodsVo>) goodsVoIPage;
    }

    @Override
    public Goods uploadPic(UploadGoodsPicParam uploadGoodsPicParam) {
        Goods goods = goodsMapper.selectById(uploadGoodsPicParam.getGoodsId());
        if (!Objects.isNull(goods)) {
            goods.setImg(uploadGoodsPicParam.getImgBase64());
            if (goodsMapper.updateById(goods)==0) throw new RuntimeException("上传失败！");
            else return goods;
        }
        else throw new RuntimeException("不存在该商品!");
    }

}
