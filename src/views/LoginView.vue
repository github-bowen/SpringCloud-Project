<template>
  <div class="login">
    <div class="content">
      <a-form
          :model="formState"
          name="normal_login"
          class="login-form"
          @finish="onFinish"
          @finishFailed="onFinishFailed"
      >
        <a-form-item
            label="Username"
            name="username"
            :rules="[{ required: true, message: 'Please input your username!' }]"
        >
          <a-input v-model:value="formState.username">
            <template #prefix>
              <UserOutlined class="site-form-item-icon" />
            </template>
          </a-input>
        </a-form-item>

        <a-form-item
            label="Password"
            name="pwd"
            :rules="[{ required: true, message: 'Please input your password!' }]"
        >
          <a-input-password v-model:value="formState.pwd">
            <template #prefix>
              <LockOutlined class="site-form-item-icon" />
            </template>
          </a-input-password>
        </a-form-item>


        <a-form-item>
          <a-button :disabled="disabled" type="primary" html-type="submit" class="login-form-button">
            Log in
          </a-button>
          Or
          <a @click="goRegister" style="color: firebrick">register now!</a>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>

<script setup>
import {computed, reactive} from "vue";
import {loginReq} from "@/api/user";
import {useRouter} from "vue-router"
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue';

let $router = useRouter()

let goRegister = () => {
  $router.push('/register')
}

const formState = reactive({
  username: '',
  pwd: '',
  remember: true,
});

const disabled = computed(() => {
  return !(formState.username && formState.pwd);
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
  localStorage.setItem('userId', '123123')
  localStorage.setItem('user', JSON.stringify({
    token: '123123',
    isAdmin: 'true',
    userId: '123123',
  }))
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
  background: linear-gradient(to bottom, rgb(34, 152, 201), lightblue);
  display: flex;
  justify-content: center;
  align-items: center;

  .content {
    width: 400px;
    padding-top: 20px;
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 0;
    border: 1px solid #ffffff;
    border-radius: 8px;
  }
}
</style>
