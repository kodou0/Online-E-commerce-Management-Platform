<template>
  <el-menu active-text-color="#ffd04b" :background-color="variables.menuBg" class="el-menu-vertical-demo"
    :default-active="defaultActive" text-color="#fff" router unique-opened :collapse="!$store.getters.siderType">
    <el-sub-menu :index="item.id" v-for="item in menusList" :key="item.id">
      <template #title v-if="item.hidden === 0">
        <el-icon>
          <component :is="item.icon"></component>
        </el-icon>
        <span>{{ item.title }}</span>
      </template>
      <el-menu-item :index="'/' + it.name" v-for="it in item.children" :key="it.id" @click="savePath(it.name)">
        <template #title>
          <el-icon>
            <component :is="icon"></component>
          </el-icon>
          <span>{{ it.title }}</span>
        </template>
      </el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
  import {
    ref
  } from 'vue'
  import variables from '@/styles/variables.scss'
  import {
    menuListByParentIdRole
  } from '@/api/menu'

  // const iconList = ref(['user', 'setting', 'shop', 'tickets', 'pie-chart', 'SetUp'])
  const icon = ref('menu')

  const defaultActive = ref(sessionStorage.getItem('path') || '/shoppingCart')

  const menusList = ref([])
  const initMenusList = async () => {
    const res = await menuListByParentIdRole()
    res.data.forEach((item) => {
      if (item.hidden === 0) {
        menusList.value.push(item)
      }
    })
  }
  initMenusList()

  const savePath = (path) => {
    sessionStorage.setItem('path', `/${path}`)
  }
</script>

<style lang="scss" scoped></style>
