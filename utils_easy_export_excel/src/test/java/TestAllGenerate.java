//import guohao.utils.export.excel.model.*;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
///**
// * @author guohao
// * @since 2023/12/29
// */
//public class TestAllGenerate {
//
//    @Test
//    public void singleHead() {
//        ExportRoot exportRoot = ExportRoot.builder()
//                .exportFileName("test.xlsx")
//                .dbInfo(
//                        DbInfo.builder()
//                                .build()
//                )
//                .dbTableInfo(
//                        DbTableInfo.builder()
//                                .dbTableName("test")
//                                .filters("id < 3")
//                                .build()
//                )
//                .exportColumnsInfo(List.of(
//                        ExportColumnInfo.builder()
//                                .dbColumnInfo(
//                                        DbColumnInfo.builder()
//                                                .dbColumnName("id")
//                                                .build()
//                                )
//                                .build(),
//                        ExportColumnInfo.builder()
//                                .dbColumnInfo(
//                                        DbColumnInfo.builder()
//                                                .dbColumnName("name")
//                                                .build()
//                                )
//                                .build()
//                ))
//                .build();
//        System.out.println(exportRoot.head());
////        exportRoot.export();
//    }
//
//
//}
