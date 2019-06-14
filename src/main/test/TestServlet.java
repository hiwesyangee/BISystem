package test;

import biquery.cronsquery.Crons;
import biquery.cronsquery.CronsByChannel;
import biquery.cronsquery.CronsInterface;
import biquery.cronsquery.CronsTotalData;
import biquery.cronsrankquery.CronsRank;
import biquery.cronsrankquery.CronsRankByChannel;
import biquery.cronsrankquery.CronsRankInterface;
import biquery.devicehourquery.DeviceHour;
import biquery.devicehourquery.DeviceHourByChannel;
import biquery.devicehourquery.DeviceHourInterface;
import biquery.deviceliucun.DeviceLiucun;
import biquery.deviceliucun.DeviceLiucunByChannel;
import biquery.deviceliucun.DeviceLiucunInterface;
import biquery.devicequery.*;
import biquery.orderrankquery.OrderRank;
import biquery.orderrankquery.OrderRankByChannel;
import biquery.orderrankquery.OrderRankInterface;
import biquery.payquery.Pay;
import biquery.payquery.PayByChannel;
import biquery.payquery.PayInterface;
import biquery.payquery.PayTotalData;
import biquery.payrankquery.PayRank;
import biquery.payrankquery.PayRankByChannel;
import biquery.payrankquery.PayRankInterface;
import biquery.readrankquery.ReadRank;
import biquery.readrankquery.ReadRankByChannel;
import biquery.readrankquery.ReadRankInterface;
import biquery.userhourquery.UserHour;
import biquery.userhourquery.UserHourByChannel;
import biquery.userhourquery.UserHourInterface;
import biquery.userliucun.UserLiucun;
import biquery.userliucun.UserLiucunByChannel;
import biquery.userliucun.UserLiucunInterface;
import biquery.userquery.User;
import biquery.userquery.UserByChannel;
import biquery.userquery.UserInterface;
import biquery.userquery.UserTotalData;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 对结果数据进行封装
 */
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
//        String line;
//        StringBuilder sb = new StringBuilder();
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//        String reqBody = sb.toString();
//        String[] arr = reqBody.split("\"");
//
//        // 获取前端参数
//        String type = arr[3];   // 类型
//        String time1 = arr[7];  // 时间1
//        String time2 = arr[11]; // 时间2
//        String[] typeArr = type.split("-");
//
//        String startTime = time1.replaceAll("-", "");
//        String stopTime = time2.replaceAll("-", "");
//
//        List<Object> list = new ArrayList<>(); // 天json字符串
//        List<Object> monthList = new ArrayList<>(); // 月json字符串
//
//        /** 12.19新修改的设备、用户、每小时在线对外接口 */
//        /** ⬇⬇⬇⬇⬇⬇⬇01.11新修改的设备、用户、每小时在线对外接口⬇⬇⬇⬇⬇⬇⬇ */
//        if (typeArr[0].equals("device")) { // 携带渠道channel     device-xiaomi
//            if (startTime.equals(stopTime)) { // 查询一天设备相关数据
//                List<DeviceByChannel> inList = DeviceInterface.getDayDevice(startTime, typeArr[1]);
//                list.addAll(inList);
//                List<DeviceTotalData> deviceTotalDataByChannel = DeviceInterface.getTotalDataByChannel(inList);
//                monthList.addAll(deviceTotalDataByChannel);
//            } else { // 查询多天设备相关数据
//                List<DeviceByChannel> inList = DeviceInterface.getManyDayDevice(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//                List<DeviceTotalData> deviceTotalDataByChannel = DeviceInterface.getTotalDataByChannel(inList);
//                monthList.addAll(deviceTotalDataByChannel);
//            }
//        } else if (type.equals("deviceAll")) {
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<Device> inList = DeviceInterface.getDayDevice(startTime);
//                list.addAll(inList);
//                List<DeviceTotalData> deviceTotalData = DeviceInterface.getTotalData(inList);
//                monthList.addAll(deviceTotalData);
//            } else { // 查询多天用户每小时在线情况
//                List<Device> inList = DeviceInterface.getManyDayDevice(startTime, stopTime);
//                list.addAll(inList);
//                List<DeviceTotalData> deviceTotalData = DeviceInterface.getTotalData(inList);
//                monthList.addAll(deviceTotalData);
//            }
//        } else if (typeArr[0].equals("user")) { // 携带渠道channel
//            if (startTime.equals(stopTime)) { // 查询一天用户相关数据
//                List<UserByChannel> inList = UserInterface.getDayUser(startTime, typeArr[1]);
//                list.addAll(inList);
//                List<UserTotalData> userTotalDataByChannel = UserInterface.getTotalDataByChannel(inList);
//                monthList.addAll(userTotalDataByChannel);
//            } else { // 查询多天用户相关数据
//                List<UserByChannel> inList = UserInterface.getManyDayUser(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//                List<UserTotalData> userTotalDataByChannel = UserInterface.getTotalDataByChannel(inList);
//                monthList.addAll(userTotalDataByChannel);
//            }
//        } else if (type.equals("userAll")) {
//            if (startTime.equals(stopTime)) { // 查询一天用户相关数据
//                List<User> inList = UserInterface.getDayUser(startTime);
//                list.addAll(inList);
//                List<UserTotalData> userTotalData = UserInterface.getTotalData(inList);
//                monthList.addAll(userTotalData);
//            } else { // 查询多天用户相关数据
//                List<User> inList = UserInterface.getManyDayUser(startTime, stopTime);
//                list.addAll(inList);
//                List<UserTotalData> userTotalData = UserInterface.getTotalData(inList);
//                monthList.addAll(userTotalData);
//            }
//        } else if (typeArr[0].equals("userHour")) { // 携带渠道channel
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<UserHourByChannel> inList = UserHourInterface.getDayHourUser(startTime, typeArr[1]);
//                list.addAll(inList);
//            } else { // 查询多天用户每小时在线情况
//                List<UserHourByChannel> inList = UserHourInterface.getManyDayHourUser(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//            }
//        } else if (type.equals("userHourAll")) {
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<UserHour> inList = UserHourInterface.getDayHourUser(startTime);
//                list.addAll(inList);
//            } else { // 查询多天用户每小时在线情况
//                List<UserHour> inList = UserHourInterface.getManyDayHourUser(startTime, stopTime);
//                list.addAll(inList);
//            }
//        } else if (typeArr[0].equals("deviceHour")) { // 携带渠道channel
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<DeviceHourByChannel> inList = DeviceHourInterface.getDayHourDevice(startTime, typeArr[1]);
//                list.addAll(inList);
//            } else { // 查询多天用户每小时在线情况
//                List<DeviceHourByChannel> inList = DeviceHourInterface.getManyDayHourDevice(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//            }
//        } else if (type.equals("deviceHourAll")) {
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<DeviceHour> inList = DeviceHourInterface.getDayHourDevice(startTime);
//                list.addAll(inList);
//            } else { // 查询多天用户每小时在线情况
//                List<DeviceHour> inList = DeviceHourInterface.getManyDayHourDevice(startTime, stopTime);
//                list.addAll(inList);
//            }
//            // 全部完毕，开始检查
//            /** ⬆⬆⬆⬆⬆⬆⬆01.11新修改的设备、用户、每小时在线对外接口⬆⬆⬆⬆⬆⬆⬆ */
//
//
//            /** 12.24新修改的充值、消费对外接口 */
//            /** 01.15新修改的充值消费、充值消费排行、订阅阅读排行接口 */
//        } else if (typeArr[0].equals("pay")) { // 携带渠道channel
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<PayByChannel> inList = PayInterface.getDayPay(startTime, typeArr[1]);
//                list.addAll(inList);
//                List<PayTotalData> payTotalDataByChannel = PayInterface.getTotalDataByChannel(inList);
//                monthList.addAll(payTotalDataByChannel);
//            } else { // 查询多天用户每小时在线情况
//                List<PayByChannel> inList = PayInterface.getManyDayPay(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//                List<PayTotalData> payTotalDataByChannel = PayInterface.getTotalDataByChannel(inList);
//                monthList.addAll(payTotalDataByChannel);
//            }
//        } else if (type.equals("payAll")) {
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<Pay> inList = PayInterface.getDayPay(startTime);
//                list.addAll(inList);
//                List<PayTotalData> payTotalData = PayInterface.getTotalData(inList);
//                monthList.addAll(payTotalData);
//            } else { // 查询多天用户每小时在线情况
//                List<Pay> inList = PayInterface.getManyDayPay(startTime, stopTime);
//                list.addAll(inList);
//                List<PayTotalData> payTotalData = PayInterface.getTotalData(inList);
//                monthList.addAll(payTotalData);
//            }
//        } else if (typeArr[0].equals("crons")) { // 携带渠道channel
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<CronsByChannel> inList = CronsInterface.getDayCrons(startTime, typeArr[1]);
//                list.addAll(inList);
//                List<CronsTotalData> cronsTotalDataByChannel = CronsInterface.getTotalDataByChannel(inList);
//                monthList.addAll(cronsTotalDataByChannel);
//            } else { // 查询多天用户每小时在线情况
//                List<CronsByChannel> inList = CronsInterface.getManyDayCrons(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//                List<CronsTotalData> cronsTotalDataByChannel = CronsInterface.getTotalDataByChannel(inList);
//                monthList.addAll(cronsTotalDataByChannel);
//            }
//        } else if (type.equals("cronsAll")) {
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<Crons> inList = CronsInterface.getDayCrons(startTime);
//                list.addAll(inList);
//                List<CronsTotalData> cronsTotalData = CronsInterface.getTotalData(inList);
//                monthList.addAll(cronsTotalData);
//            } else { // 查询多天用户每小时在线情况
//                List<Crons> inList = CronsInterface.getManyDayCrons(startTime, stopTime);
//                list.addAll(inList);
//                List<CronsTotalData> cronsTotalData = CronsInterface.getTotalData(inList);
//                monthList.addAll(cronsTotalData);
//            }
//        } else if (typeArr[0].equals("payRank")) { // 携带渠道channel
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<PayRankByChannel> inList = PayRankInterface.getDayPayRank(startTime, typeArr[1]);
//                list.addAll(inList);
//            } else { // 查询多天用户每小时在线情况
//                List<PayRankByChannel> inList = PayRankInterface.getManyDayPayRank(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//            }
//        } else if (type.equals("payRankAll")) { /** 改 */
//            if (startTime.equals(stopTime)) {  // 查询一天充值排行
//                List<PayRank> inList = PayRankInterface.getDayPayRank(startTime);
//                list.addAll(inList);
//            } else {  // 查询多天充值排行
//                List<PayRank> inList = PayRankInterface.getManyDayPayRank(startTime, stopTime);
//                list.addAll(inList);
//            }
//        } else if (typeArr[0].equals("cronsRank")) { // 携带渠道channel
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<CronsRankByChannel> inList = CronsRankInterface.getDayCronsRank(startTime, typeArr[1]);
//                list.addAll(inList);
//            } else { // 查询多天用户每小时在线情况
//                List<CronsRankByChannel> inList = CronsRankInterface.getManyDayCronsRank(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//            }
//        } else if (type.equals("cronsRankAll")) { /** 改 */
//            if (startTime.equals(stopTime)) {  // 查询一天充值排行
//                List<CronsRank> inList = CronsRankInterface.getDayCronsRank(startTime);
//                list.addAll(inList);
//            } else {  // 查询多天充值排行
//                List<CronsRank> inList = CronsRankInterface.getManyDayCronsRank(startTime, stopTime);
//                list.addAll(inList);
//            }
//        } else if (typeArr[0].equals("orderRank")) { // 携带渠道channel
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<OrderRankByChannel> inList = OrderRankInterface.getDayOrderRank(startTime, typeArr[1]);
//                list.addAll(inList);
//            } else { // 查询多天用户每小时在线情况
//                List<OrderRankByChannel> inList = OrderRankInterface.getManyDayOrderRank(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//            }
//        } else if (type.equals("orderRankAll")) { /** 改 */
//            if (startTime.equals(stopTime)) {  // 查询一天充值排行
//                List<OrderRank> inList = OrderRankInterface.getDayOrderRank(startTime);
//                list.addAll(inList);
//            } else {  // 查询多天充值排行
//                List<OrderRank> inList = OrderRankInterface.getManyDayOrderRank(startTime, stopTime);
//                list.addAll(inList);
//            }
//        } else if (typeArr[0].equals("readRank")) { // 携带渠道channel
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<ReadRankByChannel> inList = ReadRankInterface.getDayReadRank(startTime, typeArr[1]);
//                list.addAll(inList);
//            } else { // 查询多天用户每小时在线情况
//                List<ReadRankByChannel> inList = ReadRankInterface.getManyDayReadRank(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//            }
//        } else if (type.equals("readRankAll")) { /** 改 */
//            if (startTime.equals(stopTime)) {  // 查询一天充值排行
//                List<ReadRank> inList = ReadRankInterface.getDayReadRank(startTime);
//                list.addAll(inList);
//            } else {  // 查询多天充值排行
//                List<ReadRank> inList = ReadRankInterface.getManyDayReadRank(startTime, stopTime);
//                list.addAll(inList);
//            }
//        } else if (typeArr[0].equals("rdevice")) { // 携带渠道channel     device-xiaomi
//            if (startTime.equals(stopTime)) { // 查询一天设备相关数据
//                List<DeviceByChannel> inList = RealDeviceInterface.getDayDevice(startTime, typeArr[1]);
//                list.addAll(inList);
//                List<DeviceTotalData> deviceTotalDataByChannel = DeviceInterface.getTotalDataByChannel(inList);
//                monthList.addAll(deviceTotalDataByChannel);
//            } else { // 查询多天设备相关数据
//                List<DeviceByChannel> inList = RealDeviceInterface.getManyDayDevice(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//                List<DeviceTotalData> deviceTotalDataByChannel = DeviceInterface.getTotalDataByChannel(inList);
//                monthList.addAll(deviceTotalDataByChannel);
//            }
//        } else if (type.equals("rdeviceAll")) {
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<Device> inList = RealDeviceInterface.getDayDevice(startTime);
//                list.addAll(inList);
//                List<DeviceTotalData> deviceTotalData = DeviceInterface.getTotalData(inList);
//                monthList.addAll(deviceTotalData);
//            } else { // 查询多天用户每小时在线情况
//                List<Device> inList = RealDeviceInterface.getManyDayDevice(startTime, stopTime);
//                list.addAll(inList);
//                List<DeviceTotalData> deviceTotalData = DeviceInterface.getTotalData(inList);
//                monthList.addAll(deviceTotalData);
//            }
//        }
//        /** 02.01新增接口，week2A和week2B查询两周留存 **/
//        else if (typeArr[0].equals("deviceWeek2")) { // 携带渠道channel     deviceWeek2-xiaomi
//            if (startTime.equals(stopTime)) { // 查询设备双周留存相关数据
//                List<DeviceLiucunByChannel> inList = DeviceLiucunInterface.getDayDeviceLiucun(startTime, typeArr[1]);
//                list.addAll(inList);
//            } else { // 查询多天设备相关数据
//                List<DeviceLiucunByChannel> inList = DeviceLiucunInterface.getManyDayDeviceLiucun(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//            }
//        } else if (type.equals("deviceWeek2All")) {
//            if (startTime.equals(stopTime)) { // 查询设备双周留存相关数据
//                List<DeviceLiucun> inList = DeviceLiucunInterface.getDayDeviceLiucun(startTime);
//                list.addAll(inList);
//            } else { // 查询设备双周留存相关数据
//                List<DeviceLiucun> inList = DeviceLiucunInterface.getManyDayDeviceLiucun(startTime, stopTime);
//                list.addAll(inList);
//            }
//        }
//        if (typeArr[0].equals("userWeek2")) { // 携带渠道channel     UserWeek2-xiaomi
//            if (startTime.equals(stopTime)) { // 查询用户双周留存相关数据
//                List<UserLiucunByChannel> inList = UserLiucunInterface.getDayUserLiucun(startTime, typeArr[1]);
//                list.addAll(inList);
//            } else { // 查询多天用户相关数据
//                List<UserLiucunByChannel> inList = UserLiucunInterface.getManyDayUserLiucun(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//            }
//        } else if (type.equals("userWeek2All")) {
//            if (startTime.equals(stopTime)) { // 查询用户双周留存相关数据
//                List<UserLiucun> inList = UserLiucunInterface.getDayUserLiucun(startTime);
//                list.addAll(inList);
//            } else { // 查询用户双周留存相关数据
//                List<UserLiucun> inList = UserLiucunInterface.getManyDayUserLiucun(startTime, stopTime);
//                list.addAll(inList);
//            }
//        }
//
////        /** 12.26新增接口，自定义获取充值消费数据 */
////        else if (typeArr[0].equals("payCustom")) { // 携带渠道channel
////            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
////                List<PayCustomByChannel> inList = PayCustomInterface.getDayPayCustom(startTime, typeArr[1]);
////                list.addAll(inList);
////            } else { // 查询多天用户每小时在线情况
////                List<PayCustomByChannel> inList = PayCustomInterface.getManyDayPayCustom(startTime, stopTime, typeArr[1]);
////                list.addAll(inList);
////            }
////        } else if (type.equals("payCustomAll")) {
////            if (startTime.equals(stopTime)) {  // 查询一天充值排行
////                List<PayCustom> inList = PayCustomInterface.getDayPayCustom(startTime);
////                list.addAll(inList);
////            } else {  // 查询多天充值排行
////                List<PayCustom> inList = PayCustomInterface.getManyDayPayCustom(startTime, stopTime);
////                list.addAll(inList);
////            }
////        } else if (typeArr[0].equals("cronsCustom")) { // 携带渠道channel
////            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
////                List<CronsCustomByChannel> inList = CronsCustomInterface.getDayCronsCustom(startTime, typeArr[1]);
////                list.addAll(inList);
////            } else { // 查询多天用户每小时在线情况
////                List<CronsCustomByChannel> inList = CronsCustomInterface.getManyDayCronsCustom(startTime, stopTime, typeArr[1]);
////                list.addAll(inList);
////            }
////        } else if (type.equals("cronsCustomAll")) { /** 改 */
////            if (startTime.equals(stopTime)) {  // 查询一天充值排行
////                List<CronsCustom> inList = CronsCustomInterface.getDayCronsCustom(startTime);
////                list.addAll(inList);
////            } else {  // 查询多天充值排行
////                List<CronsCustom> inList = CronsCustomInterface.getManyDayCronsCustom(startTime, stopTime);
////                list.addAll(inList);
////            }
////        }
//
//        /** 01.04新增接口：免费订阅相关 */
//        else if (typeArr[0].equals("freeorderRank")) { // 携带渠道channel
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<OrderRankByChannel> inList = OrderRankInterface.getDayFreeOrderRank(startTime, typeArr[1]);
//                list.addAll(inList);
//            } else { // 查询多天用户每小时在线情况
//                List<OrderRankByChannel> inList = OrderRankInterface.getManyDayFreeOrderRank(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//            }
//        } else if (type.equals("freeorderRankAll")) { /** 改 */
//            if (startTime.equals(stopTime)) {  // 查询一天充值排行
//                List<OrderRank> inList = OrderRankInterface.getDayFreeOrderRank(startTime);
//                list.addAll(inList);
//            } else {  // 查询多天充值排行
//                List<OrderRank> inList = OrderRankInterface.getManyDayFreeOrderRank(startTime, stopTime);
//                list.addAll(inList);
//            }
//        } else if (typeArr[0].equals("normalorderRank")) { // 携带渠道channel
//            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
//                List<OrderRankByChannel> inList = OrderRankInterface.getDayNormalOrderRank(startTime, typeArr[1]);
//                list.addAll(inList);
//            } else { // 查询多天用户每小时在线情况
//                List<OrderRankByChannel> inList = OrderRankInterface.getManyDayNormalOrderRank(startTime, stopTime, typeArr[1]);
//                list.addAll(inList);
//            }
//        } else if (type.equals("normalorderRankAll")) { /** 改 */
//            if (startTime.equals(stopTime)) {  // 查询一天充值排行
//                List<OrderRank> inList = OrderRankInterface.getDayNormalOrderRank(startTime);
//                list.addAll(inList);
//            } else {  // 查询多天充值排行
//                List<OrderRank> inList = OrderRankInterface.getManyDayNormalOrderRank(startTime, stopTime);
//                list.addAll(inList);
//            }
//        }
//
////        /**
////         * 01.16添加：phony数据查询接口
////         */
////        else if (typeArr[0].equals("pdevice")) { // 携带渠道channel
////            if (startTime.equals(stopTime)) { // 查询一天用户每小时在线情况
////                List<PhonyDeviceByChannel> inList = PhonyDeviceInterface.getDayDevice(startTime, typeArr[1]);
////                list.addAll(inList);
////            } else { // 查询多天用户每小时在线情况
////                List<PhonyDeviceByChannel> inList = PhonyDeviceInterface.getManyDayDevice(startTime, stopTime, typeArr[1]);
////                list.addAll(inList);
////            }
////        } else if (type.equals("pdeviceAll")) { /** 改 */
////            if (startTime.equals(stopTime)) {  // 查询一天充值排行
////                List<PhonyDevice> inList = PhonyDeviceInterface.getDayDevice(startTime);
////                list.addAll(inList);
////            } else {  // 查询多天充值排行
////                List<PhonyDevice> inList = PhonyDeviceInterface.getManyDayDevice(startTime, stopTime);
////                list.addAll(inList);
////            }
////        }
////        if (type.equals("getChannel")) {
////            List<Channel> inList = ChannelInterface.getChannel();
////            list.addAll(inList);
////        }
//
//        ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
//        String json = mapper.writeValueAsString(list);    //将list中的对象转换为Json格式的数组
//
//        ObjectMapper mapperMonth = new ObjectMapper();    //提供java-json相互转换功能的类
//        String jsonMonth = mapperMonth.writeValueAsString(monthList);    //将list中的对象转换为Json格式的数组
//
//        String result = "{\"day\":" + json + ",\"total\":" + jsonMonth + "}";
//
//        //将json数据返回给客户端
//        response.setContentType("text/html; charset=utf-8");
//        response.getWriter().write(result);

        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        String result = "测试内容，仅供参考";

        //将json数据返回给客户端
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(result);

    }
}
