package guohao.utils.export.excel.model;

import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        PreparedStatement preparedStatement = connection.prepareStatement("select * from " + dbTableName + " where " + filters);
        return preparedStatement.executeQuery();
    }
}
