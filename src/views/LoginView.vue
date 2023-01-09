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
              <UserOutlined class="site-form-item-icon"/>
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
              <LockOutlined class="site-form-item-icon"/>
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
import {message} from "ant-design-vue";
import {UserOutlined, LockOutlined} from '@ant-design/icons-vue';

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
      localStorage.setItem('token', res.data.data.token);
      localStorage.setItem('userId', res.data.data.userId)
      localStorage.setItem('isAdmin', res.data.data.isAdmin,)
      console.log(res.data);
      message.success('登录成功');
      $router.push('/main');
    } else {
      message.error('登录失败,请重试!');
    }
  })
  //  TODO
  localStorage.setItem('isAdmin', 'true')
  localStorage.setItem('userId', '123123')
  console.log('Success:', values);
  $router.push('/main')
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
