<template>
  <div class="app-container">
    <div class="filter-container" style="margin-top:15px">
      <div>
        <span>数据列表</span>
        <el-button style="float: right" @click="handleAddgoods()" size="medium">
          添加
        </el-button>
      </div>
    </div>

    <el-table :data="tableData" border style="width: 100%">
      <el-table-column label="编号" width="100" align="center">
        <template #default="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column property="typeName" label="类型名称" align="center">
        <template #default="scope">{{ scope.row.typeName }}</template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center">
        <template #default="scope">
          <el-button size="mini" @click="handleUpdateProduct(scope.$index, scope.row)">编辑
          </el-button>
          <!--el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
          </el-button-->
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination v-model:currentPage="query.pageNum" v-model:page-size="query.pageSize"
        :page-sizes="[5, 10, 15, 20]" layout="total, sizes, prev, pager, next, jumper" :total="total"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>

    <el-dialog v-model="dialogVisible" :title="title" width="30%" :before-close="handleClose">
      <el-form :rules="rules" :model="updateList" ref="formRef">
        <el-form-item label="类型id" prop="id" v-if="title === '编辑商品类型'">
          <el-input disabled v-model="typeId" />
        </el-form-item>
        <el-form-item label="类型名称" prop="typeName">
          <el-input v-model="updateList.typeName" placeholder="商品名称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleAnd">确定</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
  import {
    reactive,
    ref
  } from 'vue'
  import {
    getAllGoodsType,
    addGoodsType,
    updateGoodsType
  } from '@/api/goods'
  import {
    ElMessage
  } from 'element-plus'

  const tableData = ref([])
  const query = ref({
    pageNum: 1,
    pageSize: 10
  })
  const total = ref(0)
  const initGetAllGoodsType = async () => {
    const res = await getAllGoodsType(query.value)
    tableData.value = res.data.records
    total.value = res.data.total
  }
  initGetAllGoodsType()

  const formRef = ref(null)
  const rules = ref({
    typeName: [{
      required: true,
      message: '请输入',
      trigger: 'blur'
    }]
  })

  const updateList = reactive({
    typeName: ''
  })
  const typeId = ref()
  const title = ref()
  const dialogVisible = ref(false)
  const handleUpdateProduct = (index, row) => {
    console.log('编辑')
    console.log(index, row)
    updateList.typeName = row.typeName
    typeId.value = row.id
    dialogVisible.value = true
    title.value = '编辑商品类型'
  }
  const handleAddgoods = () => {
    console.log('添加')
    updateList.typeName = ''
    dialogVisible.value = true
    title.value = '添加商品类型'
  }
  const handleAnd = () => {
    formRef.value.validate(async (valid) => {
      if (valid) {
        if (title.value === '编辑商品类型') {
          const updatequery = ref({
            id: typeId.value,
            typeName: updateList.typeName
          })
          console.log(updatequery.value)
          await updateGoodsType(updatequery.value)
        } else {
          console.log(updateList)
          await addGoodsType(updateList)
        }
        ElMessage({
          message: '操作成功',
          type: 'success'
        })
      } else {
        console.log('提交失败')
      }
    })
    dialogVisible.value = false
    initGetAllGoodsType()
  }

</script>

<style lang="scss" scoped></style>
