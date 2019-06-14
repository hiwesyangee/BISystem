package engine_abandoned.channel;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import properties.DataProcessProperties;
import utils.JavaHBaseUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 获取目前有的渠道
 */
public class ChannelInterface {
    public static List<Channel> getChannel() {
        List<Channel> list = new ArrayList<>();

        ResultScanner scanner = JavaHBaseUtils.getScanner(DataProcessProperties.DAYUSERLOGINSTATTABLE);

        Set<String> set = new HashSet<>();
        for (Result result : scanner) {
            String s = Bytes.toString(result.getRow());
            String channel = s.split("_")[1];
            set.add(channel);
        }

        String channelList = "";
        for (String c : set) {
            channelList += c + ",";
        }
        if (channelList.length() > 1) {
            channelList = channelList.substring(0, channelList.length() - 1);
        }
        list.add(new Channel(channelList));
        return list;
    }
}
