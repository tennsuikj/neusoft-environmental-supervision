<template>
    <div>
        <!-- 头部 -->
        <div style="height: 60px; background-color:deepskyblue;display: flex;align-items: center">
            <div style=" width: 220px; display: flex;align-items: center;padding-left: 15px;">
                <!-- log -->
                <img style="width:40px;" src="" alt="">
                <span style="font-size: 24px;color: #fff;">公众监督员后台</span>
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
                <el-menu router :default-active="$router.currentRoute.value.path" :default-openeds="['1']"
                    style="border: 0;">

                    <el-menu-item index="/supervisor">
                        <el-icon>
                            <House />
                        </el-icon>
                        系统首页
                    </el-menu-item>

                      <el-menu-item index="/supervisor/feedback">
                        <el-icon>
                             <ChatDotRound />
                        </el-icon>
                        反馈信息
                    </el-menu-item>

                              <el-menu-item index="/supervisor/feedbackHistory">
                        <el-icon>
                             <Document />
                        </el-icon>
                        历史反馈信息
                    </el-menu-item>

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
    user: JSON.parse(localStorage.getItem('st-user') || '{}')
})

//退出登录
const logout = () => {
    localStorage.removeItem('st-user');//清除登录信息
    location.href = '/supervisorLogin';
}

//更新用户信息,接收子组件传递过来的事件
const updateUser = () => {
    data.user = JSON.parse(localStorage.getItem('st-user') || '{}')
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