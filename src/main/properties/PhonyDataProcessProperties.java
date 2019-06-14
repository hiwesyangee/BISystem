package properties;

/**
 * Phony数据表相关常数
 */
public class PhonyDataProcessProperties {
    /**
     * phony设备相关表
     */
    // 1.每日设备登录相关表————————rowkey：20181016===channel
    public static final String PHONYDAYDEVICELOGINSTATTABLE = "phony_day_device_login_stat";
    public static final String[] cfsOfPHONYDAYDEVICELOGINSTATTABLE = {"info"};
    public static final String[] columnsOfPHONYDAYDEVICELOGINSTATTABLE = {"activeNumber"}; // 活跃设备数
    // 活跃设备数

    // 2.每日设备登录时间表————————rowkey：20190115121212===uuid===channel
    public static final String PHONYDEVICELOGINTIMETABLE = "phony_device_login_time";
    public static final String[] cfsOfPHONYDEVICELOGINTIMETABLE = {"info"};
    public static final String[] columnsOfPHONYDEVICELOGINTIMETABLE = {"uuid", "channel"}; // 每次登录的uuid,渠道

    /**
     * phony用户相关表
     */
    // 3.每日用户登录相关表————————rowkey：20181016===channel
    public static final String PHONYDAYUSERLOGINSTATTABLE = "phony_day_user_login_stat";
    public static final String[] cfsOfPHONYDAYUSERLOGINSTATTABLE = {"info"};
    public static final String[] columnsOfPHONYDAYUSERLOGINSTATTABLE = {"activeNumber"}; // 活跃用户数
    // 活跃设备数

    // 4.每日用户登录时间表————————rowkey：20190115121212===uid===channel
    public static final String PHONYUSERLOGINTIMETABLE = "phony_user_login_time";
    public static final String[] cfsOfPHONYUSERLOGINTIMETABLE = {"info"};
    public static final String[] columnsOfPHONYUSERLOGINTIMETABLE = {"uuid", "channel"}; // 每次登录的uid,渠道

}
