<template>
    <div>
        <div class="card" style="margin-bottom: 5px;">
            <el-descriptions :column="1" border style="padding-right: 40px; padding-top: 20px;" label-width="220px">
                <el-descriptions-item label="空气质量检测总数量">
                    {{ data.aqiCount }}
                </el-descriptions-item>
                <el-descriptions-item label="空气质量检测良好数量">
                    {{ data.aqiGoodCount }}
                </el-descriptions-item>
                <el-descriptions-item label="省会城市网格覆盖范围">
                    {{ data.provinceCoverage }}
                </el-descriptions-item>
                <el-descriptions-item label="大城市网格覆盖范围">
                    {{ data.cityCoverage }}
                </el-descriptions-item>
            </el-descriptions>
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
    aqiCount: 0,
    aqiGoodCount: 0,
    provinceCoverage: 0,
    cityCoverage: 0,
})




//加载数据
const load = () => {
    request.get('/statistics/getAqiCount').then(res => {
        data.aqiCount = res.data
    })
    request.get('/statistics/getAqiGoodCount').then(res => {
        data.aqiGoodCount = res.data
    })
    request.get('/statistics/getProvinceCoverage').then(res => {
        data.provinceCoverage = res.data
    })
    request.get('/statistics/getCityCoverage').then(res => {
        data.cityCoverage = res.data
    })
}
load()





</script>