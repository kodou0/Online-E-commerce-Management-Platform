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
      <el-table-column label="设置" align="center">
        <template #default="scope">
          <el-button size="mini" @click="handleShowNextLevel(scope.$index, scope.row)">查看下级
          </el-button>
          <!--el-button size="mini" @click="handleTransferProduct(scope.$index, scope.row)">转移商品
          </el-button-->
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
      <el-pagination v-model:currentPage="queryList.pageNum" v-model:page-size="queryList.pageSize" :page-sizes="[5, 10, 15, 20]"
        layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogType === 1 ? '编辑菜单' : '添加菜单'" width="30%" :before-close="handleClose">
      <el-form>
        <el-form-item label="菜单id" prop="id" v-show="dialogType === 1">
          <el-input v-model="updateMenuList.id" disabled placeholder="商品名称" />
        </el-form-item>
        <el-form-item label="菜单名称" prop="title">
          <el-input v-model="updateMenuList.title" placeholder="商品名称" />
        </el-form-item>
        <el-form-item label="菜单路径" prop="name">
          <el-input v-model="updateMenuList.name" placeholder="商品名称" />
        </el-form-item>
        <el-form-item label="菜单图标" prop="icon">
          <IconSelect @update:iconName="updateIcon"/>
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
    ref, reactive
  } from 'vue'
  import {
    useRouter
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
  import IconSelect from '../component/Naruto-Icon/Naruto-Icon.vue'

  const router = useRouter()
  const handleShowNextLevel = (index, row) => {
    console.log('查看下级')
    router.push({
      path: '/menuNext',
      query: {
        id: row.id
      }
    })
  }

  const updateIcon = (value) => {
    updateMenuList.icon = value
    console.log(value)
  }

  const menusList = ref([])
  const total = ref(1)
  const queryList = ref({
    parentId: 0,
    pageNum: 1,
    pageSize: 10
  })
  const initMenusList = async () => {
    const res = await menuListByParentId(queryList.value)
    console.log(res.data)
    menusList.value = res.data.records
    total.value = res.data.total
    console.log(menusList)
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
    parentId: 0,
    title: '',
    level: 0,
    sort: 0,
    name: '',
    icon: '',
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
    updateMenuList.createdTime = '2020-02-03 08:20:16'
  }
// 添加，编辑
  const AddMenu = async() => {
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
      const delForm = ref({
        id: row.id
      })
      await menuDelete(delForm.value)
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

  // 分页器改变页数
  const handleSizeChange = (pageSize) => {
    queryList.value.pageNum = 1
    queryList.value.pageSize = pageSize
    initMenusList()
  }
  // 分页器获取当前页数
  const handleCurrentChange = (pageNum) => {
    queryList.value.pageNum = pageNum
    initMenusList()
  }

</script>

<style lang="scss" scoped></style>
