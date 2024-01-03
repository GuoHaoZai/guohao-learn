package guohao.utils.export.excel.model;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.StringJoiner;

@Data
@Builder
@FieldDefaults(makeFinal = true)
public class DbTableInfo {
    private DbInfo dbInfo;
    /**
     * 数据库表信息
     */
    private String dbTableName;

    /**
     * 数据库表过滤信息
     */
    private String filters;

    @SneakyThrows
    public ResultSet query() {
        Connection connection = dbInfo.getDataSource().getConnection();
        String sql = "select * from " + dbTableName + " where " + filters;
        System.out.println(sql);
        return connection.prepareStatement(sql).executeQuery();
    }

    @SneakyThrows
    public ResultSet query(Object value, String column) {
        Connection connection = dbInfo.getDataSource().getConnection();
        String sql = "select * from " + dbTableName + " where `" + column + "` = " + value;
        if (filters != null) {
            sql += " and " + filters;
        }
        System.out.println(sql);
        return connection.prepareStatement(sql).executeQuery();
    }

    @SneakyThrows
    public ResultSet query(Collection<Object> values, String column) {
        Connection connection = dbInfo.getDataSource().getConnection();
        StringJoiner stringJoiner = new StringJoiner(",");
        values.forEach(value -> stringJoiner.add(value.toString()));
        String sql = "select * from " + dbTableName + " where `" + column + "` IN (" + stringJoiner + ")";
        if (filters != null) {
            sql += " and " + filters;
        }
        System.out.println(sql);
        return connection.prepareStatement(sql).executeQuery();
    }
}
