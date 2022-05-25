package SpaceEncoding;

import java.lang.Math.*;

import static java.lang.Math.*;

/**
 * @Author UbiP Lab Laptop 02
 * @Date 2022/4/6 10:03
 * @Version 1.0
 */
public class Distance {
    /**
     * @brief get rad according to the degrees.
     * @param degree:
     *
     * @return: rad according to the degrees.
     *
     *
     */
    private  double EARTH_RADIUS = 6378.137;

    public  double Deg2Rad(double degree)
    {
        return degree * PI / (90 * 2);
    }

    /**
     * @brief calc the direction distance of two coordinates.
     * @param srcLon: the longitude of source coordinate;
     *        srcLat: the latitude of source coordinate;
     *        destLon: the longitude of destination coordinate;
     *        destLat: the latitude of destination coordinate;
     *
     * @return DirectDistance: the direction distance from source coordinate to destination coordinate.
     */
     public double GetDirectDistance(double srcLon, double srcLat, double destLon, double destLat)
    {

        double radSrcLat = Deg2Rad(srcLat);
        double radDestLat = Deg2Rad(destLat);
        double a = radSrcLat - radDestLat;
        double b = Deg2Rad(srcLon) - Deg2Rad(destLon);

        double DirectDistance = 2 * asin(sqrt(pow(sin(a/2),2) +	cos(radSrcLat)*cos(radDestLat)*pow(sin(b/2),2)));

        DirectDistance = DirectDistance * EARTH_RADIUS;
        DirectDistance = round(DirectDistance * 10000) / 10000;

        return DirectDistance;
    }
}
