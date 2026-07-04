<template>
  <el-dropdown>
    <span class="el-dropdown-link">
      <el-avatar shape="square" :size="40" :src="squareUrl"></el-avatar>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="logout">退出</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { logout as logotApi } from '@/api/login'

const store = useStore()

const squareUrl = ref(
  'https://pic1.zhimg.com/80/v2-fbc9e879edcac179c13079e7c247f0fb_720w.jpg?source=1940ef5c'
)

const logout = () => {
  logotApi()
  .then((res) => {
    store.dispatch('app/logout')
   })
  // store.dispatch('app/logout')
}
</script>

<style lang="scss" scoped>
::v-deep .el-dropdown-menu__item {
  white-space: nowrap;
}
</style>
