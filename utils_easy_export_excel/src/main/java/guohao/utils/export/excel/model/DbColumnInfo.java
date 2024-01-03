package guohao.utils.export.excel.model;

import com.google.common.base.Splitter;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Data
@Builder
@FieldDefaults(makeFinal = true)
public class DbColumnInfo {

    private DbTableInfo dbTableInfo;

    private String dbColumnName;

    @Nullable
    private ColumnValueProcessor dbColumnValueProcessor;

    @Nonnull
    public ColumnValueProcessor getDbColumnValueProcessor() {
        return Optional.ofNullable(dbColumnValueProcessor).orElse(ColumnValueProcessor.DEFAULT);
    }

    public ResultSet query(Object relateValue) {
        if (relateValue == null) {
            return dbTableInfo.query();
        } else if (relateValue instanceof Collection) {
            return dbTableInfo.query((Collection<Object>) relateValue, dbColumnName);
        } else {
            return dbTableInfo.query(relateValue, dbColumnName);
        }
    }

    public Object process(@Nullable Object dbValue) {
        return getDbColumnValueProcessor().process(dbValue);
    }

    @Getter
    @FieldDefaults(makeFinal = true)
    public static class ColumnValueProcessor {
        private DbColumnValueStrategy strategy;
        private String params;

        public static final ColumnValueProcessor DEFAULT = new ColumnValueProcessor(DbColumnValueStrategy.COLUMN_VALUE, null);

        public ColumnValueProcessor(DbColumnValueStrategy strategy, String params) {
            this.strategy = strategy;
            this.params = params;
        }

        public Object process(@Nullable Object dbValue) {
            if (Objects.isNull(dbValue)) {
                return null;
            }
            switch (strategy) {
                case SPLITTER: {
                    return Splitter.on(this.getParams()).splitToList(String.valueOf(dbValue));
                }
                case JSON_PATH: {
                    throw new UnsupportedOperationException();
                }
                case COLUMN_VALUE: {
                    return dbValue;
                }
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }

    public enum DbColumnValueStrategy {
        SPLITTER,
        JSON_PATH,
        COLUMN_VALUE
    }
}
