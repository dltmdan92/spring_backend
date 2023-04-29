package com.seungmoo.backend.configuration;

import com.seungmoo.backend.configuration.constants.TransactionType;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * DB
 * username: seungmoo
 * password: 1234qwer
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Value("${backend.datasource.main.jdbc-url}")
    private String mainUrl;

    @Value("${backend.datasource.main.username}")
    private String mainUsername;

    @Value("${backend.datasource.main.password}")
    private String mainPassword;

    @Value("${backend.datasource.replica.jdbc-url}")
    private String replicaUrl;

    @Value("${backend.datasource.replica.username}")
    private String replicaUsername;

    @Value("${backend.datasource.replica.password}")
    private String replicaPassword;

    @Bean(destroyMethod = "close")
    @ConfigurationProperties(prefix = "backend.datasource.main.extra")
    public HikariDataSource mainDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(mainUrl)
                .username(mainUsername)
                .password(mainPassword)
                .build();
    }

    @Bean(destroyMethod = "close")
    @ConfigurationProperties(prefix = "backend.datasource.replica.extra")
    public HikariDataSource replicaDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(replicaUrl)
                .username(replicaUsername)
                .password(replicaPassword)
                .build();
    }

    @Bean
    public TransactionRoutingDataSource routingDataSource(@Qualifier("mainDataSource") DataSource readWriteDataSource,
                                                          @Qualifier("replicaDataSource") DataSource readOnlyDataSource) {
        TransactionRoutingDataSource transactionRoutingDataSource = new TransactionRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();

        dataSourceMap.put(TransactionType.READ_ONLY, readOnlyDataSource);
        dataSourceMap.put(TransactionType.READ_WRITE, readWriteDataSource);

        transactionRoutingDataSource.setTargetDataSources(dataSourceMap);

        return transactionRoutingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource(TransactionRoutingDataSource dataSource) {
        return new LazyConnectionDataSourceProxy(dataSource);
    }
}
