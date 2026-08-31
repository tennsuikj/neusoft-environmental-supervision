<template>
    <div class="card" style="width: 50%;padding:40px 20px;">
        <el-form ref="formRef" :rules="data.rules" :model="data.form" :label-width="80"
            style="padding-right: 40px; padding-top: 20px;">

            <div style="width:100%; display: flex; justify-content: center;margin-bottom: 20px;">

                <!-- action绑定自己的后端路径 -->
                <el-upload class="avatar-uploader" action="http://localhost:9090/files/upload" :show-file-list="false"
                    :on-success="handleAvatarSuccess">
                    <img v-if="data.form.avatar" :src="data.form.avatar" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon">
                        <Plus />
                    </el-icon>
                </el-upload>

            </div>


            <el-form-item label="账号" prop="username">
                <el-input disabled v-model="data.form.username" autocomplete="off" placeholder="请输入账号" />
            </el-form-item>

            <el-form-item label="名称" prop="name">
                <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入名称" />
            </el-form-item>
            <!-- 仅当员工登录时显示以下字段 -->
            <div v-if="data.user.role === 'EMP'">
                <el-form-item label="性别">
                    <el-radio-group v-model="data.form.sex">
                        <el-radio value="男" label="男"></el-radio>
                        <el-radio value="女" label="女"></el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="工号" prop="no">
                    <el-input disabled v-model="data.form.no" autocomplete="off" placeholder="请输入工号" />
                </el-form-item>

                <el-form-item label="年龄">
                    <el-input-number style="width: 200px;" :min="18" v-model="data.form.age" autocomplete="off"
                        placeholder="请输入年龄" />
                </el-form-item>

                <el-form-item label="个人介绍">
                    <el-input :rows="3" type="textarea" v-model="data.form.descr" autocomplete="off"
                        placeholder="请输入个人介绍" />
                </el-form-item>
            </div>

            <div style="text-align: center;">
                <el-button @click="updateUser" type="primary" style="padding: 20px 30px;">更新个人信息</el-button>
            </div>
        </el-form>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import request from '@/utils/request.js';
import { ElMessage } from 'element-plus';
const formRef = ref()
const data = reactive({
    user: JSON.parse(localStorage.getItem('st-user')),
    form: {},
    rules: {
        username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
    }//表单校验规则
})

//头像上传成功回调
const handleAvatarSuccess = (res) => {
    console.log(res.data);
    data.form.avatar = res.data

}

//子组件发送请求更细父组件数据
const emit = defineEmits(['updateUser'])

if (data.user.role === 'EMP') {
    request.get('/employee/selectById/' + data.user.id).then(res => {
        data.form = data.user
    })
} else if (data.user.role === 'ADMIN') {
    data.form = data.user
}

const updateUser = () => {
    if (data.user.role === 'EMP') {
        request.put('/employee/update', data.form).then(res => {
            if (res.code === '200') {
                console.log(data.form);

                ElMessage.success('更新成功')
                //更新缓存数据
                localStorage.setItem('st-user', JSON.stringify(data.form));
                //子组件发送请求更细父组件数据
                emit('updateUser')

            } else {
                ElMessage.error(res.msg)
            }
        })
    } else if (data.user.role === 'ADMIN') {

        request.put('/admin/update', data.form).then(res => {
            if (res.code === '200') {
                ElMessage.success('更新成功')

                //更新缓存数据
                localStorage.setItem('st-user', JSON.stringify(data.form));
                //子组件发送请求更细父组件数据
                emit('updateUser')

            } else {
                ElMessage.error(res.msg)
            }
        })
    }
}

</script>


<style scoped>
.avatar-uploader .avatar {
    width: 120px;
    height: 120px;
    display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 50%;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    text-align: center;
}
</style>