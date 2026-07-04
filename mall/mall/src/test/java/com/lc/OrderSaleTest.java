package com.lc;


import com.lc.mapper.OrderSaleMapper;
import com.lc.service.OrderSaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootTest
public class OrderSaleTest
{
    @Autowired
    private OrderSaleService orderSaleService;

    @Autowired
    private OrderSaleMapper orderSaleMapper;




    @Test
    public void Amount() throws Exception {
        System.out.println(orderSaleService.OrderAmountDay());
        System.out.println(orderSaleService.OrderAmountYes());
        System.out.println(orderSaleService.OrderAmountMon());
        System.out.println(orderSaleService.OrderAmountWeek());

    }
    @Test
    public void Sum() throws Exception {
        System.out.println(orderSaleService.OrderSumDay());
        System.out.println(orderSaleService.OrderSumYes());
        System.out.println(orderSaleService.OrderSumMon());
        System.out.println(orderSaleService.OrderSumWeek());
    }

    @Test
    public void Sale() throws Exception {
        System.out.println(orderSaleService.OrderSaleDay(2 ));
        System.out.println(orderSaleService.OrderSaleYes(1));
        System.out.println(+orderSaleService.OrderSaleMon(1));
        System.out.println(orderSaleService.OrderSaleWeek(1));
        System.out.println(orderSaleService.OrderSaleAll());
    }

    @Test
    public void On() throws Exception {
        System.out.println(orderSaleService.MonthOnMonthAmount());
        System.out.println(orderSaleService.MonthOnMonthSum());
        System.out.println(orderSaleService.MonthOnMonthSale(2));
        System.out.println(orderSaleService.WeekOnWeekAmount());
        System.out.println(orderSaleService.WeekOnWeekSum());
        System.out.println(orderSaleService.WeekOnWeekSale(2));
    }

    @Test
    public void Period() throws Exception {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date BeginTime=simpleDateFormat.parse("2022-07-31");
        Date EndTime=simpleDateFormat.parse("2022-08-01");
        System.out.println(orderSaleService.OrderAmountPeriodOfTime(BeginTime,EndTime));
        System.out.println(orderSaleService.OrderSumPeriodOfTime(BeginTime,EndTime));
        System.out.println(orderSaleService.OrderSalePeriodOfTime(1,BeginTime,EndTime));

    }




}
