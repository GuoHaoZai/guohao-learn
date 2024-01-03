import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.google.common.collect.Lists;
import guohao.utils.export.excel.model.DbColumnInfo;
import guohao.utils.export.excel.model.ExportColumnInfo;
import guohao.utils.export.excel.model.ExportRelateTableInfo;
import guohao.utils.export.excel.model.ExportRoot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author guohao
 * @since 2023/12/29
 */
public class TestEasyExcel {

    /**
     * <table border="2">
     *     <thead>
     *         <tr>
     *             <th>id<th/>
     *             <th>姓名<th/>
     *         <tr/>
     *     </thead>
     *     <tbody>
     *         <tr>
     *             <td>1</td>
     *             <td>a</td>
     *         </tr>
     *         <tr>
     *             <td>2</td>
     *             <td>b</td>
     *         </tr>
     *     </tbody>
     * <table/>
     */
    @Test
    void single_Column() {
        EasyExcelFactory
                .write("test.xlsx")
                .sheet(0)
                .head(List.of(
                        List.of("id"),
                        List.of("姓名"))
                )
                .doWrite(List.of(
                        List.of("1", "a"),
                        List.of("2", "b")
                ));
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
     *     <tbody>
     *         <tr>
     *             <th>1</th>
     *             <th>a</th>
     *             <th>1</th>
     *         </tr>
     *     </tbody>
     * <table/>
     */
    @Test
    void secondLine_ExportColumnName() {
        EasyExcelFactory
                .write("test.xlsx")
                .sheet(0)
                .head(List.of(
                        Lists.newArrayList("id"),
                        Lists.newArrayList("姓名"),
                        Lists.newArrayList("性别", "性别1"),
                        Lists.newArrayList("性别", "性别2")
                ))
                .doWrite(List.of(
                        List.of("1", "a", "男1", "男2"),
                        List.of("2", "b", "女1", "女2")
                ));

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
    void mergeLine() {
        EasyExcelFactory
                .write("test.xlsx")
                .sheet(0)
                .head(List.of(
                        Lists.newArrayList("id"),
                        Lists.newArrayList("姓名"),
                        Lists.newArrayList("性别", "性别1"),
                        Lists.newArrayList("性别", "性别2")
                ))
                .registerWriteHandler(new LoopMergeStrategy(2,1,0))
                .doWrite(List.of(
                        List.of("1a", "a", "男1a", "男2a"),
                        List.of("1b", "b", "男1b", "男2b"),
                        List.of("2a", "a", "女1a", "女2a"),
                        List.of("2b", "b", "女1b", "女2b")
                ));


    }

    @Test
    void nullValue() {
        EasyExcelFactory
                .write("test.xlsx")
                .sheet(0)
                .head(List.of(
                        Lists.newArrayList("id"),
                        Lists.newArrayList("姓名"),
                        Lists.newArrayList("性别1"),
                        Lists.newArrayList("性别2")
                ))
                .doWrite(List.of(
                        Lists.newArrayList("1a", "a", "男1a", "男2a"),
                        Lists.newArrayList("", "b", "男1b", "男2b"),
                        Lists.newArrayList("2a", null, "女1a", "女2a"),
                        Lists.newArrayList(null, null, "女1b", "女2b")
                ));
    }

}
