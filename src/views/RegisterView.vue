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

        <a-form-item
            label="重复密码"
            name="pwd2"
            :rules="[{ required: true, message: '请再次输入密码!' }]"
        >
          <a-input-password v-model:value="formState.pwd2"/>
        </a-form-item>


        <a-form-item :wrapper-col="{ offset: 4, span: 16 }">
          <a-button :disabled="disabled" type="primary" html-type="submit" class="register-form-button">
            注册
          </a-button>
          <a href="/" style="color: firebrick;margin-left: 20px">去登录</a>
        </a-form-item>

      </a-form>
    </div>
  </div>
</template>

<script setup>
import {computed, reactive} from "vue";
import {registerReq} from "@/api/user";
import {useRouter} from "vue-router"

let $router = useRouter()

const formState = reactive({
  username: '',
  pwd: '',
  pwd2: '',
  remember: true,
});


// 提交登录
const onFinish = values => {
  registerReq('post', {
    userId: formState.username,
    password: formState.pwd2
  }).then(res=>{
    if (res.success) {
      $router.push('/')
    } else {
      //TODO:错误提示
    }
  })
  $router.push('/')
  console.log('Success:', values);
};
const onFinishFailed = errorInfo => {
  console.log('Failed:', errorInfo);
};

const disabled = computed(() => {
  return !(formState.pwd2 === formState.pwd);
});
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
    padding-bottom: 0;
    border: 1px solid #ffffff;
    border-radius: 8px;
  }
}
</style>
