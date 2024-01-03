package guohao.utils.export.excel.model;

import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author guohao
 * @since 2023/12/29
 */
class TestAllGenerate {

    @Test
    void singleHead() {
        DbInfo dbInfo = DbInfo.builder()

                .build();
        ExportRoot exportRoot = ExportRoot.builder()
                .exportFileName("test.xlsx")
                .dbTableInfo(
                        DbTableInfo.builder()
                                .dbInfo(dbInfo)
                                .dbTableName("product")
                                .filters("id IN (0,71,118,2)")
                                .build()
                )
                .exportColumnsInfo(List.of(
                        ExportColumnInfo.builder()
                                .dbColumnInfo(
                                        DbColumnInfo.builder()
                                                .dbColumnName("id")
                                                .build()
                                )
                                .build(),
                        ExportColumnInfo.builder()
                                .dbColumnInfo(
                                        DbColumnInfo.builder()
                                                .dbColumnName("name")
                                                .build()
                                )
                                .build(),
                        ExportColumnInfo.builder()
                                .dbColumnInfo(
                                        DbColumnInfo.builder()
                                                .dbColumnName("id")
                                                .build()
                                )
                                .exportRelateTableInfo(
                                        ExportRelateTableInfo.builder()
                                                .dbColumnInfo(
                                                        DbColumnInfo.builder()
                                                                .dbTableInfo(
                                                                        DbTableInfo.builder()
                                                                                .dbInfo(dbInfo)
                                                                                .dbTableName("product_course")
                                                                                .build()
                                                                )
                                                                .dbColumnName("product_id")
                                                                .build()
                                                )
                                                .exportColumnsInfo(List.of(
                                                        ExportColumnInfo.builder()
                                                                .dbColumnInfo(
                                                                        DbColumnInfo.builder()
                                                                                .dbColumnName("course_id")
                                                                                .build()
                                                                )
                                                                .build()
                                                ))
                                                .build()
                                )
                                .build()
                        ))
                .build();
        System.out.println(exportRoot.head());
        System.out.println(exportRoot.data());
        exportRoot.export();
    }


}
