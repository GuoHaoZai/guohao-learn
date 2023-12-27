package guohao.utils.export.excel.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(makeFinal = true)
public class DbColumnInfo {

    private DbTableInfo dbTableInfo;

    private String dbColumnName;

    private ColumnValueProcessor dbColumnValueProcessor = ColumnValueProcessor.DEFAULT;

    public static class ColumnValueProcessor {
        private DbColumnValueStrategy strategy;
        private String params;

        public static final ColumnValueProcessor DEFAULT = new ColumnValueProcessor(DbColumnValueStrategy.COLUMN_VALUE, null);

        public ColumnValueProcessor(DbColumnValueStrategy strategy, String params) {
            this.strategy = strategy;
            this.params = params;
        }
    }

    public enum DbColumnValueStrategy {
        SPLITTER,
        JSON_PATH,
        COLUMN_VALUE
    }
}
