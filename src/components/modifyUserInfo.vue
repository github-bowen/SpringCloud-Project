<template>
  <a-form
      ref="formRef"
      name="custom-validation"
      :model="formState"
      :rules="rules"
      v-bind="layout"
      @finish="handleFinish"
      @validate="handleValidate"
      @finishFailed="handleFinishFailed"
  >
    <div style="text-align: center; font-size: 28px; margin-right: 10px" >修改密码</div>

    <a-form-item>
      <a-comment style=" font-size: 16px;">您的用户ID:{{userId}}</a-comment>
    </a-form-item>
    <a-form-item has-feedback label="PrePassword" name="prePass">
      <a-input v-model:value="formState.prePass" type="password" autocomplete="off" />
    </a-form-item>
    <a-form-item has-feedback label="Password" name="pass">
      <a-input v-model:value="formState.pass" type="password" autocomplete="off" />
    </a-form-item>
    <a-form-item has-feedback label="Confirm" name="checkPass">
      <a-input v-model:value="formState.checkPass" type="password" autocomplete="off" />
    </a-form-item>
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" html-type="submit">Submit</a-button>
      <a-button style="margin-left: 10px" @click="resetForm">Reset</a-button>
    </a-form-item>
  </a-form>
</template>
<script>
import { defineComponent, reactive, ref } from 'vue';
import {modifyUserInfoReq} from "@/api/user";
export default defineComponent({
  setup() {
    const formRef = ref();
    const formState = reactive({
      prePass:'',
      pass: '',
      checkPass: '',
    });
    let userId = localStorage.getItem('userId')
    let validatePass = async (_rule, value) => {
      if (value === '') {
        return Promise.reject('Please input the password');
      } else {
        if (formState.checkPass !== '') {
          await formRef.value.validateFields('checkPass');
        }
        return Promise.resolve();
      }
    };
    let validatePass2 = async (_rule, value) => {
      if (value === '') {
        return Promise.reject('Please input the password again');
      } else if (value !== formState.pass) {
        return Promise.reject("Two inputs don't match!");
      } else {
        return Promise.resolve();
      }
    };
    const rules = {
      prePass: [{
        required: true,
        trigger: 'change',
      }],
      pass: [{
        required: true,
        validator: validatePass,
        trigger: 'change',
      }],
      checkPass: [{
        validator: validatePass2,
        trigger: 'change',
      }],
    };
    const layout = {
      labelCol: {
        span: 4,
      },
      wrapperCol: {
        span: 14,
      },
    };
    const handleFinish = values => {
      modifyUserInfoReq('post', {
        prePassword:formState.prePass,
        password:formState.pass
      })
      console.log(values, formState);
    };
    const handleFinishFailed = errors => {
      console.log(errors);
    };
    const resetForm = () => {
      formRef.value.resetFields();
    };
    const handleValidate = (...args) => {
      console.log(args);
    };
    return {
      userId,
      formState,
      formRef,
      rules,
      layout,
      handleFinishFailed,
      handleFinish,
      resetForm,
      handleValidate,
    };
  },
});
</script>
