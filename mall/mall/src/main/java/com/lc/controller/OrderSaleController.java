package com.lc.controller;

import com.lc.common.result.Result;
import com.lc.service.OrderSaleService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/OrderInfo")
public class OrderSaleController
{

    @Autowired
    private OrderSaleService orderSaleService;

    /**
     *
     * @return 今日订单数
     */
    @GetMapping("/TodayAmount")
    public Result GetTodayAmount() throws Exception {
        return Result.ok(orderSaleService.OrderAmountDay());
    }

    /**
     *
     * @return 今日销售额
     */
    @GetMapping("/TodaySum")
    public Result GetTodaySum() throws Exception {
        return Result.ok(orderSaleService.OrderSumDay());
    }

    /**
     *
     * @param id 商品id
     */
    @GetMapping("/TodaySale")
    public Result GetTodaySale(@Param("id")int id) throws Exception {
        return Result.ok(orderSaleService.OrderSaleDay(id));
    }

    /**
     *
     * @return 昨日订单数
     */
    @GetMapping("/YesAmount")
    public Result GetYesAmount() throws Exception {
        return Result.ok(orderSaleService.OrderAmountYes());
    }

    @GetMapping("/YesSum")
    public Result GetYesSum() throws Exception {
        return Result.ok(orderSaleService.OrderSumYes());
    }

    @GetMapping("/YesSale")
    public Result GetYesSum(@Param("id")int id) throws Exception {
        return Result.ok(orderSaleService.OrderSaleYes(id));
    }

    /**
     *
     * @return 月订单数
     */
    @GetMapping("/MonAmount")
    public Result GetMonAmount() throws Exception {
        return Result.ok(orderSaleService.OrderAmountMon());
    }

    @GetMapping("/MonSum")
    public Result GetMonSum() throws Exception {
        return Result.ok(orderSaleService.OrderSumMon());
    }

    @GetMapping("/MonSale")
    public Result GetMonSale(@Param("id")int id) throws Exception {
        return Result.ok(orderSaleService.OrderSaleMon(id));
    }

    /**
     * 周订单数
     */
    @GetMapping("/WeekAmount")
    public Result GetWeekAmount() throws Exception {
        return Result.ok(orderSaleService.OrderAmountWeek());
    }

    @GetMapping("/WeekSum")
    public Result GetWeekSum() throws Exception {
        return Result.ok(orderSaleService.OrderSumWeek());
    }

    @GetMapping("/WeekSale")
    public Result GetWeekSale(@Param("id") int id) throws Exception {
        return Result.ok(orderSaleService.OrderSaleWeek(id));
    }

    /**
     *
     * @return 月订单数同比
     */
    @GetMapping("/MonthOnMonthAmount")
    public Result GetMonthOnMonthAmount() throws Exception {
        return Result.ok(orderSaleService.MonthOnMonthAmount());
    }

    @GetMapping("/MonthOnMonthSum")
    public Result GetMonthOnMonthSum() throws Exception {
        return Result.ok(orderSaleService.MonthOnMonthSum());
    }

    @GetMapping("/MonthOnMonthSale")
    public Result GetMonthOnMonthSale(@Param("id")int id) throws Exception {
        return Result.ok(orderSaleService.MonthOnMonthSale(id));
    }

    @GetMapping("/WeekOnWeekAmount")
    public Result GetWeekOnWeekAmount() throws Exception {
        return Result.ok(orderSaleService.WeekOnWeekAmount());
    }

    @GetMapping("/WeekOnWeekSum")
    public Result GetWeekOnWeekSum() throws Exception {
        return Result.ok(orderSaleService.WeekOnWeekSum());
    }

    @GetMapping("/WeekOnWeekSale")
    public Result GetWeekOnWeekSale(@Param("id")int id) throws Exception {
        return Result.ok(orderSaleService.WeekOnWeekSale(id));
    }

    /**
     *
     * @param begintime 开始时间
     * @param endtime 结束时间
     * @return 一段时间的订单数
     */
    @GetMapping("/PeriodOfTimeAmount")
    public Result GetPeriodOfTimeAmount( String begintime, String endtime) throws Exception {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date BeginTime=simpleDateFormat.parse(begintime);
        Date EndTime=simpleDateFormat.parse(endtime);
        return Result.ok(orderSaleService.OrderAmountPeriodOfTime(BeginTime,EndTime));
    }

    @GetMapping("/PeriodOfTimeSum")
    public Result GetPeriodOfTimeSum( String begintime, String endtime) throws Exception {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date BeginTime=simpleDateFormat.parse(begintime);
        Date EndTime=simpleDateFormat.parse(endtime);
        return Result.ok(orderSaleService.OrderSumPeriodOfTime(BeginTime, EndTime));
    }

    @GetMapping("/PeriodOfTimeSale")
    public Result GetPeriodOfTimeSale(@Param("id")int id, String begintime, String endtime) throws Exception {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date BeginTime=simpleDateFormat.parse(begintime);
        Date EndTime=simpleDateFormat.parse(endtime);
        return Result.ok(orderSaleService.OrderSalePeriodOfTime(id, BeginTime, EndTime));
    }
    /**
     *
     * @return 总销量
     */
    @GetMapping("/AllSale")
    public Result GetAllSale() throws Exception {
        return Result.ok(orderSaleService.OrderSaleAll());
    }













}
