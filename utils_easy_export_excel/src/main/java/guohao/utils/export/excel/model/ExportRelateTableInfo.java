package guohao.utils.export.excel.model;

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
public class ExportRelateTableInfo {
    private DbColumnInfo dbColumnInfo;
    private ExportColumnInfo.Count dbTableRelate;

    private List<ExportColumnInfo> exportColumnsInfo;

    public List<List<String>> head() {
        return exportColumnsInfo.stream()
                .map(ExportColumnInfo::head)
                .reduce(ListUtils::union)
                .orElse(new ArrayList<>());
    }

    @SneakyThrows
    public Object[][] data(Object values) {
        ResultSet resultSet = dbColumnInfo.query(values);

        List<Object[]> result = new ArrayList<>();
        while (resultSet.next()) {
            Blocks blocks = new Blocks();
            for (ExportColumnInfo exportColumnInfo : exportColumnsInfo) {
                blocks.addBlock(exportColumnInfo.data(resultSet));
            }
            result.addAll(blocks.mergeToArray());
        }
        return result.toArray(new Object[0][0]);
    }
}
