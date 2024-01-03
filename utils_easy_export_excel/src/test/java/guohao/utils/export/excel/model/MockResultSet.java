package guohao.utils.export.excel.model;

import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;

class MockResultSet {
    private final Map<String, Integer> columnIndices;
    private final Object[][] data;
    private int rowIndex;

    private MockResultSet(final String[] columnNames,
                          final Object[][] data) {// create a map of column name to column index
        this.columnIndices = IntStream.range(0, columnNames.length)
                .boxed()
                .collect(Collectors.toMap(k -> columnNames[k],
                                          Function.identity(),
                                          (a, b) -> {
                                              throw new RuntimeException("Duplicate column " + a);
                                          },
                                          LinkedHashMap::new));
        this.data = data;
        this.rowIndex = -1;
    }

    private ResultSet buildMock() throws SQLException {
        ResultSetMetaData rsmd = Mockito.mock(ResultSetMetaData.class);
        Mockito.when(rsmd.getColumnCount())
                .thenReturn(columnIndices.size());

        ResultSet rs = Mockito.mock(ResultSet.class);
        Mockito.when(rs.getMetaData())
                .thenReturn(rsmd);
        Mockito.when(rs.next())
                .thenAnswer(invocation -> {
                    rowIndex++;
                    return rowIndex < data.length;
                });
        Mockito.when(rs.getObject(anyInt()))
                .thenAnswer(invocation -> {
                    final var index = (int) invocation.getArgument(0);
                    return data[rowIndex][index - 1];
                });
        Mockito.when(rs.getObject(anyString()))
                .thenAnswer(invocation -> {
                    final var columnName = (String) invocation.getArgument(0);
                    Integer index = columnIndices.get(columnName);
                    return data[rowIndex][index];
                });
        return rs;
    }

    public static ResultSet create(final String[] columnNames, final Object[][] data) throws SQLException {
        return new MockResultSet(columnNames, data).buildMock();
    }
}