<template>
  <a-table :columns="columns" :data-source="dataSource"
           :pagination="pagination" :showQuickJumper="true"
           bordered>
    <template #headerCell="{ column }">
      <template v-if="column.key === 'trainId'">
        <span style="color: #1890ff">Name</span>
      </template>
    </template>
    <!--    搜索-->
    <template
        #customFilterDropdown="{ setSelectedKeys, selectedKeys, confirm, clearFilters, column }"
    >
      <div style="padding: 8px">
        <a-input
            ref="searchInput"
            :placeholder="`Search ${column.dataIndex}`"
            :value="selectedKeys[0]"
            style="width: 188px; margin-bottom: 8px; display: block"
            @change="e => setSelectedKeys(e.target.value ? [e.target.value] : [])"
            @pressEnter="handleSearch(selectedKeys, confirm, column.dataIndex)"
        />
        <a-button
            type="primary"
            size="small"
            style="width: 90px; margin-right: 8px"
            @click="handleSearch(selectedKeys, confirm, column.dataIndex)"
        >
          <template #icon>
            <SearchOutlined/>
          </template>
          Search
        </a-button>
        <a-button size="small" style="width: 90px" @click="handleReset(clearFilters)">
          Reset
        </a-button>
      </div>
    </template>
    <template #customFilterIcon="{ filtered }">
      <search-outlined :style="{ color: filtered ? '#108ee9' : undefined }"/>
    </template>
    <!--    展示数据-->
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
      <!--      操作-->
      <template v-else-if="column.dataIndex === 'operation'">
        <div class="editable-row-operations" style="display: inline-flex">
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
        <a-popconfirm
            v-if="dataSource.length"
            title="Sure to delete?"
            @confirm="onDelete(record.key)"
            style="display: inline-flex"
        >
          <a>Delete</a>
        </a-popconfirm>

      </template>
    </template>
    <template #expandedRowRender="{ record }">
      <p style="margin: 0">
        {{ record.description }}
      </p>
    </template>
  </a-table>
</template>
<script>
import {cloneDeep} from 'lodash-es';
import {defineComponent, reactive, ref, toRefs} from 'vue';
import {SearchOutlined} from '@ant-design/icons-vue';

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
  trainId: '2',
  route: '西直门-...-东直门',
  capacity: 999,
  startTime: '5:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
}, {
  key: 3,
  trainId: '3',
  route: '西直门-...-东直门',
  capacity: 999,
  startTime: '6:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
}, {
  key: 4,
  trainId: '4',
  route: '西直门-...-东直门',
  capacity: 999,
  startTime: '6:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
}, {
  key: 5,
  trainId: '5',
  route: '西直门-...-东直门',
  capacity: 999,
  startTime: '6:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
}, {
  key: 6,
  trainId: '6',
  route: '西直门-...-东直门',
  capacity: 999,
  startTime: '6:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
}, {
  key: 7,
  trainId: '7',
  route: '西直门-...-东直门',
  capacity: 999,
  startTime: '6:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
}, {
  key: 8,
  trainId: '8',
  route: '西直门-...-东直门',
  capacity: 999,
  startTime: '6:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
}, {
  key: 9,
  trainId: '9',
  route: '西直门-...-东直门',
  capacity: 999,
  startTime: '6:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
}, {
  key: 10,
  trainId: '10',
  route: '西直门-...-东直门',
  capacity: 999,
  startTime: '6:00',
  frequency: '5分钟一次',
  description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
},
  {
    key: 11,
    trainId: '2',
    route: '西直门-...-东直门',
    capacity: 999,
    startTime: '5:00',
    frequency: '5分钟一次',
    description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
  }, {
    key: 12,
    trainId: '3',
    route: '西直门-...-东直门',
    capacity: 999,
    startTime: '6:00',
    frequency: '5分钟一次',
    description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
  }, {
    key: 13,
    trainId: '4',
    route: '西直门-...-东直门',
    capacity: 999,
    startTime: '6:00',
    frequency: '5分钟一次',
    description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
  }, {
    key: 14,
    trainId: '5',
    route: '西直门-...-东直门',
    capacity: 999,
    startTime: '6:00',
    frequency: '5分钟一次',
    description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
  }, {
    key: 15,
    trainId: '6',
    route: '西直门-...-东直门',
    capacity: 999,
    startTime: '6:00',
    frequency: '5分钟一次',
    description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
  }, {
    key: 16,
    trainId: '7',
    route: '西直门-...-东直门',
    capacity: 999,
    startTime: '6:00',
    frequency: '5分钟一次',
    description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
  }, {
    key: 17,
    trainId: '8',
    route: '西直门-...-东直门',
    capacity: 999,
    startTime: '6:00',
    frequency: '5分钟一次',
    description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
  }, {
    key: 18,
    trainId: '9',
    route: '西直门-...-东直门',
    capacity: 999,
    startTime: '6:00',
    frequency: '5分钟一次',
    description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
  }, {
    key: 19,
    trainId: '10',
    route: '西直门-...-东直门',
    capacity: 999,
    startTime: '6:00',
    frequency: '5分钟一次',
    description: '西直门-大钟寺-知春路-五道口-上地-xxx-xxx-xxx-xxx-xxx-xxx-东直门',
  },
];
export default defineComponent({
  components: {SearchOutlined},
  setup() {
    const state = reactive({
      searchText: '',
      searchedColumn: '',
    });
    const searchInput = ref();
    const columns = [{
      title: '车次ID',
      dataIndex: 'trainId',
      key: 'trainId',
      fixed: true,
      customFilterDropdown: true,
      onFilter: (value, record) => record.trainId.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            searchInput.value.focus();
          }, 100);
        }
      },
    }, {
      title: '车次路线',
      dataIndex: 'route',
      key: 'route',
      customFilterDropdown: true,
      onFilter: (value, record) => record.route.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            searchInput.value.focus();
          }, 100);
        }
      },
    }, {
      title: '载客容量',
      dataIndex: 'capacity',
      key: 'capacity',
    }, {
      title: '发车时间',
      dataIndex: 'startTime',
      key: 'startTime',
      customFilterDropdown: true,
      onFilter: (value, record) => record.startTime.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            searchInput.value.focus();
          }, 100);
        }
      },
    }, {
      title: '发车频次',
      dataIndex: 'frequency',
      key: 'frequency',
    }, {
      title: 'operation',
      dataIndex: 'operation',
    }];

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
    const handleSearch = (selectedKeys, confirm, dataIndex) => {
      confirm();
      state.searchText = selectedKeys[0];
      state.searchedColumn = dataIndex;
    };
    const onDelete = key => {
      dataSource.value = dataSource.value.filter(item => item.key !== key);
    };
    const handleReset = clearFilters => {
      clearFilters({
        confirm: true,
      });
      state.searchText = '';
    };
    return {
      dataSource,
      columns,
      editingKey: '',
      editableData,
      edit,
      save,
      cancel,
      handleSearch,
      handleReset,
      searchInput,
      onDelete,
      ...toRefs(state),
      pagination: {pageSizeOptions: ['10', '20', '30', '40', '50'], position: 'topLeft'}
    };
  },
});
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>
