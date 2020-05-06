<template>
  <div>
    <el-container>
      <el-header style="background: red">
        <div>
          用户名:{{user.username}} 姓名:{{user.realname}} logo:{{user.logo}}
          <el-button type="text" @click="logoutHandle">退出</el-button>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu
            :default-active="defaultActive"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            @select="handleSelect"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">
            <menuelsubmenu :list="menuList"></menuelsubmenu>
          </el-menu>
        </el-aside>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>活动管理</el-breadcrumb-item>
            <el-breadcrumb-item>活动列表</el-breadcrumb-item>
            <el-breadcrumb-item>活动详情</el-breadcrumb-item>
          </el-breadcrumb>
          <router-view :key="routePath"/> <!--class="animated fadeInRight nfaster"-->
        </el-main>
      </el-container>
    </el-container>

  </div>
</template>

<script>
  import menuelsubmenu from "./menu-el-submenu"
  import {ADDUSER,ADDMENUROUTE} from '@/store/mutations-types'
  const _import = require('@/router/import-' + process.env.NODE_ENV)

  export default {
    name: "main",
    data() {
      return {
        systemId:"",
        defaultActive: "",
        menuList: [],
        user: {id: "", username: "", realname: "", logo: "",},
      }
    },
    computed: {
      routePath() {
        return this.$route.path;
      }
    },
    components: {menuelsubmenu},
    activated() {

      //加载用户信息
      this.initUser();
      //加载系统信息

      //加载菜单
      this.initMenu();




    },
    methods: {
      initMenu(){
        //加载菜单
        const menuListItem = sessionStorage.getItem("menuList");
        if(!menuListItem){
          // 查询菜单
          this.$http({
            url: `/sys/menu/nav`,
            method: 'get',
            params: this.$http.adornParams({systemId:this.systemId})
          }).then(({data}) => {
            if (data && data.code === 0) {
              const menuList = [
                {
                  "id": "1", "name": "系统管理", type:"10","roterName":"", "":"","viewUrl":"", children: [
                    {"id": "1-1", "name": "用户管理", type:"20","roterName":"home1", "componentUrl":"sys/user/user-list","viewUrl":"/sys/user-list"},
                    /*            {"id": "1-2", "name": "角色管理", type:"20","roterName":"home2", "componentUrl":"sys/home2","viewUrl":"/sys/home2"},
                                {"id": "1-3", "name": "菜单管理", type:"20","roterName":"home3", "componentUrl":"sys/home3","viewUrl":"/sys/home3"},
                                {"id": "1-4", "name": "字典表管理", type:"20","roterName":"home4", "componentUrl":"sys/home4","viewUrl":"/sys/home4"}*/
                  ]
                }]

              var routerList = [];
              this.createRoter(menuList,routerList);

              let i = 0;
              for(i =0;i<this.$router.options.routes.length;i++){
                if(this.$router.options.routes[i].name=="main"){
                  break;
                }
              }

              this.$router.options.routes[i].children = this.$router.options.routes[i].children.concat(routerList)

              this.$router.addRoutes([this.$router.options.routes[i]])
              //sessionStorage.setItem("menuList",JSON.stringify(menuList))

              var munuId = "";
              this.handleDefaultActive(menuList,munuId);
              this.menuList = menuList
            }else{
              this.$message.error(data.msg)
            }

          }).catch((res) => {
            this.$message.error("网络异常,请刷新页面")
          }).finally((res) => {

          })
        }else{
          const menuList = JSON.parse(menuListItem)
          var munuId = "";
          this.handleDefaultActive(menuList,munuId);
          this.menuList = menuList
        }
      },
      initUser(){
        this.$http({
          url: `/auth/user`,
          method: 'get'
        }).then(({data}) => {
          if (data && data.code === 0) {
            console.log("------------------",data.data)
            const {id, username,realname,logo}  = data.data;
            console.log("34554678654321",Object.assign({}, { id,username,realname,logo}))

            this.$store.commit(ADDUSER,Object.assign({}, { id,username,realname,logo}))
            // 请求用户信息
            this.$set(this, "user", this.$store.getters.user)
          }
        }).finally((res) => {

        })
      },
      // 根据菜单生成路由
      createRoter(list,routerList){
        list.forEach(item=>{
          if(item.type=='20'||item.type=='30'){
            if(!item.componentUrl.startsWith("/")){
              item.componentUrl = "/"+item.componentUrl;
            }

            if(!item.viewUrl.startsWith("/")){
              item.viewUrl = "/"+item.viewUrl;
            }

            var routerTemp = {
              path: item.viewUrl,
              component: _import('modules'+item.componentUrl),
              name: item.roterName,
              meta: {id: item.id, title: item.name ,isLogin:true}
            };

            routerList.push(routerTemp)
            this.$store.commit(ADDMENUROUTE,{id:item.id,path: item.viewUrl,name: item.roterName})
          }
          if(item.children&&item.children.length){
            this.createRoter(item.children,routerList);
          }
        })
      },

      handleDefaultActive(list,munuId){
        for (let i = 0; i < list.length; i++) {
          if(list[i].type == '20'){//菜单
            munuId = list[i].id
          }
          if(munuId && this.$route.meta.id==list[i].id){
            this.defaultActive = munuId
            return true
          }
          if(list[i].children&&list[i].children.length){
            return this.handleDefaultActive(list[i].children,munuId)
          }
        }
        return false
      },
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      },
      handleSelect(key, keyPath) {
        var route = this.$store.getters.menuRoute[key]
        this.$router.push(route.path)
      },
      //用户退出
      logoutHandle(){
        this.clearUser();
        this.$router.push("/login")
      },

    }
  }
</script>

<style scoped>

</style>
