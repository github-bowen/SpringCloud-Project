<template>
  <div>
    <a-button type="primary" @click="showModal">购买车票</a-button>
    <a-modal v-model:visible="visible" title="购买车票" @ok="handleOk">
      <a-form
          ref="formRef"
          name="dynamic_form_nest_item"
          :model="dynamicValidateForm"
          @finish="onFinish"
      >
        <a-comment style="display:inline-flex;">车次ID: {{ train_id }}</a-comment>
        <a-comment style="display:inline-flex;">车次路线: {{ train_route }}</a-comment>
        <a-form-item label="起始站" :rules="{required: true}">
          <a-input v-model:value="dynamicValidateForm.beginStation"/>
        </a-form-item>
        <a-form-item label="目的地" :rules="{required: true}">
          <a-input v-model:value="dynamicValidateForm.endStation"/>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" html-type="submit">购买</a-button>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script>
import {defineComponent, reactive, ref} from 'vue';
import {message} from "ant-design-vue";
import router from '../router'
import {addTicketReq} from "@/api/ticket";

export default defineComponent({
  props: {
    trainId: String,
    trainRoute:String
  },
  // eslint-disable-next-line no-unused-vars
  setup(props, context) {
    const visible = ref(false);
    const showModal = () => {
      visible.value = true;
    };
    const formRef = ref();
    const dynamicValidateForm = reactive({
      beginStation: '',
      endStation: ''
    });
    // console.log("props",props)
    // eslint-disable-next-line vue/no-setup-props-destructure
    const train_id = props.trainId;
    // eslint-disable-next-line vue/no-setup-props-destructure
    const train_route = props.trainRoute;

    const sendAddRequest = async () => {
      let ret = false;
      await addTicketReq('post', {
        beginStation: dynamicValidateForm.beginStation,
        endStation: dynamicValidateForm.endStation
      }, train_id).then(res => {
        let data = res.data
        if (data.success) {
          message.success('车票购买成功')
          return true;
        } else {
          message.error('车票购买失败')
          return false;
        }
      })
      return ret;
    };
    const clearForm = () => {
      dynamicValidateForm.beginStation = '';
      dynamicValidateForm.endStation = '';

    }
    const handleOk = e => {
      // request
      if (sendAddRequest()) {
        console.log(e);
        /*发送请求,清除表格*/
        visible.value = false;
        clearForm();
        router.go(0);
      }
    };

    const onFinish = values => {
      // request
      var b = sendAddRequest();
      if (b) {
        console.log('Received values of form:', values);
        //console.log('dynamicValidateForm:', dynamicValidateForm);
        visible.value = false;
        clearForm();
        router.go(0);
      }
    };

    return {
      train_id,
      visible,
      showModal,
      handleOk,
      formRef,
      dynamicValidateForm,
      onFinish,
      train_route
    };
  },
});
</script>
