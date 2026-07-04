<template>
  <div class="app-container">
    <div class="filter-container" style="margin-top:15px">
      <div>
        <span>数据列表</span>
        <el-button style="float: right" size="medium">
          <router-link :to="{ path: '/categoriesUpadete'}">添加</router-link>
        </el-button>
      </div>
    </div>

    <el-table :data="tableData" border style="width: 100%">
      <el-table-column label="编号" width="100" align="center">
        <template #default="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column property="name" label="分类名称" align="center">
        <template #default="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="级别" width="120" align="center">
        <template #default="scope">{{ scope.row.level }}</template>
      </el-table-column>
      <el-table-column label="属性数量" width="120" align="center">
        <template #default="scope">{{ scope.row.productCount }}</template>
      </el-table-column>
      <el-table-column label="数量单位" width="120" align="center">
        <template #default="scope">{{ scope.row.productUnit }}</template>
      </el-table-column>
      <el-table-column label="排序" width="120" align="center">
        <template #default="scope">{{ scope.row.sort }}</template>
      </el-table-column>
      <el-table-column label="设置" width="280" align="center">
        <template #default="scope">
          <el-button size="mini" @click="handleShowNextLevel(scope.$index, scope.row)">查看下级
          </el-button>
          <!--el-button size="mini" @click="handleTransferProduct(scope.$index, scope.row)">转移商品
          </el-button-->
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160" align="center">
        <template #default="scope">
          <el-button size="mini">
            <router-link :to="{ path: '/categoriesUpadete'}">编辑</router-link>
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background v-model:currentPage="currentPage4" v-model:page-size="pageSize4"
        :page-sizes="[5, 10, 15, 20]" layout="total, sizes, prev, pager, next, jumper" :total="20"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>

  </div>
</template>

<script setup>
  import {
    ref
  } from 'vue'
  import {
    useRouter,
    useRoute
  } from 'vue-router'

  const currentPage4 = ref(4)
  const pageSize4 = ref(100)

  const tableData = [{
      id: '1',
      name: '手机',
      level: '1',
      productCount: '5',
      productUnit: '件',
      sort: '1'
    },
    {
      id: '1',
      name: '手机',
      level: '1',
      productCount: '5',
      productUnit: '件',
      sort: '1'
    },
    {
      id: '1',
      name: '手机',
      level: '1',
      productCount: '5',
      productUnit: '件',
      sort: '1'
    },
    {
      id: '1',
      name: '手机',
      level: '1',
      productCount: '5',
      productUnit: '件',
      sort: '1'
    },
    {
      id: '1',
      name: '手机',
      level: '1',
      productCount: '5',
      productUnit: '件',
      sort: '1'
    }
  ]
  const router = useRouter()
  const handleShowNextLevel = (index, row) => {
    console.log('查看下级')
    router.push({
      path: '/categories',
      query: {
        id: row.id
      }
    })
  }

  const route = useRoute()
  if (route.query != null) {
    console.log(route.query.id)
  }

</script>

<style lang="scss" scoped></style>
