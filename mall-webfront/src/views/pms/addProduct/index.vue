<template>
  <div class="app-container" style="padding: 35px 35px 15px;margin: 20px auto;width:800px;">
    <div class="filter-container">
      <span>{{ id ? '修改商品信息' : '添加商品' }}</span>
    </div>
    <el-card class="add-container" shadow="never" style="padding: 35px 35px 15px;">
      <el-form :model="goodForm" :rules="rules" ref="goodRef" label-width="100px" class="goodForm">
        <el-form-item label="商品名称" prop="goods_name">
          <el-input style="width: 300px" v-model="goodForm.goodsName" placeholder="请输入商品名称"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" prop="price">
          <el-input type="number" min="0" style="width: 300px" v-model="goodForm.price" placeholder="请输入商品价格">
          </el-input>
        </el-form-item>
        <el-form-item label="商品类型" prop="goodsTypeId">
          <el-select v-model="goodForm.goodsTypeId" class="m-2" placeholder="商品类型">
            <el-option v-for="item in goodsTypeOptions" :key="item.id" :label="item.typeName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-select v-model="goodForm.brand" class="m-2" placeholder="商品类型">
            <el-option v-for="item in brandOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="上架状态" prop="shelves">
          <el-radio-group v-model="goodForm.shelves">
            <el-radio label="1">上架</el-radio>
            <el-radio label="0">下架</el-radio> <!-- 1是上架，0是下架  -->
          </el-radio-group>
        </el-form-item>
        <el-form-item required label="商品主图" prop="img">
          <el-upload list-type="picture" action='' accept=".jpg, .png" :limit="1" :auto-upload="false"
            :file-list="fileList" :on-change="getFile" :on-preview="handlePictureCardPreview"
            :on-remove="handleUploadRemove">
            <el-button size="small" type="primary" @click="uploadimg">选择图片上传</el-button>
          </el-upload>
          <!--img class="img" :src="goodForm.img"/-->
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitAdd()">{{ id ? '立即修改' : '立即创建' }}</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
  import {
    reactive,
    ref
  } from 'vue'
  import {
    useRouter,
    useRoute
  } from 'vue-router'
  import {
    addGoods,
    getAllGoodsType,
    getBase64,
    updateGoods
  } from '@/api/goods'
  import { ElMessage } from 'element-plus'

  const goodForm = reactive({
    goodsTypeId: '1',
    goodsName: 'nothing',
    price: '2.22',
    brand: '华为',
    shelves: '0',
    img: 'http'
  })

  const route = useRoute()
  const id = ref()
  if (route.params != null) {
    goodForm.goodsName = route.params.goodsName
    goodForm.price = route.params.price
    goodForm.goodsTypeId = route.params.goodsTypeId
    goodForm.brand = route.params.brand
    goodForm.shelves = route.params.shelves
    goodForm.img = route.params.img
    id.value = route.params.id
  }

  const rules = ref({
    img: [{
      required: 'true',
      message: '请上传主图',
      trigger: ['change']
    }],
    goodsName: [{
      required: 'true',
      message: '请填写商品名称',
      trigger: ['change']
    }],
    price: [{
      required: 'true',
      message: '请填写商品价格',
      trigger: ['change']
    }],
    stockNum: [{
      required: 'true',
      message: '请填写商品库存',
      trigger: ['change']
    }]
  })

  const brandOptions = [{
      value: '1',
      label: '华为'
    },
    {
      value: '2',
      label: '苹果'
    },
    {
      value: '3',
      label: '三星'
    },
    {
      value: '4',
      label: '小米'
    }
  ]
  // const value = ref()
  // const handleChangeGoodsTypeId = (value) => {
  //   goodForm.goodsTypeId = value
  //   console.log(value)
  // }

  const goodsTypeOptions = ref([])
  const initGetAllGoodsType = async () => {
    const query = ref({
      pageNum: 1,
      pageSize: 1000
    })
    const res = await getAllGoodsType(query.value)
    goodsTypeOptions.value = res.data.records
  }
  initGetAllGoodsType()

  const router = useRouter()
  const submitAdd = async () => {
    if (id.value) {
      console.log('修改商品信息')
      goodForm.id = id.value
      await updateGoods(goodForm)
      ElMessage({
        type: 'success',
        message: '修改成功'
      })
    } else {
      await addGoods(goodForm)
      ElMessage({
        type: 'success',
        message: '添加成功'
      })
    }
    router.push({
      path: '/product'
    })
  }

  const getFile = (file, fileList) => {
  getBase64(file.raw).then(res => {
    const params = res
    goodForm.img = params
    console.log(goodForm.img)
  })
}
</script>

<style lang="scss" scoped>
  .disUoloadBtn .el-upload--picture-card {
    display: none;
    /* 上传按钮隐藏 */
  }
  .img {
    width: 80px;
    height: 80px;
    margin-top: 30px;
    margin-left: 20px
  }

</style>
