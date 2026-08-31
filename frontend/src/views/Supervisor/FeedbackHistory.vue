<template>
    <div>

        <div class="card" style="margin-bottom: 5px;">
            <!-- 表格 -->
            <el-table :data="data.tableData" stripe>
                <el-table-column label="编号" prop="afId" />
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
                <el-table-column label="反馈日期" prop="afDate" show-overflow-tooltip />
                <el-table-column label="反馈时间" prop="afTime" show-overflow-tooltip />

                <el-table-column label="操作" width="100px">
                    <template #default="scope">
                        <el-button @click="detail(scope.row)" type="primary" :icon="Document" circle></el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>


        <!-- 详情的弹窗 -->
        <el-dialog title="详细数据" v-model="data.detailVisible" width="50%" destroy-on-close>
            <el-descriptions :column="1" border style="padding-right: 40px; padding-top: 20px;" label-width="180px">
                <el-descriptions-item label="公众监督反馈信息编号">
                    {{ data.detail.afId }}
                </el-descriptions-item>
                <el-descriptions-item label="反馈者信息">
                    <el-tag>{{ data.detail.realName }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="反馈者联系电话">
                    {{ data.detail.telId }}
                </el-descriptions-item>
                <el-descriptions-item label="反馈信息所在地址">
                    <el-tag>{{ data.detail.provinceName }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.detail.cityName }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.detail.address }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="反馈信息描述">
                    {{ data.detail.information }}
                </el-descriptions-item>
                <el-descriptions-item label="预估等级">
                    <el-tag>{{ data.detail.chineseExplain }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.detail.aqiExplain }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="反馈日期时间">
                    <el-tag>{{ data.detail.afDate }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.detail.afTime }}</el-tag>
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
    user: JSON.parse(localStorage.getItem('supervisor-user') || '{}'),
})


//加载数据
const load = () => {
    request.get('/aqiFeedback/listAqiFeedbackByTelId', {
        params: {
            telId: data.user.telId
        }
    }).then(res => {
        data.tableData = res.data
        console.log(res.data)
    })
}
load()


//打开详情弹窗
const detail = (row) => {
    data.detail = JSON.parse(JSON.stringify(row)) //深拷贝一个新对象，用于编辑，这样就不会影响行对象
    data.detailVisible = true
}


</script>