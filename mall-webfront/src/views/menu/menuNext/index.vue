<template>
  <div class="app-container">
    <div class="filter-container" style="margin-top:15px">
      <div>
        <span>数据列表</span>
        <el-button style="float: right" @click="handleAddMenu()" size="medium">
          添加
        </el-button>
      </div>
    </div>

    <el-table :data="menusList" border style="width: 100%">
      <el-table-column label="id" width="80" align="center">
        <template #default="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column property="title" label="菜单名称" align="center">
        <template #default="scope">{{ scope.row.title }}</template>
      </el-table-column>
      <el-table-column label="级别" width="100" align="center">
        <template #default="scope">{{ scope.row.level }}</template>
      </el-table-column>
      <el-table-column label="父级id" width="80" align="center">
        <template #default="scope">{{ scope.row.parentId }}</template>
      </el-table-column>
      <el-table-column label="路径" width="80" align="center">
        <template #default="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="icon" width="80" align="center">
        <template #default="scope">{{ scope.row.icon }}</template>
      </el-table-column>
      <el-table-column label="排序" width="80" align="center">
        <template #default="scope">{{ scope.row.sort }}</template>
      </el-table-column>
      <el-table-column label="创建时间" width="120" align="center">
        <template #default="scope">{{ scope.row.createdTime }}</template>
      </el-table-column>
      <el-table-column label="是否隐藏" width="120" align="center">
        <template #default="scope">
          <p>隐藏：
            <el-switch :active-value="1" :inactive-value="0" v-model="scope.row.hidden"
              @change="beforeChange(scope.row)">
              <!-- 1是上架，0是下架  -->
            </el-switch>
          </p>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center">
        <template #default="scope">
          <el-button size="mini" @click="handleUpdateMenu(scope.$index, scope.row)">编辑
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination v-model:currentPage="page.pageNum" v-model:page-size="page.pageSize" :page-sizes="[5, 10, 15, 20]"
        layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogType === 1 ? '编辑菜单' : '添加菜单'" width="30%"
      :before-close="handleClose">
      <el-form>
        <el-form-item label="菜单id" prop="id">
          <el-input v-model="updateMenuList.id" disabled placeholder="商品名称" />
        </el-form-item>
        <el-form-item label="菜单名称" prop="title">
          <el-input v-model="updateMenuList.title" placeholder="商品名称" />
        </el-form-item>
        <el-form-item label="菜单路径" prop="name">
          <el-input v-model="updateMenuList.name" placeholder="商品名称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="AddMenu">确定</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
  import {
    ref,
    reactive
  } from 'vue'
  import {
    useRoute
  } from 'vue-router'
  import {
    menuListByParentId,
    menuUpdateHidden,
    menuAdd,
    menuUpdate,
    menuDelete
  } from '@/api/menu'
  import {
    ElMessage,
    ElMessageBox
  } from 'element-plus'

  const route = useRoute()
  const id = ref()
  if (route.query != null) {
    id.value = route.query.id
  }

  const page = ref({
    parentId: id.value,
    pageNum: 1,
    pageSize: 10
  })
  const total = ref(1)
  const menusList = ref([])
  const initMenusList = async () => {
    const res = await menuListByParentId(page.value)
    console.log(res.data)
    menusList.value = res.data.records
    total.value = res.data.total
    console.log(menusList)
  }
  initMenusList()
  // 隐藏菜单
  const loading = ref(false)
  const beforeChange = (row) => {
    loading.value = true
    const queryHidden = ref({
      hidden: row.hidden,
      id: row.id
    })
    return new Promise((resolve) => {
      setTimeout(() => {
        menuUpdateHidden(queryHidden.value)
        loading.value = false
        if (row.hidden === 1) {
          ElMessage.success('隐藏成功')
        } else {
          ElMessage.success('不隐藏')
        }
        return resolve(true)
      }, 1000)
    })
  }
  // 添加菜单
  const updateMenuList = reactive({
    id: 30,
    parentId: id.value,
    title: '',
    level: 1,
    sort: 0,
    name: '',
    icon: 'study',
    hidden: 0

  })
  const dialogVisible = ref(false)
  const dialogType = ref()
  // 添加菜单按钮事件
  const handleAddMenu = () => {
    console.log('添加')
    updateMenuList.id = ''
    updateMenuList.name = ''
    updateMenuList.title = ''
    dialogType.value = 0
    dialogVisible.value = true
  }
  // 编辑菜单按钮
  const handleUpdateMenu = (index, row) => {
    console.log('编辑')
    dialogType.value = 1
    dialogVisible.value = true
    updateMenuList.id = row.id
    updateMenuList.parentId = row.parentId
    updateMenuList.title = row.title
    updateMenuList.level = row.level
    updateMenuList.sort = row.sort
    updateMenuList.name = row.name
    updateMenuList.icon = row.icon
    updateMenuList.hidden = row.hidden
  }
  // 添加，编辑
  const AddMenu = async () => {
    console.log(updateMenuList)
    if (dialogType.value === 1) {
      console.log(updateMenuList)
      await menuUpdate(updateMenuList.id, updateMenuList)
    } else {
      await menuAdd(updateMenuList)
    }
    dialogVisible.value = false
    initMenusList()
  }
  // 删除
  const handleDelete = (index, row) => {
    ElMessageBox.confirm('是否确认删除该菜单', '确认', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
      .then(async () => {
        await menuDelete(row.id)
        ElMessage({
          type: 'success',
          message: '删除成功'
        })
        initMenusList()
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消删除'
        })
      })
  }

  const handleSizeChange = (pageSize) => {
    page.value.pageNum = 1
    page.value.pageSize = pageSize
    initMenusList()
  }

  const handleCurrentChange = (pageNum) => {
    page.value.pageNum = pageNum
    initMenusList()
  }

</script>

<style lang="scss" scoped></style>
