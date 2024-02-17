package com.licheng.sample.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.licheng.sample.entity.AppEntity;
import com.licheng.sample.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/*
 * @author LiCheng
 * @date  2024-02-16 00:39:27
 *
 * app实体的mapper
 */
@Mapper
public interface AppMapper extends BaseMapper<AppEntity> {
}
