package com.atguigu.lease.web.app.mapper;

import com.atguigu.lease.model.entity.RepairRecord;
import com.atguigu.lease.web.app.vo.myroom.RepairRecordVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RepairRecordMapper extends BaseMapper<RepairRecord> {
    
    /**
     * 分页查询用户的报修记录
     */
    Page<RepairRecordVo> pageRepairRecordByUserId(Page<RepairRecordVo> page, 
                                                   @Param("userId") Long userId);
}