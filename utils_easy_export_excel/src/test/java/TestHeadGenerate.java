import guohao.utils.export.excel.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author guohao
 * @since 2023/12/29
 */
public class TestHeadGenerate {

    /**
     * 单行表头，全部使用{@link ExportColumnInfo#getExportColumnName()}
     * <table border="2">
     *     <thead>
     *         <tr>
     *             <th rowspan="2">id<th/>
     *             <th rowspan="2">姓名<th/>
     *         <tr/>
     *     </thead>
     * <table/>
     */
    @Test
    void single_ExportColumnName() {
        ExportRoot exportRoot = ExportRoot.builder()
                .exportColumnsInfo(List.of(
                        ExportColumnInfo.builder()
                                .exportColumnName("id")
                                .dbColumnInfo(DbColumnInfo.builder().build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("姓名")
                                .dbColumnInfo(DbColumnInfo.builder().build())
                                .build()
                ))
                .build();
        Assertions.assertEquals(List.of(List.of("id"),
                                        List.of("姓名")),
                                exportRoot.head());
    }

    /**
     * 单行表头,全部使用{@link DbColumnInfo#getDbColumnName()}
     * <table border="2">
     *     <thead>
     *         <tr>
     *             <th rowspan="2">id<th/>
     *             <th rowspan="2">姓名<th/>
     *         <tr/>
     *     </thead>
     * <table/>
     */
    @Test
    void single_DbColumnName() {
        ExportRoot exportRoot = ExportRoot.builder()
                .exportColumnsInfo(List.of(
                        ExportColumnInfo.builder()
                                .dbColumnInfo(DbColumnInfo.builder().dbColumnName("id").build())
                                .build(),
                        ExportColumnInfo.builder()
                                .dbColumnInfo(DbColumnInfo.builder().dbColumnName("name").build())
                                .build()
                ))
                .build();
        Assertions.assertEquals(List.of(List.of("id"),
                                        List.of("name")),
                                exportRoot.head());
    }

    /**
     * 单行表头,使用{@link DbColumnInfo#getDbColumnName()} 和 {@link ExportColumnInfo#getExportColumnName()}
     * <table border="2">
     *     <thead>
     *         <tr>
     *             <th rowspan="2">id<th/>
     *             <th rowspan="2">姓名<th/>
     *         <tr/>
     *     </thead>
     * <table/>
     */
    @Test
    void single_ExportColumnName_DbColumnName() {
        ExportRoot exportRoot = ExportRoot.builder()
                .exportColumnsInfo(List.of(
                        ExportColumnInfo.builder()
                                .dbColumnInfo(DbColumnInfo.builder().dbColumnName("id").build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("姓名")
                                .dbColumnInfo(DbColumnInfo.builder().dbColumnName("name").build())
                                .build()
                ))
                .build();
        Assertions.assertEquals(List.of(List.of("id"),
                                        List.of("姓名")),
                                exportRoot.head());
    }


    /**
     * 两行表头
     * <table border="2">
     *     <thead>
     *         <tr>
     *             <th>id<th/>
     *             <th>姓名<th/>
     *             <th colspan="2">性别<th/>
     *         <tr/>
     *         <tr>
     *             <th><th/>
     *             <th><th/>
     *             <th>性别1<th/>
     *             <th>性别2<th/>
     *         <tr/>
     *     </thead>
     * <table/>
     */
    @Test
    void secondLine_ExportColumnName() {
        ExportRoot exportRoot = ExportRoot.builder()
                .exportFileName("test.xlsx")
                .exportColumnsInfo(List.of(
                        ExportColumnInfo.builder()
                                .dbColumnInfo(DbColumnInfo.builder().dbColumnName("id").build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("姓名")
                                .dbColumnInfo(DbColumnInfo.builder().build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("性别")
                                .dbColumnInfo(DbColumnInfo.builder().build())
                                .exportRelateTableInfo(
                                        ExportRelateTableInfo.builder()
                                                .dbColumnInfo(DbColumnInfo.builder().build())
                                                .exportColumnsInfo(List.of(
                                                        ExportColumnInfo.builder()
                                                                .exportColumnName("男")
                                                                .dbColumnInfo(DbColumnInfo.builder().build())
                                                                .build(),
                                                        ExportColumnInfo.builder()
                                                                .exportColumnName("女")
                                                                .dbColumnInfo(DbColumnInfo.builder().build())
                                                                .build()
                                                ))
                                                .build()
                                )
                                .build()
                ))
                .build();
        Assertions.assertEquals(List.of(List.of("id"),
                                        List.of("姓名"),
                                        List.of("性别", "男"),
                                        List.of("性别", "女")
                                ),
                                exportRoot.head());
    }


    /**
     * 三行表头
     * <table border="2">
     *     <thead>
     *         <tr>
     *             <th rowspan="1">id<th/>
     *             <th rowspan="1">姓名<th/>
     *             <th colspan="6">性别<th/>
     *         <tr/>
     *         <tr>
     *             <th><th/>
     *             <th><th/>
     *             <th>男<th/>
     *             <th colspan="1">女<th/>
     *         <tr/>
     *         <tr>
     *             <th><th/>
     *             <th><th/>
     *             <th><th/>
     *             <th>女1<th/>
     *             <th>女2<th/>
     *         <tr/>
     *     </thead>
     * <table/>
     */
    @Test
    void thirdLine_ExportColumnName() {
        ExportRoot exportRoot = ExportRoot.builder()
                .exportFileName("test.xlsx")
                .exportColumnsInfo(List.of(
                        ExportColumnInfo.builder()
                                .dbColumnInfo(DbColumnInfo.builder().dbColumnName("id").build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("姓名")
                                .dbColumnInfo(DbColumnInfo.builder().build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("性别")
                                .dbColumnInfo(DbColumnInfo.builder().build())
                                .exportRelateTableInfo(
                                        ExportRelateTableInfo.builder()
                                                .dbColumnInfo(DbColumnInfo.builder().build())
                                                .exportColumnsInfo(List.of(
                                                        ExportColumnInfo.builder()
                                                                .exportColumnName("男")
                                                                .dbColumnInfo(DbColumnInfo.builder().build())
                                                                .build(),
                                                        ExportColumnInfo.builder()
                                                                .exportColumnName("女")
                                                                .dbColumnInfo(DbColumnInfo.builder().build())
                                                                .exportRelateTableInfo(ExportRelateTableInfo.builder()
                                                                                               .dbColumnInfo(DbColumnInfo.builder().build())
                                                                                               .exportColumnsInfo(List.of(
                                                                                                       ExportColumnInfo.builder()
                                                                                                               .exportColumnName("女1")
                                                                                                               .dbColumnInfo(DbColumnInfo.builder().build())
                                                                                                               .build(),
                                                                                                       ExportColumnInfo.builder()
                                                                                                               .exportColumnName("女2")
                                                                                                               .dbColumnInfo(DbColumnInfo.builder().build())
                                                                                                               .build())
                                                                                               )
                                                                                               .build()
                                                                )
                                                                .build()
                                                ))
                                                .build()
                                )
                                .build()
                ))
                .build();
        Assertions.assertEquals(List.of(List.of("id"),
                                        List.of("姓名"),
                                        List.of("性别", "男"),
                                        List.of("性别", "女", "女1"),
                                        List.of("性别", "女", "女2")
                                ),
                                exportRoot.head());
    }


    /**
     * 两行表头。<br/>
     * 和{@link #thirdLine_ExportColumnName()}对比，第二行没有{@code exportColumnName("女")}时，第三行自动合并到第二行
     * <table border="2">
     *     <thead>
     *         <tr>
     *             <th rowspan="1">id<th/>
     *             <th rowspan="1">姓名<th/>
     *             <th colspan="6">性别<th/>
     *         <tr/>
     *         <tr>
     *             <th><th/>
     *             <th><th/>
     *             <th>男<th/>
     *             <th colspan="1">女1<th/>
     *             <th colspan="1">女2<th/>
     *         <tr/>
     *     </thead>
     * <table/>
     */
    @Test
    void secondLine_merge_ExportColumnName() {
        ExportRoot exportRoot = ExportRoot.builder()
                .exportFileName("test.xlsx")
                .exportColumnsInfo(List.of(
                        ExportColumnInfo.builder()
                                .dbColumnInfo(DbColumnInfo.builder().dbColumnName("id").build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("姓名")
                                .dbColumnInfo(DbColumnInfo.builder().build())
                                .build(),
                        ExportColumnInfo.builder()
                                .exportColumnName("性别")
                                .dbColumnInfo(DbColumnInfo.builder().build())
                                .exportRelateTableInfo(
                                        ExportRelateTableInfo.builder()
                                                .dbColumnInfo(DbColumnInfo.builder().build())
                                                .exportColumnsInfo(List.of(
                                                        ExportColumnInfo.builder()
                                                                .exportColumnName("男")
                                                                .dbColumnInfo(DbColumnInfo.builder().build())
                                                                .build(),
                                                        ExportColumnInfo.builder()
//                                                                .exportColumnName("女")
                                                                .dbColumnInfo(DbColumnInfo.builder().build())
                                                                .exportRelateTableInfo(ExportRelateTableInfo.builder()
                                                                                               .dbColumnInfo(DbColumnInfo.builder().build())
                                                                                               .exportColumnsInfo(List.of(
                                                                                                       ExportColumnInfo.builder()
                                                                                                               .exportColumnName("女1")
                                                                                                               .dbColumnInfo(DbColumnInfo.builder().build())
                                                                                                               .build(),
                                                                                                       ExportColumnInfo.builder()
                                                                                                               .exportColumnName("女2")
                                                                                                               .dbColumnInfo(DbColumnInfo.builder().build())
                                                                                                               .build())
                                                                                               )
                                                                                               .build()
                                                                )
                                                                .build()
                                                ))
                                                .build()
                                )
                                .build()
                ))
                .build();
        Assertions.assertEquals(List.of(List.of("id"),
                                        List.of("姓名"),
                                        List.of("性别", "男"),
                                        List.of("性别", "女1"),
                                        List.of("性别", "女2")
                                ),
                                exportRoot.head());
        exportRoot.export();
    }
}
