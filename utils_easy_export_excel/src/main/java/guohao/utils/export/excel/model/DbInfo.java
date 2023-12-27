package guohao.utils.export.excel.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author guohao
 * @since 2023/12/29
 */
@Data
@Builder
@FieldDefaults(makeFinal = true)
public class DbInfo {

    private String username;
    private String password;
    private String url;
}
