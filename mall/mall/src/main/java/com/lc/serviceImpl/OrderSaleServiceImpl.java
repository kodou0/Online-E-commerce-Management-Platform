package com.lc.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.common.method.GetDate;
import com.lc.common.method.GetPeriodOfLastWeek;
import com.lc.common.method.GetPeriodOfWeek;
import com.lc.mapper.OrderMapper;
import com.lc.mapper.OrderSaleMapper;
import com.lc.model.entity.Order;
import com.lc.model.entity.OrderInfo;
import com.lc.model.vo.*;
import com.lc.service.OrderSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderSaleServiceImpl extends ServiceImpl<OrderSaleMapper, OrderInfo> implements OrderSaleService
{
    @Autowired
    private OrderSaleMapper orderSaleMapper;

    @Autowired
    private OrderMapper orderMapper;


    GetDate getDate=new GetDate();

    /**
     *
     * @return 每日订单数
     *
     */

    @Override
    public Long OrderAmountDay() {

        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.apply("date_format(created_time,'%Y-%m-%d') = {0}", getDate.GetToday());
        return orderMapper.selectCount(queryWrapper);
    }

    /**
     *
     * @return 昨日订单数
     */
    @Override
    public Long OrderAmountYes() {

        QueryWrapper<Order>queryWrapper =new QueryWrapper<>();
        queryWrapper.apply("date_format(created_time,'%Y-%m-%d') = {0}",getDate.GetYesterday());
        return orderMapper.selectCount(queryWrapper);
    }


    /**
     *
     * @return 今月订单数
     */
    @Override
    public Long OrderAmountMon() {

        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.apply("date_format(created_time,'%Y-%m') = {0}",getDate.GetMonth());
        return orderMapper.selectCount(queryWrapper);
    }


    /**
     *
     * @return 今日销售额
     */
    @Override
    public double OrderSumDay() {
        double sum=0;
        Order order;
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.apply("date_format(created_time,'%Y-%m-%d') = {0}", getDate.GetToday());
        List<Order> list=orderMapper.selectList(queryWrapper);
        for (Order value : list) {
            order = value;
            sum += order.getSum();
        }
        return sum;
    }


    @Override
    public double OrderSumYes() {
        double sum=0;
        Order order;
        QueryWrapper<Order>queryWrapper =new QueryWrapper<>();
        queryWrapper.apply("date_format(created_time,'%Y-%m-%d') = {0}",getDate.GetYesterday());
        List<Order> list=orderMapper.selectList(queryWrapper);
        for (Order value : list) {
            order = value;
            sum += order.getSum();
        }
        return sum;
    }


    @Override
    public double OrderSumMon() {
        double sum=0;
        Order order;
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.apply("date_format(created_time,'%Y-%m') = {0}",getDate.GetMonth());
        List<Order> list=orderMapper.selectList(queryWrapper);
        for (Order value : list) {
            order = value;
            sum += order.getSum();
        }
        return sum;
    }

    /**
     * @param id 商品id
     * @return 今日销量
     */
    @Override
    public int OrderSaleDay(int id) throws NullPointerException
    {
        int sum= 0;
        List<OrderSaleVo> list=orderSaleMapper.SelectAllOrderInfo();
        String today=getDate.GetToday();
        for (OrderSaleVo orderInfoVo : list) {
            String OrderTime = getDate.TransToDate(orderInfoVo.getCreatedTime());
            if (today.equals(OrderTime)&&id==orderInfoVo.getGoodsId())
            {
                sum+=orderInfoVo.getAmount();
            }
        }
        return sum;
    }

    /**
     * @param id 商品id
     * @return 昨日销量
     */
    @Override
    public int OrderSaleYes(int id) throws NullPointerException{
        int sum= 0;
        List<OrderSaleVo> list=orderSaleMapper.SelectAllOrderInfo();
        String Yesterday=getDate.GetYesterday();
        for (OrderSaleVo orderInfoVo : list) {
            String OrderTime = getDate.TransToDate(orderInfoVo.getCreatedTime());
            if (Yesterday.equals(OrderTime)&&id==orderInfoVo.getGoodsId())
            {
                sum+=orderInfoVo.getAmount();
            }
        }
        return sum;
    }

    @Override
    public int OrderSaleMon(int id) throws NullPointerException {
        int sum= 0;
        List<OrderSaleVo> list=orderSaleMapper.SelectAllOrderInfo();
        String Month=getDate.GetMonth();
        for (OrderSaleVo orderInfoVo : list) {
            String OrderTime = getDate.TransToDateMonth(orderInfoVo.getCreatedTime());
            if (Month.equals(OrderTime)&&id==orderInfoVo.getGoodsId())
            {
                sum+=orderInfoVo.getAmount();
            }
        }
        return sum;
    }

    /**
     *
     * @return 总销量
     */
    @Override
    public int OrderSaleAll()
    {
        int sum=0;
        List<OrderSaleVo> list=orderSaleMapper.SelectAllOrderInfo();
        for (OrderSaleVo orderInfoVo : list) {
            sum += orderInfoVo.getAmount();
        }
        return sum;
    }


    /**
     *
     * @return 本周订单数
     */
    @Override
    public int OrderAmountWeek() {
        Date[] date =GetPeriodOfWeek.GetBeginAndEndOfWeek();
        List<OrderVo> list=orderSaleMapper.SelectAllOrderSaleWeek(date[0],date[1]);
        return list.size();
    }

    @Override
    public double OrderSumWeek() {
        double sum=0;
        Date[] date =GetPeriodOfWeek.GetBeginAndEndOfWeek();
        List<OrderVo> list=orderSaleMapper.SelectAllOrderSaleWeek(date[0],date[1]);
        System.out.println(list);
        for (OrderVo orderInfoVo : list) {
            sum += orderInfoVo.getSum();
        }
        return sum;
    }

    /**
     * @param id 商品id
     * @return 本周订单数
     */
    @Override
    public int OrderSaleWeek(int id) {
        int amount=0;
        Date[] date =GetPeriodOfWeek.GetBeginAndEndOfWeek();
        List<OrderSaleVo> list=orderSaleMapper.SelectAllOrderInfoWeek(date[0],date[1]);
        for (OrderSaleVo orderInfoVo : list) {
            if (id == orderInfoVo.getGoodsId()) {
                amount += orderInfoVo.getAmount();
            }
        }
        return amount;
    }

    /**
     *
     * @return 订单月同比
     */
    @Override
    public String MonthOnMonthAmount() {
        Long amountMon=OrderAmountMon();
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.apply("date_format(created_time,'%Y-%m') = {0}",getDate.GetLastMon());
        double ratio=(amountMon*1.0-orderMapper.selectCount(queryWrapper)*1.0)/orderMapper.selectCount(queryWrapper)*1.0;
        DecimalFormat decimalFormat=new DecimalFormat("##%");
        return decimalFormat.format(ratio);

    }

    @Override
    public String WeekOnWeekAmount() {
        int AmountWeek=OrderAmountWeek();
        Date[] date = GetPeriodOfLastWeek.GetBeginAndEndOfLastWeek();
        List<OrderVo> list=orderSaleMapper.SelectAllOrderSaleWeek(date[0],date[1]);
        double ratio=(AmountWeek*1.0-list.size()*1.0)/AmountWeek;
        DecimalFormat decimalFormat=new DecimalFormat("##%");
        return decimalFormat.format(ratio);
    }

    @Override
    public String MonthOnMonthSum() {
        double sum=0;
        Order order;
        double SumMon=OrderSumMon();
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.apply("date_format(created_time,'%Y-%m') = {0}",getDate.GetLastMon());
        List<Order> list=orderMapper.selectList(queryWrapper);
        for (Order value : list) {
            order = value;
            sum += order.getSum();
        }
        double ratio=(SumMon -sum*1.0)/sum;
        DecimalFormat decimalFormat=new DecimalFormat("##%");
        return decimalFormat.format(ratio);
    }


    @Override
    public String WeekOnWeekSum() {
        double sumWeek=OrderSumWeek();
        double sum=0;
        Date[] date = GetPeriodOfLastWeek.GetBeginAndEndOfLastWeek();
        List<OrderVo> list=orderSaleMapper.SelectAllOrderSaleWeek(date[0],date[1]);
        for (OrderVo orderInfoVo : list) {
            sum += orderInfoVo.getSum();
        }
        double ratio=(sumWeek -sum*1.0)/sum;
        DecimalFormat decimalFormat=new DecimalFormat("##%");
        return decimalFormat.format(ratio);
    }

    @Override
    public String MonthOnMonthSale(int id) {
        int  SaleMon=OrderSaleMon(id);
        String LastMon=getDate.GetLastMon();
        Double sum= (double) 0;
        int OrderId;
        List<OrderSaleVo> list=orderSaleMapper.SelectAllOrderInfo();
        for (OrderSaleVo orderInfoVo : list) {
            String OrderTime = getDate.TransToDateMonth(orderInfoVo.getCreatedTime());
            if (LastMon.equals(OrderTime)) {
               sum+=orderInfoVo.getAmount();
            }
        }
        double ratio=(SaleMon*1.0-sum*1.0)/sum;
        DecimalFormat decimalFormat=new DecimalFormat("##%");
        return decimalFormat.format(ratio);
    }

    @Override
    public String WeekOnWeekSale(int id) {
        int SaleWeek=OrderSaleWeek(id);
        int amount=0;
        Date[] date = GetPeriodOfLastWeek.GetBeginAndEndOfLastWeek();
        List<OrderSaleVo> list=orderSaleMapper.SelectAllOrderInfoWeek(date[0],date[1]);
        for (OrderSaleVo orderInfoVo : list) {
            if (id == orderInfoVo.getGoodsId()) {
                amount += orderInfoVo.getAmount();
            }
        }
        double ratio=(SaleWeek*1.0-amount*1.0)/amount;
        DecimalFormat decimalFormat=new DecimalFormat("##%");
        return decimalFormat.format(ratio);
    }

    @Override

    public List<OrderPeriodOfTimeAmount> OrderAmountPeriodOfTime(Date BeginTime, Date EndTime) throws ParseException {
        List<OrderVo> list=orderSaleMapper.SelectAllOrderSaleWeek(BeginTime,EndTime);
        List<OrderPeriodOfTimeAmount> amountList=new ArrayList<>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar Begin=Calendar.getInstance();
        Begin.setTime(BeginTime);
        Calendar End=Calendar.getInstance();
        End.setTime(EndTime);
        End.add(Calendar.DATE, 1);
        int amount=0;
        while(!Begin.getTime().equals(End.getTime()))
        {
            for(int i=0;i< list.size();i++)
            {

                String str=simpleDateFormat.format(list.get(i).getCreatedTime());
                Date date=simpleDateFormat.parse(str);
                if(Begin.getTime().equals(date))
                {
                    amount++;
                }
            }
            OrderPeriodOfTimeAmount orderPeriodOfTimeAmount=new OrderPeriodOfTimeAmount();
            orderPeriodOfTimeAmount.setAmount(amount);
            orderPeriodOfTimeAmount.setDate(simpleDateFormat.format(Begin.getTime()));
            amountList.add(orderPeriodOfTimeAmount);
            Begin.add(Calendar.DATE, 1);


        }
        return amountList;
    }


    /**
     * @param BeginTime 开始时间
     * @param EndTime  结束时间
     * @return 一段时间内的销售额
     */
    @Override
    public List<OrderPeriodOfTimeSum> OrderSumPeriodOfTime(Date  BeginTime, Date  EndTime) throws ParseException {
        List<OrderVo> list=orderSaleMapper.SelectAllOrderSaleWeek(BeginTime,EndTime);
        List<OrderPeriodOfTimeSum> SumList=new ArrayList<>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar Begin=Calendar.getInstance();
        Begin.setTime(BeginTime);
        Calendar End=Calendar.getInstance();
        End.setTime(EndTime);
        End.add(Calendar.DATE, 1);
        double sum=0;
        while(!Begin.getTime().equals(End.getTime()))
        {
            for(int i=0;i< list.size();i++)
            {

                String str=simpleDateFormat.format(list.get(i).getCreatedTime());
                Date date=simpleDateFormat.parse(str);
                if(Begin.getTime().equals(date))
                {
                    sum+=list.get(i).getSum();
                }
            }
            OrderPeriodOfTimeSum orderPeriodOfTimeSum=new OrderPeriodOfTimeSum();
            orderPeriodOfTimeSum.setSum(sum);
            orderPeriodOfTimeSum.setDate(simpleDateFormat.format(Begin.getTime()));
            SumList.add(orderPeriodOfTimeSum);
            Begin.add(Calendar.DATE, 1);


        }
        return SumList;
    }

    @Override
    public List<OrderPeriodOfTimeSale> OrderSalePeriodOfTime(int id, Date  BeginTime, Date EndTime) throws ParseException {
        List<OrderSaleVo> list=orderSaleMapper.SelectAllOrderInfoPeriodOfTime(BeginTime,EndTime);
        List<OrderPeriodOfTimeSale> SaleList=new ArrayList<>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar Begin=Calendar.getInstance();
        Begin.setTime(BeginTime);
        Calendar End=Calendar.getInstance();
        End.setTime(EndTime);
        End.add(Calendar.DATE, 1);
        int sale=0;
        while(!Begin.getTime().equals(End.getTime()))
        {
            for(int i=0;i< list.size();i++)
            {

                String str=simpleDateFormat.format(list.get(i).getCreatedTime());
                Date date=simpleDateFormat.parse(str);
                if(Begin.getTime().equals(date)&&id==list.get(i).getGoodsId())
                {
                    sale+=list.get(i).getAmount();
                }
            }
            OrderPeriodOfTimeSale orderPeriodOfTimeSale=new OrderPeriodOfTimeSale();
            orderPeriodOfTimeSale.setId(id);
            orderPeriodOfTimeSale.setSale(sale);
            orderPeriodOfTimeSale.setDate(simpleDateFormat.format(Begin.getTime()));
            SaleList.add(orderPeriodOfTimeSale);
            Begin.add(Calendar.DATE, 1);


        }
        return SaleList;
    }

}
