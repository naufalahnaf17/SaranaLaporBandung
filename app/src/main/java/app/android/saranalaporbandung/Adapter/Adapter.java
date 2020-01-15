package app.android.saranalaporbandung.Adapter;

public class Adapter {
    String idPost;
    String captionPost;
    String locationPost;
    String datePost;
    String likePost;
    String statusPost;
    String urlPost;
    String urlPhotoUser;
    String nameUserPost;

    public Adapter(String idPost, String captionPost, String locationPost, String datePost, String likePost, String statusPost, String urlPost , String urlPhotoUser , String nameUserPost) {
        this.idPost = idPost;
        this.captionPost = captionPost;
        this.locationPost = locationPost;
        this.datePost = datePost;
        this.likePost = likePost;
        this.statusPost = statusPost;
        this.urlPost = urlPost;
        this.urlPhotoUser = urlPhotoUser;
        this.nameUserPost = nameUserPost;
    }

    public Adapter(){}

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getCaptionPost() {
        return captionPost;
    }

    public void setCaptionPost(String captionPost) {
        this.captionPost = captionPost;
    }

    public String getLocationPost() {
        return locationPost;
    }

    public void setLocationPost(String locationPost) {
        this.locationPost = locationPost;
    }

    public String getDatePost() {
        return datePost;
    }

    public void setDatePost(String datePost) {
        this.datePost = datePost;
    }

    public String getLikePost() {
        return likePost;
    }

    public void setLikePost(String likePost) {
        this.likePost = likePost;
    }

    public String getStatusPost() {
        return statusPost;
    }

    public void setStatusPost(String statusPost) {
        this.statusPost = statusPost;
    }

    public String getUrlPost() {
        return urlPost;
    }

    public void setUrlPost(String urlPost) {
        this.urlPost = urlPost;
    }

    public String getUrlPhotoUser() {
        return urlPhotoUser;
    }

    public void setUrlPhotoUser(String urlPhotoUser) {
        this.urlPhotoUser = urlPhotoUser;
    }

    public String getNameUserPost() {
        return nameUserPost;
    }

    public void setNameUserPost(String nameUserPost) {
        this.nameUserPost = nameUserPost;
    }
}
