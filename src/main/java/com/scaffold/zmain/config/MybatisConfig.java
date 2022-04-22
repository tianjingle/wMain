package com.scaffold.zmain.config;

import com.scaffold.zmain.db.user.mapper.TwoMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * @Author tianjl
 * @Date 2021/8/16 11:05
 * @Discription disc
 */
@Configuration
public class MybatisConfig {


    @Bean(name = "dataBaseNoun")
    public SqlSessionFactoryBean getSqlSessionFactoryOne1() throws Exception {
        //xml和实体的映射
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(new MyDataSource(true));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.scaffold.zmain.db.noun");
        Resource[] resources = new Resource[]{
                new ClassPathResource("mybatis/noun/OneMapper.xml"),
                new ClassPathResource("mybatis/noun/CandidateStockMapper.xml")};
        sqlSessionFactoryBean.setMapperLocations(resources);
        sqlSessionFactoryBean.setPlugins(new MybatisLanjieqi());
        return sqlSessionFactoryBean;
    }


    @Bean(name = "dataBaseUser")
    public SqlSessionFactoryBean getSqlSessionFactoryUser() throws Exception {
        //xml和实体的映射
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(new MyDataSource(true));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.scaffold.zmain.db.user");
        Resource[] resources = new Resource[]{
                new ClassPathResource("mybatis/user/TwoMapper.xml")};
        sqlSessionFactoryBean.setMapperLocations(resources);
        sqlSessionFactoryBean.setPlugins(new MybatisLanjieqi());
        return sqlSessionFactoryBean;
    }

//    @Bean(name = "dataBaseUser")
//    public MapperFactoryBean getSqlSessionFactoryTwo() throws Exception {
//        //xml和实体的映射
//        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(new MyDataSource(false));
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.scaffold.zmain.db.user");
//        sqlSessionFactoryBean.setMapperLocations(new ClassPathResource("mybatis/user/TwoMapper.xml"));
//        //单个数据源所有的数据库映射
//        MapperFactoryBean mapperFactoryBean=new MapperFactoryBean();
//        //设置sqlSessionTemplate,zhuru yong de
//        mapperFactoryBean.setMapperInterface(TwoMapper.class);
//        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactoryBean.getObject());
//        return mapperFactoryBean;
//    }

    @Bean
    @ConditionalOnBean(name = "dataBaseNoun")
    public static MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.scaffold.zmain.db.noun.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("dataBaseNoun");
        return mapperScannerConfigurer;
    }


    @Bean
    @ConditionalOnBean(name = "dataBaseUser")
    public static MapperScannerConfigurer mapperScannerConfigurerUser(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.scaffold.zmain.db.user.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("dataBaseUser");
        return mapperScannerConfigurer;
    }
}
