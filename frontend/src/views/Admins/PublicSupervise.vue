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

            <span style="margin-right: 6px;">预估等级</span>
            <el-select v-model="data.estimatedGrade" placeholder="-- 全部 --" style="width: 130px; margin-right: 20px;">
                <el-option v-for="item in data.gradeList" :key="item.aqiId" :label="item.chineseExplain"
                    :value="item.aqiId" />
            </el-select>

            <span style="margin-right: 6px;">反馈日期</span>
            <el-date-picker v-model="data.afDate" type="date" value-format="YYYY-MM-DD" placeholder="反馈日期"
                style="width: 180px; margin-right: 15px;" />

            <el-button type="danger" @click="reset">清空</el-button>
            <el-button type="primary" @click="load">查询</el-button>

            <el-radio-group v-model="data.state" style="margin-left: 30px;" @change="load()">
                <el-radio :label="0">未指派</el-radio>
                <el-radio :label="1">已指派</el-radio>
                <el-radio :label="2">已确认</el-radio>
            </el-radio-group>
        </div>

        <div class="card" style="margin-bottom: 5px;">
            <!-- 表格 -->
            <el-table :data="data.tableData" stripe>
                <el-table-column label="编号" prop="afId" width="80" />
                <el-table-column label="反馈者姓名" prop="realName" />
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
                        <el-button v-if="data.state === 0" @click="assign(scope.row)" type="danger" :icon="CircleCheck"
                            circle></el-button>
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

        <!-- 指派的弹窗 -->
        <el-dialog title="指派网格员" v-model="data.assignVisible" width="70%" destroy-on-close>
            <el-descriptions :column="1" border style="padding-right: 40px; padding-top: 20px;" label-width="180px">
                <el-descriptions-item label="公众监督反馈信息编号">
                    {{ data.assign.afId }}
                </el-descriptions-item>
                <el-descriptions-item label="反馈者信息">
                    <el-tag>{{ data.assign.realName }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="反馈者联系电话">
                    {{ data.assign.telId }}
                </el-descriptions-item>
                <el-descriptions-item label="反馈信息所在地址">
                    <el-tag>{{ data.assign.provinceName }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.assign.cityName }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.assign.address }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="反馈信息描述">
                    {{ data.assign.information }}
                </el-descriptions-item>
                <el-descriptions-item label="预估等级">
                    <el-tag>{{ data.assign.chineseExplain }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.assign.aqiExplain }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="反馈日期时间">
                    <el-tag>{{ data.assign.afDate }}</el-tag>
                    <el-tag style="margin-left: 8px;">{{ data.assign.afTime }}</el-tag>
                </el-descriptions-item>
            </el-descriptions>

            <div style="display: flex; align-items: center; gap: 15px; flex-wrap: wrap;">
                <span>是否异地指派</span>
                <el-switch v-model="data.assign.isRemote" @change="handleIsRemote" />

                <span>省区域</span>
                <el-select v-model="data.assign.assignProvinceId" placeholder="请选择省" style="width: 150px;"
                    :disabled="!data.assign.isRemote" @change="(() => { data.assign.assignCityId = null })">
                    <el-option v-for="item in data.provinceAndCity" :key="item.provinceId" :label="item.provinceName"
                        :value="item.provinceId" />
                </el-select>

                <span>市区域</span>
                <el-select v-model="data.assign.assignCityId" placeholder="请选择市" style="width: 150px;"
                    :disabled="!data.assign.isRemote"
                    @change="listGridMemberByProvinceIdByCityId(data.assign.assignProvinceId, data.assign.assignCityId)">
                    <el-option
                        v-for="item in (data.provinceAndCity.find(p => p.provinceId === data.assign.assignProvinceId) || {}).cityList || []"
                        :key="item.cityId" :label="item.cityName" :value="item.cityId" />
                </el-select>

                <span>异地指派给</span>
                <el-select v-model="data.assign.gmId" placeholder="请选择网格员" style="width: 180px;">
                    <el-option v-for="item in data.gridMemberList" :key="item.gmId" :label="item.gmName"
                        :value="item.gmId" />
                </el-select>

                <el-button type="primary" @click="handleAssign">异地指派</el-button>
            </div>
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
    gradeList: [],
    gridMemberList: [],
    pageNum: 1,
    pageSize: 5,
    total: 0,
    detailVisible: false,
    detail: {},
    assignVisible: false,
    assign: {},
    state: 0,// 选中的状态
    provinceId: null,
    cityId: null,
    estimatedGrade: null,
    afDate: null,
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


//加载数据
const load = () => {
    request.get('/aqiFeedback/listAqiFeedbackPage', {
        params: {
            pageNum: data.pageNum,
            pageSize: data.pageSize,
            state: data.state,
            provinceId: data.provinceId,
            cityId: data.cityId,
            estimatedGrade: data.estimatedGrade,
            afDate: data.afDate
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
    data.estimatedGrade = null
    data.afDate = null
    load()
}


//打开详情弹窗
const detail = (row) => {
    data.detail = JSON.parse(JSON.stringify(row)) //深拷贝一个新对象，用于编辑，这样就不会影响行对象
    data.detailVisible = true
}

//更新数据
const assign = (row) => {

    data.assign = JSON.parse(JSON.stringify(row)) //深拷贝一个新对象，用于编辑，这样就不会影响行对象
    data.assignVisible = true
    handleIsRemote()
}

const listGridMemberByProvinceIdByCityId = (provinceId, cityId) => {
    data.assign.gmId = null
    request.get('/gridMember/listGridMemberByProvinceIdByCityId', {
        params: {
            provinceId: provinceId,
            cityId: cityId
        }
    }).then(res => {
        data.gridMemberList = res.data
    })
}

// 是否异地指派
const handleIsRemote = () => {
    if (data.assign.isRemote) {
        data.assign.assignProvinceId = null
        data.assign.assignCityId = null
    }
    else {
        data.assign.assignProvinceId = data.assign.provinceId
        data.assign.assignCityId = data.assign.cityId
        listGridMemberByProvinceIdByCityId(data.assign.provinceId, data.assign.cityId)
    }
}
const handleAssign = () => {
    if (!data.assign.gmId) {
        ElMessage.error("操作失败，请选择网格员")
        return
    }
    data.assign.state = 1

    request.put('/aqiFeedback/updateAqiFeedbackAssign', data.assign).then(res => {
        console.log(data.assign)
        if (res.code === '200') {
            ElMessage.success("操作成功")
            data.assignVisible = false
            load()
        }
        else {
            ElMessage.error(res.msg)
        }
    })

}



</script>