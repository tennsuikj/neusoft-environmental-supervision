<template>
    <div class="login-container">
        <div class="login-box">
            <h2 style="text-align: center; margin-bottom: 20px;">东软环保公众监督系统注册</h2>

            <el-form ref="formRef" :rules="data.rules" :model="data.form" label-width="100px">
                <el-form-item label="手机号码" prop="telId">
                    <el-input v-model="data.form.telId" placeholder="请输入手机号码" />
                </el-form-item>

                <el-form-item label="真实姓名" prop="realName">
                    <el-input v-model="data.form.realName" placeholder="真实姓名便于我们联系您" />
                </el-form-item>

                <el-form-item label="出生日期" prop="birthday">
                    <el-date-picker v-model="data.form.birthday" type="date" placeholder="请选择出生日期"
                        value-format="YYYY-MM-DD" style="width: 100%;" />
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-radio-group v-model="data.form.sex">
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="0">女</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="密码" prop="password">
                    <el-input show-password v-model="data.form.password" placeholder="请输入密码" />
                </el-form-item>

                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input show-password v-model="data.form.confirmPassword" placeholder="请再次输入密码" />
                </el-form-item>

                <el-form-item>
                    <el-button @click="register" type="primary" style="width: 100%; margin-top: 10px;">注 册</el-button>
                </el-form-item>
                <div style="text-align: right;">
                    已有账号？请<a style="color: #0742b1; text-decoration: none;" href="/supervisorLogin">返回登录</a>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { User, Lock } from '@element-plus/icons-vue';
import request from '@/utils/request';
import { ElMessage } from 'element-plus';

const validatePass = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次确认密码'))
    } else if (value !== data.form.password) {
        callback(new Error('两次输入密码不一致'))
    } else {
        callback()
    }
}

const data = reactive({
    form: {},
    rules: {
        telId: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
        realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
        birthday: [
            { required: true, message: '请选择出生日期', trigger: 'blur' },
            {
                validator: (rule, value, callback) => {
                    // 未选择时不做范围校验，交给必填规则处理
                    if (!value) return callback()
                    const birthDate = new Date(value)
                    const today = new Date()
                    // 重置今天的时分秒，仅按日期维度比较
                    today.setHours(0, 0, 0, 0)
                    if (birthDate > today) {
                        callback(new Error('出生日期不能超过当前日期'))
                    } else {
                        callback()
                    }
                },
                trigger: 'change'
            }
        ],
        sex: [{ required: true, message: '请选择性别', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        confirmPassword: [{ required: true, validator: validatePass, trigger: 'blur' }]
    }//表单校验规则
})

const formRef = ref()

const register = () => {
    formRef.value.validate((valid) => {
        if (valid) {

            request.post('/supervisorRegister', data.form).then((res) => {
                if (res.code === '200') {
                    ElMessage.success('注册成功');
                    setTimeout(() => {
                        location.href = '/supervisorLogin';
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