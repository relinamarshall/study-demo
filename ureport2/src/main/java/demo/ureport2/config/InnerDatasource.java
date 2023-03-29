package demo.ureport2.config;

import com.bstek.ureport.definition.datasource.BuildinDatasource;

import org.springframework.stereotype.Component;

import java.sql.Connection;

import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 * InnerDatasource
 *
 * @author Wenzhou
 * @since 2023/3/29 11:31
 */
@Component
@RequiredArgsConstructor
public class InnerDatasource implements BuildinDatasource {
    /**
     * datasource
     */
    private final DataSource datasource;

    @Override
    public String name() {
        return "内部数据源";
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        return datasource.getConnection();
    }
}
