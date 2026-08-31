<template>
    <div>

        <div class="card" style="margin-bottom: 5px;">
            <!-- 表格 -->
            <el-table :data="data.tableData" stripe>
                <el-table-column label="编号" prop="afId" />
                <el-table-column label="所在省" prop="provinceName" />
                <el-table-column label="所在市" prop="cityName" />
                <el-table-column label="详细地址" prop="address" show-overflow-tooltip />
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
                        <el-button @click="examine(scope.row)" type="primary" circle>检测</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>


        <!-- 检测弹窗 -->
        <el-dialog title="详细数据" v-model="data.examineVisible" width="50%" destroy-on-close>
            <!-- 顶部反馈基础信息 -->
            <el-descriptions :column="1" border style="margin-bottom: 25px;" label-width="120px">
                <el-descriptions-item label="反馈用户">
                    {{ data.examine.realName }} {{ data.examine.telId }}
                </el-descriptions-item>
                <el-descriptions-item label="网格">
                    {{ data.examine.provinceName }} {{ data.examine.cityName }}
                </el-descriptions-item>
                <el-descriptions-item label="详细地址">
                    {{ data.examine.address }}
                </el-descriptions-item>
                <el-descriptions-item label="预估等级">
                    {{ data.examine.chineseExplain }} {{ data.examine.aqiExplain }}
                </el-descriptions-item>
                <el-descriptions-item label="信息描述">
                    {{ data.examine.information }}
                </el-descriptions-item>
            </el-descriptions>

            <!-- 污染物浓度输入区 -->
            <div style="display: flex; flex-direction: column; gap: 18px; margin-bottom: 25px;">
                <div style="display: flex; align-items: center; gap: 12px;">
                    <span style="width: 90px; flex-shrink: 0;">SO₂浓度</span>
                    <el-input v-model="data.examineForm.so2Value" placeholder="请输入SO₂浓度"
                        style="flex: 1; max-width: 300px;" />
                    <span style="width: 65px; flex-shrink: 0;">ug/m3</span>
                    <span
                        style="display: inline-block; width: 52px; height: 44px; line-height: 44px; text-align: center; color: #fff; border-radius: 6px; font-weight: bold; font-size: 18px;"
                        :style="{ backgroundColor: so2Grade.color }">
                        {{ so2Grade.chineseExplain }}
                    </span>
                </div>
                <div style="display: flex; align-items: center; gap: 12px;">
                    <span style="width: 90px; flex-shrink: 0;">CO浓度</span>
                    <el-input v-model="data.examineForm.coValue" placeholder="请输入CO浓度"
                        style="flex: 1; max-width: 300px;" />
                    <span style="width: 65px; flex-shrink: 0;">ug/m3</span>
                    <span
                        style="display: inline-block; width: 52px; height: 44px; line-height: 44px; text-align: center; color: #fff;  border-radius: 6px; font-weight: bold; font-size: 18px;"
                        :style="{ backgroundColor: coGrade.color }">{{ coGrade.chineseExplain }}</span>
                </div>
                <div style="display: flex; align-items: center; gap: 12px;">
                    <span style="width: 90px; flex-shrink: 0;">PM2.5</span>
                    <el-input v-model="data.examineForm.spmValue" placeholder="请输入PM2.5浓度"
                        style="flex: 1; max-width: 300px;" />
                    <span style="width: 65px; flex-shrink: 0;">ug/m3</span>
                    <span
                        style="display: inline-block; width: 52px; height: 44px; line-height: 44px; text-align: center; color: #fff; border-radius: 6px; font-weight: bold; font-size: 18px;"
                        :style="{ backgroundColor: spmGrade.color }">{{ spmGrade.chineseExplain }}</span>
                </div>
                <el-radio-group v-model="data.examineForm.aqiId">
                    <div style="display: flex; gap: 18px; flex-wrap: wrap; align-items: center;">
                        <div v-for="item in data.gradeList" :key="item.aqiId"
                            style="display: flex; align-items: center; gap: 8px;">
                            <span :style="{
                                display: 'inline-block',
                                width: '52px',
                                height: '42px',
                                lineHeight: '42px',
                                textAlign: 'center',
                                color: '#fff',
                                fontWeight: 'bold',
                                backgroundColor: item.color,
                                borderRadius: '4px',
                                fontSize: '18px'
                            }">{{ item.chineseExplain }}</span>
                            <el-radio :label="item.aqiId" />
                        </div>
                    </div>
                </el-radio-group>
            </div>


            <!-- 提交按钮 -->
            <div style="text-align: center;">
                <el-button type="primary" @click="handleExamine" style="width: 240px; height: 48px; font-size: 20px;">
                    提交实测数据
                </el-button>
            </div>
        </el-dialog>


    </div>
</template>

<script setup>
import request from '@/utils/request.js';
import { Document, CircleCheck } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus';
import { reactive, ref, computed } from 'vue';
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import '@/assets/view.css'
const data = reactive({
    tableData: [],
    user: JSON.parse(localStorage.getItem('gridMember-user') || '{}'),
    examineVisible: false,
    examine: {},
    examineForm: {},
    gradeList: [],

})
// 根据浓度值 + 污染物类型，从后端等级列表中匹配对应等级
const getPollutantGrade = (value, type) => {
    const num = Number(value)
    // 空值、非法值、等级列表未加载时返回灰色占位
    if (isNaN(num) || num < 0 || data.gradeList.length === 0) {
        return { grade: '-', chineseExplain: '无', color: '#ccc' }
    }

    // 按等级ID降序排列，从高等级向低等级匹配，确保命中最高符合条件的等级
    const sortedList = [...data.gradeList].sort((a, b) => b.aqiId - a.aqiId)

    for (const item of sortedList) {
        let minVal, maxVal
        // 根据污染物类型读取对应阈值字段
        switch (type) {
            case 'so2':
                minVal = item.so2Min
                maxVal = item.so2Max
                break
            case 'co':
                minVal = item.coMin
                maxVal = item.coMax
                break
            case 'spm':
                minVal = item.spmMin
                maxVal = item.spmMax
                break
            default:
                continue
        }
        // 命中区间则返回对应等级与颜色
        if (num >= minVal && num <= maxVal) {
            return { grade: item.aqiId, chineseExplain: item.chineseExplain, color: item.color }
        }
    }

    // 浓度超出最高级阈值时，返回最高等级
    const highestGrade = sortedList[0]
    return { grade: highestGrade.aqiId, chineseExplain: highestGrade.chineseExplain, color: highestGrade.color }
}

// 三个污染物各自的等级（对应输入框右侧色块）
const so2Grade = computed(() => getPollutantGrade(data.examineForm.so2Value, 'so2'))
const coGrade = computed(() => getPollutantGrade(data.examineForm.coValue, 'co'))
const spmGrade = computed(() => getPollutantGrade(data.examineForm.spmValue, 'spm'))


const loadGradeList = () => {
    request.get('/aqi/listAqiAll').then(res => {
        data.gradeList = res.data
    })
}
loadGradeList()

//加载数据
const load = () => {
    request.get('/aqiFeedback/listAqiFeedbackByGmId', {
        params: {
            gmId: data.user.gmId
        }
    }).then(res => {
        data.tableData = res.data
        console.log(res.data)
    })
}
load()


//打开详情弹窗
const examine = (row) => {
    data.examine = JSON.parse(JSON.stringify(row)) //深拷贝一个新对象，用于编辑，这样就不会影响行对象
    data.examineVisible = true
    data.examineForm = {}
}

const handleExamine = () => {
    data.examineForm.provinceId = data.examine.provinceId
    data.examineForm.cityId = data.examine.cityId
    data.examineForm.address = data.examine.address
    data.examineForm.so2Level = so2Grade.value.grade
    data.examineForm.coLevel = coGrade.value.grade
    data.examineForm.spmLevel = spmGrade.value.grade
    data.examineForm.gmId = data.user.gmId
    data.examineForm.fdId = data.examine.telId
    data.examineForm.information = data.examine.information
    console.log(data.examineForm)
    request.post('/statistics/saveStatistics', data.examineForm).then(res => {
        if (res.code === '200') {
            ElMessage.success("提交成功")
        } else {
            ElMessage.error(res.msg)
        }
    })
    data.examine.state = 2
    request.put('/aqiFeedback/updateAqiFeedbackState', data.examine).then(res => {
        if (res.code === '200') {
            data.examineVisible = false
            load()
        } else {
            ElMessage.error(res.msg)
        }
    })
}



</script>