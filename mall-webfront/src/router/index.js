import { createRouter, createWebHashHistory } from 'vue-router'

export const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/login')
  },
  {
    path: '/',
    name: '/',
    meta: {
      title: '首页'
    },
    component: () => import('../layout'),
    redirect: '/shoppingCart',
    children: [
      {
        path: 'admin',
        name: 'admin',
        meta: {
          title: '用户列表'
        },
        component: () => import('@/views/ums/users/index.vue')
      },
      {
        path: 'productCate',
        name: 'productCate',
        meta: {
          title: '商品分类'
        },
        component: () => import('@/views/pms/productCate/index.vue')
      },
      {
        path: 'productCateUpadete',
        name: 'productCateUpadete',
        meta: {
          title: '修改分类'
        },
        component: () => import('@/views/pms/productCate/update.vue')
      },
      {
        path: 'productAttr',
        name: 'productAttr',
        meta: {
          title: '分类管理'
        },
        component: () => import('@/views/pms/productAttr/index.vue')
      },
      {
        path: 'product',
        name: 'product',
        meta: {
          title: '商品列表'
        },
        component: () => import('@/views/pms/product/index.vue')
      },
      {
        path: 'addProduct',
        name: 'addProduct',
        meta: {
          title: '添加商品'
        },
        component: () => import('@/views/pms/addProduct/index.vue')
      },
      {
        path: 'order',
        name: 'order',
        meta: {
          title: '订单列表'
        },
        component: () => import('@/views/oms/orders/index.vue')
      },
      {
        path: 'report',
        name: 'report',
        meta: {
          title: '数据统计'
        },
        component: () => import('@/views/sms/reports/index.vue')
      },
      {
        path: 'shoppingCart',
        name: 'shoppingCart',
        meta: {
          title: '购物车'
        },
        component: () => import('@/views/oms/shoppingCart/index.vue')
      },
      {
        path: 'rights',
        name: 'rights',
        meta: {
          title: '权限列表'
        },
        component: () => import('@/views/ums/resource/index.vue')
      },
      {
        path: 'resource',
        name: 'resource',
        meta: {
          title: '权限列表'
        },
        component: () => import('@/views/ums/resource/index.vue')
      },
      {
        path: 'role',
        name: 'role',
        meta: {
          title: '角色列表'
        },
        component: () => import('@/views/ums/roles/index.vue')
      },
      {
        path: 'menu',
        name: 'menu',
        meta: {
          title: '菜单列表'
        },
        component: () => import('@/views/menu/menu/index.vue')
      },
      {
        path: 'menuNext',
        name: 'menuNext',
        meta: {
          title: '查看下级'
        },
        component: () => import('@/views/menu/menuNext/index.vue')
      }
    ]
  }
]

export const asyncRouterMap = [
  {
    id: 1,
    authName: '用户管理',
    path: 'admin',
    component: 'layout',
    redirect: '/admin',
    order: 1,
    children: [{
      id: 10,
      authName: '用户列表',
      path: 'admin',
      children: [],
      order: null,
      component: () => import('@/views/ums/users/index.vue')
    }]
  },
  {
    id: 2,
    authName: '权限管理',
    path: 'role',
    redirect: '/role',
    order: 2,
    children: [{
        id: 20,
        authName: '角色列表',
        path: 'role',
        children: [],
        order: null,
        component: () => import('@/views/ums/roles/index.vue')
      },
      {
        id: 21,
        authName: '权限列表',
        path: 'rights',
        children: [],
        order: null,
        component: () => import('@/views/ums/resource/index.vue')
      }
    ]
  },
  {
    id: 3,
    authName: '商品管理',
    path: 'goods',
    order: 3,
    children: [{
        id: 30,
        authName: '商品列表',
        path: 'goods',
        children: [],
        order: 1,
        component: () => import('@/views/pms/product/index.vue')
      },
      {
        id: 31,
        authName: '添加商品',
        path: 'addGoods',
        children: [],
        order: 2,
        component: () => import('@/views/pms/addProduct/index.vue')
      },
      {
        id: 32,
        authName: '分类参数',
        path: 'productAttr',
        children: [],
        order: 3,
        component: () => import('@/views/pms/productAttr/index.vue')
      },
      {
        id: 33,
        authName: '商品分类',
        path: 'productCate',
        children: [],
        order: 4,
        component: () => import('@/views/pms/productCate/index.vue')
      }
    ]
  },
  {
    id: 4,
    authName: '订单管理',
    path: 'order',
    order: 4,
    children: [{
      id: 40,
      authName: '订单列表',
      path: 'order',
      children: [],
      order: null,
      component: () => import('@/views/oms/orders/index.vue')
    }]
  },
  {
    id: 5,
    authName: '数据统计',
    path: 'reports',
    order: 5,
    children: [{
      id: 50,
      authName: '数据报表',
      path: 'reports',
      children: [],
      order: null,
      component: () => import('@/views/sms/reports/index.vue')
    }]
  },
  {
    id: 6,
    authName: '菜单管理',
    path: 'menu',
    order: 6,
    children: [{
      id: 60,
      authName: '菜单列表',
      path: 'menu',
      children: [],
      order: null,
      component: () => import('@/views/menu/menu/index.vue')
    }]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
