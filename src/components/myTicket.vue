<template>
  <a-table :columns="columns" :data-source="dataInit"
           :pagination="pagination" :showQuickJumper="true"
           bordered v-if="r">
    <template #headerCell="{ column }">
      <template v-if="column.key === 'ticketId'">
        <span style="color: #1890ff">Name </span>
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
      <template v-if="['ticketId', 'beginStation', 'endStation'].includes(column.dataIndex)">
        <div>
          {{ text }}
        </div>
      </template>
      <!--      操作-->
      <template v-else-if="column.dataIndex === 'operation'">
        <a-popconfirm
            v-if="dataSource.length"
            title="确定要退票吗?"
            @confirm="onDelete(record.ticketId, record.key)"
            style="display: inline-flex"
        >
          <a>退票</a>
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

import {defineComponent, reactive, ref, toRefs} from 'vue';
import {SearchOutlined} from '@ant-design/icons-vue';
import {message} from "ant-design-vue";
import {delTicketReq, getMyTicket} from "@/api/ticket";
import router from '../router'


let dataInit = []
getMyTicket('get', {}).then(res => {
  if (res.data.success) {
    //dataInit = []
    for (let i = 0; i < res.data.data.length; i++) {
      //console.log(res.data.data[i]);
      dataInit.push({
        key: res.data.data[i].key,
        ticketId: res.data.data[i].ticketId,
        trainId: res.data.data[i].trainId,
        beginStation: res.data.data[i].beginStation,
        endStation: res.data.data[i].endStation,
        startTime: res.data.data[i].startTime,
        description: res.data.data[i].description,
      });
    }
    console.log(dataInit);
    message.success('成功加载车票信息')
  } else {
    message.success('加载车票信息失败')
  }
})
// dataInit.push({
//   key: 1,
//   ticketId: 1,
//   trainId: 'g888',
//   beginStation: '北京',
//   endStation: '东京',
//   startTime: '2022-1-23',
//   description: '北京-南京-东京-西京',
// })

console.log("datainit", dataInit);
export default defineComponent({
  // eslint-disable-next-line vue/no-unused-components
  components: {SearchOutlined},
  setup() {
    const state = reactive({
      searchText: '',
      searchedColumn: '',
    });
    const searchInput = ref();
    let r = true;
    const columns = [{
      title: '车票ID',
      dataIndex: 'ticketId',
      key: 'ticketId',
      fixed: true,
      customFilterDropdown: true,
      onFilter: (value, record) => record.ticketId.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            searchInput.value.focus();
          }, 100);
        }
      },
    },{
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
      title: '出发站',
      dataIndex: 'beginStation',
      key: 'beginStation',
      fixed: true,
      customFilterDropdown: true,
      onFilter: (value, record) => record.beginStation.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            searchInput.value.focus();
          }, 100);
        }
      },
    }, {
      title: '目的地',
      dataIndex: 'endStation',
      key: 'endStation',
      customFilterDropdown: true,
      onFilter: (value, record) => record.endStation.toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: visible => {
        if (visible) {
          setTimeout(() => {
            searchInput.value.focus();
          }, 100);
        }
      },
    },{
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
      title: 'operation',
      dataIndex: 'operation',
    }];
    let dataSource = ref(dataInit);

    const handleSearch = (selectedKeys, confirm, dataIndex) => {
      confirm();
      state.searchText = selectedKeys[0];
      state.searchedColumn = dataIndex;
    };

    const handleReset = clearFilters => {
      clearFilters({
        confirm: true,
      });
      state.searchText = '';
    };

    const onDelete = (ticketId, key2) => {
      delTicketReq('get', {}, ticketId).then(res => {
        if (res.data.success) {
          message.success('删除成功')
        } else {
          message.success('删除失败')
        }
      },)
      dataSource.value = dataSource.value.filter(item => item.key !== key2);
      dataInit = dataInit.filter(item => item.key !== key2);
      router.go(0);
    };

    return {
      r,
      onDelete,
      dataInit,
      dataSource,
      columns,
      editingKey: '',
      handleSearch,
      handleReset,
      searchInput,
      ...toRefs(state),
      pagination: {pageSizeOptions: ['10', '20', '30', '40', '50'], position: 'topLeft'}
    };
  },
  mounted() {
    //this.loadInfo();
  },
  methods: {
    loadInfo: function () {
      getMyTicket('get').then(res => {
        if (res.data.success) {
          //dataInit = []
          for (let i = 0; i < res.data.data.length; i++) {
            //console.log(res.data.data[i]);
            dataInit.push({
              key: res.data.data[i].key,
              ticketId: res.data.data[i].ticketId,
              trainId: res.data.data[i].trainId,
              beginStation: res.data.data[i].beginStation,
              endStation: res.data.data[i].endStation,
              startTime: res.data.data[i].startTime,
              description: res.data.data[i].description,
            });
          }
          //console.log(dataInit);
          message.success('成功加载车票信息')
        } else {
          message.success('加载车票信息失败')
        }
      })
    },
    rerender() {
      this.r = false;
      this.$nextTick(() => this.r = true)
    }
  }
});
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>
