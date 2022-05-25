package Test;

/**
 * @Author UbiP Lab Laptop 02
 * @Date 2022/4/18 15:09
 * @Version 1.0
 */
public class FuctionTime {
    public static void main(String[] args) throws Exception {
        int test_time = 100;
        long[][] Time = new long[test_time][3];
        long[][] Size = new long[test_time][3];
        double[][] distance = new double[test_time][3];

        int fuctionNumber = 20;
        for (int i = 0; i < test_time; i++) {
//            long[] sizeOf = new long[3];
//            long[] time = new long[3];
//            double[] distance = new double[3];

            new testOneByFive().OneByFive(fuctionNumber,Time[i],Size[i],distance[i]);

        }

        System.out.println();
        System.out.println("-----------------");
        System.out.println("fuctionNumber : "+fuctionNumber);

        long[] t = ComputeAve(Time);
        long[] s = ComputeAve(Size);
        double[] d = ComputeAve(distance);
        System.out.println("-----------------");
        System.out.println("Rider_time:");
        System.out.println(t[0]);
        System.out.println("Driver_time:");
        System.out.println(t[1]);
        System.out.println("RHSP_time:");
        System.out.println(t[2]);
        System.out.println("-----------------");
        System.out.println("Rider_size:");
        System.out.println(s[0]);
        System.out.println("Driver_size:");
        System.out.println(s[1]);
        System.out.println("RHSP_size:");
        System.out.println(s[2]);
        System.out.println("-----------------");
        System.out.println("Max_distance:");
        System.out.println(d[0]);
        System.out.println("Min_distance:");
        System.out.println(d[1]);
        System.out.println("Ave_distance:");
        System.out.println(d[2]);

    }

    public static long[] ComputeAve(long[][] matrix){
        long[] result = new long[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            result[0] += matrix[i][0];
            result[1] += matrix[i][1];
            result[2] += matrix[i][2];
        }
        for (int i = 0; i < result.length; i++) {
            result[i] = result[i]/matrix.length;
        }
        return result;
    }

    public static double[] ComputeAve(double[][] matrix){
        double[] result = new double[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            result[0] += matrix[i][0];
            result[1] += matrix[i][1];
            result[2] += matrix[i][2];
        }
        for (int i = 0; i < result.length; i++) {
            result[i] = result[i]/matrix.length;
        }
        return result;
    }

}
