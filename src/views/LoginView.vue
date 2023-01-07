<template>
  <div class="login">
    <div class="content">
      <a-form
          :model="formState"
          name="basic"
          :label-col="{ span: 4 }"
          :wrapper-col="{ span: 18 }"
          autocomplete="off"
          @finish="onFinish"
          @finishFailed="onFinishFailed"
      >
        <a-form-item
            label="账号"
            name="username"
            :rules="[{ required: true, message: '请输入账号!' }]"
        >
          <a-input v-model:value="formState.username"/>
        </a-form-item>

        <a-form-item
            label="密码"
            name="pwd"
            :rules="[{ required: true, message: '请输入密码!' }]"
        >
          <a-input-password v-model:value="formState.pwd"/>
        </a-form-item>

        <a-form-item name="remember" :wrapper-col="{ offset: 4, span: 16 }">
          <a-checkbox v-model:checked="formState.remember">Remember me</a-checkbox>
        </a-form-item>

        <a-form-item :wrapper-col="{ offset: 4, span: 16 }">
          <a-button type="primary" html-type="submit">登录</a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script setup>
import {reactive} from "vue";
import {loginReq} from "@/api/user";
import {useRouter} from "vue-router"

let $router = useRouter()

const formState = reactive({
  username: '',
  pwd: '',
  remember: true,
});

// 提交登录
const onFinish = values => {
  loginReq("post", {
    userId: formState.username,
    password: formState.pwd
  }).then(res => {
    if (res.data.success) {
      localStorage.setItem('user', JSON.stringify({
        token: res.data.token,
        isAdmin: res.data.isAdmin,
        userId: res.data.userId
      }))
      localStorage.setItem('token', res.data.token)
      console.log(res.data)
    }
  })
  //  TODO
  localStorage.setItem('isAdmin', 'true')
  $router.push('/main')
  console.log('Success:', values);
};
const onFinishFailed = errorInfo => {
  console.log('Failed:', errorInfo);
};
</script>

<style lang="scss" scoped>
.login {
  width: 100vw;
  height: 100vh;
  background: linear-gradient(to bottom, rgb(10, 108, 149), lightblue);
  display: flex;
  justify-content: center;
  align-items: center;

  .content {
    width: 500px;
    padding-top: 20px;
    padding-bottom: 0;
    border: 1px solid #ffffff;
    border-radius: 8px;
  }
}
</style>
