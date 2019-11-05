package com.example.taobaodemo;

public class Contants {

    public  static final String DES_KEY="Cniao5_123456";

    public static class API{
        public static final String BASE_URL = "http://112.124.22.238:8081/";

        public static final String BANNER_HOME= BASE_URL+"course_api/banner/query?type=1";
        public static final String CAMAIGN_HOME= BASE_URL+"course_api/campaign/recommend";

        public static final String WARES_HOT=BASE_URL + "course_api/wares/hot";

        public static final String CATEGORY_LIST=BASE_URL +"course_api/category/list";
        public static final String WARES_LIST=BASE_URL +"course_api/wares/list";

        public static final String WARES_CAMPAIN_LIST=BASE_URL +"course_api/wares/campaign/list";
        public static final String WARES_DETAIL=BASE_URL +"course_api/wares/detail.html";

        public static final String LOGIN=BASE_URL +"course_api/auth/login";
        public static final String REG=BASE_URL +"course_api/auth/reg";

        public static final String USER_DETAIL=BASE_URL +"course_api/user/get?id=1";
    }
}
