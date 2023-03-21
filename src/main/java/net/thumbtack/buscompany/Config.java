package net.thumbtack.buscompany;

import net.thumbtack.buscompany.dto.SettingsDtoResponse;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.Reader;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {
    private static final SqlSessionFactory sqlSessionFactory = buildSqlSessionFactory();
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    @Value("${user_idle_timeout}")
    private static int timeout;

    public static SqlSessionFactory buildSqlSessionFactory() {
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
            return new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            LOGGER.error("Error loading mybatis-config.xml", e);
        }
        return null;
    }

    @Bean
    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Bean
    public String getSessionId() {
        return "";
    }

    @Bean
    public int getTimeout() {
        return timeout;
    }

    @Bean
    public static SettingsDtoResponse getSettingsDtoResponse() {
        return new SettingsDtoResponse();
    }

}
