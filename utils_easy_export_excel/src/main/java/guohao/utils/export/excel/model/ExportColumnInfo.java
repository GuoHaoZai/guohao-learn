package guohao.utils.export.excel.model;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.CollectionUtils;

import java.sql.ResultSet;
import java.util.*;

/**
 * 导出列信息
 */
@Data
@Builder
@FieldDefaults(makeFinal = true)
public class ExportColumnInfo {
    /**
     * 导出列名
     */
    private String exportColumnName;
    /**
     * 导出列数
     */
    private Count exportColumnCount = Count.SINGLE;
    /**
     * 导出列行数
     */
    private Count exportRowCount = Count.SINGLE;
    /**
     * 导出值映射
     */
    private Map<Object, Object> exportMappingValue = new HashMap<>();

    private ExportRelateTableInfo exportRelateTableInfo;

    private ExportMultiMerge exportMultiMerge = new ExportMultiMerge();

    /**
     * 数据库名
     */
    private DbColumnInfo dbColumnInfo;


    public ExportColumnInfo putExportMappingValue(Object oldValue, Object newValue) {
        this.exportMappingValue.put(oldValue, newValue);
        return this;
    }

    public static class ExportMultiMerge {
        private MergeStrategy strategy = MergeStrategy.BY_ROW;
        private String columnSplitter = ",";
        private String rowSplitter = "\n";
    }

    public List<List<String>> head() {
        return Optional.ofNullable(exportRelateTableInfo)
                .map(ExportRelateTableInfo::head)
                .filter(CollectionUtils::isNotEmpty)
                .map(heads -> {
                    if (Objects.isNull(exportColumnName)) {
                        return heads;
                    }
                    List<List<String>> result = new ArrayList<>();
                    for (List<String> head : heads) {
                        ArrayList<String> headResult = new ArrayList<>(head);
                        headResult.add(0, exportColumnName);
                        result.add(headResult);
                    }
                    return result;
                })
                .filter(CollectionUtils::isNotEmpty)
                .orElseGet(()-> {
                    List<List<String>> result = new ArrayList<>();
                    result.add(Lists.newArrayList(Optional.ofNullable(exportColumnName).orElseGet(dbColumnInfo::getDbColumnName)));
                    return result;
                });
    }

    @SneakyThrows
    public void data(ResultSet resultSet, List<Object> line) {
        Object columnValue = resultSet.getObject(dbColumnInfo.getDbColumnName());

        if (this.getExportMappingValue().containsKey(String.valueOf(columnValue))) {
            line.add(this.getExportMappingValue().get(String.valueOf(columnValue)));
        } else {
            line.add(columnValue);
        }
    }

    public enum MergeStrategy {
        BY_ROW,
        BY_COLUMN
    }

    public enum Count {
        SINGLE,
        MULTI
    }
}
