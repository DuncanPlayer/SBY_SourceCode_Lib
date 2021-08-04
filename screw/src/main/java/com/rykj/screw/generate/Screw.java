package com.rykj.screw.generate;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.util.ArrayList;

/**
 * @Author 方便面
 * @Date 2021/8/4 14:32
 */
public class Screw {


    public void generate() {
        // 1.获取数据源
        DataSource dataSource = getDataSource();
        // 2.获取数据库文档生成配置（文件路径、文件类型）
        EngineConfig engineConfig = getEngineConfig();
        // 3.获取数据库表的处理配置，可忽略
        ProcessConfig processConfig = getProcessConfig();
        // 4.Screw 完整配置
        Configuration config = getScrewConfig(dataSource, engineConfig, processConfig);
        // 5.执行生成数据库文档
        new DocumentationExecute(config).execute();
    }

    private Configuration getScrewConfig(DataSource dataSource, EngineConfig engineConfig, ProcessConfig processConfig) {
        return Configuration.builder()
                //  版本
                .version("1.0.0")
                //  描述
                .description("rykj_数据库设计文档生成")
                //  数据源
                .dataSource(dataSource)
                //  生成配置
                .engineConfig(engineConfig)
                //  生成配置
                .produceConfig(processConfig)
                .build();
    }

    /**
     *  需要生成那些表
     * @return
     */
    private ProcessConfig getProcessConfig() {
        ArrayList<String> designatedTablePrefix = new ArrayList<>();
        designatedTablePrefix.add("basic_");
        return ProcessConfig.builder()
                // 指定只生成 basic_camera 表
                .designatedTablePrefix(designatedTablePrefix)
                // .designatedTableName(new ArrayList<>(Collections.singletonList("basic_camera")))
                .build();
    }

    private EngineConfig getEngineConfig() {
        //生成配置
        return EngineConfig.builder()
                //  生成文件路径
                .fileOutputDir("C:\\Users\\Administrator\\Desktop\\")
                //  打开目录
                .openOutputDir(true)
                //  文件类型
                .fileType(EngineFileType.HTML)
                //  生成模板实现
                .produceType(EngineTemplateType.freemarker)
                //  自定义文件名称
                .fileName("数据库结构文档").build();
    }

    private DataSource getDataSource() {
        //  数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://192.168.0.118:53307/rykj_energy?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("123456");
        //  设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        return new HikariDataSource(hikariConfig);
    }
}
