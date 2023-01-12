<template>
  <a-table :columns="columns" :data-source="dataInit"
           :pagination="pagination" :showQuickJumper="true"
           bordered v-if="r">
    <template #headerCell="{ column }">
      <template v-if="column.key === 'trainId'">
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
      <template v-if="['startTime', 'capacity'].includes(column.dataIndex)">
        <div>
            {{ text }}
        </div>
      </template>
      <!--      操作-->
      <template v-else-if="column.dataIndex === 'operation'">
        <but-ticket-form :trainId = "record.trainId"></but-ticket-form>
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
import {showTrainReq} from "@/api/train";
import {message} from "ant-design-vue";
import ButTicketForm from "@/components/butTicketForm";

let dataInit = []
showTrainReq('get').then(res => {
  if (res.data.success) {
    //dataInit = []
    for (let i = 0; i < res.data.data.length; i++) {
      //console.log(res.data.data[i]);
      dataInit.push({
        key: res.data.data[i].key,
        trainId: res.data.data[i].trainId,
        route: res.data.data[i].route,
        capacity: res.data.data[i].capacity,
        startTime: res.data.data[i].startTime,
        // frequency: res.data.data[i].frequency,
        description: res.data.data[i].description,
      });
    }
    console.log(dataInit);
    message.success('成功加载车次信息')
  } else {
    message.success('加载车次信息失败')
  }
})

console.log("datainit", dataInit);
export default defineComponent({
  components: {ButTicketForm,  SearchOutlined},
  setup() {
    const state = reactive({
      searchText: '',
      searchedColumn: '',
    });
    const searchInput = ref();
    let r = true;
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
      title: '剩余容量',
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

    // const buy = key => {
    //   let pre = dataSource.value.filter(item => key === item.key)[0].trainId;
    //
    // };
    return {
      r,
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
    this.loadInfo();
  },
  methods: {
    loadInfo: function () {
      showTrainReq('get').then(res => {
        if (res.data.success) {
          dataInit = []
          for (let i = 0; i < res.data.data.length; i++) {
            //console.log(res.data.data[i]);
            dataInit.push({
              key: res.data.data[i].key,
              trainId: res.data.data[i].trainId,
              route: res.data.data[i].route,
              capacity: res.data.data[i].capacity,
              startTime: res.data.data[i].startTime,
              frequency: res.data.data[i].frequency,
              description: res.data.data[i].description,
            });
          }
          this.rerender();
          //console.log(dataInit);
          //message.success('成功加载车次信息')
        } else {
          message.success('加载车次信息失败')
        }
      })
    },
    rerender(){
      this.r = false;
      this.$nextTick(()=>this.r = true)
    }
  }
});
</script>
<style scoped>
.editable-row-operations a {
  margin-right: 8px;
}
</style>
