<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="6">
        <el-input placeholder="请输入搜索的用户角色名称" clearable v-model="queryForm.name"></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initGetRoleList">{{
        $t('table.search')
      }}</el-button>
      <el-button type="primary" @click="handleDialogValue()">添加角色</el-button>
    </el-row>

    <el-table :data="tableData" border style="width: 100%">
      <el-table-column align="center" :width="item.width" :prop="item.prop" :label="item.label"
        v-for="(item, index) in option" :key="index">
        <template #default="{ row }" v-if="item.prop === 'status'">
          <p>
            是否禁用：
            <el-switch :active-value="1" :inactive-value="0" v-model="row.status" @change="beforeChange(row)">
            </el-switch>
          </p>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250" align="center">
        <template #default="scope">
          <p>
            <el-button type="primary" plain size="small" @click="handleAllocateMenu(scope.row)">
              分配菜单
            </el-button>
            <el-button type="primary" plain size="small" @click="handleAllocateResource(scope.row)">
              分配资源
            </el-button>
          </p>
          <p style="margin-top:5px">
            <el-button type="primary" plain :icon="Edit" size="small" @click="handleDialogValue(scope.row)">
              编辑
            </el-button>
            <el-button type="danger" plain :icon="Delete" size="small" @click="delRole(scope.row)">删除
            </el-button>
          </p>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination-container">
      <el-pagination v-model:currentPage="queryForm.pageNum" v-model:page-size="queryForm.pageSize"
        :page-sizes="[5, 10, 15, 20]" layout="total, sizes, prev, pager, next, jumper" :total="total"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>
  </el-card>
  <Dialog v-model="dialogVisible" :dialogTitle="dialogTitle" v-if="dialogVisible" @initRoleList="initGetRoleList"
    :dialogTableValue="dialogTableValue" />
  <AllocateResourceDialog v-model="dialogAllocate" :dialogTitle="allocateTitle" v-if="dialogAllocate" @initRoleList="initGetRoleList"
    :dialogTableValue="allocateValue" />
    <AllocateMenuDialog v-model="dialogAllocateMenu" :dialogTitle="allocateMenuTitle" v-if="dialogAllocateMenu" @initRoleList="initGetRoleList"
    :dialogTableValue="allocateMenuValue" />
</template>

<script setup>
  import {
    Search,
    Edit,
    Delete
  } from '@element-plus/icons-vue'
  import {
    ref
  } from 'vue'
  import {
    getRole,
    deleteRole,
    statusUpdate
  } from '@/api/role'
  import {
    ElMessage,
    ElMessageBox
  } from 'element-plus'
  import Dialog from './components/dialog.vue'
  import AllocateMenuDialog from './components/allocateMenu.vue'
  import AllocateResourceDialog from './components/allocateResource.vue'
  import {
    isNull
  } from '@/utils/filters'

  const queryForm = ref({
    name: '',
    pageNum: 1,
    pageSize: 10
  })
  const total = ref(1)
// 编辑添加参数
  const dialogVisible = ref(false)
  const dialogTitle = ref('')
  const dialogTableValue = ref({})
// 分配资源
  const dialogAllocate = ref(false)
  const allocateTitle = ref('')
  const allocateValue = ref({})
  // 分配菜单
  const dialogAllocateMenu = ref(false)
  const allocateMenuTitle = ref('')
  const allocateMenuValue = ref({})

  const tableData = ref([])

  const option = [{
      label: 'id',
      prop: 'id',
      width: '120px'
    },
    {
      label: '角色名称',
      prop: 'name'
    },
    {
      label: '备注',
      prop: 'remark'
    },
    {
      label: '创建时间',
      prop: 'createTime',
      width: '300px'
    },
    {
      label: '数量',
      prop: 'count',
      width: '120px'
    },
    {
      label: '角色状态',
      prop: 'status'
    }
  ]

  const initGetRoleList = async () => {
    const res = await getRole(queryForm.value)
    console.log(res)
    total.value = res.data.total
    tableData.value = res.data.records
  }
  initGetRoleList()

  const handleSizeChange = (pageSize) => {
    queryForm.value.pageNum = 1
    queryForm.value.pageSize = pageSize
    initGetRoleList()
  }

  const handleCurrentChange = (pageNum) => {
    queryForm.value.pageNum = pageNum
    initGetRoleList()
  }
// 添加编辑弹窗
  const handleDialogValue = (row) => {
    if (isNull(row)) {
      dialogTitle.value = '添加'
      dialogTableValue.value = {}
    } else {
      dialogTitle.value = '编辑'
      dialogTableValue.value = JSON.parse(JSON.stringify(row))
      console.log(dialogTableValue.value)
    }
    dialogVisible.value = true
  }
  // 分配菜单弹窗
  const handleAllocateMenu = (row) => {
    allocateMenuTitle.value = '分配菜单'
    allocateMenuValue.value = row
    dialogAllocateMenu.value = true
  }
  // 分配资源弹窗
  const handleAllocateResource = (row) => {
    allocateTitle.value = '分配资源'
    allocateValue.value = row
    dialogAllocate.value = true
  }
// 删除
  const delRole = (row) => {
    ElMessageBox.confirm('是否确认删除该用户', '确认', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
      .then(async () => {
        const delForm = ref({
          roleId: row.id
        })
        console.log(delForm.value)
        await deleteRole(delForm.value)
        ElMessage({
          type: 'success',
          message: '删除成功'
        })
        initGetRoleList()
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除'
        })
      })
  }

  const loading = ref(false)
  const beforeChange = (row) => {
    loading.value = true
    const query = ref({
      id: row.id,
      status: row.status
    })
    console.log(query.value)
    return new Promise((resolve) => {
      setTimeout(() => {
        statusUpdate(query.value)
        ElMessage({
          type: 'success',
          message: '成功'
        })
        loading.value = false
        return resolve(true)
      }, 1000)
    })
  }

</script>

<style lang="scss" scoped>
  .header {
    padding-bottom: 16px;
    box-sizing: border-box;
  }

  ::v-deep .el-input__suffix {
    align-items: center;
  }

</style>
