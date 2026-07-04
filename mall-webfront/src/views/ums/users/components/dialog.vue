<template>
  <el-dialog
    :model-value="dialogVisible"
    :title="dialogTitle"
    width="40%"
    @close="handleClose"
  >
    <el-form ref="formRef" :model="form" label-width="80px" :rules="rules">
      <!-- :rules="props.dialogTitle === '添加' ? rules : false" -->
      <el-form-item label="账号" prop="username" v-if="dialogTitle === '添加'">
        <el-input v-model="form.username" placeholder=" 账号唯一" />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="密码" prop="password" v-if="dialogTitle === '添加'">
        <el-input v-model="form.password" type="password" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="form.gender">
          <el-radio label="0">未知</el-radio>
          <el-radio label="1">男</el-radio>
          <el-radio label="2">女</el-radio>
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
import { addUser, editUser } from '@/api/users'
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
  username: '',
  name: '',
  password: '',
  gender: '',
  phone: ''
})

const rules = ref({
  username: [
    {
      required: true,
      message: '请输入',
      trigger: 'blur'
    },
    {
      type: 'string',
      pattern: /^[A-Za-z0-9]+$/,
      message: '由英文字母和数字组成'
    }
  ],
  password: [
    {
      required: true,
      message: '请输入',
      trigger: 'blur'
    }
  ],
  name: [
    {
      required: true,
      message: '请输入',
      trigger: 'blur'
    }
  ],
  gender: [
    {
      required: true,
      message: '请选择',
      trigger: 'blur'
    }
  ],
  phone: [
    {
      required: true,
      message: '请输入',
      trigger: 'blur'
    },
    {
      type: 'string',
      pattern:
        /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
      message: '请正确填写您的手机号码！'
    }
  ]
})

watch(
  () => props.dialogTableValue,
  () => {
    console.log(props.dialogTableValue)
    form.value = props.dialogTableValue
    if (props.dialogTitle === '编辑') {
      form.value.gender = props.dialogTableValue.gender.toString()
    }
  },
  { deep: true, immediate: true }
)

const emits = defineEmits(['update:modelValue', 'initUserList'])

const handleClose = () => {
  emits('update:modelValue', false)
}

const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      if (props.dialogTitle === '编辑') {
        const edit = ref({
          name: form.value.name,
          gender: form.value.gender,
          phone: form.value.phone,
          id: form.value.id
        })
        await editUser(edit.value)
      } else {
        await addUser(form.value)
      }
      ElMessage({
        message: '操作成功',
        type: 'success'
      })
      emits('initUserList')
      handleClose()
    } else {
      console.log('提交失败')
      return false
    }
  })
}
</script>

<style lang="scss" scoped></style>
