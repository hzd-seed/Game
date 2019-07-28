package team.tetris.block;


import java.util.HashMap;
import java.util.Map;

public class Sliver extends Block {

    private final Map<Integer, Integer[][]> blockMap = new HashMap<>();
    private final Map<Integer, Integer[]> blockStatusMap = new HashMap<>();

    @Override
    public void turn() {
        status = (status + 1) % 2;
        this.blocks = blockMap.get(status);
        this.width = blockStatusMap.get(status)[0];
        this.height = blockStatusMap.get(status)[1];
    }

    public Sliver() {
        blocks = new Integer[4][1];
        final Integer[][] block1 = new Integer[][]{
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0}
        };
        blockMap.put(0, block1);
        blockStatusMap.put(0, new Integer[]{4, 1});
        final Integer[][] block2 = new Integer[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        blockMap.put(1, block2);
        blockStatusMap.put(1, new Integer[]{1, 4});
        // 设置状态初始化
        status = 1;
        turn();
    }
}
