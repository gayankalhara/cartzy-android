package cartzy.iflexicon.com.cartzy.Models;

import android.net.Uri;

/**
 * Created by HIS on 10/26/2016.
 */

public class User {
    Uri photo;
    String uername;
    String email;
    String id;
    String status;

    public User() {
    }

    public String getUername() {
        return uername;
    }

    public void setUername(String uername) {
        this.uername = uername;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
