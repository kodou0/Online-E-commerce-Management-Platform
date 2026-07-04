<template>
  <el-dialog :model-value="dialogVisible" :title="dialogTitle" width="60%" @close="handleClose">
    <el-card>
      <el-table :data="tableData" border style="width: 100%" ref="tableEle" @selection-change="handleSelectionChange"
        :row-class-name="rowClassNameFun" @select="select" @select-all="selectAll" node-key="id" row-key="id">
        <el-table-column width="50"></el-table-column>
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column align="center" :width="item.width" :prop="item.prop" :label="item.label"
          v-for="(item, index) in option" :key="index">
        </el-table-column>
      </el-table>
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
    menuList
    // menuListByParentIdRole
  } from '@/api/menu'
  import {
    allocateMenu
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
      label: '菜单名称',
      prop: 'title'
    },
    {
      label: '父级id',
      prop: 'parentId'
    },
    {
      label: '路径',
      prop: 'name'
    },
    {
      label: '级别',
      prop: 'level'
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

  const initGetTabelData = async () => {
    const res = await menuList(query)
    tableData.value = res.data
    // initMenusList()
  }
  initGetTabelData()

  // const initMenusList = async () => {
  //   const res = await menuListByParentIdRole()
  //   res.data.forEach((item) => {
  //     isIndeterminateMap.value[item.id] = true
  //     const index = getArrayIndex(tableData.value, item.id)
  //     tableEle.value.toggleRowSelection(tableData.value[index], true)
  //     if (item.children) {
  //       item.children.forEach((it) => {
  //         isIndeterminateMap.value[it.id] = true
  //         const index1 = getArrayIndex(tableData.value[index].children, it.id)
  //         tableEle.value.toggleRowSelection(tableData.value[index].children[index1], true)
  //       })
  //     }
  //   })
  //   console.log(isIndeterminateMap.value)
  // }

  // const getArrayIndex = (arr, id) => {
  //   let i = arr.length
  //   while (i--) {
  //     if (arr[i].id === id) {
  //       return i
  //     }
  //   }
  //   return -1
  // }

  const handleClose = () => {
    emits('update:modelValue', false)
  }

  const handleConfirm = async () => {
    console.log('ids', ids.value)
    const query = ref({
      roleId: roleId.value,
      menuIds: ids.value
    })
    await allocateMenu(query.value)
    ElMessage({
      message: '操作成功',
      type: 'success'
    })
    emits('initRoleList')
    handleClose()
  }

  const tableEle = ref(null)
  const isIndeterminateMap = ref({})

  function initStatusMap(data) {
    data.forEach(ele => {
      isIndeterminateMap.value[ele.id] = false
      if (ele.children && ele.children.length) {
        initStatusMap(ele.children)
      }
    })
  }
  initStatusMap(tableData.value)
  console.log('isIndeterminateMap', isIndeterminateMap.value)

  function lookForAllId(data, findId) {
    let result
    for (let i = 0; i < data.length; i++) {
      if (data[i].id === findId) {
        return data[i]
      }
      if (data[i].children && data[i].children.length) {
        result = lookForAllId(data[i].children, findId)
      }
    }
    return result
  }

  const selectSonHandle = (ele, isSelect) => {
    ele.map(j => {
      tableEle.value.toggleRowSelection(j, isSelect)
      isIndeterminateMap.value[j.id] = isSelect
      if (j.children) {
        selectSonHandle(j.children, isSelect)
      }
    })
  }

  // 点击复选框事件
  const select = (selection, row) => {
    let isSelect = isIndeterminateMap.value[row.id]
    if (isSelect === 'indeterminate') {
      isSelect = true
      isIndeterminateMap.value[row.id] = true
    } else {
      isSelect = !isSelect
      isIndeterminateMap.value[row.id] = isSelect
    }

    if (row.children && row.children.length) {
      selectSonHandle(row.children, isSelect)
    }

    const parentId = row.parentId
    console.log('parentId', parentId)
    handlelook(row, parentId)
    console.log('is', isIndeterminateMap.value)
    ids.value = []
    for (const key in isIndeterminateMap.value) {
      if (isIndeterminateMap.value[key]) {
        console.log(true)
        ids.value.push(key)
      }
    }
    console.log(ids.value)
  }

  function handlelook(row, parentId) {
    if (row.parentId !== 0) {
      const ele = lookForAllId(tableData.value, parentId)
      if (ele) {
        const isALLTrue = selectStatus(ele, true)
        const isALLFalse = selectStatus(ele, false)
        if (isALLTrue) {
          console.log('全选')
          isIndeterminateMap.value[ele.id] = true
          tableEle.value.toggleRowSelection(ele, true)
        } else if (isALLFalse) {
          console.log('全不选')
          isIndeterminateMap.value[ele.id] = false
          tableEle.value.toggleRowSelection(ele, false)
        } else {
          console.log('不明确')
          tableEle.value.toggleRowSelection(ele, true)
          isIndeterminateMap.value[ele.id] = 'indeterminate'
        }
      }
      if (ele.parentId !== 0) {
        handlelook(ele, ele.parentId)
      }
    }
  }

  // 点击全选框事件
  const selectAll = (selection) => {
    const isSelect = selection.some(el => {
      const leverOneIds = tableData.value.map(j => j.id)
      return leverOneIds.includes(el.id)
    })

    const isCancel = !tableData.value.every(el => {
      const selectIds = selection.map(j => j.id)
      return selectIds.includes(el.id)
    })
    if (isSelect) {
      console.log('全选')
      const allSelectionHandle = (el) => {
        isIndeterminateMap.value[el.id] = true
        if (el.children && el.children.length) {
          el.children.map(j => {
            tableEle.value.toggleRowSelection(j, true)
            isIndeterminateMap.value[j.id] = true
            allSelectionHandle(j)
          })
        }
      }
      selection.map(el => {
        allSelectionHandle(el)
      })
    }
    if (isCancel) {
      console.log('全不选')
      const CancelHandle = (el) => {
        isIndeterminateMap.value[el.id] = false
        if (el.children && el.children.length) {
          el.children.map(j => {
            tableEle.value.toggleRowSelection(j, false)
            isIndeterminateMap.value[j.id] = false
            CancelHandle(j)
          })
        }
      }
      tableData.value.map(el => {
        CancelHandle(el)
      })
    }
  }

  const selectStatus = (item, status = true) => {
    const isAllSelect = []
    item.children.forEach((childrenItem) => {
      isAllSelect.push(isIndeterminateMap.value[childrenItem.id])
    })
    const isStauts = isAllSelect.every((selectItem) => {
      return status === selectItem
    })
    return isStauts
  }

  const rowClassNameFun = ({
    row
  }) => {
    const isSelect = isIndeterminateMap.value[row.id]
    if (isSelect === 'indeterminate') {
      return 'indeterminate'
    }
  }
</script>

<style lang="scss" scoped></style>
