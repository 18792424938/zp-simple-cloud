<template>
  <div class="main-config">
    <div class="search-group">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="审批人">
          <el-input v-model="searchForm.user" placeholder="审批人"></el-input>
        </el-form-item>
        <el-form-item label="活动区域">
          <el-select v-model="searchForm.region" placeholder="活动区域">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDataList()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="button-group">
      <el-button type="primary" @click="addOrUpdateView()">新增</el-button>
    </div>
    <!--:default-expand-all="true" 默认展开全部-->
    <el-table
      v-loading="tableloading"
      :data="tableData"
      border>
      <el-table-column
        prop="name"
        label="姓名"
        width="180">
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="addOrUpdateView(scope.row)">修改</el-button>
          <el-button type="text" @click="deleteHandle(scope.row)">禁用</el-button>
        </template>
      </el-table-column>
    </el-table><!--
    -->
    <div class="pager-footer">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pager.currentPage"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="pager.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pager.total">
      </el-pagination>
    </div>


    <!--菜单新增或者修改-->
    <el-dialog
      title="新增"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      :lock-scroll="false"
      width="50%">
      <el-form v-loading="userFormloading" :model="userForm" :rules="userRules" ref="userForm" label-width="120px">
        <el-form-item  label="用户名:" prop="username">
          <el-input v-model="userForm.username" :readonly="userForm.id?true:false" placeholder="请输入" clearable></el-input>
        </el-form-item>
        <el-form-item label="姓名:" prop="realname">
          <el-input v-model="userForm.realname" placeholder="请输入" clearable></el-input>
        </el-form-item>
        <el-form-item v-if="!userForm.id" label="默认密码:" prop="password">
          <el-input v-model="userForm.password" readonly placeholder="请输入" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态:" prop="type">
          <el-radio-group v-model="userForm.status"  clearable>
            <el-radio :label="10">启用</el-radio>
            <el-radio :label="20">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOrUpdateHandle">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "user-list",
    data() {
      return {
        pager:{
          currentPage:0,
          currentSize:10,
          total:0,
        },
        tableloading:false,
        userFormloading: false,
        dialogVisible: false,
        tableData: [],
        searchForm:{},
        userForm: {
          id: "",
          username: "",
          realname: "",
          password: "",
          status: 10
        },
        userRules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
          ],
          realname: [
            {required: true, message: '请输入姓名', trigger: 'change'},
          ],
          status: [
            {required: true, message: '请选择状态', trigger: 'change'},
          ],

        }
      }
    },
    activated() {
      this.getDataList();

    },
    methods: {
      getDataList() {
        this.tableloading = true;
        this.$http({
          url: `/sys/user/list`,
          method: 'get',
          params:this.$http.adornParams({
            currentPage: this.pager.currentPage,
            currentSize: this.pager.currentSize,
          })
        }).then(({data}) => {
          if (data.code == 0 && data.data) {
            this.pager.total = data.data.total
            this.tableData = data.data.records;
          }
        }).finally((res) => {
          this.tableloading = false
        })
      },
      //新增或者修改
      addOrUpdateView(row) {
        this.dialogVisible = true;
        this.$nextTick(() => {
          this.$refs["userForm"].resetFields();
        })
        if (row) {//修改
          this.userFormloading = true;
          this.$http({
            url: `/sys/user/info/${row.id}`,
            method: 'get'
          }).then(({data}) => {
            if (data.code == 0 && data.data) {
              this.$set(this,'userForm',data.data)
            }
          }).finally((res) => {
            this.userFormloading = false;
          })
        } else {
          this.userForm.password = '11111111'
          this.userFormloading = false;
        }

      },
      //菜单保存
      addOrUpdateHandle() {
        this.$refs["userForm"].validate((valid) => {
          if (valid) {
            this.userFormloading = true;
            this.$http({
              url: `/sys/user/${this.userForm.id?'update':'save'}`,
              method: 'post',
              data: this.$http.adornData(this.userForm)
            }).then(({data}) => {
              if (data.code == 0 ) {
                this.$message({
                  message:  '操作成功',
                  type: 'success'
                });
                this.getDataList();
                this.dialogVisible = false;
              }else{
                this.$message.error(data.msg)
              }
            }).finally((res) => {
              this.userFormloading = false;
            })
          }
        })

      },
      //启用/禁用
      deleteHandle(row) {
        this.tableloading = true
        this.$http({
          url: `/sys/user/delete/${row.id}`,
          method: 'get'
        }).then(({data}) => {
          if (data.code == 0 ) {
            this.$message({
              message:  '操作成功',
              type: 'success'
            });
            this.getTree();
          }else{
            this.$message.error(data.msg)
          }
        }).finally((res) => {
          this.tableloading = false
        })
      },
    }
  }
</script>

<style scoped>

</style>
