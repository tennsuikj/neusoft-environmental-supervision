<template>
    <div>
        <!-- 头部 -->
        <div style="height: 60px; background-color:deepskyblue;display: flex;align-items: center">
            <div style=" width: 220px; display: flex;align-items: center;padding-left: 15px;">
                <!-- log -->
                <img style="width:40px;" src="" alt="">
                <span style="font-size: 24px;color: #fff;">管理员后台管理</span>
            </div>
            <div style="flex: 1;"></div>
            <!-- 用户信息 -->
            <div style="width: fit-content;display: flex;align-items: center;padding-right: 10px;">
                <!-- 头像 -->
                <img style="width: 40px; height: 40px; border-radius: 50%;"
                    :src="data.user.avatar || 'https://p2.ssl.qhimgs1.com/t012454caf8630b5fe8.png'" alt="">
                <span style="color:white;margin-left: 5px;">{{ data.user.name }}</span>
            </div>
        </div>

        <!-- 下面部分 -->
        <div style="display: flex;">
            <!-- 左侧导航菜单 -->
            <div style="width: 200px;border-right: 1px solid #ddd; min-height: calc(100vh - 60px)">
                <el-menu router :default-active="$router.currentRoute.value.path" :default-openeds="['1','2']"
                    style="border: 0;">

                    <el-menu-item index="/admins">
                        <el-icon>
                            <House />
                        </el-icon>
                        系统首页
                    </el-menu-item>

                    <!-- 公众监督数据管理 -->
                    <el-sub-menu index="1">
                        <template #title>
                            <el-icon>
                                <ChatDotRound />
                            </el-icon>
                            <span>公众监督数据管理</span>
                        </template>
                        <el-menu-item index="/admins/publicSupervise">公众监督数据列表</el-menu-item>
                        <el-menu-item index="/admins/aqiConfirm">确认AQI数据列表</el-menu-item>
                    </el-sub-menu>

                    <!-- 统计数据管理 -->
                    <el-sub-menu index="2">
                        <template #title>
                            <el-icon>
                                <Document />
                            </el-icon>
                            <span>统计数据管理</span>
                        </template>
                        <el-menu-item index="/admins/provinceStat">省分组检查统计</el-menu-item>
                        <el-menu-item index="/admins/aqiDist">AQI指数分布统计</el-menu-item>
                        <el-menu-item index="/admins/aqiTrend">AQI指数趋势统计</el-menu-item>
                        <el-menu-item index="/admins/otherStat">其它数据统计</el-menu-item>
                    </el-sub-menu>

                    <el-menu-item @click="logout">
                        <el-icon>
                            <SwitchButton />
                        </el-icon>
                        退出登录
                    </el-menu-item>


                </el-menu>
            </div>

            <!-- 右侧主体 -->
            <div style="flex:1;width: 0;background-color:aliceblue;padding: 10px;">
                <router-view @updateUser="updateUser" />
            </div>
        </div>

    </div>
</template>

<script setup>

import { RouterView } from 'vue-router';
import { reactive } from 'vue';


const data = reactive({
    user: JSON.parse(localStorage.getItem('admins-user') || '{}')
})

//退出登录
const logout = () => {
    localStorage.removeItem('admins-user');//清除登录信息
    location.href = '/adminsLogin';
}

//更新用户信息,接收子组件传递过来的事件
const updateUser = () => {
    data.user = JSON.parse(localStorage.getItem('admins-user') || '{}')
}
</script>

<style>
/* 高亮显示背景色 */
.el-menu .is-active {
    background-color: #e6ecf7;
}

.el-sub-menu__title {
    background-color: white;
}
</style>