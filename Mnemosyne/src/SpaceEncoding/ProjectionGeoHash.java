package SpaceEncoding;

import java.util.ArrayList;
import java.util.List;

public class ProjectionGeoHash {
    public final double Max_Lat = 90;
    public final double Min_Lat = -90;
    public final double Max_Lng = 180;
    public final double Min_Lng = -180;

    private final double latUnit = (Max_Lat - Min_Lat) / (1 << 20);     //纬度单元
    private final double lngUnit = (Max_Lng - Min_Lng) / (1 << 20);     //经度单元
    private final String[] base32Lookup =
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "b", "c", "d", "e", "f", "g", "h",
                    "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};



    public List<String> around(double lat, double lng ,int length) {
        List<String> list = new ArrayList<String>();
        list.add(encode_number(lat, lng ,length));         //当前 目标点 转换
        //  周围8格
        return list;
    }

    public String encode(double lat, double lng , int length) {
        List<Character> latList = new ArrayList<Character>();
        List<Character> lngList = new ArrayList<Character>();
        //经纬度的0/1编码
        convert(Min_Lat, Max_Lat, lat, latList ,length);
        convert(Min_Lng, Max_Lng, lng, lngList ,length);
        StringBuilder sb = new StringBuilder();
        //经纬度的编码合并，从0开始，奇数为是纬度，偶数为是经度
        for (int index = 0; index < latList.size(); index++) {
            sb.append(lngList.get(index)).append(latList.get(index));
        }
//        return sb.toString();
        return base32Encode(sb.toString());     //扩充
    }

    public String encode_number(double lat, double lng , int length) {
        List<Character> latList = new ArrayList<Character>();
        List<Character> lngList = new ArrayList<Character>();
        //经纬度的0/1编码
        convert(Min_Lat, Max_Lat, lat, latList ,length);
        convert(Min_Lng, Max_Lng, lng, lngList ,length);
        StringBuilder sb = new StringBuilder();
        //经纬度的编码合并，从0开始，奇数为是纬度，偶数为是经度
        for (int index = 0; index < latList.size(); index++) {
            sb.append(lngList.get(index)).append(latList.get(index));
        }
      return sb.toString();

    }

    //转换成0/1字符
    private void convert(double min, double max, double value, List<Character> list ,int length) {
        if (list.size() > (length - 1)) {
            return;
        }
        double mid = (max + min) / 2;
        if (value < mid) {
            list.add('0');
            convert(min, mid, value, list, length);
        } else {
            list.add('1');
            convert(mid, max, value, list, length);
        }
    }

    //每位扩充为5位
    private String base32Encode(final String str) {
        String unit = "";
        StringBuilder sb = new StringBuilder();
        for (int start = 0; start < str.length(); start = start + 5) {
            unit = str.substring(start, start + 5);
            sb.append(base32Lookup[convertToIndex(unit)]);
        }
        return sb.toString();
    }

    private int convertToIndex(String str) {
        int length = str.length();
        int result = 0;
        for (int index = 0; index < length; index++) {
            result += str.charAt(index) == '0' ? 0 : 1 << (length - 1 - index);
        }
        return result;
    }


}
