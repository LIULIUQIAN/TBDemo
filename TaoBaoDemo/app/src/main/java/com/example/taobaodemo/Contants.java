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

        public static final String ORDER_CREATE=BASE_URL +"course_api/order/create";
        public static final String ORDER_COMPLEPE=BASE_URL +"/order/complete";

        public static final String PAY_URL = "http://218.244.151.190/demo/charge";

        public static final String ADDRESS_LIST=BASE_URL +"course_api/addr/list";
        public static final String ADDRESS_CREATE=BASE_URL +"course_api/addr/create";
        public static final String ADDRESS_UPDATE=BASE_URL +"course_api/addr/update";

    }
}
