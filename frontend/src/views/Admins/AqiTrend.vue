<template>
    <div>
        <div class="card" style="margin-bottom: 5px;">
            <!-- 表格 -->
            <el-table :data="data.tableData" stripe>
                <el-table-column label="序号" type="index" width="80" />
                <el-table-column label="月份" prop="statMonth" />
                <el-table-column label="AQI空气质量指数超标统计" prop="aqiOverCount" />
            </el-table>
     
        </div>

      

    </div>
</template>

<script setup>
import request from '@/utils/request.js';
import { Document, CircleCheck } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus';
import { reactive, ref } from 'vue';
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import '@/assets/view.css'

const data = reactive({
    tableData: [],
})




//加载数据
const load = () => {
    request.get('/statistics/listAqiTrendTotalStatis').then(res => {
        data.tableData = res.data
    })
}
load()





</script>