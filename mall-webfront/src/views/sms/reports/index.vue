<template>
  <div class="report">
    <div>
      <el-row justify="center" :gutter="50">
        <el-col :span="5">
          <div class="out-border">
            <div class="layout-title">今日订单总数</div>
            <div class="total-frame">
              <img :src="home" class="total-icon" />
              <div class="total-title">今日订单总数</div>
              <div class="total-value">{{ cardForm.todayAmount }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="out-border">
            <div class="layout-title">昨日订单总数</div>
            <div class="total-frame">
              <img :src="home" class="total-icon" />
              <div class="total-title">昨日订单总数</div>
              <div class="total-value">{{ cardForm.yesAmount }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="out-border">
            <div class="layout-title">今日销售总额</div>
            <div class="total-frame">
              <img :src="yesterday" class="total-icon" />
              <div class="total-title">今日销售总额</div>
              <div class="total-value">￥{{ cardForm.todaySum }}</div>
            </div>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="out-border">
            <div class="layout-title">昨日销售总额</div>
            <div class="total-frame">
              <img :src="yesterday" class="total-icon" />
              <div class="total-title">昨日销售总额</div>
              <div class="total-value">￥{{ cardForm.yesSum }}</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="statistics-layout">
      <div class="layout-title">订单统计</div>
      <el-row>
        <el-col :span="4">
          <div class="table-box">
            <div>
              <div class="table-title">本月订单总数</div>
              <div class="table-sum">{{ leftForm.monAmount }}</div>
              <div class="table-tip">
                <span
                  :class="
                    leftForm.monthOnMonthAmount[0] != '-'
                      ? 'color-success'
                      : 'color-danger'
                  "
                  >{{ leftForm.monthOnMonthAmount }}</span
                >
                <span class="table-tip-battle">同比上月</span>
              </div>
            </div>
            <div style="margin-top: 20px">
              <div class="table-title">本周订单总数</div>
              <div class="table-sum">{{ leftForm.weekAmount }}</div>
              <div class="table-tip">
                <span
                  :class="
                    leftForm.weekOnWeekAmount[0] != '-'
                      ? 'color-success'
                      : 'color-danger'
                  "
                  >{{ leftForm.weekOnWeekAmount }}</span
                >
                <span class="table-tip-battle">同比上周</span>
              </div>
            </div>
            <div style="margin-top: 20px">
              <div class="table-title">本月销售总额</div>
              <div class="table-sum">{{ leftForm.monSum }}</div>
              <div class="table-tip">
                <span
                  :class="
                    leftForm.monthOnMonthSum[0] != '-'
                      ? 'color-success'
                      : 'color-danger'
                  "
                  >{{ leftForm.monthOnMonthSum }}</span
                >
                <span class="table-tip-battle">同比上月</span>
              </div>
            </div>
            <div style="margin-top: 20px">
              <div class="table-title">本周销售总额</div>
              <div class="table-sum">{{ leftForm.weekSum }}</div>
              <div class="table-tip">
                <span
                  :class="
                    leftForm.weekOnWeekSum[0] != '-'
                      ? 'color-success'
                      : 'color-danger'
                  "
                  >{{ leftForm.weekOnWeekSum }}</span
                >
                <span class="table-tip-battle">同比上周</span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="20">
          <div class="chart">
            <el-date-picker
              class="picker"
              size="small"
              v-model="orderCountDate"
              type="daterange"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="handleDateChange"
              value-format="YYYY-MM-DD"
            >
            </el-date-picker>
            <div ref="main" class="line"></div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import home from '@/assets/home_order.png'
// import today from '@/assets/home_today_amount.png'
import yesterday from '@/assets/home_yesterday_amount.png'
import {
  ref
  // , onMounted
} from 'vue'
import * as echarts from 'echarts'
import { formatDate } from '@/api/date'
import {
  todayAmount,
  yesAmount,
  todaySum,
  yesSum,
  monAmount,
  monthOnMonthAmount,
  weekAmount,
  weekOnWeekAmount,
  monSum,
  monthOnMonthSum,
  weekSum,
  weekOnWeekSum,
  periodOfTimeAmount,
  periodOfTimeSum
} from '@/api/report'

const orderCountDate = ref([])
const showLine = ref({
  date: [],
  line1: [],
  line2: []
})
const main = ref()
// onMounted(() => {
//   init()
// })
function init() {
  var myChart = echarts.init(main.value)
  var option = {
    tooltip: {
      trigger: 'axis',
      position: function (point, params, dom, rect, size) {
        if (size.viewSize[0] / 2 >= point[0]) {
          return [point[0] + 50, '10%']
        } else {
          return [point[0] - 200, '10%']
        }
      }
    },
    legend: {
      y: 'bottom',
      x: 'right'
    },
    xAxis: {
      // boundaryGap: false,
      axisTick: {
        show: false
      },
      data: showLine.value.date
    },
    yAxis: [
      {
        type: 'value',
        name: '订单总数',
        min: 0,
        max: Math.max.apply(null, showLine.value.line1) * 1.1,
        position: 'left'
      },
      {
        type: 'value',
        name: '订单总额',
        min: 0,
        max: Math.max.apply(null, showLine.value.line2) * 1.2,
        position: 'right'
      }
    ],
    series: [
      {
        yAxisIndex: 0,
        name: '订单数量',
        type: 'line',
        data: showLine.value.line1
      },
      {
        yAxisIndex: 1,
        name: '订单金额',
        type: 'line',
        data: showLine.value.line2
      }
    ]
  }
  myChart.setOption(option)
}
const handleDateChange = async () => {
  console.log(orderCountDate)
  const arr1 = (
    await periodOfTimeAmount({
      begintime: orderCountDate.value[0],
      endtime: orderCountDate.value[1]
    })
  ).data
  const arr2 = (
    await periodOfTimeSum({
      begintime: orderCountDate.value[0],
      endtime: orderCountDate.value[1]
    })
  ).data
  showLine.value.date = arr1.map((item) => {
    return item.date
  })
  showLine.value.line1 = arr1.map((item) => {
    return item.amount
  })
  showLine.value.line2 = arr2.map((item) => {
    return item.sum
  })
  console.log(showLine.value.date)
  console.log(showLine.value.line1)
  console.log(showLine.value.line2)
  init()
}
const initOrderCountDate = () => {
  const start = new Date()
  const end = new Date()
  end.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
  const start1 = formatDate(start, 'yyyy-MM-dd')
  const end1 = formatDate(end, 'yyyy-MM-dd')
  orderCountDate.value = [end1, start1]
  console.log(1)
  console.log(orderCountDate.value)
  handleDateChange()
}
initOrderCountDate()

const cardForm = ref({
  todayAmount: 0,
  yesAmount: 0,
  todaySum: 0,
  yesSum: 0
})
const getCardForm = async () => {
  cardForm.value.todayAmount = (await todayAmount()).data
  cardForm.value.yesAmount = (await yesAmount()).data
  cardForm.value.todaySum = (await todaySum()).data
  cardForm.value.yesSum = (await yesSum()).data
  console.log(cardForm.value)
}
getCardForm()

const leftForm = ref({
  monAmount: 0,
  monthOnMonthAmount: 0,
  weekAmount: 0,
  weekOnWeekAmount: 0,
  monSum: 0,
  monthOnMonthSum: 0,
  weekSum: 0,
  weekOnWeekSum: 0
})
const getLeftForm = async () => {
  leftForm.value.monAmount = (await monAmount()).data
  leftForm.value.monthOnMonthAmount = (await monthOnMonthAmount()).data
  leftForm.value.weekAmount = (await weekAmount()).data
  leftForm.value.weekOnWeekAmount = (await weekOnWeekAmount()).data
  leftForm.value.monSum = (await monSum()).data
  leftForm.value.monthOnMonthSum = (await monthOnMonthSum()).data
  leftForm.value.weekSum = (await weekSum()).data
  leftForm.value.weekOnWeekSum = (await weekOnWeekSum()).data
  console.log(await weekSum())
}
getLeftForm()
</script>

<style lang="scss" scoped>
.total-frame {
  padding: 20px;
  height: 100px;
}

.layout-title {
  color: #606266;
  padding: 15px;
  background: #f2f6fc;
  font-weight: bold;
}
.total-icon {
  color: #409eff;
  width: 60px;
  height: 60px;
}

.total-title {
  position: relative;
  font-size: 16px;
  color: #909399;
  left: 70px;
  top: -60px;
}

.total-value {
  position: relative;
  font-size: 18px;
  color: #606266;
  left: 70px;
  top: -50px;
}

.out-border {
  border: 1px solid #dcdfe6;
}

.statistics-layout {
  margin-top: 20px;
  border: 1px solid #dcdfe6;
}

.table-box {
  padding: 20px;
}

.table-title {
  color: #909399;
  font-size: 14px;
}

.table-sum {
  color: #606266;
  font-size: 24px;
  padding: 10px 0;
}

.table-tip {
  font-size: 14px;
}

.table-tip-battle {
  color: #c0c4cc;
}

.chart {
  padding: 10px;
  border-left: 1px solid #dcdfe6;
}

.picker {
  float: right;
  z-index: 1;
}

.line {
  width: 95%;
  height: 400px;
  margin: 20px;
}
</style>
