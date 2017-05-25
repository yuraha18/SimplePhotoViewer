package com.yuraha18.simplephotoviewer.model.UnsplashAPI;

/**
 * Created by User on 5/23/2017.
 */

public class ApiConstants {
    public static final String CONNECTION_URL = "https://api.unsplash.com/";

    public static final String authURL = ApiConstants.AUTH_BASIC_URL +
            "authorize?client_id=5b23d16c7e1489929207e45977ddf24b048630db2c37b1b802f3fa9a5a9c026f&response_type=code&redirect_uri=urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob&response_type=code&scope=public+write_likes" ;

    public static final String AUTH_BASIC_URL = "https://unsplash.com/oauth/token/";

    public static final String CLIENT_ID = "5b23d16c7e1489929207e45977ddf24b048630db2c37b1b802f3fa9a5a9c026f";
    public static final String CLIENT_SECRET ="2a68c19b443f49957f8fb2d79fc9470b6ae51c84440be82cbc0b3c2f487e0122";
    public static final String REDIRECT_URI="urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob";
    public static final String GRANT_TYPE="authorization_code";
    public static final String TOKEN_URL ="https://unsplash.com/oauth/token";
    public static final String OAUTH_URL ="https://unsplash.com/oauth/authorize";
    public static final String OAUTH_SCOPE="public+write_likes";
    public static final String APP_PREFERENCES = "access_tokens";
    public static final String ACCESS_TOKEN ="token";

    public static final String GET_AUTH_TOKEN_URL = "?client_id=5b23d16c7e1489929207e45977ddf24b048630db2c37b1b802f3fa9a5a9c026f&client_secret=2a68c19b443f49957f8fb2d79fc9470b6ae51c84440be82cbc0b3c2f487e0122&redirect_uri=urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob&grant_type=authorization_code";

    public static final String LIKE_PHOTO = "photos/{id}/like";
    public static final String UNLIKE_PHOTO = "photos/{id}/like/";

    public static final String SCOPE = "public+write_likes";
    public static final String GET_RANDOM_PHOTO = "photos/random?client_id=" + CLIENT_ID;
    public static final String GET_LIST_PHOTOS = "/photos?client_id=" + CLIENT_ID;


    public static final String GROUP_BY_OLDEST = "oldest";
    public static final String GROUP_BY_LATEST = "latest";
    public static final String GROUP_BY_POPULAR = "popular";
    public static final String RANDOM_PHOTO = "random";

     public static final String AUTH_URL = "https://unsplash.com/oauth/authorize";
    //  public static final String

    /* Exceptions */
    public static final String CANT_CONNECCT_TO_INTERNET = "";
    public static final String REQUEST_LIMIT_EXCEPTION = "";

}
