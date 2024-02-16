package com.licheng.sample.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/*
 * @author LiCheng
 * @date  2024-02-14 18:20:50
 *
 * mybatisPlus相关配置类
 * 这里的配置对象和用yml property等配置文件内的配置内容一致
 */
@Configuration
@MapperScan("com.licheng.sample.*")
public class MybatisPlusConfig {

  /*  @Value("${lc.config.mybatis-plus.sqlShow}")
    private boolean sqlShow;*/

    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));//如果配置多个插件,切记分页最后添加
        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor()); 如果有多数据源可以不配具体类型 否则都建议配上具体的DbType

        return interceptor;
    }

    @Bean
    public StdOutImpl stdOutImpl() {
        StdOutImpl stdOut = new StdOutImpl("org.apache.ibatis.logging.Log4j2Impl");
        return stdOut;
    }

    /**
     * 这里配置mybatisPlus的相关内容
     *
     * @return
     */
    @Bean
    public MybatisConfiguration mybatisConfiguration() {
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();

        // sql打印 这里强制info级别也打印
//        if (sqlShow) {
//            //直接指定日志输出实现 通过isDebugEnabled 强制debug级别输出
//            //使用DruidDataSource后无法输出sql 需要将mybatisSql打印配置注册到Druid
//            mybatisConfiguration.setLogImpl(StdOutImpl.class);
//
//        }

        // 是否开启自动驼峰命名规则映射:从数据库列名到Java属性驼峰命名的类似映射
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);

        // 允许在resultType="map"时映射null值
        // 如果查询结果中包含空值的列，则 MyBatis 在映射的时候，不会映射这个字段
        mybatisConfiguration.setCallSettersOnNulls(true);

        return mybatisConfiguration;
    }

    /**
     * 公共配置
     * 一般常见的数据库根据数据库方言都有默认配置 无需修改
     *
     * @return
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();

        // 1. db相关
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        //表名下划线命名 默认为true
        dbConfig.setTableUnderline(true);
        //是否开启大写命名，默认不开启
        dbConfig.setCapitalMode(false);
        globalConfig.setDbConfig(dbConfig);

        return globalConfig;

    }


}