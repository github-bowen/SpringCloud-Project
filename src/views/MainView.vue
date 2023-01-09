<template>
  <a-layout class="layout">
    <!--   左侧 -->
    <a-layout-sider v-model:collapsed="collapsed" :trigger="null" collapsible>

      <img src="../assets/LOGO.jpg" v-if="collapsed" alt="" class="logoSmall">
      <img src="../assets/LOGO.jpg" v-else alt="" class="logoBig">
      <a-menu
          v-model:openKeys="openKeys"
          v-model:selectedKeys="selectedKeys"
          mode="inline"
          theme="dark"
          :inline-collapsed="collapsed"
      >

        <a-sub-menu key="sub1">
          <template #icon>
            <UserOutlined/>
          </template>
          <template #title>个人中心</template>
          <a-menu-item key="1" @click="()=>option=1">我的信息</a-menu-item>
          <a-menu-item key="2" @click="()=>option=2">我的车票</a-menu-item>
        </a-sub-menu>
        <a-menu-item key="3" @click="()=>option=3">
          <template #icon>
            <PayCircleOutlined/>
          </template>
          <span>购买车票</span>
        </a-menu-item>

        <a-menu-item key="4" v-if="isAdmin" @click="()=>option=4">
          <template #icon>
            <UnorderedListOutlined/>
          </template>
          <span>车次管理</span>
        </a-menu-item>

        <a-sub-menu key="sub2" v-if="isAdmin">
          <template #icon>
            <AppstoreOutlined/>
          </template>
          <template #title>多级菜单样例</template>
          <a-menu-item key="9">Option 9</a-menu-item>
          <a-menu-item key="10">Option 10</a-menu-item>
          <a-sub-menu key="sub3" title="Submenu">
            <a-menu-item key="11">Option 11</a-menu-item>
            <a-menu-item key="12">Option 12</a-menu-item>
          </a-sub-menu>
        </a-sub-menu>
      </a-menu>

    </a-layout-sider>
    <!--    右侧-->
    <a-layout>
      <!--顶部-->
      <a-layout-header style="background: #fff; padding: 0">
        <menu-unfold-outlined
            v-if="collapsed"
            class="trigger"
            @click="() => (collapsed = !collapsed)"
        />
        <menu-fold-outlined v-else class="trigger" @click="() => (collapsed = !collapsed)"/>
        <a-button type="primary" danger class="logoutButton" @click="logout">退出登录</a-button>
      </a-layout-header>
      <!--      主体-->
      <a-layout-content
          :style="{ margin: '10px 16px', padding: '10px', background: '#fff', minHeight: '280px' }">
        <modify-user-info v-if="option===1" class="userInfoComponent"></modify-user-info>
<!--        <test-component v-else-if="option === 2"></test-component>-->
<!--        <test-component v-else-if="option === 3"></test-component>-->
        <test-component v-else-if="option === 4"></test-component>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup>
import {
  // UserOutlined,
  // VideoCameraOutlined,
  // UploadOutlined,
  PayCircleOutlined,
  UnorderedListOutlined,
  UserOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined
} from '@ant-design/icons-vue';
import {ref} from 'vue'
import {useRouter} from "vue-router";
import TestComponent from "@/components/testComponent";
import ModifyUserInfo from "@/components/modifyUserInfo";
//左侧菜单选中的key
let $router = useRouter()
let user = JSON.parse(localStorage.getItem('user'))
if (!user) {
  $router.push('/') //返回登录页面
}
let selectedKeys = ref(['1'])
// 是否折叠
let collapsed = ref(false)
let isAdmin = ref(localStorage.getItem('isAdmin') === 'true')
let openKeys = ref(['sub1'])
let option = ref(1);

let logout = () => {
  localStorage.clear();
  $router.push('/');
}

// let gotoMain = () => {
//   this.option = 1
//   console.log(option)
// }
//
// let gotoMyTicket = () => {
//   this.option = 2
// }
//
// let gotoMarket = () => {
//   this.option = 3
// }
//
// let gotoManage = () => {
//   this.option = 4
// }


</script>

<style scoped lang="scss">
.layout {
  width: 100vw;
  height: 100vh;

  .logoSmall {
    height: 60px;
    width: 60px;
    background: rgba(255, 255, 255, 0.3);
    margin: 8px;
    color: white;
    text-align: center;
    font-size: 18px;
  }

  .logoBig {
    height: 100px;
    width: 100px;
    background: rgba(255, 255, 255, 0.3);
    margin: 20px 20px 20px 45px;
    color: white;
    text-align: center;
    font-size: 18px;
  }

  .trigger {
    font-size: 18px;
    line-height: 64px;
    padding: 0 24px;
    cursor: pointer;
    transition: color 0.3s;

    &:hover {
      color: #1890ff;
    }
  }

  .logoutButton {
    margin-top: 15px;
    margin-right: 30px;
    float: right;
  }

  .userInfoComponent{
    margin-left: 15vw;
    width: 50vw;
    padding-top: 20px;
    padding-left: 20px;
    padding-right: 20px;
    padding-bottom: 0;
    border: 1px solid dodgerblue;
    border-radius: 8px;
  }
}

</style>
