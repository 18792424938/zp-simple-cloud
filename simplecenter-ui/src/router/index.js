import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
import {ADDMENUROUTE} from '../store/mutations-types'
import de from "element-ui/src/locale/lang/de";


// 封装页面组件导入
const _import = require('./import-' + process.env.NODE_ENV)

//全局系统路由
const globalRoutes = [
  { path: '/404', component: _import('common/404'), name: 'notfound', meta: { title: '404未找到' ,isLogin:false} },
  { path: '/login', component: _import('common/login'), name: 'login', meta: { title: '登录' ,isLogin:false} }

]


const mainRoutes = {
  path: '/',
  component: _import('common/main'),
  name: 'main',
  redirect: { name: "home" },
  children: [{path: '/home', component: _import('common/home'), name: 'home', meta: { title: '首页' ,isLogin:true}}]
}

// 系统管理路由
const customRoutes = [

]



Vue.use(Router)
const router = new Router({
  mode: 'history',
  path: '/',
  routes:  globalRoutes.concat(customRoutes).concat(mainRoutes)
})



// 获取默认路径
const defaultRote = (list) => {
  for (let i = 0; i < list.length; i++) {
    if(list[i].type=='20'){
      return list[i]
    }
    if(list[i].children&&list[i].children.lenth){
      var result = this.defaultRote(list[i].children)
      if(result){
        return result;
      }
    }
  }
  return ;
}


// 根据菜单生成路由
const createRoter = (list,routerList) => {
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
      store.commit(ADDMENUROUTE,{id:item.id,path: item.viewUrl,name: item.roterName})
    }
    if(item.children&&item.children.length){
      createRoter(item.children,routerList);
    }
  })
}


router.beforeEach((to, from, next) => {


  if(1==1){
    next();
    return;
  }

  let defaultName = "";
  for (let i = 0; i < router.options.routes.length; i++) { //获取菜单的main
    if(router.options.routes[i].name=="main"){
      document.title = to.meta.title?to.meta.title:"首页"
      next()
      return
    }
    if(router.options.routes[i].meta&&router.options.routes[i].meta.isDefault){
      defaultName = router.options.routes[i].name
    }
  }

  const localMenuList = sessionStorage.getItem("menuList")
  if(localMenuList){
    const menuList = JSON.parse(localMenuList)//查看缓存是否有路由
    var routerList = [];

    createRoter(menuList,routerList);

    const mainRoutes = {
      path: '/',
      component: _import('common/main'),
      name: 'main',
      redirect: { name: defaultName?defaultName:defaultRote(menuList).roterName },
      children: routerList
    }

    router.options.routes.push(mainRoutes)
    router.addRoutes([mainRoutes])
    next({...to, replace: true})
  }else{




  }





// next() ;
// 添加动态(菜单)路由
// 1. 已经添加 or 全局路由, 直接访问
// 2. 获取菜单列表, 添加并保存本地存储

})


export default router;
