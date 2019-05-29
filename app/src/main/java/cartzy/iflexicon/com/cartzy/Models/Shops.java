package cartzy.iflexicon.com.cartzy.Models;


/**
 * Created by Udesh on 10/28/2016.
 */

public class Shops {
    String name ;
    String place_id;
    String icon;
    String vicinity;
    String id;
    double latitude;
    double longitude;
    String isOpen;
    String photoRef;

    public Shops() {
    }


    public String getName() {
        return name;
    }

    public double getLatitude(){return latitude;}

    public double getLongitude(){return longitude;}

    public String getPhotoRef() {return photoRef;}

    public String getId(){return id;}

    public void setLatitude(double latitude){this.latitude = latitude;}

    public void setLongitude(double longitude){this.longitude = longitude;}



    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) { this.vicinity = vicinity;  }

    public String getIcon() {
        return icon;
    }

    public String getIsOpen(){return isOpen;}

    public void setIsOpen(String isOpen){this.isOpen = isOpen;}

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setPhotoRef(String photoRef){this.photoRef = photoRef;}

    public void setId(String id){this.id = id;}


}
