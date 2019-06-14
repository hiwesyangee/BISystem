package utils;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.*;

/**
 * HBase工具类Java版本
 *
 * @author Hiwes
 * @version 1.0
 * @since 2018/10/15
 */
public class JavaHBaseUtils {
    /**
     * 创建HBase表
     *
     * @param tableName 表名
     * @param cfs       列族数组
     * @return
     */
    public static boolean createTable(String tableName, String[] cfs) {
        try (HBaseAdmin admin = (HBaseAdmin) JavaHBaseConn.getHBaseConn().getAdmin()) {
            if (admin.tableExists((tableName))) {
                return false;
            }
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            Arrays.stream(cfs).forEach(cf -> {
                HColumnDescriptor columnDescriptor = new HColumnDescriptor(cf);
                columnDescriptor.setMaxVersions(1);
                tableDescriptor.addFamily(columnDescriptor);
            });
            admin.createTable(tableDescriptor);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    /**
     * 删除hbase表.
     *
     * @param tableName 表名
     * @return 是否删除成功
     */
    public static boolean deleteTable(String tableName) {
        try (HBaseAdmin admin = (HBaseAdmin) JavaHBaseConn.getHBaseConn().getAdmin()) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 插入Hbase表数据
     *
     * @param tableName 表名
     * @param rowKey    唯一标识
     * @param cfName    列族名
     * @param qualifier 列标识
     * @param data      数据
     * @return 是否插入成功
     */
    public static boolean putRow(String tableName, String rowKey, String cfName, String qualifier, String data) {
        try (Table table = JavaHBaseConn.getTable(tableName)) {
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes(cfName), Bytes.toBytes(qualifier), Bytes.toBytes(data));
            table.put(put);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    /**
     * 批量插入Hbase数据
     *
     * @param tableName 表名
     * @param puts
     * @return 是否插入成功
     */
    public static boolean putRows(String tableName, List<Put> puts) {
        try (Table table = JavaHBaseConn.getTable(tableName)) {
            table.put(puts);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    /**
     * 获取单元格数据
     *
     * @param tableName
     * @param rowKey
     * @param cf
     * @param column
     * @return
     */
    public static String getValue(String tableName, String rowKey, String cf, String column) {
        String result1 = "";
        try (Table table = JavaHBaseConn.getTable(tableName)) {
            Get g = new Get(Bytes.toBytes(rowKey));
            Result result = table.get(g);
            byte[] value = result.getValue(Bytes.toBytes(cf), Bytes.toBytes(column));
            result1 = Bytes.toString(value);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result1;
    }

    /**
     * 查询HBase单条数据
     *
     * @param tableName 表名
     * @param rowkey    唯一标识
     * @return 查询结果
     */
    public static Result getRow(String tableName, String rowkey) {
        try (Table table = JavaHBaseConn.getTable(tableName)) {
            Get get = new Get(Bytes.toBytes(rowkey));
            return table.get(get);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * 通过过滤查询数据
     *
     * @param tableName  表名
     * @param rowKey     唯一标识
     * @param filterList 过滤条件
     * @return 返回结果
     */
    public static Result getRow(String tableName, String rowKey, FilterList filterList) {
        try (Table table = JavaHBaseConn.getTable(tableName)) {
            Get get = new Get(Bytes.toBytes(rowKey));
            get.setFilter(filterList);
            return table.get(get);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * 全表扫描
     *
     * @param tableName 表名
     * @return
     */
    public static ResultScanner getScanner(String tableName) {
        try (Table table = JavaHBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.setCaching(1000);
            return table.getScanner(scan);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * 批量检索数据
     *
     * @param tableName 表名
     * @param startRow  起始标识
     * @param endRow    终止标识
     * @return ResultScanner实例
     */
    public static ResultScanner getScanner(String tableName, String startRow, String endRow) {
        try (Table table = JavaHBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRow));
            scan.setStopRow(Bytes.toBytes(endRow));
            scan.setCaching(1000);
            return table.getScanner(scan);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * 设置过滤器，批量检索数据
     *
     * @param tableName  表名
     * @param startRow   起始标识
     * @param endRow     终止标识
     * @param filterList 过滤器
     * @return ResultScanner实例
     */
    public static ResultScanner getScanner(String tableName, String startRow, String endRow, FilterList filterList) {
        try (Table table = JavaHBaseConn.getTable(tableName)) {
            Scan scan = new Scan();
            scan.setStartRow(Bytes.toBytes(startRow));
            scan.setStopRow(Bytes.toBytes(endRow));
            scan.setFilter(filterList);
            scan.setCaching(1000);
            return table.getScanner(scan);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * 删除HBase一行记录
     *
     * @param tableName 表名
     * @param rowKey    唯一标识
     * @return 是否删除成功
     */
    public static boolean deleteRow(String tableName, String rowKey) {
        try (Table table = JavaHBaseConn.getTable(tableName)) {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            table.delete(delete);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    /**
     * 删除HBase表指定列族
     *
     * @param tableName 表名
     * @param cfName    列族名
     * @return 删除是否成功
     */
    public static boolean deleteColumnFamily(String tableName, String cfName) {
        try (HBaseAdmin admin = (HBaseAdmin) JavaHBaseConn.getHBaseConn().getAdmin()) {
            admin.deleteColumn(tableName, cfName);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    /**
     * 删除HBase表指定列
     *
     * @param tableName 表名
     * @param rowKey    唯一标识
     * @param cfName    列族名
     * @param qualifier 列名
     * @return 删除是否成功
     */
    public static boolean deleteQualifier(String tableName, String rowKey, String cfName, String qualifier) {
        try (Table table = JavaHBaseConn.getTable(tableName)) {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            delete.addColumn(Bytes.toBytes(cfName), Bytes.toBytes(qualifier));
            table.delete(delete);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return true;
    }

    /**
     * 根据表名和输入条件，在数据库表中进行每日相关数据查询
     *
     * @param tableName 表名
     * @param dayData   每日具体数据
     * @return Map(每日具体时间, 每日具体数据)
     */
    public static Map<String, List<String>> query(String tableName, String dayData, String quarlifier) throws IOException {
        Map<String, List<String>> map = new HashMap<>();

        List<String> list = new ArrayList<>();
        Table table = JavaHBaseConn.getTable(tableName);
        String cf = "info";
        Scan scan = new Scan();
        Filter filter = new PrefixFilter(Bytes.toBytes(dayData));
        scan.setFilter(filter);

        ResultScanner rs = table.getScanner(scan);
        for (Result result : rs) {
            String value = Bytes.toString(result.getValue(cf.getBytes(), quarlifier.getBytes()));
            list.add(value);
        }
        map.put(dayData, list);
        return map;
    }

}
