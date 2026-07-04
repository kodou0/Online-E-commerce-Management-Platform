<template>
  <el-input @click="dialogVisible = true" v-model="currentIconName">
  </el-input>
  <el-dialog v-model="dialogVisible" title="请选择图标" width="50%" :before-close="handleClose" @open="beforeOpen">
    <el-scrollbar height="400px">
    <div style="display: flex;flex-wrap: wrap;">
      <div v-for="(name,index) in icons" :index="index" :key="index" style="cursor: pointer;padding: 1rem"
        :class="currentIconName === name ? 'red' : ''" @click="currentIconName = name">
        <component :is="name" style="width: 2rem;height: 2rem">
        </component>
      </div>
    </div>
    </el-scrollbar>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleOk">确定</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script>
  import * as ElIcons from '@element-plus/icons-vue'
  import {
    reactive,
    toRefs,
    watch
  } from 'vue'

  export default {
    components: {
      ...ElIcons
    },
    name: 'Naruto-Icon.vue',
    emits: ['update:iconName'],
    props: {
      iconName: {
        type: String
      }
    },
    setup(props, context) {
      const getData = () => {
        const icons = []
        for (const name in ElIcons) {
          icons.push(name)
        }
        return icons
      }
      const handleClose = () => {
        iconList.dialogVisible = false
      }
      const beforeOpen = () => {

      }
      const handleOk = () => {
        context.emit('update:iconName', iconList.currentIconName)
        handleClose()
      }
      const iconList = reactive({
        icons: getData(),
        dialogVisible: false,
        currentIconName: 'Aim'
      })
      watch(() => props.iconName, (val) => {
        iconList.currentIconName = val
      })
      return {
        ...toRefs(iconList),
        handleClose,
        beforeOpen,
        handleOk
      }
    }
  }

</script>

<style scoped lang="scss">
  .red {
    background-color: palevioletred;
    color: white;
  }

</style>
