<template>
  <add-train-form></add-train-form>
  <a-table :columns="columns" :data-source="dataSource" bordered>
    <template #bodyCell="{ column, text, record }">
      <template v-if="['startTime', 'frequency', 'capacity'].includes(column.dataIndex)">
        <div>
          <a-input
              v-if="editableData[record.key]"
              v-model:value="editableData[record.key][column.dataIndex]"
              style="margin: -5px 0"
          />
          <template v-else>
            {{ text }}
          </template>
        </div>
      </template>
      <template v-else-if="column.dataIndex === 'operation'">
        <div class="editable-row-operations">
          <span v-if="editableData[record.key]">
            <a-typography-link @click="save(record.key)">Save</a-typography-link>
            <a-popconfirm title="Sure to cancel?" @confirm="cancel(record.key)">
              <a>Cancel</a>
            </a-popconfirm>
          </span>
          <span v-else>
            <a @click="edit(record.key)">Edit</a>
          </span>
        </div>
      </template>
    </template>
  </a-table>
</template>
<script>
import { cloneDeep } from 'lodash-es';
import { defineComponent, reactive, ref } from 'vue';
import AddTrainForm from "@/components/addTrainForm";
const columns = [{
  title: '车次ID',
  dataIndex: 'trainId',
  key: 'trainId',
  fixed: true,
}, {
  title: '车次路线',
  dataIndex: 'route',
  key: 'route',
}, {
  title: '载客容量',
  dataIndex: 'capacity',
  key: 'capacity',
}, {
  title: '发车时间',
  dataIndex: 'startTime',
  key: 'startTime',
}, {
  title: '发车频次',
  dataIndex: 'frequency',
  key: 'frequency',
}, {
  title: 'operation',
  dataIndex: 'operation',
}];
const data = [{
  key: 1,
  trainId: '13号线',
  route: '始发-..经停站加号展开..-终点',
  capacity: 999,
  startTime: '4:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
}, {
  key: 2,
  trainId: '13号线',
  route: '西直门-...-东直门',
  capacity: 999,
  startTime: '4:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
}, {
  key: 3,
  trainId: '13号线',
  route: '西直门-...-东直门',
  capacity: 999,
  startTime: '4:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
}];
export default defineComponent({
  components: {AddTrainForm},
  setup() {
    const dataSource = ref(data);
    const editableData = reactive({});
    const edit = key => {
      editableData[key] = cloneDeep(dataSource.value.filter(item => key === item.key)[0]);
    };
    const save = key => {
      Object.assign(dataSource.value.filter(item => key === item.key)[0], editableData[key]);
      delete editableData[key];
    };
    const cancel = key => {
      delete editableData[key];
    };
    return {
      dataSource,
      columns,
      editingKey: '',
      editableData,
      edit,
      save,
      cancel,
    };
  },
});
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>
