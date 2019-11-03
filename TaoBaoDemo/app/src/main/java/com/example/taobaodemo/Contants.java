package com.example.taobaodemo;

public class Contants {

    public static class API{
        public static final String BASE_URL = "http://112.124.22.238:8081/";

        public static final String BANNER_HOME= BASE_URL+"course_api/banner/query?type=1";
        public static final String CAMAIGN_HOME= BASE_URL+"course_api/campaign/recommend";

        public static final String WARES_HOT=BASE_URL + "course_api/wares/hot";

        public static final String CATEGORY_LIST=BASE_URL +"course_api/category/list";
        public static final String WARES_LIST=BASE_URL +"course_api/wares/list";

        public static final String WARES_CAMPAIN_LIST=BASE_URL +"course_api/wares/campaign/list";
    }
}
