<template>
    <div class="login-container">
        <div class="login-box">
            <h2 style="text-align: center;">欢迎使用东软环保公众监督系统</h2>

            <el-form ref="formRef" :rules="data.rules" :model="data.form" style="margin-top: 20px;">
                <el-form-item prop="telId">
                    <el-input v-model="data.form.telId" placeholder="请输入手机号" prefix-icon="User" />
                </el-form-item>

                <el-form-item prop="password">
                    <el-input show-password v-model="data.form.password" placeholder="请输入密码" prefix-icon="Lock" />
                </el-form-item>

                <el-form-item>
                    <el-button @click="login" type="primary" style="width: 100%;">登 录</el-button>
                </el-form-item>
                <div style="text-align: right;">还没有账号？请<a style="color: #0742b1; text-decoration: none;"
                        href="/supervisorRegister">注册</a>
                </div>
            </el-form>

        </div>

    </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import request from '@/utils/request';
import { ElMessage } from 'element-plus';
const data = reactive({
    form: {
        role: 'SUPERVISOR'
    },
    rules: {
        telId: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
    }//表单校验规则
})

const formRef = ref()

const login = () => {
    formRef.value.validate((valid) => {
        if (valid) {

            request.post('/supervisorLogin', data.form).then((res) => {
                if (res.code === '200') {
                    ElMessage.success('登录成功');
                    localStorage.setItem('supervisor-user', JSON.stringify(res.data));
                    setTimeout(() => {
                        location.href = '/supervisor';
                    }, 500);

                } else {
                    ElMessage.error(res.msg);
                }
            });
        } else {
            console.log('表单验证失败');
            return false;
        }
    });
};

</script>

<style scoped>
.login-container {

    height: 100vh;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f0f2f5;
}

.login-box {
    width: 400px;
    padding: 40px;
    box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    background-color: #ffffff;
}
</style>