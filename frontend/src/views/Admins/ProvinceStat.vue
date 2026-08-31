<template>
    <div>
        <div class="card" style="margin-bottom: 5px;">
            <!-- 表格 -->
            <el-table :data="data.tableData" stripe>
                <el-table-column label="省编号" prop="provinceId" />
                <el-table-column label="省简称" prop="provinceAbbr" />
                <el-table-column label="省名称" prop="provinceName" />
                <el-table-column label="SO2超标累计" prop="so2OverCount" />
                <el-table-column label="CO超标累计" prop="coOverCount" />
                <el-table-column label="PM2.5超标累计" prop="spmOverCount" />
                <el-table-column label="AQI超标累计" prop="aqiOverCount" />

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
    request.get('/statistics/listProvinceItemTotalStatis').then(res => {
        data.tableData = res.data
    })
}
load()





</script>