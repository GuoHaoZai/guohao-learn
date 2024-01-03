package guohao.utils.export.excel.model;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

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

    public Optional<ExportRelateTableInfo> getExportRelateTableInfo() {
        return Optional.ofNullable(exportRelateTableInfo);
    }

    public static class ExportMultiMerge {
        private MergeStrategy strategy = MergeStrategy.BY_ROW;
        private String columnSplitter = ",";
        private String rowSplitter = "\n";
    }

    public List<List<String>> head() {
        return Optional.ofNullable(exportRelateTableInfo)
                .map(ExportRelateTableInfo::head)
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
                .orElseGet(() -> {
                    List<List<String>> result = new ArrayList<>();
                    result.add(Lists.newArrayList(Optional.ofNullable(exportColumnName).orElseGet(dbColumnInfo::getDbColumnName)));
                    return result;
                });
    }

    @SneakyThrows
    public Object[][] data(ResultSet resultSet) {
        Object processResult = Optional.ofNullable(resultSet.getObject(dbColumnInfo.getDbColumnName()))
                .map(this.getDbColumnInfo()::process)
                .orElse(null);
        Object mappingResult = exportMapping(processResult);

        return getExportRelateTableInfo()
                .map(theExportRelateTableInfo -> theExportRelateTableInfo.data(mappingResult))
                .orElseGet(()-> new Object[][]{{mappingResult}});
    }

    Object exportMapping(Object processResult) {
        if (this.getExportMappingValue().isEmpty()) {
            return processResult;
        } else if (processResult instanceof Collection) {
            return ((Collection<?>)processResult).stream()
                    .map(value -> this.getExportMappingValue().getOrDefault(value, value))
                    .collect(Collectors.toList());
        } else {
            return this.getExportMappingValue().getOrDefault(processResult, processResult);
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
