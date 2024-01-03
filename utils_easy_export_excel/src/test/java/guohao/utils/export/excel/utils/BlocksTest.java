package guohao.utils.export.excel.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BlocksTest {

    @Test
    void test() {
        Blocks blocks = new Blocks();
        blocks.addBlock(new Object[][]{
                        {null}
                })
                .addBlock(new Object[][]{
                        {1}
                })
                .addBlock(new Object[][]{
                        {2, "2"},
                        {2, "2"}
                })
                .addBlock(new Object[][]{
                        {3, "3", "3"},
                        {3, "3", "3"},
                        {3, "3", "3"}
                })
                .addBlock(new Object[][]{
                        {4, "4"},
                        {4, "4"}
                });
        Object[][] result = {
                {null, 1, 2, "2", 3, "3", "3", 4, "4"},
                {null, null, 2, "2", 3, "3", "3", 4, "4"},
                {null, null, null, null, 3, "3", "3", null, null}
        };
        Assertions.assertArrayEquals(result, blocks.merge());
    }
}
