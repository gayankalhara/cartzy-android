package cartzy.iflexicon.com.cartzy.util;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Thirumagal on 10/19/2016.
 */

public class PlacesData implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    public String ID;

    @SerializedName("name")
    public String Name;

    @SerializedName("reference")
    public String Reference;


    @SerializedName("location")
    public String Location;

    @SerializedName("icon")
    public String Icon;

    @SerializedName("vicinity")
    public String Vicinity;

    @SerializedName("geometry")
    public String Geometry;

    @SerializedName("formatted_address")
    public String FormattedAddress;

    @SerializedName("formatted_phone_number")
    public String FormattedPhoneNumber;

    public PlacesData() {
    }

}

