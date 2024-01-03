package guohao.utils.export.excel.utils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Blocks {
    private final List<Block> blocks = new ArrayList<>();

    private int maxRow = 0;
    private int sumCol = 0;

    public Blocks addBlock(Object[][] block) {
        Block newBlock = new Block(block);
        blocks.add(newBlock);
        this.maxRow = Math.max(this.maxRow, newBlock.getRowNum());
        this.sumCol += newBlock.getColNum();
        return this;
    }

    public List<List<Object>> mergeToList() {
        return Arrays.stream(merge())
                .map(Arrays::asList)
                .collect(Collectors.toList());
    }

    public List<Object[]> mergeToArray() {
        return Arrays.stream(merge())
                .collect(Collectors.toList());
    }

    public Object[][] merge() {
        Object[][] result = new Object[this.maxRow][this.sumCol];
        int curCol = 0;
        for (Block block : blocks) {
            block.copy(curCol, result);
            curCol += block.getColNum();
        }
        return result;
    }

    @Getter
    public static class Block {
        private final Object[][] block;
        private int colNum = 0;
        private int rowNum = 0;

        public Block(Object[][] block) {
            this.block = block;
            this.rowNum = block.length;
            for (Object[] objects : block) {
                this.colNum = Math.max(this.colNum, objects.length);
            }
        }

        public void copy(int startCol, Object[][] matrix) {
            for (int i = 0; i < this.block.length; i++) {
                System.arraycopy(block[i], 0, matrix[i], startCol,block[i].length);
            }
        }
    }

}
