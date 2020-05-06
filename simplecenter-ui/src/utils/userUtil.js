import store from '../store'
import {DELUSER,DELMENUROUTE} from '../store/mutations-types'
import Vue from "vue";

export function clearUser () {
  sessionStorage.removeItem("menuList")
  store.commit(DELUSER)
  store.commit(DELMENUROUTE)
  console.log("Vue.cookie.",Vue.cookie)
  Vue.cookie.remove('token')
}
