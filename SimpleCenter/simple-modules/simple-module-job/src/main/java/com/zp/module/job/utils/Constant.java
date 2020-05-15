package com.zp.module.job.utils;

/**
 * 常量
 * 
 * @author  zhaipan
 * @date 2017年11月15日 下午1:23:52
 */
public class Constant {

    /**
     * 定时任务状态
     * 
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

  
    

 
     
    
}
