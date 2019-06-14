package properties;

public class DataProcessProperties {
    // 0. 用户对应渠道表————————rowkey：uid
    public final static String USER4DEVICE = "user_for_device";
    public final static String[] cfsOfUSER4DEVICE = {"info"};
    public final static String[] columnsOfUSER4DEVICE = {"uuid"}; // 设备号

    /** 12.17修改设备和用户相关数据的计算 */
    /**
     * 设备相关
     */
    // 1.每日设备登录相关表————————rowkey：20181016===channel
    public final static String DAYDEVICELOGINSTATTABLE = "day_device_login_stat";
    public final static String[] cfsOfDAYDEVICELOGINSTATTABLE = {"info"};
    public final static String[] columnsOfDAYDEVICELOGINSTATTABLE = {"loginDevice", "loginNumber", "newDevice", "newNumber", "liucunDevice", "liucunNumber"}; // 登录设备号，登录设备数，新增设备号，新增设备数，留存设备号，留存设备数
    // 登录设备号，登录设备数，新增设备号，新增设备数，留存设备号，留存设备数

    // 2.每日总设备登录相关表————————rowkey：20181016
    public final static String DAYTOTALDEVICELOGINSTATTABLE = "day_total_device_login_stat";
    public final static String[] cfsOfDAYTOTALDEVICELOGINSTATTABLE = {"info"};
    public final static String[] columnsOfDAYTOTALDEVICELOGINSTATTABLE = {"loginDevice", "loginNumber", "newDevice", "newNumber", "liucunDevice", "liucunNumber"}; // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
    // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数

    // 3.每日设备登录时间表————————rowkey：20181016121212===uuid===channel
    public final static String DEVICELOGINTIMETABLE = "device_login_time";
    public final static String[] cfsOfDEVICELOGINTIMETABLE = {"info"};
    public final static String[] columnsOfDEVICELOGINTIMETABLE = {"uuid", "second", "channel"}; // 每次登录的uuid,使用时长分钟,uuid的渠道

    // 4.设备首次登录时间表————————rowkey：uuid
    public final static String DEVICEFIRESTLOGINTABLE = "device_first_login";
    public final static String[] cfsOfDEVICEFIRESTLOGINTABLE = {"info"};
    public final static String[] columnsOfDEVICEFIRESTLOGINTABLE = {"firstDay"}; // 首次登录的天

    /**
     * !!!隐患:存在设备没有下线的小时，现在统计的只是每小时同时登陆设备数，看什么时候能暴露!!!
     **/
    // 5.每小时同时在线设备数表————————rowkey：2018101612===channel
    public final static String HOURONLINEDEVICETABLE = "hour_online_device";
    public final static String[] cfsOfHOURONLINEDEVICETABLE = {"info"};
    public final static String[] columnsOfHOURONLINEDEVICETABLE = {"list", "number"}; // 每小时在线设备号，每小时在线设备数

    // 6.每小时同时在线总设备数表————————rowkey：2018101612
    public final static String HOURTOTALONLINEDEVICETABLE = "hour_total_online_device";
    public final static String[] cfsOfHOURTOTALONLINEDEVICETABLE = {"info"};
    public final static String[] columnsOfHOURTOTALONLINEDEVICETABLE = {"list", "number"}; // 每小时在线设备号，每小时在线设备数

    /**
     * 用户相关
     */
    // 7.每日用户登录相关表————————rowkey：20181016===channel
    public final static String DAYUSERLOGINSTATTABLE = "day_user_login_stat";
    public final static String[] cfsOfDAYUSERLOGINSTATTABLE = {"info"};
    public final static String[] columnsOfDAYUSERLOGINSTATTABLE = {"loginUser", "loginNumber", "newUser", "newNumber", "liucunUser", "liucunNumber"}; // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
    // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数

    // 7.2）衍生表：每日绑定手机用户登录相关表————————rowkey：20181016===channel
    public final static String DAYUSERLOGINSTATWITHPHONETABLE = "day_user_login_stat_with_phone";
    public final static String[] cfsOfDAYUSERLOGINSTATWITHPHONETABLE = {"info"};
    public final static String[] columnsOfDAYUSERLOGINSTATWITHPHONETABLE = {"loginUser", "loginNumber", "newUser", "newNumber", "liucunUser", "liucunNumber"}; // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
    // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数

    // 8.每日总用户登录相关表————————rowkey：20181016
    public final static String DAYTOTALUSERLOGINSTATTABLE = "day_total_user_login_stat";
    public final static String[] cfsOfDAYTOTALUSERLOGINSTATTABLE = {"info"};
    public final static String[] columnsOfDAYTOTALUSERLOGINSTATTABLE = {"loginUser", "loginNumber", "newUser", "newNumber", "liucunUser", "liucunNumber"}; // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
    // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数

    // 8.2）衍生表：每日总绑定手机用户登录相关表————————rowkey：20181016
    public final static String DAYTOTALUSERLOGINSTATWITHPHONETABLE = "day_total_user_login_stat_with_phone";
    public final static String[] cfsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE = {"info"};
    public final static String[] columnsOfDAYTOTALUSERLOGINSTATWITHPHONETABLE = {"loginUser", "loginNumber", "newUser", "newNumber", "liucunUser", "liucunNumber"}; // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数
    // 登录用户号，登录用户数，新增用户号，新增用户数，留存用户号，留存用户数

    // 9.每日用户登录时间表————————rowkey：20181016121212===uid===channel
    public final static String USERLOGINTIMETABLE = "user_login_time";
    public final static String[] cfsOfUSERLOGINTIMETABLE = {"info"};
    public final static String[] columnsOfUSERLOGINTIMETABLE = {"uid", "second", "channel"}; // 每次登录的uid,使用时长,uid渠道

    // 9.2）衍生表：每日绑定手机用户登录时间表————————rowkey：20181016121212===uid===channel
    public final static String USERLOGINTIMEWITHPHONETABLE = "user_login_time_with_phone";
    public final static String[] cfsOfUSERLOGINTIMEWITHPHONETABLE = {"info"};
    public final static String[] columnsOfUSERLOGINTIMEWITHPHONETABLE = {"uid", "second", "channel"}; // 每次登录的uid,使用时长,uid渠道

    // 10.用户首次登录时间表————————rowkey：uid
    public final static String USERFIRESTLOGINTABLE = "user_first_login";
    public final static String[] cfsOfUSERFIRESTLOGINTABLE = {"info"};
    public final static String[] columnsOfUSERFIRESTLOGINTABLE = {"firstDay"}; // 首次登录的天

    // 11.每小时同时在线设备数表————————rowkey：2018101612===channel
    public final static String HOURONLINEUSERTABLE = "hour_online_user";
    public final static String[] cfsOfHOURONLINEUSERTABLE = {"info"};
    public final static String[] columnsOfHOURONLINEUSERTABLE = {"list", "number"}; // 每小时在线用户号，每小时在线用户数

    // 11.2）衍生表：每小时绑定手机同时在线设备数表————————rowkey：2018101612===channel
    public final static String HOURONLINEUSERWITHPHONETABLE = "hour_online_user_with_phone";
    public final static String[] cfsOfHOURONLINEUSERWITHPHONETABLE = {"info"};
    public final static String[] columnsOfHOURONLINEUSERWITHPHONETABLE = {"list", "number"}; // 每小时在线用户号，每小时在线用户数

    // 12.每小时总同时在线设备数表————————rowkey：2018101612
    public final static String HOURTOTALONLINEUSERTABLE = "hour_total_online_user";
    public final static String[] cfsOfHOURTOTALONLINEUSERTABLE = {"info"};
    public final static String[] columnsOfHOURTOTALONLINEUSERTABLE = {"list", "number"}; // 每小时在线用户号，每小时在线用户数

    // 12.2）衍生表：每小时绑定手机总同时在线设备数表————————rowkey：2018101612
    public final static String HOURTOTALONLINEUSERWITHPHONETABLE = "hour_total_online_user_with_phone";
    public final static String[] cfsOfHOURTOTALONLINEUSERWITHPHONETABLE = {"info"};
    public final static String[] columnsOfHOURTOTALONLINEUSERWITHPHONETABLE = {"list", "number"}; // 每小时在线用户号，每小时在线用户数

    /** 12.21修改充值消费数据的计算 */
    /**
     * 充值相关
     */
    // 13.每日充值时间表——————————————rowkey:20181016121212===uid===channel
    public final static String USERPAYTIMETABLE = "user_pay_time";
    public final static String[] cfsOfUSERPAYTIMETABLE = {"info"};
    public final static String[] columnsOfUSERPAYTIMETABLE = {"uid", "uuid", "amount", "type", "channel"}; // 每次充值的uid,uuid,金额,类型,uid渠道

    // 14.每日充值人数渠道统计表————————————————rowkey:20181016===channel
    public final static String DAYUSERPAYNUMBERSTATBYCHANNELTABLE = "day_user_pay_number_stat_by_channel";
    public final static String[] cfsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE = {"info"};
    public final static String[] columnsOfDAYUSERPAYNUMBERSTATBYCHANNELTABLE = {"aPayUser", "aPayNumber", "nPayUser", "npayNumber"}; // 活跃充值人/数，新增充值人/数

    // 15.每日充值人数总统计表————————————————rowkey:20181016
    public final static String DAYUSERPAYNUMBERSTATTABLE = "day_user_number_pay_stat";
    public final static String[] cfsOfDAYUSERPAYNUMBERSTATTABLE = {"info"};
    public final static String[] columnsOfDAYUSERPAYNUMBERSTATTABLE = {"aPayUser", "aPayNumber", "nPayUser", "nPayNumber"}; // 活跃充值人/数，新增充值人/数

    // 16.每日充值金额渠道统计表————————————————rowkey:2018101016===channel
    public final static String DAYUSERPAYAMOUNTSTATBYCHANNELTABLE = "day_user_pay_amount_stat_by_channel";
    public final static String[] cfsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE = {"info"};
    public final static String[] columnsOfDAYUSERPAYAMOUNTSTATBYCHANNELTABLE = {"apm", "npm"}; // 活跃充值额，新增充值额

    // 17.每日充值金额总统计表————————————————rowkey:20181016
    public final static String DAYUSERPAYAMOUNTSTATTABLE = "day_user_pay_amount_stat";
    public final static String[] cfsOfDAYUSERPAYAMOUNTSTATTABLE = {"info"};
    public final static String[] columnsOfDAYUSERPAYAMOUNTSTATTABLE = {"apm", "npm"}; // 活跃充值额，新增充值额

    /**
     * 消费相关
     */
    // 18.每日消费时间表——————————————rowkey:20181016121212===uid===channel
    public final static String USERCRONSTIMETABLE = "user_crons_time";
    public final static String[] cfsOfUSERCRONSTIMETABLE = {"info"};
    public final static String[] columnsOfUSERCRONSTIMETABLE = {"uid", "uuid", "amount", "type", "channel"}; // 每次消费的uid,uuid,金额,类型,uid渠道

    // 24.每日消费人数渠道统计表————————————————rowkey:20181016===channel
    public final static String DAYUSERCRONSNUMBERSTATBYCHANNELTABLE = "day_user_crons_number_stat_by_channel";
    public final static String[] cfsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE = {"info"};
    public final static String[] columnsOfDAYUSERCRONSNUMBERSTATBYCHANNELTABLE = {"aCronsUser", "aCronsNumber", "nCronsUser", "nCronsNumber"}; // 新增消费人/数

    // 19.每日消费人数总统计表————————————————rowkey:20181016
    public final static String DAYUSERCRONSNUMBERSTATTABLE = "day_user_number_crons_stat";
    public final static String[] cfsOfDAYUSERCRONSNUMBERSTATTABLE = {"info"};
    public final static String[] columnsOfDAYUSERCRONSNUMBERSTATTABLE = {"aCronsUser", "aCronsNumber", "nCronsUser", "nCronsNumber"}; // 新增消费人/数

    // 20.每日消费金额渠道统计表————————————————rowkey:20181016===channel
    public final static String DAYUSERCRONSAMOUNTSTATBYCHANNELTABLE = "day_user_crons_amount_stat_by_channel";
    public final static String[] cfsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE = {"info"};
    public final static String[] columnsOfDAYUSERCRONSAMOUNTSTATBYCHANNELTABLE = {"acm", "ncm"}; // 活跃消费额，新增消费额

    // 21.每日消费金额总统计表————————————————rowkey:20181016
    public final static String DAYUSERCRONSAMOUNTSTATTABLE = "day_user_crons_amount_stat";
    public final static String[] cfsOfDAYUSERCRONSAMOUNTSTATTABLE = {"info"};
    public final static String[] columnsOfDAYUSERCRONSAMOUNTSTATTABLE = {"acm", "ncm"}; // 活跃消费额，新增消费额

    /**
     * 订阅相关
     */
    // 22.每日订阅时间表——————————————rowkey:20181016121212===uid===channel
    public final static String USERORDERTIMETABLE = "user_order_time";
    public final static String[] cfsOfUSERORDERTIMETABLE = {"info"};
    public final static String[] columnsOfUSERORDERTIMETABLE = {"uid", "uuid", "bName", "cid", "cName", "channel"}; // 每次订阅的uid,uuid,书名，章节ID，uid渠道

    // 22.5）每日免费订阅表————————————————rowkey:20181016121212===uid===channel
    public final static String USERFREEORDERTIMETABLE = "user_free_order_time";
    public final static String[] cfsOfUSERFREEORDERTIMETABLE = {"info"};
    public final static String[] columnsOfUSERFREEORDERTIMETABLE = {"uid", "uuid", "bName", "cid", "cName", "channel"}; // 每次免费订阅的uid,uuid,书名，章节ID，uid渠道

    /**
     * 阅读相关
     */
    // 23.每日阅读时间表——————————————rowkey:20181016121212===uid===channel
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
