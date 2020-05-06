<template>
  <div class="main-config">
    <el-button type="info" @click="toHome">跳转</el-button>
  </div>
</template>

<script>
  export default {
    name: "home",
    activated() {

    },
    methods: {
      toHome() {
        this.$router.push({name: "home1"})
      }
    }

  }
</script>

<style scoped>

</style>
