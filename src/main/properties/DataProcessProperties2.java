package properties;

public class DataProcessProperties2 {
    // 0. 用户对应渠道表————————rowkey：uid
    public final static String USER4DEVICE = "user_for_device";
    public final static String[] cfsOfUSER4DEVICE = {"info"};
    public final static String[] columnsOfUSER4DEVICE = {"uuid", "channel"}; // 设备号

    /** 12.17修改设备和用户相关数据的计算 */
    /**
     * 设备相关
     */
    // 1.每日设备登录相关表————————rowkey：20181016===channel
    public final static String DAYDEVICELOGINSTATTABLE = "day_device_login_stat";
    public final static String[] cfsOfDAYDEVICELOGINSTATTABLE = {"info"};
    public final static String[] columnsOfDAYDEVICELOGINSTATTABLE = {"newNumber", "activeNumber", "totalLoginTimes", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth"};
    // 新增设备数，活跃设备数，总登录次数，留存1-7-14-30

    // 2.每日总设备登录相关表————————rowkey：20181016
    public final static String DAYTOTALDEVICELOGINSTATTABLE = "day_total_device_login_stat";
    public final static String[] cfsOfDAYTOTALDEVICELOGINSTATTABLE = {"info"};
    public final static String[] columnsOfDAYTOTALDEVICELOGINSTATTABLE = {"newNumber", "activeNumber", "totalLoginTimes", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth"};
    // 新增设备数，活跃设备数，总登录次数，留存1-7-14-30

    // 3.每日设备登录时间表————————rowkey：20181016121212===uuid===channel
    public final static String DEVICELOGINTIMETABLE = "device_login_time";
    public final static String[] cfsOfDEVICELOGINTIMETABLE = {"info"};
    public final static String[] columnsOfDEVICELOGINTIMETABLE = {"uuid", "second", "channel", "logints", "offlinets"}; // 每次登录的uuid,使用时长分钟,uuid的渠道

    // 3.5}; 每日新增设备登录时间表————————rowkey：20181016121212===uuid===channel
    public final static String NEWDEVICELOGINTIMETABLE = "new_device_login_time";
    public final static String[] cfsOfNEWDEVICELOGINTIMETABLE = {"info"};
    public final static String[] columnsOfNEWDEVICELOGINTIMETABLE = {"uuid", "second", "channel", "logints"}; // 每次登录的uuid,使用时长分钟,uuid的渠道

    // 4.设备首次登录时间表————————rowkey：uuid
    public final static String DEVICEFIRESTLOGINTABLE = "device_first_login";
    public final static String[] cfsOfDEVICEFIRESTLOGINTABLE = {"info"};
    public final static String[] columnsOfDEVICEFIRESTLOGINTABLE = {"firstDay"}; // 首次登录的天

    // 5.每小时同时在线设备数表————————rowkey：2018101612===channel
    public final static String HOURONLINEDEVICETABLE = "hour_online_device";
    public final static String[] cfsOfHOURONLINEDEVICETABLE = {"info"};
    public final static String[] columnsOfHOURONLINEDEVICETABLE = {"number"}; // 每小时在线设备数

    // 6.每小时同时在线总设备数表————————rowkey：2018101612
    public final static String HOURTOTALONLINEDEVICETABLE = "hour_total_online_device";
    public final static String[] cfsOfHOURTOTALONLINEDEVICETABLE = {"info"};
    public final static String[] columnsOfHOURTOTALONLINEDEVICETABLE = {"number"}; //每小时在线设备数

    /**
     * 用户相关
     */
    // 7.每日用户登录相关表————————rowkey：20181016===channel
    public final static String DAYUSERLOGINSTATTABLE = "day_user_login_stat";
    public final static String[] cfsOfDAYUSERLOGINSTATTABLE = {"info"};
    public final static String[] columnsOfDAYUSERLOGINSTATTABLE = {"newNumber", "activeNumber", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth"};
    // 渠道新增用户数，活跃用户数，最大在线用户数，留存1-7-14-30

    // 7.2）衍生表：每日绑定手机用户登录相关表————————rowkey：20181016===channel
    public final static String DAYUSERLOGINSTATWITHPHONETABLE = "day_user_login_stat_with_phone";
    public final static String[] cfsOfDAYUSERLOGINSTATWITHPHONETABLE = {"info"};
    public final static String[] columnsOfDAYUSERLOGINSTATWITHPHONETABLE = {"newNumber", "activeNumber", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth"}; // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
    // 渠道新增手机用户数，活跃手机用户数，最大在线手机用户数，留存1-7-14-30

    // 8.每日总用户登录相关表————————rowkey：20181016
    public final static String DAYTOTALUSERLOGINSTATTABLE = "day_total_user_login_stat";
    public final static String[] cfsOfDAYTOTALUSERLOGINSTATTABLE = {"info"};
    public final static String[] columnsOfDAYTOTALUSERLOGINSTATTABLE = {"newNumber", "activeNumber", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth"}; // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
    // 总新增用户数，活跃用户数，最大在线用户数，留存1-7-14-30

    // 8.2）衍生表：每日总绑定手机用户登录相关表————————rowkey：20181016
    public final static String DAYTOTALUSERLOGINSTATWITHPHONETABLE = "day_total_user_login_stat_with_phone";
    public final static String[] cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE = {"info"};
    public final static String[] columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE = {"newNumber", "activeNumber", "liucun", "liucun2", "liucun3", "liucun4", "liucun5", "liucun6", "liucunWeek", "liucunWeek2", "liucunMonth"}; // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
    // 总新增手机用户数，活跃手机用户数，最大在线手机用户数，留存1-7-14-30

    // 9.每日用户登录时间表————————rowkey：20181016121212===uid===channel
    public final static String USERLOGINTIMETABLE = "user_login_time";
    public final static String[] cfsOfUSERLOGINTIMETABLE = {"info"};
    public final static String[] columnsOfUSERLOGINTIMETABLE = {"uid", "second", "channel", "logints", "offlinets"}; // 每次登录的uid,使用时长,uid渠道

    // 9.2）衍生表：每日绑定手机用户登录时间表————————rowkey：20181016121212===uid===channel
    public final static String USERLOGINTIMEWITHPHONETABLE = "user_login_time_with_phone";
    public final static String[] cfsOfUSERLOGINTIMEWITHPHONETABLE = {"info"};
    public final static String[] columnsOfUSERLOGINTIMEWITHPHONETABLE = {"uid", "second", "channel", "logints", "offlinets"}; // 每次登录的uid,使用时长,uid渠道

    // 9.每日新增用户登录时间表————————rowkey：20181016121212===uid===channel
    public final static String NEWUSERLOGINTIMETABLE = "new_user_login_time";
    public final static String[] cfsOfNEWUSERLOGINTIMETABLE = {"info"};
    public final static String[] columnsOfNEWUSERLOGINTIMETABLE = {"uid", "second", "channel", "logints"}; // 每次登录的uid,使用时长,uid渠道

    // 9.2）衍生表：每日新增绑定手机用户登录时间表————————rowkey：20181016121212===uid===channel
    public final static String NEWUSERLOGINTIMEWITHPHONETABLE = "new_user_login_time_with_phone";
    public final static String[] cfsOfNEWUSERLOGINTIMEWITHPHONETABLE = {"info"};
    public final static String[] columnsOfNEWUSERLOGINTIMEWITHPHONETABLE = {"uid", "second", "channel", "logints"}; // 每次登录的uid,使用时长,uid渠道

    // 10.用户首次登录时间表————————rowkey：uid
    public final static String USERFIRESTLOGINTABLE = "user_first_login";
    public final static String[] cfsOfUSERFIRESTLOGINTABLE = {"info"};
    public final static String[] columnsOfUSERFIRESTLOGINTABLE = {"firstDay"}; // 首次登录的天

    // 11.每小时同时在线设备数表————————rowkey：2018101612===channel
    public final static String HOURONLINEUSERTABLE = "hour_online_user";
    public final static String[] cfsOfHOURONLINEUSERTABLE = {"info"};
    public final static String[] columnsOfHOURONLINEUSERTABLE = {"number"}; // 渠道每小时在线用户数

    // 11.2）衍生表：每小时绑定手机同时在线设备数表————————rowkey：2018101612===channel
    public final static String HOURONLINEUSERWITHPHONETABLE = "hour_online_user_with_phone";
    public final static String[] cfsOfHOURONLINEUSERWITHPHONETABLE = {"info"};
    public final static String[] columnsOfHOURONLINEUSERWITHPHONETABLE = {"number"}; // 渠道每小时在线手机用户数

    // 12.每小时总同时在线设备数表————————rowkey：2018101612
    public final static String HOURTOTALONLINEUSERTABLE = "hour_total_online_user";
    public final static String[] cfsOfHOURTOTALONLINEUSERTABLE = {"info"};
    public final static String[] columnsOfHOURTOTALONLINEUSERTABLE = {"number"}; // 总每小时在线用户数

    // 12.2）衍生表：每小时绑定手机总同时在线设备数表————————rowkey：2018101612
    public final static String HOURTOTALONLINEUSERWITHPHONETABLE = "hour_total_online_user_with_phone";
    public final static String[] cfsOfHOURTOTALONLINEUSERWITHPHONETABLE = {"info"};
    public final static String[] columnsOfHOURTOTALONLINEUSERWITHPHONETABLE = {"number"}; // 总每小时在线手机用户数

    /** 12.21修改充值消费数据的计算 */
    /** 充值相关 */
    /**
     * 01.18修改充值消费数据的存储，不再进行计算
     */
    // 13.每日用户充值时间表——————————————rowkey:20181016121212===uid===channel
    public final static String USERPAYTIMETABLE = "user_pay_time";
    public final static String[] cfsOfUSERPAYTIMETABLE = {"info"};
    public final static String[] columnsOfUSERPAYTIMETABLE = {"uid", "uuid", "amount", "type", "channel"}; // 每次充值的uid,uuid,金额,类型,uid渠道

    // 13.5) 每日新增用户充值时间表——————————————rowkey:20181016121212===uid===channel
    public final static String NEWUSERPAYTIMETABLE = "new_user_pay_time";
    public final static String[] cfsOfNEWUSERPAYTIMETABLE = {"info"};
    public final static String[] columnsOfNEWUSERPAYTIMETABLE = {"uid", "uuid", "amount", "type", "channel"}; // 每次充值的uid,uuid,金额,类型,uid渠道

    // 14.每日充值渠道统计表————————————————rowkey:20181016===channel
    public final static String DAYUSERPAYSTATBYCHANNELTABLE = "day_user_pay_stat_by_channel";
    public final static String[] cfsOfDAYUSERPAYSTATBYCHANNELTABLE = {"info"};
    public final static String[] columnsOfDAYUSERPAYSTATBYCHANNELTABLE = {"apm", "npm", "activePay", "newPay", "apr", "npr", "appc", "nppc", "aARPU", "aARPPU", "nARPU", "nARPPU"};

    // 15.每日充值总统计表————————————————rowkey:20181016
    public final static String DAYUSERPAYSTATTABLE = "day_user_pay_stat";
    public final static String[] cfsOfDAYUSERPAYSTATTABLE = {"info"};
    public final static String[] columnsOfDAYUSERPAYSTATTABLE = {"apm", "npm", "activePay", "newPay", "apr", "npr", "appc", "nppc", "aARPU", "aARPPU", "nARPU", "nARPPU"};

    /**
     * 消费相关
     */
    // 16.每日用户消费时间表——————————————rowkey:20181016121212===uid===channel
    public final static String USERCRONSTIMETABLE = "user_crons_time";
    public final static String[] cfsOfUSERCRONSTIMETABLE = {"info"};
    public final static String[] columnsOfUSERCRONSTIMETABLE = {"uid", "uuid", "amount", "type", "channel"}; // 每次消费的uid,uuid,金额,类型,uid渠道

    // 16.5};每日新增用户消费时间表——————————————rowkey:20181016121212===uid===channel
    public final static String NEWUSERCRONSTIMETABLE = "new_user_crons_time";
    public final static String[] cfsOfNEWUSERCRONSTIMETABLE = {"info"};
    public final static String[] columnsOfNEWUSERCRONSTIMETABLE = {"uid", "uuid", "amount", "type", "channel"}; // 每次消费的uid,uuid,金额,类型,uid渠道

    // 17.每日消费渠道统计表————————————————rowkey:20181016===channel
    public final static String DAYUSERCRONSSTATBYCHANNELTABLE = "day_user_crons_stat_by_channel";
    public final static String[] cfsOfDAYUSERCRONSSTATBYCHANNELTABLE = {"info"};
    public final static String[] columnsOfDAYUSERCRONSSTATBYCHANNELTABLE = {"acm", "ncm", "activeCrons", "newCrons", "acr", "ncr", "apcc", "npcc"};

    // 18.每日消费人数总统计表————————————————rowkey:20181016
    public final static String DAYUSERCRONSSTATTABLE = "day_user_crons_stat";
    public final static String[] cfsOfDAYUSERCRONSSTATTABLE = {"info"};
    public final static String[] columnsOfDAYUSERCRONSSTATTABLE = {"acm", "ncm", "activeCrons", "newCrons", "acr", "ncr", "apcc", "npcc"};

    /**
     * 订阅相关
     */
    // 19.每日订阅时间表——————————————rowkey:20181016121212===uid===channel
    public final static String USERORDERTIMETABLE = "user_order_time";
    public final static String[] cfsOfUSERORDERTIMETABLE = {"info"};
    public final static String[] columnsOfUSERORDERTIMETABLE = {"uid", "uuid", "bName", "cid", "cName", "channel"}; // 每次订阅的uid,uuid,书名，章节ID，uid渠道

    // 19.5）每日免费订阅表————————————————rowkey:20181016121212===uid===channel
    public final static String USERFREEORDERTIMETABLE = "user_free_order_time";
    public final static String[] cfsOfUSERFREEORDERTIMETABLE = {"info"};
    public final static String[] columnsOfUSERFREEORDERTIMETABLE = {"uid", "uuid", "bName", "cid", "cName", "channel"}; // 每次免费订阅的uid,uuid,书名，章节ID，uid渠道

    /**
     * 阅读相关
     */
    // 20.每日阅读时间表——————————————rowkey:20181016121212===uid===channel
    public final static String USERREADTIMETABLE = "user_read_time";
    public final static String[] cfsOfUSERREADTIMETABLE = {"info"};
    public final static String[] columnsOfUSERREADTIMETABLE = {"uid", "uuid", "bName", "cid", "cName", "channel"}; // 每次阅读的uid,uuid,书名，章节ID，uid渠道

    /**
     * 模拟添加:设备/用户最后操作时间
     */
    // 24.设备和用户最后操作时间表————————————————rowkey:uuid_uid
    public final static String DEVICELASTIMEUSETIMETABLE = "user_lasttime_use";
    public final static String[] cfsOfDEVICELASTIMEUSETIMETABLE = {"info"};
    public final static String[] columnsOfDEVICELASTIMEUSETIMETABLE = {"timestamp"}; // 时间戳

}
