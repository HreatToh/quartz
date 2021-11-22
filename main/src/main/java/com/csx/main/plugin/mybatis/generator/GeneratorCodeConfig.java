package com.csx.main.plugin.mybatis.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.csx.common.utils.ToolUtils;
import com.csx.main.plugin.mybatis.generator.entity.*;

import java.util.ArrayList;
import java.util.List;

public class GeneratorCodeConfig {

    public static String[] tables = new String[]{
            "quartz_blob_triggers",
            "quartz_calendars",
            "quartz_cron_triggers",
            "quartz_fired_triggers",
            "quartz_job_details",
            "quartz_locks",
            "quartz_paused_trigger_grps",
            "quartz_scheduler_state",
            "quartz_simple_triggers",
            "quartz_simprop_triggers",
            "quartz_triggers"
    };
    public static void main(String[] args) {
        //代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        MyGlobalConfig globalConfig = new MyGlobalConfig();

        String basePath = System.getProperty("user.dir");
        globalConfig.setOutputDir(ToolUtils.format("/src/main/java" , basePath));
        globalConfig.setAuthor("Chengshx");
        globalConfig.setOpen(false);
        //实体属性 Swagger2 注解
        globalConfig.setSwagger2(false);



        // 数据源配置
        MyDataSourceConfig dataSourceConfig = new MyDataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/quartz?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root");

        // 包配置
        MyPackageConfig packageConfig = new MyPackageConfig();
        packageConfig.setModuleName("schedule");
        packageConfig.setParent("com.github.chengshx.quartz.schedule.ioc");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");

        // 自定义配置
        MyInjectionConfig injectionConfig = new MyInjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> fileOutConfigs = new ArrayList<FileOutConfig>();
        // 自定义配置会被优先输出
        fileOutConfigs.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return ToolUtils.format("{}/src/main/resources/mapper/mysql/{}/{}Mapper{}" , basePath , packageConfig.getModuleName() , tableInfo.getEntityName() , StringPool.DOT_XML);
            }
        });

        injectionConfig.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir(filePath);
                return false;
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigs);

        // 配置模板
        MyTemplateConfig templateConfig = new MyTemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);


        // 策略配置
        MyStrategyConfig strategy = new MyStrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

        strategy.setEntityLombokModel(true);
        // 公共父类
        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(tables);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("QUARTZ_");

        mpg.setGlobalConfig(globalConfig);
        mpg.setDataSource(dataSourceConfig);
        mpg.setPackageInfo(packageConfig);
        mpg.setTemplate(templateConfig);
        mpg.setCfg(injectionConfig);
        mpg.setTemplate(templateConfig);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
