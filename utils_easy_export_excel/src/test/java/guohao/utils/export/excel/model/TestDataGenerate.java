package guohao.utils.export.excel.model;

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
        DbTableInfo dbTableInfo = Mockito.mock(DbTableInfo.class);
        Mockito.when(dbTableInfo.query())
                .thenAnswer(invocation -> {
                    return MockResultSet.create(new String[]{"id", "name", "extend_ids"},
                                                new Object[][]{
                                                        {1, "a", "1,2"},
                                                        {2, "b", "3,4"},
                                                });
                });

        DbTableInfo relaTableInfo = Mockito.mock(DbTableInfo.class);
        Mockito.when(relaTableInfo.query(Mockito.any(), Mockito.anyString()))
                .thenAnswer((invocation) -> {
                    return MockResultSet.create(new String[]{"id", "rela_name"},
                                                new Object[][]{
                                                        {1, "rela_a"},
                                                        {2, "rela_b"},
                                                });
                });
        Mockito.when(relaTableInfo.query(Mockito.anyCollection(), Mockito.anyString()))
                .thenAnswer((invocation) -> {
                    return MockResultSet.create(new String[]{"id", "rela_name"},
                                                new Object[][]{
                                                        {1, "rela_a"},
                                                        {2, "rela_b"},
                                                });
                });

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
                                .exportColumnName("extend")
                                .dbColumnInfo(DbColumnInfo.builder()
                                                      .dbColumnName("extend_ids")
                                                      .dbColumnValueProcessor(new DbColumnInfo.ColumnValueProcessor(DbColumnInfo.DbColumnValueStrategy.SPLITTER, ","))
                                                      .build())
                                .exportRelateTableInfo(ExportRelateTableInfo.builder()
                                                               .dbColumnInfo(DbColumnInfo.builder()
                                                                                     .dbTableInfo(relaTableInfo)
                                                                                     .dbColumnName("id")
                                                                                     .build())
                                                               .exportColumnsInfo(List.of(
                                                                       ExportColumnInfo.builder()
                                                                               .exportColumnName("关联名")
                                                                               .dbColumnInfo(DbColumnInfo.builder()
                                                                                                     .dbColumnName("rela_name")
                                                                                                     .build())
                                                                               .build()
                                                               ))
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
