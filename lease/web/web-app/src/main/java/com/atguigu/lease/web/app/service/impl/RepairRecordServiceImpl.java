package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.model.entity.RepairRecord;
import com.atguigu.lease.web.app.mapper.RepairRecordMapper;
import com.atguigu.lease.web.app.service.RepairRecordService;
import com.atguigu.lease.web.app.vo.myroom.RepairRecordVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RepairRecordServiceImpl 
    extends ServiceImpl<RepairRecordMapper, RepairRecord> 
    implements RepairRecordService {
    
    @Override
    public Page<RepairRecordVo> pageRepairRecordByUserId(Page<RepairRecordVo> page, Long userId) {
        return baseMapper.pageRepairRecordByUserId(page, userId);
    }
}