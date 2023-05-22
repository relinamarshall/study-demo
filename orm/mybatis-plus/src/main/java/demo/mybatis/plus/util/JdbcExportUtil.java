package demo.mybatis.plus.util;

import com.alibaba.fastjson.JSONObject;

import org.springframework.jdbc.support.JdbcUtils;

import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.function.Function;

import javax.sql.DataSource;

/**
 * JdbcExportUtil
 *
 * @author Wenzhou
 * @since 2023/5/22 16:44
 */
public final class JdbcExportUtil {
    public JdbcExportUtil() {

    }

    /**
     * exportJsonRows
     *
     * @param dataSource DataSource
     * @param fetchSize  int
     * @param sql        String
     * @param writer     Writer
     * @return int
     * @throws Exception
     */
    public static int exportJsonRows(DataSource dataSource,
                                     int fetchSize,
                                     String sql,
                                     Writer writer) throws Exception {
        return exportJsonRows(dataSource, fetchSize, sql, new Object[0], writer);
    }

    /**
     * exportJsonRows
     *
     * @param dataSource DataSource
     * @param fetchSize  int
     * @param sql        String
     * @param args       Object[]
     * @param writer     Writer
     * @return int
     * @throws Exception
     */
    public static int exportJsonRows(DataSource dataSource,
                                     int fetchSize,
                                     String sql,
                                     Object[] args,
                                     Writer writer) throws Exception {
        return exportJsonRows(dataSource, fetchSize, sql, args, writer, null);
    }

    /**
     * exportJsonRows
     *
     * @param dataSource DataSource
     * @param fetchSize  int
     * @param sql        String
     * @param writer     Writer
     * @param rowFn      Function<JSONObject, Integer>
     * @return int
     * @throws Exception
     */
    public static int exportJsonRows(DataSource dataSource,
                                     int fetchSize,
                                     String sql,
                                     Writer writer,
                                     Function<JSONObject, Integer> rowFn) throws Exception {
        return exportJsonRows(dataSource, fetchSize, sql, new Object[0], writer, rowFn);
    }

    /**
     * exportJsonRows
     *
     * @param dataSource DataSource
     * @param fetchSize  int
     * @param sql        String
     * @param args       Object[]
     * @param writer     Writer
     * @param rowFn      Function<JSONObject, Integer>
     * @return int
     * @throws Exception
     */
    public static int exportJsonRows(DataSource dataSource,
                                     int fetchSize,
                                     String sql,
                                     Object[] args,
                                     Writer writer,
                                     Function<JSONObject, Integer> rowFn) throws Exception {
        return executeSelect(dataSource, fetchSize, sql, args, (fieldNames, rs) -> {
            JSONObject row = new JSONObject(fieldNames.length);

            for (int i = 0; i < fieldNames.length; i++) {
                Object value = rs.getObject(i + 1);
                row.put(fieldNames[i], value);
            }

            writer.write(row.toJSONString());
            writer.write(10);
            return null != rowFn ? rowFn.apply(row) : 1;
        });
    }

    /**
     * executeSelect
     *
     * @param dataSource DataSource
     * @param fetchSize  int
     * @param sql        String
     * @param args       Object[]
     * @param handler    JdbcExportUtil.ResultSetHandler
     * @return int
     * @throws Exception
     */
    public static int executeSelect(DataSource dataSource,
                                    int fetchSize,
                                    String sql,
                                    Object[] args,
                                    JdbcExportUtil.ResultSetHandler handler) throws Exception {
        int nRows = 0;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            ps.setFetchSize(fetchSize);
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] fieldNames = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                fieldNames[i] = NameConvertUtil.toCamelCase(metaData.getColumnName(i + 1));
            }

            while (rs.next()) {
                nRows += handler.processRow(fieldNames, rs);
            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeConnection(connection);
        }
        return nRows;
    }

    /**
     * ResultSetHandler
     */
    public interface ResultSetHandler {
        /**
         * processRow
         *
         * @param var1 String[]
         * @param var2 ResultSet
         * @return int
         * @throws Exception
         */
        int processRow(String[] var1, ResultSet var2) throws Exception;
    }
}
