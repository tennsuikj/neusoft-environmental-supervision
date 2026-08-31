<template>
    <div>
        <div class="card" style="margin-bottom: 5px;">
            <span style="margin-right: 6px;">省区域</span>
            <el-select v-model="data.provinceId" placeholder="-- 全部 --" style="width: 130px; margin-right: 20px;"
                @change="(() => { data.cityId = null })">
                <el-option v-for="item in data.provinceAndCity" :key="item.provinceId" :label="item.provinceName"
                    :value="item.provinceId" />
            </el-select>

            <span style="margin-right: 6px;">市区域</span>
            <el-select v-model="data.cityId" placeholder="-- 全部 --" style="width: 130px; margin-right: 20px;">
                <el-option
                    v-for="item in (data.provinceAndCity.find(p => p.provinceId === data.provinceId) || {}).cityList || []"
                    :key="item.cityId" :label="item.cityName" :value="item.cityId" />
            </el-select>

            <span style="margin-right: 6px;">确认日期</span>
            <el-date-picker v-model="data.confirmDate" type="date" value-format="YYYY-MM-DD" placeholder="确认日期"
                style="width: 180px; margin-right: 15px;" />

            <el-button type="danger" @click="reset">清空</el-button>
            <el-button type="primary" @click="load">查询</el-button>

        </div>

        <div class="card" style="margin-bottom: 5px;">
            <!-- 表格 -->
            <el-table :data="data.tableData" stripe>
                <el-table-column label="编号" prop="id" width="80" />
                <el-table-column label="所在省" prop="provinceName" />
                <el-table-column label="所在市" prop="cityName" />
                <el-table-column label="预估污染等级">
                    <template #default="scope">
                        <span :style="{
                            color: scope.row.color,
                            fontWeight: 'bold'
                        }">
                            {{ scope.row.aqiExplain }} （{{ scope.row.chineseExplain }}）
                        </span>
                    </template>
                </el-table-column>
                <el-table-column label="确认日期" prop="confirmDate" show-overflow-tooltip />
                <el-table-column label="确认时间" prop="confirmTime" show-overflow-tooltip />
                <el-table-column label="网格员" prop="gmName" />
                <el-table-column label="反馈者" prop="realName" />

                <el-table-column label="操作" width="100px">
                    <template #default="scope">
                        <el-button @click="detail(scope.row)" type="primary" :icon="Document" circle></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页 -->
            <div style="margin-top: 15px;">
                <el-pagination @size-change="load" @current-change="load" v-model:current-page="data.pageNum"
                    v-model:page-size="data.pageSize" :page-sizes="[5, 10, 15, 20]" background
                    layout="total,sizes,prev,pager,next,jumper" :total="data.total" />
            </div>
        </div>


        <!-- 详情的弹窗 -->
        <el-dialog title="公众监督数据详情" v-model="data.detailVisible" width="50%" destroy-on-close>
            <el-descriptions :column="1" border style="padding-right: 40px; padding-top: 20px;" label-width="180px">
                <el-descriptions-item label="确认AQI数据编号">
                    {{ data.detail.id }}
                </el-descriptions-item>
                <el-descriptions-item label="确认信息所在地址">
                    <el-tag>{{ data.detail.provinceName }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.detail.cityName }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.detail.address }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="确认AQI等级">
                    <el-tag>{{ data.detail.chineseExplain }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.detail.aqiExplain }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="确认日期时间">
                    <el-tag>{{ data.detail.confirmDate }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.detail.confirmTime }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="网格员信息">
                    <el-tag>{{ data.detail.gmName }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.detail.gmId }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="公众监督员信息">
                    <el-tag>{{ data.detail.realName }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.detail.fdId }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="公众监督员反馈信息描述">
                    {{ data.detail.information }}
                </el-descriptions-item>
            </el-descriptions>
        </el-dialog>

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
    provinceAndCity: [],
    pageNum: 1,
    pageSize: 5,
    total: 0,
    detailVisible: false,
    detail: {},
    provinceId: null,
    cityId: null,
    confirmDate: null,
})


const loadProvinceAndCity = () => {
    request.get('/gridCity/getProvinceAndCity').then(res => {
        data.provinceAndCity = res.data
    })
}
loadProvinceAndCity()


//加载数据
const load = () => {
    console.log(data.provinceId, data.cityId, data.confirmDate)
    request.get('/statistics/listStatisticsPage', {
        params: {
            pageNum: data.pageNum,
            pageSize: data.pageSize,
            provinceId: data.provinceId,
            cityId: data.cityId,
            confirmDate: data.confirmDate
        }
    }).then(res => {
        data.tableData = res.data.list
        data.total = res.data.total
    })
}
load()


//重置数据
const reset = () => {
    data.provinceId = null
    data.cityId = null
    data.confirmDate = null
    load()
}


//打开详情弹窗
const detail = (row) => {
    data.detail = JSON.parse(JSON.stringify(row)) //深拷贝一个新对象，用于编辑，这样就不会影响行对象
    data.detailVisible = true
}



</script>