package co.uglytruth.lindy.location;

/**
 * Created by tjw127 on 11/8/17.
 */

public class LocationObject {

    public Double latitude;
    public Double longitude;

    public LocationObject setLocation(Double aLatitude, Double aLongitude)
    {
        this.latitude = aLatitude;
        this.longitude = aLongitude;

        return this;
    }
}
