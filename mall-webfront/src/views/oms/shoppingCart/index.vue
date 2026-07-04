<template>
  <div class="app-container">
    <div class="filter-container" style="margin-top: 15px; border-bottom: none">
      <div>
        <!-- <el-icon style="margin-right: 10px; font-size: 20px"
          ><ShoppingCartFull
        /></el-icon> -->
        <span>购物车</span>
        <el-button
          type="success"
          style="margin-left: 70%"
          @click="initGetGoodsList"
          >新建购物车</el-button
        >
        <el-button type="warning" style="margin-left: 5%" @click="clearShop"
          >清空购物车</el-button
        >
      </div>
    </div>
    <el-table
      border
      style="width: 100%"
      :data="listData"
      @selection-change="selected"
    >
      <el-table-column type="selection" align="center" width="50">
      </el-table-column>
      <el-table-column prop="imgUrl" label="商品图片" align="center">
        <template #default="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.imgUrl"
            :fit="fit"
          />
        </template>
      </el-table-column>
      <el-table-column prop="goodsName" align="center" label="商品名称">
      </el-table-column>
      <el-table-column prop="price" align="center" label="单价">
      </el-table-column>
      <el-table-column prop="amount" align="center" label="数量">
        <template #default="scope">
          <el-input-number
            v-model="scope.row.amount"
            :min="1"
            :max="99"
            @change="handleChange(scope.$index, scope.row, scope)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="total" align="center" label="小计">
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-link type="danger" @click="handleDelete(scope.$index, scope.row)"
            >删除</el-link
          >
        </template>
      </el-table-column>
    </el-table>
    <div class="filter-container" style="border-top: none">
      <div>
        <span style="margin-left: 70%"
          >已选商品
          <span style="font-size: 20px; color: #e6a23c"> {{ numTotal }}</span>
          件</span
        >
        <span style="margin-left: 30px"
          >共计
          <span style="font-size: 20px; color: #e6a23c"> {{ priceTotal }}</span>
          元</span
        >
        <el-button
          size="mini"
          type="primary"
          style="margin-left: 30px"
          @click="simit"
          >结算
        </el-button>
      </div>
      <el-dialog
        v-model="dialogVisible"
        title="结算"
        width="60%"
        empty-text="暂无数据"
      >
        <el-table border style="width: 100%" :data="selectedList">
          <el-table-column prop="imgUrl" label="商品图片" align="center">
            <template #default="scope">
              <el-image
                style="width: 50px; height: 50px"
                :src="scope.row.imgUrl"
                :fit="fit"
              />
            </template>
          </el-table-column>
          <el-table-column prop="goodsName" align="center" label="商品名称">
          </el-table-column>
          <el-table-column prop="price" align="center" label="单价">
          </el-table-column>
          <el-table-column prop="amount" align="center" label="数量">
          </el-table-column>
          <el-table-column prop="total" align="center" label="小计">
          </el-table-column>
        </el-table>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="clearSelected">返回上一步</el-button>
            <el-button type="primary" @click="clearSelected"
              >提交订单</el-button
            >
          </span>
        </template>
      </el-dialog>
      <el-dialog v-model="dialogVisible2" title="新建购物车" width="50%">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column
            prop="img"
            label="商品图片"
            width="100"
            align="center"
          >
            <template #default="scope">
              <el-image
                style="width: 50px; height: 50px"
                :src="scope.row.img"
                :fit="fit"
              />
            </template>
          </el-table-column>
          <el-table-column property="name" label="商品名称" align="center">
            <template #default="scope">{{ scope.row.goodsName }}</template>
          </el-table-column>
          <el-table-column label="价格" width="120" align="center">
            <template #default="scope">
              <p>价格：{{ scope.row.price }}</p>
            </template>
          </el-table-column>
          <el-table-column label="品牌" width="100" align="center">
            <template #default="scope">{{ scope.row.brand }} </template>
          </el-table-column>
          <el-table-column label="操作" width="160" align="center">
            <template #default="scope">
              <el-link type="success" @click="addShop(scope.$index, scope.row)"
                >加入购物车</el-link
              >
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          v-model:currentPage="queryForm.pageNum"
          v-model:page-size="queryForm.pageSize"
          :page-sizes="[5, 10, 15, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </el-dialog>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import {
  getGoodsList,
  delShoppingCart,
  getGoods,
  addShoppingCart,
  clearShoppingCart
} from '@/api/shoppingCart'
const dialogVisible = ref(false)
const listData = ref([])
const selectedList = ref([])
const priceTotal = ref(0)
const numTotal = ref(0)
// const apiData2 = ref({
//   orderId: '',
//   amount: 1
// })
const handleChange = (index, row) => {
  for (var i = 0; i < selectedList.value.length; i++) {
    if (row.id === selectedList.value[i].id) break
  }
  if (i !== selectedList.value.length) {
    priceTotal.value = priceTotal.value - listData.value[index].total
    listData.value[index].total = row.amount * row.price
    priceTotal.value = priceTotal.value + listData.value[index].total
  } else {
    listData.value[index].total = row.amount * row.price
  }
}
const handleDelete = (index, row) => {
  console.log(row)
  if (row.sel === true) {
    priceTotal.value -= row.total
  }
  delShoppingCart(row.id)
  getList()
}
const selected = (selection) => {
  selectedList.value = selection
  numTotal.value = selection.length
  priceTotal.value = 0
  for (let i = 0; i < selection.length; i++) {
    priceTotal.value += selection[i].total
  }
}
const simit = () => {
  if (selectedList.value.length === 0) ElMessage.error('暂无选定商品')
  else dialogVisible.value = true
}
const getList = async () => {
  const res = await getGoodsList()
  console.log(res)
  listData.value = res.data
  for (let i = 0; i < listData.value.length; i++) {
    listData.value[i].total = listData.value[i].amount * listData.value[i].price
  }
}
getList()
// 获取列表
const clearSelected = () => {
  dialogVisible.value = false
}
const tableData = ref([])
const queryForm = ref({
  pageNum: 1,
  pageSize: 5
})
const total = ref(0)
const dialogVisible2 = ref(false)
const apiData = ref({
  goodsId: 0,
  amount: 1,
  imgUrl: '',
  goodsName: '',
  price: 0
})
const initGetGoodsList = async () => {
  dialogVisible2.value = true
  const res = await getGoods(queryForm.value)
  console.log(res.data)
  tableData.value = res.data.records
  total.value = res.data.total
  console.log(tableData.value)
}
const handleSizeChange = (pageSize) => {
  queryForm.value.pageNum = 1
  queryForm.value.pageSize = pageSize
  initGetGoodsList()
}
const handleCurrentChange = (pageNum) => {
  queryForm.value.pageNum = pageNum
  initGetGoodsList()
}
const addShop = (index, row) => {
  console.log(index)
  console.log(row.img)
  apiData.value.goodsId = row.id
  apiData.value.imgUrl = row.img
  apiData.value.goodsName = row.goodsName
  apiData.value.price = row.price
  console.log(apiData.value)
  addShoppingCart(apiData.value)
  dialogVisible2.value = false
  getList()
}
const clearShop = () => {
  clearShoppingCart()
  getList()
}
</script>

<style lang="scss" scoped>
</style>
