package guohao.utils.export.excel.model;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nonnull;
import javax.sql.DataSource;

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

    @Nonnull
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.getUrl());
        config.setUsername(this.getUsername());
        config.setPassword(this.getPassword());
        return new HikariDataSource(config);
    }

}
