package guohao.utils.export.excel.model;

import com.alibaba.excel.EasyExcelFactory;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<List<String>> head() {
        return getExportColumnsInfo().stream()
                .map(ExportColumnInfo::head)
                .reduce(ListUtils::union)
                .orElse(new ArrayList<>());
    }

    public List<List<Object>> data() {
        return Collections.emptyList();
    }
}
