package guohao.utils.export.excel.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.ListUtils;

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
}
