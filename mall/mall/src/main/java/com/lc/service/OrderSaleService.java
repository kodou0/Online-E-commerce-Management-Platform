package com.lc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.model.entity.OrderInfo;
import com.lc.model.vo.OrderPeriodOfTimeAmount;
import com.lc.model.vo.OrderPeriodOfTimeSale;
import com.lc.model.vo.OrderPeriodOfTimeSum;

import java.util.Date;
import java.util.List;

public interface OrderSaleService extends IService<OrderInfo>
{
    Long OrderAmountDay()throws Exception;
    Long OrderAmountYes()throws Exception;
    Long OrderAmountMon()throws Exception;
    int OrderAmountWeek()throws Exception;
    String MonthOnMonthAmount()throws Exception;
    String WeekOnWeekAmount() throws Exception;

    List<OrderPeriodOfTimeAmount> OrderAmountPeriodOfTime(Date BeginTime, Date EndTime)throws Exception;



    double OrderSumDay()throws Exception;
    double OrderSumYes()throws Exception;
    double OrderSumMon()throws Exception;
    double OrderSumWeek()throws Exception;
    String MonthOnMonthSum()throws Exception;
    String WeekOnWeekSum() throws Exception;

    List<OrderPeriodOfTimeSum> OrderSumPeriodOfTime(Date BeginTime, Date EndTime)throws Exception;


    int OrderSaleDay(int id) throws Exception;
    int OrderSaleYes(int id) throws Exception;
    int OrderSaleMon(int id) throws Exception;
    int OrderSaleWeek(int id) throws Exception;
    String MonthOnMonthSale(int id)throws Exception;
    String WeekOnWeekSale(int id) throws Exception;
    int OrderSaleAll() throws Exception;
    List<OrderPeriodOfTimeSale> OrderSalePeriodOfTime(int id, Date BeginTime, Date EndTime)throws Exception;









}
