<template>
  <div class="app-container">
    <div class="filter-container">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button style="float: right" @click="handleSearchList()" type="primary" size="medium">查询结果
        </el-button>
        <el-button style="float: right;margin-right: 15px" @click="handleResetSearch()" size="medium">
          重置
        </el-button>
      </div>
      <el-form :inline="true" :model="listQuery" ref="resetFormData" class="demo-form-inline" style="padding-top:15px">
        <el-form-item label="资源名称：">
          <el-input v-model="listQuery.name" class="input-width" placeholder="资源名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="资源路径：">
          <el-input v-model="listQuery.path" class="input-width" placeholder="资源路径" clearable></el-input>
        </el-form-item>
        <el-form-item label="资源分类：">
          <el-select v-model="listQuery.categoryId" placeholder="全部" clearable class="input-width">
            <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </div>

    <el-card class="operate-container" shadow="never">
      <el-icon>
        <component :is="icon" style="width:50px;height:50px"></component>
      </el-icon>
      <span>数据列表</span>
      <el-button size="mini" class="btn-add" @click="handleAdd()" style="margin-left: 20px">添加</el-button>
      <el-button size="mini" class="btn-add" @click="handleShowCategory()">资源分类</el-button>
    </el-card>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column label="编号" width="100" align="center">
        <template #default="scope">{{scope.row.id}}</template>
      </el-table-column>
      <el-table-column label="资源名称" align="center">
        <template #default="scope">{{scope.row.name}}</template>
      </el-table-column>
      <el-table-column label="资源路径" align="center">
        <template #default="scope">{{scope.row.permission}}</template>
      </el-table-column>
      <el-table-column label="描述" align="center">
        <template #default="scope">{{scope.row.description}}</template>
      </el-table-column>
      <el-table-column label="资源分类" align="center">
        <template #default="scope">{{scope.row.categoryId}}</template>
      </el-table-column>
      <el-table-column label="添加时间" width="160" align="center">
        <template #default="scope">{{scope.row.createTime}}</template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center">
        <template #default="scope">
          <el-button size="mini" @click="handleUpdate(scope.$index, scope.row)">编辑
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination v-model:currentPage="listQuery.pageNum" v-model:page-size="listQuery.pageSize"
        :page-sizes="[5, 10, 15, 20]" layout="total, sizes, prev, pager, next, jumper" :total="total"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>

    <el-dialog :title="isEdit?'编辑资源':'添加资源'" v-model="dialogVisible" :before-close="handleClose">
      <el-form :model="resource" ref="resourceForm" label-width="150px" size="large" :rules="isEdit ? false : rules">
        <el-form-item label="资源名称：" prop="name">
          <el-input v-model="resource.name" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="资源路径：" prop="permission">
          <el-input v-model="resource.permission" style="width: 250px"></el-input>
        </el-form-item>
        <el-form-item label="资源分类：" prop="categoryId">
          <el-select v-model="resource.categoryId" placeholder="全部" clearable style="width: 250px">
            <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述：" prop="description">
          <el-input v-model="resource.description" type="textarea" :rows="5" style="width: 250px"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleDialogConfirm()">确 定</el-button>
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
    getResource,
    addResource,
    updateResource,
    delResource
  } from '@/api/resource'
  import {
    ElMessage,
    ElMessageBox
  } from 'element-plus'

  const icon = ref('Postcard')
  const listQuery = reactive({
    name: '',
    path: '',
    categoryId: '',
    pageNum: 1,
    pageSize: 10
  })
  const categoryOptions = ref([{
      value: 1,
      label: '用户管理'
    },
    {
      value: 2,
      label: '权限模块'
    },
    {
      value: 3,
      label: '商品模块'
    },
    {
      value: 4,
      label: '订单模块'
    },
    {
      value: 5,
      label: '数据统计'
    },
    {
      value: 6,
      label: '其他模块'
    }
  ])

  // 获取列表
  const tableData = ref([])
  const total = ref(1)
  const initGetResourceList = async () => {
    const res = await getResource(listQuery)
    console.log(res.data)
    tableData.value = res.data.records
    total.value = res.data.total
    console.log(tableData.value)
  }
  initGetResourceList()

  // 重置
  const resetFormData = ref(null)
  const handleResetSearch = () => {
    resetFormData.value.resetFields()
    console.log(0)
    console.log(resetFormData.value)
  }

  // 查询
  const handleSearchList = () => {
    initGetResourceList()
  }

  // 对话框
  const dialogVisible = ref(false)
  const isEdit = ref(false)
  const resource = reactive({
    name: '',
    permission: '',
    categoryId: '',
    description: ''
  })
  const resourceForm = ref(null)
  const rules = ref({
    name: [{
      required: true,
      message: '请输入',
      trigger: 'blur'
    }],
    permission: [{
      required: true,
      message: '请输入',
      trigger: 'blur'
    }],
    description: [{
      required: true,
      message: '请输入',
      trigger: 'blur'
    }]
  })

  // 添加
  const handleAdd = () => {
    dialogVisible.value = true
    resource.name = ''
    resource.permission = ''
    resource.categoryId = ''
    resource.description = ''
  }

  // 编辑
  const resourceId = ref()
  const handleUpdate = (index, row) => {
    isEdit.value = true
    dialogVisible.value = true
    resourceId.value = row.id
    resource.name = row.name
    resource.permission = row.permission
    resource.categoryId = row.categoryId
    resource.description = row.description
  }

  // 获取当前时间
  // const getNowData = () => {
  //   const nowData = new Date()
  //   const year = nowData.getFullYear()
  //   const month = nowData.getMonth() + 1
  //   const day = nowData.getDate()
  //   return year + '-0' + month + '-0' + day
  // }

  // 编辑、添加确定
  const handleDialogConfirm = () => {
    resourceForm.value.validate(async (valid) => {
      if (valid) {
        if (isEdit.value) {
          console.log('编辑')
          resource.id = resourceId.value
          console.log(resource)
          await updateResource(resource)
          ElMessage({
            message: '添加成功',
            type: 'success'
          })
          initGetResourceList()
          dialogVisible.value = false
        } else {
          console.log('添加')
          resource.createTime = new Date()
          console.log(resource)
          await addResource(resource)
          ElMessage({
            message: '添加成功',
            type: 'success'
          })
          initGetResourceList()
          dialogVisible.value = false
        }
      }
    })
  }

  // 删除
  const handleDelete = (index, row) => {
    ElMessageBox.confirm('是否确认删除该商品', '确认', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      await delResource(row.id)
      ElMessage({
        type: 'success',
        message: '删除成功'
      })
      initGetResourceList()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除'
      })
    })
  }

  // 分页器改变页数
  const handleSizeChange = (pageSize) => {
    listQuery.pageNum = 1
    listQuery.pageSize = pageSize
    initGetResourceList()
  }
  // 分页器获取当前页数
  const handleCurrentChange = (pageNum) => {
    listQuery.pageNum = pageNum
    initGetResourceList()
  }

</script>

<style lang="scss" scoped>
  .pagination-container {
    padding-bottom: 100px
  }

  ::v-deep .el-input__prefix {
    align-items: center;
  }

  ::v-deep .el-input__suffix {
    align-items: center;
  }

</style>
