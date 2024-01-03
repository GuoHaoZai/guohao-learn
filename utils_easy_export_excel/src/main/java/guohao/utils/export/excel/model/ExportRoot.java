package guohao.utils.export.excel.model;

import com.alibaba.excel.EasyExcelFactory;
import guohao.utils.export.excel.utils.Blocks;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.ListUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@FieldDefaults(makeFinal = true)
public class ExportRoot {

    /**
     * excel导出文件名
     */
    private String exportFileName;
    /**
     * 数据库信息
     */
    private DbInfo dbInfo;
    /**
     * 数据库表信息
     */
    private DbTableInfo dbTableInfo;
    private List<ExportColumnInfo> exportColumnsInfo;

    public void export() {
        EasyExcelFactory
                .write(this.getExportFileName())
                .inMemory(true)
                .sheet(0)
                .head(head())
                .doWrite(data());
    }

    /**
     * @apiNote 返回的一定需要是可变list
     */
    List<List<String>> head() {
        return getExportColumnsInfo().stream()
                .map(ExportColumnInfo::head)
                .reduce(ListUtils::union)
                .orElse(new ArrayList<>());
    }

    @SneakyThrows
    List<List<Object>> data() {
        ResultSet resultSet = dbTableInfo.query();

        List<List<Object>> result = new ArrayList<>();
        while (resultSet.next()) {
            Blocks blocks = new Blocks();
            for (ExportColumnInfo exportColumnInfo : exportColumnsInfo) {
                blocks.addBlock(exportColumnInfo.data(resultSet));
            }
            result.addAll(blocks.mergeToList());
        }
        return result;
    }
}
