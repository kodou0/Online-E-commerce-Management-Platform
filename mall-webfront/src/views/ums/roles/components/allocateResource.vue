<template>
  <el-dialog :model-value="dialogVisible" :title="dialogTitle" width="70%" @close="handleClose">
    <el-card>
      <el-table :data="tableData" border style="width: 100%" ref="multipleTable"
        @selection-change="handleSelectionChange" :row-key="getRowKeys">
        <el-table-column type="selection" width="50" align="center" :reserve-selection="true" />
        <el-table-column align="center" :width="item.width" :prop="item.prop" :label="item.label"
          v-for="(item, index) in option" :key="index">
        </el-table-column>
      </el-table>
      <div class="pagination-container">
        <el-pagination v-model:currentPage="query.pageNum" v-model:page-size="query.pageSize"
          :page-sizes="[5, 10, 15, 20]" layout="total, sizes, prev, pager, next, jumper" :total="total"
          @size-change="handleSizeChange" @current-change="handleCurrentChange" />
      </div>
    </el-card>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
  import {
    defineEmits,
    ref,
    defineProps,
    watch
  } from 'vue'
  // import { addRole, editRole } from '@/api/role'
  import {
    ElMessage
  } from 'element-plus'
  import {
    getResource, getResourceById
  } from '@/api/resource'
  import {
    allocateResource
  } from '@/api/role'

  const props = defineProps({
    dialogTitle: {
      type: String,
      default: '',
      required: true
    },
    dialogTableValue: {
      type: Object,
      default: () => {}
    }
  })

  const roleId = ref()
  const ids = ref([])

  const tableData = ref([])
  const option = [{
      label: '编号',
      prop: 'id',
      width: '120px'
    },
    {
      label: '资源名称',
      prop: 'name'
    },
    {
      label: '资源类型',
      prop: 'categoryId'
    },
    {
      label: '路径',
      prop: 'permission'
    },
    {
      label: '描述',
      prop: 'description'
    }
  ]

  const query = ref({
    pageNum: 1,
    pageSize: 10
  })

  watch(
    () => props.dialogTableValue,
    () => {
      console.log(props.dialogTableValue)
      roleId.value = props.dialogTableValue.id
    }, {
      deep: true,
      immediate: true
    }
  )
  const emits = defineEmits(['update:modelValue', 'initRoleList'])

  const total = ref(0)
  const initGetTabelData = async () => {
    const res = await getResource(query.value)
    console.log(res.data)
    tableData.value = res.data.records
    total.value = res.data.total
    console.log(tableData.value)
    getResourceByRoleId()
  }
  initGetTabelData()

  const getResourceByRoleId = async () => {
    const res = await getResourceById(roleId.value)
    res.data.forEach((item) => {
      const index = getArrayIndex(tableData.value, item.id)
      console.log(index)
      multipleTable.value.toggleRowSelection(tableData.value[index], true)
    })
  }

  const getArrayIndex = (arr, id) => {
    let i = arr.length
    console.log(arr)
    while (i--) {
      if (arr[i].id === id) {
        return i
      }
    }
    return -1
  }

  const handleClose = () => {
    emits('update:modelValue', false)
  }

  const handleConfirm = async () => {
    const query = ref({
      roleId: roleId.value,
      resourceIds: ids.value
    })
    await allocateResource(query.value)
    ElMessage({
      message: '操作成功',
      type: 'success'
    })
    emits('initRoleList')
    handleClose()
  }

  const getRowKeys = (row) => {
    return row.id
  }

  const multipleTable = ref(null)

  const multipleSelection = ref([])
  const handleSelectionChange = (val) => {
    if (multipleTable.value.toggleRowSelect) {
      return
    }
    multipleSelection.value = val
    console.log('multipleSelection', multipleSelection.value)
    ids.value = []
    multipleSelection.value.map((item) => {
      ids.value.push(item.id)
    })
    console.log('ids', ids.value)
  }

  const handleSizeChange = (pageSize) => {
    query.value.pageNum = 1
    query.value.pageSize = pageSize
    initGetTabelData()
  }

  const handleCurrentChange = (pageNum) => {
    query.value.pageNum = pageNum
    initGetTabelData()
  }

</script>

<style lang="scss" scoped></style>
