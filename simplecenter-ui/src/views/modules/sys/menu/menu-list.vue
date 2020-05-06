<template>
  <div>
    <el-table
      :data="tableData"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column
        prop="date"
        label="日期"
        sortable
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        sortable
        width="180">
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  export default {
    name: "user-list",
    data() {
      return {
        tableData: []
      }
    },
    methods:{

    }
  }
</script>

<style scoped>

</style>
