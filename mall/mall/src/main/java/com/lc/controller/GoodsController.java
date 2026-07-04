package com.lc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.common.result.Result;
import com.lc.mapper.GoodsMapper;
import com.lc.mapper.GoodsTypeMapper;
import com.lc.model.Param.GoodsSearchParam;
import com.lc.model.Param.UploadGoodsPicParam;
import com.lc.model.entity.Goods;
import com.lc.model.entity.GoodsType;
import com.lc.model.vo.GoodsVo;
import com.lc.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 商品管理
     */

    @GetMapping("/getAllGoods")
    public Result getAllGoods(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    ){
        return Result.ok(goodsService.getAllGoods(pageNum,pageSize));
    }

    @PostMapping("/updateGoods")
    public Result updateGoods(@RequestBody Goods goods){
        if (goodsMapper.updateById(goods)==1){
            return Result.ok();
        }
        else return Result.error("更新失败");
    }

    @PostMapping("/addGoods")
    public Result addGoods(@RequestBody Goods goods){
        if (goodsMapper.insert(goods)==1){
            return Result.created();
        }
        else return Result.error("添加失败");
    }

    @DeleteMapping("/deleteGoods")
    public Result deleteGoods(@RequestParam("id")int id){
        if (goodsMapper.deleteById(id)==1){
            return Result.ok();
        }
        else return Result.error("删除失败");
    }

    @PostMapping("/uploadGoodsPic")
    public Result uploadGoodsPic(@RequestBody UploadGoodsPicParam uploadGoodsPicParam){
        try {
            Goods goods = goodsService.uploadPic(uploadGoodsPicParam);
            return Result.ok(goods);
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/queryGoods")
    public Result queryGoods(@RequestBody GoodsSearchParam goodsSearchParam){
        Page<GoodsVo> page = goodsService.queryGoods(goodsSearchParam);
        return Result.ok(page);
    }

    /**
     *  商品类型管理
     */

    @GetMapping("/getAllGoodsType")
    public Result getAllGoodsType(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize){
        Page<GoodsType> goodsTypePage = goodsTypeMapper.selectPage(new Page<>(pageNum, pageSize), null);
        return Result.ok(goodsTypePage);
    }

    @PostMapping("/addGoodsType")
    public Result addGoodsType(@RequestBody GoodsType goodsType){
        if (goodsTypeMapper.insert(goodsType)==1){
            return Result.created();
        }
        else return Result.error("添加失败");
    }

    @PostMapping("/updateGoodsType")
    public Result updateGoodsType(@RequestBody GoodsType goodsType){
        if (goodsTypeMapper.updateById(goodsType)==1){
            return Result.ok();
        }
        else return Result.error("更新失败");
    }




}
