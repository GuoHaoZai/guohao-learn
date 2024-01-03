import guohao.utils.export.excel.model.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author guohao
 * @since 2023/12/29
 */
class TestDataGenerate {

    @Test
    void simpleTest() throws SQLException {
        ResultSet resultSet = MockResultSet.create(new String[]{"id", "name"},
                                                   new Object[][]{
                                                           {1, "a"},
                                                           {2, "b"},
                                                   });

        DbTableInfo dbTableInfo = Mockito.mock(DbTableInfo.class);
        Mockito.when(dbTableInfo.query()).thenReturn(resultSet);
        ExportRoot exportRoot = ExportRoot.builder()
                .dbTableInfo(dbTableInfo)
                .exportColumnsInfo(List.of(
                        ExportColumnInfo.builder()
                                .exportColumnName("id")
                                .dbColumnInfo(DbColumnInfo.builder()
                                                      .dbColumnName("id")
                                                      .build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("姓名")
                                .dbColumnInfo(DbColumnInfo.builder()
                                                      .dbColumnName("name")
                                                      .build())
                                .build()
                ))
                .build();
        Assertions.assertEquals(List.of(
                                        List.of(1, "a"),
                                        List.of(2, "b")
                                ),
                                exportRoot.data());
    }

    @Test
    void complexTest() throws SQLException {
        ResultSet resultSet = MockResultSet.create(new String[]{"id", "name"},
                                                   new Object[][]{
                                                           {1, "a"},
                                                           {2, "b"},
                                                   });

        DbTableInfo dbTableInfo = Mockito.mock(DbTableInfo.class);
        Mockito.when(dbTableInfo.query()).thenReturn(resultSet);
        ExportRoot exportRoot = ExportRoot.builder()
                .dbTableInfo(dbTableInfo)
                .exportColumnsInfo(List.of(
                        ExportColumnInfo.builder()
                                .exportColumnName("id")
                                .dbColumnInfo(DbColumnInfo.builder()
                                                      .dbColumnName("id")
                                                      .build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("姓名")
                                .dbColumnInfo(DbColumnInfo.builder()
                                                      .dbColumnName("name")
                                                      .build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("extendIds")
                                .dbColumnInfo(DbColumnInfo.builder()
                                                      .dbColumnName("extendIds")
//                                                      .dbTableInfo()
                                                      .dbColumnValueProcessor(new DbColumnInfo.ColumnValueProcessor(DbColumnInfo.DbColumnValueStrategy.SPLITTER, ","))
                                                      .build())
                                .dbColumnInfo(DbColumnInfo.builder()
                                                      .dbColumnName("name")
                                                      .build())
                                .build()
                        ))
                .build();
        System.out.println(exportRoot.data());
    }

    @Test
    void test_exportMapping() throws SQLException {
        ResultSet resultSet = MockResultSet.create(new String[]{"id", "name"},
                                                   new Object[][]{
                                                           {1, "a"},
                                                           {2, "b"},
                                                   });
        DbTableInfo dbTableInfo = Mockito.mock(DbTableInfo.class);
        Mockito.when(dbTableInfo.query()).thenReturn(resultSet);
        ExportRoot exportRoot = ExportRoot.builder()
                .dbTableInfo(dbTableInfo)
                .exportColumnsInfo(List.of(
                        ExportColumnInfo.builder()
                                .exportColumnName("id")
                                .dbColumnInfo(DbColumnInfo.builder()
                                                      .dbColumnName("id")
                                                      .build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("姓名")
                                .dbColumnInfo(DbColumnInfo.builder()
                                                      .dbColumnName("name")
                                                      .build())
                                .build()
                                .putExportMappingValue("a", "new-a")
                                .putExportMappingValue("b", "new-b")
                ))
                .build();
        Assertions.assertEquals(List.of(
                                        List.of(1, "new-a"),
                                        List.of(2, "new-b")
                                ),
                                exportRoot.data());
    }
}
