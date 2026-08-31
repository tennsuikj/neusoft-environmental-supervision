<template>
    <div>
        <div class="card" style="margin-bottom: 5px;">
            <!-- 表格 -->
            <el-table :data="data.tableData" stripe>
                <el-table-column label="AQI指数等级" prop="aqiId" />
                <el-table-column label="AQI指数等级描述" prop="chineseExplain" />
                <el-table-column label="AQI指数等级分布统计" prop="totalCount" />

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
    request.get('/statistics/listAqiDistributeTotalStatis').then(res => {
        data.tableData = res.data
    })
}
load()





</script>