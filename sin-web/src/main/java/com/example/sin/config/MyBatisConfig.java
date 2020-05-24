package com.example.sin.config;
import com.alibaba.druid.pool.DruidDataSource;
import com.example.sin.service.config.AppConfigBean;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = {"com.example.sin.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MyBatisConfig implements TransactionManagementConfigurer {

    @Autowired
    private AppConfigBean appConfigBean;

    @Bean(name = "realDataSource")
    @Primary
    DataSource realDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(appConfigBean.getJdbcUrl());
        dataSource.setDriverClassName(appConfigBean.getJdbcDriverClassName());
        dataSource.setUsername(appConfigBean.getJdbcUsername());
        dataSource.setPassword(appConfigBean.getJdbcPassword());
        dataSource.setFilters("stat");
        List<String> initSqls = new ArrayList<>();
        initSqls.add("set names utf8mb4");
        dataSource.setConnectionInitSqls(initSqls);
        dataSource.setMaxActive(appConfigBean.getJdbcMaxActive());
        // datasource.setMinIdle(Integer.parseInt(propertyResolver.getProperty("minIdle")));
        dataSource.setInitialSize(appConfigBean.getJdbcInitialSize());
        // datasource.setValidationQuery(propertyResolver.getProperty("validationQuery"));
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory() throws SQLException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(realDataSource());
        factoryBean.setTypeAliasesPackage("com.example.sin.entity");
        // 分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "false");
        //properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "pageNum=start;count=countSql");
        pageHelper.setProperties(properties);
        // 添加插件
        factoryBean.setPlugins(new Interceptor[]{pageHelper});
        // 添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            factoryBean
                .setMapperLocations(resolver.getResources("classpath*:/META-INF/mybatis/*.xml"));
            return factoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        try {
            return new DataSourceTransactionManager(realDataSource());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
