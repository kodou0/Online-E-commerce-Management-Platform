<template>
  <div class="app-container">
    <div class="filter-container">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
      </div>
      <el-form :inline="true" :model="formList" ref="resetFormData" class="demo-form-inline" style="padding-top:15px">
        <el-form-item prop="searchTxt">
          <el-input v-model="formList.searchTxt" placeholder="商品名称或商标或类型名" />
        </el-form-item>
        <el-form-item>
          <el-button style="float: right" @click="handleSearchList()" type="primary" size="medium">查询结果
        </el-button>
        <el-button style="float: right;margin-right: 15px" @click="handleResetSearch()" size="medium">
          重置
        </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="filter-container" style="margin-top:15px">
      <div>
        <span>数据列表</span>
        <el-button style="float: right" size="medium">
          <router-link :to="{ path: '/addProduct'}">添加</router-link>
        </el-button>
      </div>
    </div>

    <el-table :data="tableData" border style="width: 100%">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="商品id" width="100" align="center">
        <template #default="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column prop="img" label="商品图片" width="200" align="center">
        <template #default="scope">
        <img class="img" :src="scope.row.img"/>
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
      <el-table-column label="类型" width="80" align="center">
        <template #default="scope">{{ scope.row.goodsTypeId }}
        </template>
      </el-table-column>
      <el-table-column label="品牌" width="80" align="center">
        <template #default="scope">{{ scope.row.brand }}
        </template>
      </el-table-column>
      <el-table-column property="tag" label="标签" width="120" align="center">
        <template #default="scope">
          <p>上架：
            <el-switch :active-value="1" :inactive-value="0" v-model="scope.row.shelves" @change="beforeChange(scope.row)">       <!-- 1是上架，0是下架  -->
            </el-switch>
          </p>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center">
        <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">
              编辑
            </el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="batch-operate-container">
      <el-select size="small" v-model="formOperate.operateType" placeholder="批量操作">
        <el-option v-for="item in operates" :key="item.value" :label="item.label" :value="item.value">
        </el-option>
      </el-select>
      <el-button style="margin-left: 20px" class="search-button" @click="handleBatchOperate()" type="primary"
        size="small">
        确定
      </el-button>
    </div>
    <div class="pagination-container">
      <el-pagination
      v-model:currentPage="queryForm.pageNum"
      v-model:page-size="queryForm.pageSize"
      :page-sizes="[5, 10, 15, 20]"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    </div>

  </div>
</template>

<script setup>
  import {
    reactive,
    ref
  } from 'vue'
  import {
    useRouter
  } from 'vue-router'
  import {
    getGoods, delGoods, updateGoods, queryGoods
  } from '@/api/goods'
  import { ElMessage, ElMessageBox } from 'element-plus'

  const formList = reactive({
    searchTxt: ''
  })

  const formOperate = reactive({
    operateType: ''
  })

  const operates = ([{
      label: '商品上架',
      value: 'publishOn'
    },
    {
      label: '商品下架',
      value: 'publishOff'
    },
    {
      label: '设为推荐',
      value: 'recommendOn'
    },
    {
      label: '取消推荐',
      value: 'recommendOff'
    },
    {
      label: '设为新品',
      value: 'newOn'
    },
    {
      label: '取消新品',
      value: 'newOff'
    },
    {
      label: '转移到分类',
      value: 'transferCategory'
    },
    {
      label: '移入回收站',
      value: 'recycle'
    }
  ])

  const handleSearchList = async () => {
    console.log('查询结果')
    const query = ref({
      searchTxt: formList.searchTxt,
      pageNum: queryForm.value.pageNum,
      pageSize: queryForm.value.pageSize
    })
    const res = await queryGoods(query.value)
    console.log(res.data)
    tableData.value = res.data.records
    total.value = res.data.total
    console.log(tableData.value)
    queryForm.value.pageNum = 1
  }

  const resetFormData = ref(null)
  const handleResetSearch = () => {
    resetFormData.value.resetFields()
    console.log(0)
    console.log(resetFormData.value)
  }
  const router = useRouter()
  const handleEdit = (index, row) => {
    console.log('编辑')
    router.push({
      name: 'addProduct',
      params: {
        id: row.id,
        goodsName: row.goodsName,
        img: row.img,
        price: row.price,
        brand: row.brand,
        goodsTypeId: row.goodsTypeId,
        shelves: row.shelves
      }
    })
  }
  const tableData = ref([])
  const queryForm = ref({
    pageNum: 1,
    pageSize: 5
  })
  const total = ref(0)
  const initGetGoodsList = async () => {
    const res = await getGoods(queryForm.value)
    console.log(res.data)
    tableData.value = res.data.records
    total.value = res.data.total
    console.log(tableData.value)
  }
  initGetGoodsList()

  const handleDelete = (index, row) => {
    ElMessageBox.confirm('是否确认删除该商品', '确认', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      const delForm = ref({
        id: row.id
      })
      await delGoods(delForm.value)
      ElMessage({
        type: 'success',
        message: '删除成功'
      })
      initGetGoodsList()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除'
      })
    })
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

const loading = ref(false)
const beforeChange = (row) => {
  loading.value = true
  return new Promise((resolve) => {
    setTimeout(() => {
      updateGoods(row)
      loading.value = false
      if (row.shelves === 1) {
        ElMessage.success('上架成功')
      } else {
        ElMessage.success('下架成功')
      }
      return resolve(true)
    }, 1000)
  })
}

</script>

<style lang="scss" scoped>
.img{
  width: 120px;
  height: 120px;
  padding-right: 20px
}
</style>
