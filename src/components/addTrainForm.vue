<template>
  <div>
    <a-button type="primary" @click="showModal">添加车次</a-button>
    <a-modal v-model:visible="visible" title="Basic Modal" @ok="handleOk">
      <a-form
          ref="formRef"
          name="dynamic_form_nest_item"
          :model="dynamicValidateForm"
          @finish="onFinish"
      >
        <a-form-item label="车次ID" :rules="{required: true}">
          <a-input v-model:value="dynamicValidateForm.trainName" placeholder="trainName"/>
        </a-form-item>
        <a-form-item label="发车日期" :rules="{required: true}">
          <a-date-picker  v-model:value="dynamicValidateForm.startTime"/>
        </a-form-item>
        <a-form-item label="车次容量" :rules="{required: true}">
          <a-input v-model:value="dynamicValidateForm.capacity" placeholder="capacity"/>
        </a-form-item>
        <a-form-item label="发车频率" :rules="{required: true}">
          <a-input v-model:value="dynamicValidateForm.frequency" placeholder="frequency"/>
        </a-form-item>
        <a-space
            v-for="(station, index) in dynamicValidateForm.stations"
            :key="station.id"
            style="display: flex; margin-bottom: 8px"
            align="baseline"
        >
          <a-form-item
              :name="['stations', index, 'stationName']"
              :rules="{
          required: true,
          message: '缺少车站名',
        }"
              label="所经车站"
          >
            <a-input v-model:value="station.stationName" placeholder="stationName"/>
          </a-form-item>
          <MinusCircleOutlined @click="removeStation(station)"/>
        </a-space>
        <a-form-item>
          <a-button type="dashed" block @click="addStation">
            <PlusOutlined/>
            Add station
          </a-button>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" html-type="submit">Submit</a-button>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
<script>
import {defineComponent, reactive, ref} from 'vue';
import {MinusCircleOutlined, PlusOutlined} from "@ant-design/icons-vue";

export default defineComponent({
  components: {
    MinusCircleOutlined,
    PlusOutlined,
  },
  setup() {
    const visible = ref(false);
    const showModal = () => {
      visible.value = true;
    };
    const handleOk = e => {
      // request
      console.log(e);
      /*发送请求,清除表格*/
      visible.value = false;
      dynamicValidateForm.stations = []
      dynamicValidateForm.trainName = ''
      dynamicValidateForm.capacity = ''
      dynamicValidateForm.startTime = ''
      dynamicValidateForm.frequency = ''
    };
    const formRef = ref();
    const dynamicValidateForm = reactive({
      stations: [],
      trainName: '',
      capacity: '',
      startTime: '',
      frequency: ''
    });
    const removeStation = item => {
      let index = dynamicValidateForm.stations.indexOf(item);
      if (index !== -1) {
        dynamicValidateForm.stations.splice(index, 1);
      }
    };
    const addStation = () => {
      dynamicValidateForm.stations.push({
        stationName: '',
        id: Date.now(),
      });
    };
    const onFinish = values => {
      // request
      console.log('Received values of form:', values);
      console.log('dynamicValidateForm.stations:', dynamicValidateForm.stations);
      visible.value = false;
      dynamicValidateForm.stations = []
      dynamicValidateForm.trainName = ''
      dynamicValidateForm.capacity = ''
      dynamicValidateForm.startTime = ''
      dynamicValidateForm.frequency = ''
    };
    return {
      visible,
      showModal,
      handleOk,
      formRef,
      dynamicValidateForm,
      onFinish,
      removeStation,
      addStation,
    };
  },
});
</script>
