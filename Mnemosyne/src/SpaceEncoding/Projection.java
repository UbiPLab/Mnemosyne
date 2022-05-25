package SpaceEncoding;

import java.util.ArrayList;
import java.util.List;

public class Projection {

    //  原始映射函数 f(q) = (a*q + b)/d
    //  AND操作需要 4 个原始映射函数 划分整个空间

    //  纬度
    public final double Max_Lat = 50;
    public final double Min_Lat = 26;
    //  经度
    public final double Max_Lng = 124;
    public final double Min_Lng = 80;
    private final int length_lat = 30;  //划分次数 5
    private final int length_lng = 31;  //划分次数 6
    //  纬度  精度 11.25
    //  经度  精度 22.5
    private final double latUnit = (Max_Lat - Min_Lat) / (1 << 20);     //纬度单元
    private final double lngUnit = (Max_Lng - Min_Lng) / (1 << 20);     //经度单元

    public String Compute_AND(double lat, double lng ,int k) {
        return encode(lat, lng ,k);
    }

    public String Compute_AND_9_block(double lat, double lng ,int k) {
        String list = encode(lat, lng ,k);
//        list.add(encode(lat, lng ,k));         //当前 目标点  转换
//        list.add(encode(lat + latUnit, lng,k)); //2
//        list.add(encode(lat - latUnit, lng,k)); //8
//        list.add(encode(lat, lng + lngUnit,k)); //6
//        list.add(encode(lat, lng - lngUnit,k)); //4
//        list.add(encode(lat + latUnit, lng + lngUnit,k));   //3
//        list.add(encode(lat + latUnit, lng - lngUnit,k));   //1
//        list.add(encode(lat - latUnit, lng + lngUnit,k));   //9
//        list.add(encode(lat - latUnit, lng - lngUnit,k));   //7
        return list;
    }



    /**
     * 入口 Q
     * @param lat
     * @param lng
     * @param functionNum
     * @return
     */
    public String[] Compute_concatenate(double lat, double lng,int functionNum){

        String[] unit = Compute_OR(lat,lng,functionNum/2);

        String[] Q = new String[unit.length];

        for (int i = 0; i < Q.length; i++) {
            String str = Integer.toBinaryString(i);
            if(str.length()<4){
                int bit = 4-str.length();
                for(int j=0; j<bit;j++) {
                    str = "0"+str;
                }
            }
            Q[i] = str + "||" + unit[i];
        }

        return Q;
    }

    /**
     * 随机选择映射函数OR运算
     * @param lat
     * @param lng
     * @param length
     * @return
     */
    public String[] Compute_OR(double lat, double lng, int length) {
        String[] or = new String[length];
//        int[] projection_choose = createRandomFromAll(length,10);
        for (int k=0;k<or.length;k++){
            or[k] = new Projection().Compute_AND(lat,lng,k);
//            or[i] = new Projection().Compute_AND(lat,lng,projection_choose[i]+1);
        }
        return or;
    }


    public String[] Compute_OR (double lat, double lng ){
        String[] or = new String[4];
        for (int i=0;i<or.length;i++){
            or[i] = new Projection().Compute_AND(lat,lng,i+2);
        }
        return or;
    }



    public String[] Compute_concatenate(double lat, double lng ){

        String[] unit = Compute_OR(lat,lng);

        String[] Q = new String[4];
        StringBuilder sa = new StringBuilder();
        sa.append("00||"+ unit[0]);
        Q[0] = sa.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("01||"+ unit[1]);
        Q[1] = sb.toString();
        StringBuilder sc = new StringBuilder();
        sc.append("10||"+ unit[2]);
        Q[2] = sc.toString();
        StringBuilder sd = new StringBuilder();
        sd.append("11||"+ unit[3]);
        Q[3] = sd.toString();

        return Q;
    }

    public String encode(double lat, double lng ,int k) {
        List<Character> latList = new ArrayList<Character>();
        List<Character> lngList = new ArrayList<Character>();
        //经纬度的0/1编码
        Projection_lat(Min_Lat, Max_Lat, lat, latList ,k);
        Projection_lng(Min_Lng, Max_Lng, lng, lngList ,k);
        StringBuilder sb = new StringBuilder();
        //经纬度的编码合并，从0开始，前面为是纬度，后面为是经度
        for (int index = 0; index < latList.size(); index++) {
            sb.append(latList.get(index));
        }
        for (int index = 0; index < lngList.size(); index++) {
            sb.append(lngList.get(index));
        }
        return sb.toString();

    }

    private void Projection_lat(double min, double max, double value, List<Character> list ,int k) {

        if (list.size() > (length_lat - 1)) {       // length : the number of projection function
            return;
        }
        double mid = (max + min) ;       // k use for change projection function
//        switch (k){
//            case 3 :  mid = mid *3/ 8;break;
//            case 2 :  mid = mid * 5/ 8;break;
//            case 4 :  mid = mid  / 4;break;
//            case 5 :  mid = mid *3/ 4;break;
//            case 6 :  mid = mid *2 / 5;break;
//            case 7 :  mid = mid *3 / 5;break;
//            case 8 :  mid = mid / 6;break;
//            case 9 :  mid = mid *5/6;break;
//            case 1 :  mid = mid *5/ 8;break;
//            default:
//                mid  = mid/ 2;break;
//        }
        mid = chooseFunction(mid,k);

        if (value < mid) {
            list.add('0');
            Projection_lat(min, mid, value, list,k);
        } else {
            list.add('1');
            Projection_lat(mid, max, value, list,k);
        }
    }

    public double chooseFunction(double value,int k){
        double mid = value;
        switch (k){
            case 1 :  mid = mid * 1.01/ 2;break;    //3
            case 2 :  mid = mid * 1.009/ 2;break;    // 4
            case 3 :  mid = mid * 0.995/ 2;break;   //10
            case 4 :  mid = mid * 1.0825 / 2;break; //12
            case 5 :  mid = mid * 1.005/ 2;break;   //14
            case 6 :  mid = mid * 1.06/ 2;break;        //16
            case 7 :  mid = mid * 0.985/ 2;break;    //20
//            case 7 :  mid = mid * 1.0725 / 2;break; // 20
            case 8 :  mid = mid * 0.95/ 2;break;    // 37
            case 9 :  mid = mid * 1.09 / 2;break; // 39
            default:
                mid  = mid * 1.001/ 2;break;   //  1
            //  case 7 :  mid = mid * 1.0825 / 2;break; // 12

        }
        return mid;
    }

    private void Projection_lng(double min, double max, double value, List<Character> list ,int k) {

        if (list.size() > (length_lng - 1)) {       // length : the number of projection function
            return;
        }
        double mid = (max + min) ;       // k use for change projection function
//        switch (k){
//            case 2 :  mid = mid * 5/ 8;break;
//            case 3 :  mid = mid * 2/ 3;break;
//            case 4 :  mid = mid  / 4;break;
//            case 5 :  mid = mid *3/ 4;break;
//            case 6 :  mid = mid *2 / 5;break;
//            case 7 :  mid = mid *3 / 5;break;
//            case 8 :  mid = mid / 6;break;
//            case 9 :  mid = mid *5/6;break;
//            case 1 :  mid = mid *5/ 8;break;
//            default:
//                mid  = mid / 2;break;
//
//        }
        mid = chooseFunction(mid,k);

        if (value < mid) {
            list.add('0');
            Projection_lng(min, mid, value, list,k);
        } else {
            list.add('1');
            Projection_lng(mid, max, value, list,k);
        }
    }



    public int[] createRandomFromAll(int count, int all){
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



    public static String listToString(List<String> mList) {     //整个list 变为一个String
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            String[] mListArray = mList.toArray(new String[mList.size()]);
            for (int i = 0; i < mListArray.length; i++) {
                if (i < mListArray.length - 1) {
                    convertedListStr += mListArray[i] + ",";
                } else {
                    convertedListStr += mListArray[i];
                }
            }
            return convertedListStr;
        } else return "List is null!!!";
    }
}
