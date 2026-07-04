<template>
  <div class="app-container">
    <div class="filter-container">
      <div>
        <Search style="width: 1em; height: 1em; margin-right: 8px" />
        <span>筛选搜索</span>
      </div>
      <el-form :inline="true" :model="formList" ref="resetFormData" class="demo-form-inline" style="padding-top: 22px">
        <el-form-item label="订单编号" prop="id">
          <el-input v-model="formList.id" placeholder="订单编号" />
        </el-form-item>
        <el-form-item>
          <el-button style="float: right" @click="handleSearchList()" type="primary" size="medium">
            查询结果
          </el-button>
          <el-button style="float: right; margin-right: 15px" @click="handleResetSearch" size="medium">
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="filter-container" style="margin-top: 15px">
      <div>
        <span>数据列表</span>
        <el-button style="float: right" @click="addOrderPage" size="medium">
          添加
        </el-button>
      </div>
    </div>

    <el-table :data="allTableData" border style="width: 100%">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="订单编号" align="center">
        <template #default="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column label="用户账号" align="center">
        <template #default="scope">{{ scope.row.userId }}</template>
      </el-table-column>
      <el-table-column label="地址ID" align="center">
        <template #default="scope">{{ scope.row.addressId }}</template>
      </el-table-column>
      <el-table-column label="订单金额" align="center">
        <template #default="scope">{{ scope.row.sum }}</template>
      </el-table-column>
      <el-table-column label="发货状态" align="center">
        <template #default="scope"><span v-if="scope.row.deliver === 1">已发货</span><span
            v-else-if="scope.row.deliver != 1">未发货</span></template>
      </el-table-column>
      <el-table-column label="退货状态" align="center">
        <template #default="scope"><span v-if="scope.row.refund === 1">已退货</span><span
            v-else-if="scope.row.refund != 1">未退货</span></template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" width="180">
        <template #default="scope">
          <!-- {{moment(scope.row.createdTime).format('YYYY-MM-DD HH:mm:ss')}} -->
          {{ scope.row.createdTime }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" align="center" width="200">
        <template #default="{ row }">
          <el-button link size="small" @click="orderDetail(row)">详情</el-button>
          <el-button link type="primary" size="small" @click="handleClick(row)">更新</el-button>
          <el-button link type="danger" size="small" @click="delOrder(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="batch-operate-container">
      <el-select v-model="formOperate.operateType" placeholder="批量操作">
        <el-option v-for="item in operateOptions" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-button style="margin-left: 20px" class="search-button" @click="handleBatchOperate()" type="primary"
        size="small">
        确定
      </el-button>
    </div>
    <div class="pagination-container">
      <el-pagination background v-model:currentPage="queryForm.pageNum" v-model:page-size="queryForm.pageSize"
        :page-sizes="[5, 10, 15, 20]" layout="total, sizes, prev, pager, next, jumper" :total="total"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>

    <!-- 编辑对话框 -->
    <el-dialog v-model="dialogVisible" title="更新订单信息" width="40%">
      <el-form :model="form" label-width="120px" :rules="rules">
        <el-form-item label="订单编号" prop="id">
          <el-col :span="20">
            <el-input v-model="form.id" />
          </el-col>
        </el-form-item>
        <el-form-item label="用户账号" prop="userId">
          <el-col :span="20">
            <el-input v-model="form.userId" />
          </el-col>
        </el-form-item>
        <el-form-item label="地址ID" prop="addressId">
          <el-col :span="20">
            <el-input v-model="form.addressId" />
          </el-col>
        </el-form-item>
        <el-form-item label="订单金额" prop="sum">
          <el-col :span="20">
            <el-input v-model="form.sum" />
          </el-col>
        </el-form-item>
        <el-form-item label="发货状态" prop="deliver">
          <!-- <el-col :span="20"><el-input v-model="form.deliver" /></el-col> -->
          <el-select v-model="form.deliver" placeholder="发货状态">
            <el-option label="已发货" value="已发货" />
            <el-option label="未发货" value="未发货" />
          </el-select>
        </el-form-item>
        <el-form-item label="退货状态" prop="refund">
          <!-- <el-col :span="20"><el-input v-model="form.refund" /></el-col> -->
          <el-select v-model="form.refund" placeholder="退货状态">
            <el-option label="已退货" value="已退货" />
            <el-option label="未退货" value="未退货" />
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间" prop="createdTime">
          <el-col :span="20">
            <el-input v-model="form.createdTime" />
          </el-col>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="updateForm">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 添加订单 -->
    <el-dialog v-if="showPage" v-model="showPage" title="添加订单" width="60%" top="55px" :before-close="handleClose">
      <el-form :inline="true" class="demo-form-inline" style="padding-top: 22px">
        <el-form-item label="用户账号">
          <el-input v-model="addApiData.userId" placeholder="用户账号" />
        </el-form-item>
        <el-form-item label="地址ID" prop="id">
          <el-input v-model="addApiData.addressId" placeholder="地址ID" />
        </el-form-item>
        <el-form-item label="订单金额" prop="id">
          <el-input v-model="addApiData.sum" placeholder="订单金额" />
        </el-form-item>
      </el-form>
      <el-table border height="250" ref="multipleTableRef" :data="tableData" style="width: 100%"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="商品ID" align="center">
          <template #default="scope">{{ scope.row.id }}</template>
        </el-table-column>
        <el-table-column label="商品名称" align="center">
          <template #default="scope">{{ scope.row.goodsName }}</template>
        </el-table-column>
        <el-table-column label="价格" align="center">
          <template #default="scope">{{ scope.row.price }}</template>
        </el-table-column>
        <el-table-column label="数量" align="center">
          <template #default="scope">
            <el-input-number size="small" v-model="scope.row.num" :min="1" :max="1000" @change="handleChange" />
          </template>
        </el-table-column>
      </el-table>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showPage = false">取消</el-button>
          <el-button type="primary" @click="newOrder">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 订单详情 -->
    <el-dialog v-model="dialogTableVisible" title="订单详情">
    <el-table :data="orderData">
      <el-table-column property="orderId" label="订单编号" align="center"/>
      <el-table-column property="goodsId" label="商品ID" align="center"/>
      <el-table-column property="amount" label="商品数量" align="center"/>
    </el-table>
  </el-dialog>
  </div>
</template>

<script setup>
  // import Qs from 'qs'
  import {
    ElMessage,
    ElMessageBox
  } from 'element-plus'
  // 时间格式化插件
  // import moment from 'moment'
  import {
    reactive,
    ref
    // computed
  } from 'vue'
  import {
    getAllOrders,
    searchOrder,
    updateOrder,
    deleteOrder,
    addOrder,
    getOrderInfo
  } from '@/api/orders'
  import {
    getGoods
  } from '@/api/goods'
  // import { ElMessageBox } from 'element-plus'

  const showPage = ref(false)
  // const num = ref(1)
  const multipleSelection = ref([])
  const tableData = ref([])
  const addApiData = ref({
    userId: '',
    addressId: '',
    sum: '',
    goodsIds: [],
    amounts: []
  })

  // 请求获取列表数据参数
  const queryForm = ref({
    pageNum: 1,
    pageSize: 5
  })
  // 搜索请求参数
  const formList = reactive({
    id: ''
  })

  const formOperate = ref({
    operateType: ''
  })
  // 列表数据总数
  const total = ref(0)
  // 搜索id
  const handleSearchList = async () => {
    if (formList.id === '') {
      initGetAllOrders()
    } else {
      const res = await searchOrder(formList)
      allTableData.value = []
      allTableData.value[0] = res.data
      total.value = 1
    }
  }

  const form = ref({
    id: '',
    userId: '',
    addressId: '',
    sum: '',
    deliver: '',
    refund: '',
    createdTime: ''
  })

  const rules = ref({
    id: [{
      required: true,
      message: '订单编号不能为空',
      trigger: 'blur'
    }],
    userId: [{
      required: true,
      message: '用户账号不能为空',
      trigger: 'blur'
    }],
    addressId: [{
      required: true,
      message: '地址ID不能为空',
      trigger: 'blur'
    }],
    sum: [{
      required: true,
      message: '订单金额不能为空',
      trigger: 'blur'
    }],
    deliver: [{
      required: true,
      message: '发货状态不能为空',
      trigger: 'change'
    }],
    refund: [{
      required: true,
      message: '退货状态不能为空',
      trigger: 'change'
    }],
    createdTime: [{
      required: true,
      message: '创建时间不能为空',
      trigger: 'blur'
    }]
  })

  const resetFormData = ref(null)
  const handleResetSearch = () => {
    resetFormData.value.resetFields()
    initGetAllOrders()
  }

  // 表格数据
  const allTableData = ref([])
  // 获取列表数据
  const initGetAllOrders = async () => {
    const res = await getAllOrders(queryForm.value)
    console.log('res:', res.data.records)
    allTableData.value = res.data.records
    total.value = res.data.total
    console.log(allTableData)
  }
  initGetAllOrders()
  // 每页条数
  const handleSizeChange = (pageSize) => {
    queryForm.value.pageNum = 1
    queryForm.value.pageSize = pageSize
    if (formList.id === '') {
      initGetAllOrders()
    }
  }
  // 切换页面
  const handleCurrentChange = (pageNum) => {
    queryForm.value.pageNum = pageNum
    if (formList.id === '') {
      initGetAllOrders()
    }
  }

  const dialogVisible = ref(false)

  const handleClick = (row) => {
    dialogVisible.value = true
    form.value = JSON.parse(JSON.stringify(row))
    console.log(form.value.deliver)
    if (form.value.deliver === 1) {
      form.value.deliver = '已发货'
    } else {
      form.value.deliver = '未发货'
    }
    if (form.value.refund === 1) {
      form.value.refund = '已退货'
    } else {
      form.value.refund = '未退货'
    }
  }

  const updateData = ref({
    id: '',
    userId: '',
    addressId: '',
    sum: '',
    deliver: '',
    refund: '',
    createdTime: ''
  })

  const updateForm = async () => {
    // console.log(form.value)
    updateData.value = form.value
    dialogVisible.value = false
    // updateData.value.deliver = parseInt(updateData.value.deliver)
    // updateData.value.refund = parseInt(updateData.value.refund)
    if (updateData.value.deliver === '已发货') {
      updateData.value.deliver = 1
    } else {
      updateData.value.deliver = 0
    }
    if (updateData.value.refund === '已退货') {
      updateData.value.refund = 1
    } else {
      updateData.value.refund = 0
    }
    // form.value.createdTime = moment(form.value.createdTime).format()
    console.log(updateData.value)
    const res = await updateOrder(updateData.value)
    console.log(res)
    ElMessage({
      message: '更新成功！',
      type: 'success'
    })
    initGetAllOrders()
  }

  const delApiData = ref({
    id: ''
  })

  const delOrder = (row) => {
    ElMessageBox.confirm('此操作将永久删除该订单, 是否继续?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
      .then(async () => {
        delApiData.value.id = row.id
        console.log(delApiData.value)
        await deleteOrder(delApiData.value)
        ElMessage({
          type: 'success',
          message: '删除成功！'
        })
        initGetAllOrders()
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '删除失败！'
        })
      })
  }

  const addOrderPage = async () => {
    showPage.value = true
    const apiData = ref({
      pageNum: 1,
      pageSize: 10000
    })
    console.log(apiData.value)
    const res = await getGoods(apiData.value)
    tableData.value = res.data.records.map((item) => ({
      id: item.id,
      goodsName: item.goodsName,
      price: item.price,
      num: 1
    }))
  }

  const handleSelectionChange = (val) => {
    multipleSelection.value = val
    const sum = ref(0)
    multipleSelection.value.map(
      (item) => (sum.value = sum.value + item.price * item.num)
    )
    addApiData.value.sum = sum.value
  }

  const newOrder = async () => {
    addApiData.value.userId = parseInt(addApiData.value.userId)
    addApiData.value.addressId = parseInt(addApiData.value.addressId)
    addApiData.value.sum = parseFloat(addApiData.value.sum)
    var goodsIds = ref([])
    var amounts = ref([])
    multipleSelection.value.map((item) => {
      goodsIds.value.push(item.id)
      amounts.value.push(item.num)
    })
    addApiData.value.goodsIds = goodsIds.value
    addApiData.value.amounts = amounts.value
    // addApiData.value.goodsIds = Qs.stringify(addApiData.value.goodsIds, { arrayFormat: 'indices', allowDots: true })
    // addApiData.value.amounts = Qs.stringify(addApiData.value.amounts, { arrayFormat: 'indices', allowDots: true })
    // addApiData.value = Qs.stringify(addApiData.value)
    await addOrder(addApiData.value)
    ElMessage({
      type: 'success',
      message: '添加成功！'
    })
    initGetAllOrders()
    showPage.value = false
  }

  // 订单详情
  const dialogTableVisible = ref(false)
  const orderData = ref([])

  const orderDetail = async (row) => {
    dialogTableVisible.value = true
    const res = await getOrderInfo(row.id)
    orderData.value = res.data
  }

</script>

<style lang="scss" scoped>
  ::v-deep .el-input__prefix {
    align-items: center;
  }

  ::v-deep .el-input__suffix {
    align-items: center;
  }

</style>
