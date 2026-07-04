<template>
  <el-dialog
    :model-value="dialogVisible"
    :title="dialogTitle"
    width="40%"
    @close="handleClose"
  >
    <el-form ref="formRef" :model="form" label-width="70px" :rules="rules">
      <!-- :rules="props.dialogTitle === '添加' ? rules : false" -->
      <el-form-item label="角色id" prop="id" v-if="dialogTitle === '编辑'">
        <el-input disabled v-model="form.id" />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio label="0">正常</el-radio>
          <el-radio label="1">停用</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleConfirm">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { defineEmits, ref, defineProps, watch } from 'vue'
import { addRole, editRole } from '@/api/role'
import { ElMessage } from 'element-plus'

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

const formRef = ref(null)

const form = ref({
  name: '',
  status: '',
  remark: ''
})

const rules = ref({
  name: [
    {
      required: true,
      message: '请输入',
      trigger: 'blur'
    }
  ],
  status: [
    {
      required: true,
      message: '请选择',
      trigger: 'blur'
    }
  ],
  remark: [
    {
      required: true,
      message: '请输入',
      trigger: 'blur'
    }
  ]
})

watch(
  () => props.dialogTableValue,
  () => {
    console.log(props.dialogTableValue)
    form.value = props.dialogTableValue
    if (props.dialogTitle === '编辑') {
      form.value.status = props.dialogTableValue.status.toString()
    }
  },
  { deep: true, immediate: true }
)
const emits = defineEmits(['update:modelValue', 'initRoleList'])

const handleClose = () => {
  emits('update:modelValue', false)
}

const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      props.dialogTitle === '添加'
        ? await addRole(form.value)
        : await editRole(form.value)
      ElMessage({
        message: '操作成功',
        type: 'success'
      })
      emits('initRoleList')
      handleClose()
    } else {
      console.log('提交失败')
      return false
    }
  })
}
</script>

<style lang="scss" scoped></style>
