<template>
  <el-card>
    <el-row :gutter="20" class="header">
      <el-col :span="6">
        <el-input
          :placeholder="$t('table.placeholder')"
          clearable
          v-model="queryForm.name"
        ></el-input>
      </el-col>
      <el-button type="primary" :icon="Search" @click="initGetUsersList">{{
        $t('table.search')
      }}</el-button>
      <el-button type="primary" @click="handleDialogValue()">{{
        $t('table.adduser')
      }}</el-button>
      <div class="role">
        <el-select
          v-model="queryForm.roleId"
          placeholder="请选择搜索的角色名称"
          @change="initGetUsersList"
        >
          <el-option label="全部角色" value="" />
          <el-option
            v-for="(item, index) in roleList"
            :label="item.name"
            :value="item.id"
            :key="index"
          />
        </el-select>
      </div>
    </el-row>

    <el-table :data="tableData" border style="width: 100%">
      <el-table-column
        align="center"
        :width="item.width"
        :prop="item.prop"
        :label="item.label"
        v-for="(item, index) in option"
        :key="index"
      >
        <template #default="{ row }" v-if="item.prop === 'gender'">
          <p v-if="row.gender === 0">未知</p>
          <p v-if="row.gender === 1">男</p>
          <p v-if="row.gender === 2">女</p>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="350" align="center">
        <template #default="scope">
          <el-button
            type="primary"
            plain
            :icon="Edit"
            size="small"
            @click="handleDialogValue(scope.row)"
          >
            编辑
          </el-button>
          <el-button
            type="danger"
            plain
            :icon="Delete"
            size="small"
            @click="delUser(scope.row)"
            >删除
          </el-button>
          <el-button
            type="info"
            plain
            :icon="Postcard"
            size="small"
            @click="setRole(scope.row)"
            >角色分配
          </el-button>
        </template>
      </el-table-column>
    </el-table>
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
  </el-card>

  <el-dialog
    v-model="dialogFormVisible"
    v-if="dialogFormVisible"
    title="分配角色"
    width="400px"
  >
    <el-form :model="roleForm">
      <el-form-item label="角色" width="100%">
        <el-select v-model="roleForm.roleId">
          <el-option
            v-for="(item, index) in roleList"
            :label="item.name"
            :value="item.id"
            :key="index"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button
          type="primary"
          @click="submitRole"
          :disabled="roleForm.roleId == ''"
          >确认</el-button
        >
      </span>
    </template>
  </el-dialog>

  <Dialog
    v-model="dialogVisible"
    :dialogTitle="dialogTitle"
    v-if="dialogVisible"
    @initUserList="initGetUsersList"
    :dialogTableValue="dialogTableValue"
  />
</template>

<script setup>
import { Search, Edit, Delete, Postcard } from '@element-plus/icons-vue'
import { ref } from 'vue'
import {
  getUser,
  deleteUser,
  getSimpleList,
  getUserRole,
  allocate
} from '@/api/users'
import { ElMessage, ElMessageBox } from 'element-plus'
import Dialog from './components/dialog.vue'
import { isNull } from '@/utils/filters'
// import { getRole } from '@/api/role'

const roleList = ref([])
const initGetRoleList = async () => {
  roleList.value = (await getSimpleList()).data
  console.log(roleList.value)
}
initGetRoleList()

const queryForm = ref({
  roleId: '',
  name: '',
  pageNum: 1,
  pageSize: 10
})
const total = ref(1)

const dialogVisible = ref(false)
const dialogTitle = ref('')
const dialogTableValue = ref({})

const tableData = ref([])

const option = [
  {
    label: 'id',
    prop: 'id',
    width: '120px'
  },
  {
    label: '用户账号',
    prop: 'username'
  },
  {
    label: '姓名',
    prop: 'name'
  },
  {
    label: '性别',
    prop: 'gender',
    width: '120px'
  },
  {
    label: '电话',
    prop: 'phone'
  }
]

const initGetUsersList = async () => {
  const res = await getUser(queryForm.value)
  console.log(res)
  total.value = res.data.total
  tableData.value = res.data.records
}
initGetUsersList()

const handleSizeChange = (pageSize) => {
  queryForm.value.pageNum = 1
  queryForm.value.pageSize = pageSize
  initGetUsersList()
}

const handleCurrentChange = (pageNum) => {
  queryForm.value.pageNum = pageNum
  initGetUsersList()
}

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

const delUser = (row) => {
  ElMessageBox.confirm('是否确认删除该用户', '确认', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      const delForm = ref({
        id: row.id
      })
      await deleteUser(delForm.value)
      ElMessage({
        type: 'success',
        message: '删除成功'
      })
      initGetUsersList()
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除'
      })
    })
}

const roleForm = ref({
  roleId: '',
  userId: ''
})
const dialogFormVisible = ref(false)
const setRole = async (row) => {
  roleForm.value.userId = row.userId
  const res = await getUserRole({ userId: row.userId })
  if (res.data) {
    roleForm.value.roleId = res.data.id
  } else {
    roleForm.value.roleId = ''
  }
  console.log(roleForm.value.roleId)
  dialogFormVisible.value = true
}
const submitRole = async () => {
  console.log(roleForm.value)
  allocate(roleForm.value)
  ElMessage({
    type: 'success',
    message: '分配成功'
  })
  dialogFormVisible.value = false
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

.dialog-footer {
  align-items: center;
}

.role {
  position: absolute;
  right: 20px;
  width: 150px;
}
</style>
