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
            <span style="margin-right: 6px;">反馈</span>
            <el-button @click="feedback" type="primary" :icon="ChatDotRound" circle></el-button>
        </div>


        <!-- 反馈数据弹窗 -->
        <el-dialog title="反馈数据" v-model="data.feedbackVisible" width="50%" destroy-on-close>
            <!-- AQI等级说明表格 -->
            <el-table :data="data.gradeList" :show-header="false" border stripe style="margin-bottom: 25px;">
                <el-table-column prop="chineseExplain" width="60" align="center" />
                <el-table-column prop="aqiExplain" width="90" align="center" />
                <el-table-column prop="healthImpact" align="center" />
            </el-table>

            <!-- 预估等级选择 -->
            <div style="font-size: 16px; margin-bottom: 15px;">请预估当前空气质量指数等级：</div>
            <el-radio-group v-model="data.feedback.estimatedGrade">
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

            <!-- 反馈地址输入 -->
            <div style="font-size: 16px; margin: 25px 0 15px 0;">请填写地址信息：</div>
            <el-input v-model="data.feedback.address" type="textarea" :rows="1" placeholder="请填写地址信息" />

            <!-- 反馈信息输入 -->
            <div style="font-size: 16px; margin: 25px 0 15px 0;">请填写反馈信息：</div>
            <el-input v-model="data.feedback.information" type="textarea" :rows="5" placeholder="请填写反馈信息" />

            <!-- 提交按钮 -->
            <el-button type="primary" @click="handleFeedback"
                style="width: 100%; margin-top: 25px; height: 45px; font-size: 18px;">
                提 交
            </el-button>
        </el-dialog>

    </div>
</template>

<script setup>
import request from '@/utils/request.js';
import { Document, CircleCheck, ChatDotRound } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus';
import { reactive, ref } from 'vue';
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import '@/assets/view.css'

const data = reactive({
    user: JSON.parse(localStorage.getItem('supervisor-user') || '{}'),
    provinceAndCity: [],
    gradeList: [],
    feedbackVisible: false,
    feedback: {
        telId: null,
        provinceId: null,
        cityId: null,
        address: null,
        information: null,
        estimatedGrade: null,
    },
    provinceId: null,
    cityId: null,
})


const loadProvinceAndCity = () => {
    request.get('/gridCity/getProvinceAndCity').then(res => {
        data.provinceAndCity = res.data
    })
}
loadProvinceAndCity()

const loadGradeList = () => {
    request.get('/aqi/listAqiAll').then(res => {
        data.gradeList = res.data
    })
}
loadGradeList()

//打开详情弹窗
const feedback = () => {
    if (data.provinceId == null || data.cityId == null) {
        ElMessage.error('请选择省和市')
        return
    }
    if (!data.user.telId) {
        ElMessage.error('请先登录')
        return
    }
    data.feedbackVisible = true
    data.feedback = {
        telId: data.user.telId,
        provinceId: data.provinceId,
        cityId: data.cityId,
        address: null,
        information: null,
        estimatedGrade: null,
    }
}

const handleFeedback = () => {
    if (data.feedback.address == null || data.feedback.address.trim() === '') {
        ElMessage.error('请填写地址信息')
        return
    }
    if (data.feedback.information == null || data.feedback.information.trim() === '') {
        ElMessage.error('请填写反馈信息')
        return
    }
    if (data.feedback.estimatedGrade == null) {
        ElMessage.error('请选择预估等级')
        return
    }

    request.post('/aqiFeedback/saveAqiFeedback', data.feedback).then(res => {
        if (res.code === '200') {
            ElMessage.success("反馈成功")
            data.feedbackVisible = false
        }
        else {
            ElMessage.error(res.msg)
        }
    })
}





</script>