package RideRequest;

/**
 * @Author UbiP Lab Laptop 02
 * @Date 2022/4/7 9:58
 * @Version 1.0
 */
public class ExcloudDriver {
    /**
     * 从总数all中排除count个司机
     * @param count 排除数量
     * @param all   总数
     * @return  排除的司机集合
     */
    public int[] createRandom(int count, int all){
        int[] arr = new int[count];
        for (int i = 0; i < count; i++) {
            arr[i] = (int) (Math.random() * all) + 1;
            for (int j = 0; j < i; j++) {
                if (arr[j] == arr[i]) {
                    i--;
                    break;
                }
            }
        }
        return arr;
    }
}
