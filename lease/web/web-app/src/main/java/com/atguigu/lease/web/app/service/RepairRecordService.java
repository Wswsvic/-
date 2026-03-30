package com.atguigu.lease.web.app.service;

import com.atguigu.lease.model.entity.RepairRecord;
import com.atguigu.lease.web.app.vo.myroom.RepairRecordVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RepairRecordService extends IService<RepairRecord> {
    
    /**
     * 分页查询用户报修记录
     */
    Page<RepairRecordVo> pageRepairRecordByUserId(Page<RepairRecordVo> page, Long userId);
}