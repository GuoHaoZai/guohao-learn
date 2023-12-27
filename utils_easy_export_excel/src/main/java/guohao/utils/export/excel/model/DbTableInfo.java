package guohao.utils.export.excel.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nullable;

@Data
@Builder
@FieldDefaults(makeFinal = true)
public class DbTableInfo {
    @Nullable
    private DbInfo dbInfo;
    /**
     * 数据库表信息
     */
    private String dbTableName;

    /**
     * 数据库表过滤信息
     */
    private String filters;
}
