<template>
  <div>
    <div>
      欢迎学习新项目
    </div>


    <div>
      <el-button>default</el-button>
    </div>

    <div>
      <el-icon :size="20">
        <View/>
      </el-icon>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import request from '@/utils/request.js';

// const data = reactive({
//   employeeList:[]
// })

// request.get('/employee/selectAll').then(res=>{
//   console.log(res.data)
//   data.employeeList=res.data
// })

</script>
