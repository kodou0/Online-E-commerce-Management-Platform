package com.lc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.model.entity.OrderInfo;
import com.lc.model.vo.OrderSaleVo;
import com.lc.model.vo.OrderVo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderSaleMapper extends BaseMapper<OrderInfo>
{
    List<OrderSaleVo> SelectAllOrderInfo();
    Double GetSale(int id,int OrderId);

    List<OrderSaleVo> SelectAllOrderInfoWeek(Date BeginWeek,Date EndWeek);

    List<OrderSaleVo> SelectAllOrderInfoPeriodOfTime(Date BeginTime,Date EndTime);

    List<OrderVo> SelectAllOrderSaleWeek(Date BeginWeek,Date EndWeek);



}
