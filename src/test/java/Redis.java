import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.params.SetParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.Date;
import java.util.List;

/**
 * Project name(项目名称)：redis_jedis_key_command
 * Package(包名): PACKAGE_NAME
 * Class(类名): Redis
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/17
 * Time(创建时间)： 12:42
 * Version(版本): 1.0
 * Description(描述)： 键（Key）命令
 * <p>
 * <p>
 * <p>
 * <p>
 * 命令	        说明
 * DEL	        若键存在的情况下，该命令用于删除键
 * DUMP	        用于序列化给定 key ，并返回被序列化的值
 * EXISTS	    用于检查键是否存在，若存在则返回 1，否则返回 0
 * EXPIRE	    设置 key 的过期时间，以秒为单位
 * EXPIREAT	    该命令与 EXPIRE 相似，用于为 key 设置过期时间，不同在于，它的时间参数值采用的是时间戳格式。
 * KEYS	        此命令用于查找与指定 pattern 匹配的 key
 * MOVE	        将当前数据库中的 key 移动至指定的数据库中（默认存储为 0 库，可选 1-15中的任意库）
 * PERSIST	    该命令用于删除 key 的过期时间，然后 key 将一直存在，不会过期
 * PEXPIRE	    设置 key 的过期，以毫秒为单位
 * RANDOMKEY    从当前数据库中随机返回一个 key
 * RENAME	    修改 key 的名称
 * SCAN	        基于游标的迭代器，用于迭代数据库中存在的所有键，cursor 指的是迭代游标
 * TTL	        用于检查 key 还剩多长时间过期，以秒为单位
 * TYPE	        该命令用于获取 value 的数据类型。
 */


public class Redis
{
    /**
     * The Jedis.
     */
    Jedis jedis;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp()
    {
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("123456");
        System.out.println("打开");
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown()
    {
        System.out.println("关闭");
        jedis.close();
    }

    /**
     * Set.
     */
    @Test
    void set()
    {
        System.out.println(jedis.set("key1", "你好"));
        System.out.println(jedis.get("key1"));
    }

    /**
     * Delete.
     */
    @Test
    void delete()
    {
        //不存在
        System.out.println(jedis.del("key2"));
        //存在
        System.out.println(jedis.del("key1"));
    }

    /**
     * Dump.
     */
    @Test
    void dump()
    {
        byte[] key1s = jedis.dump("key1");
        for (byte key1 : key1s)
        {
            System.out.print(key1 + " ");
        }
        System.out.println();

        //不存在
        key1s = jedis.dump("key2");
        if (key1s == null)
        {
            System.out.println("空");
            return;
        }
        for (byte key1 : key1s)
        {
            System.out.print(key1 + " ");
        }
        System.out.println();
    }


    /**
     * Exists.
     */
    @Test
    void exists()
    {
        boolean key1 = jedis.exists("key1");
        System.out.println(key1);
        System.out.println(jedis.exists("key2"));
    }

    /**
     * Expire.
     */
    @Test
    void expire()
    {
        System.out.println(jedis.set("key3", "hello"));
        System.out.println(jedis.expire("key3", 100));
    }

    /**
     * Expireat.
     */
    @Test
    void expireat()
    {
        System.out.println(new Date().getTime());
        System.out.println(jedis.set("key4", "hello"));
        System.out.println(jedis.expireAt("key4", 9520171953299L));
    }

    /**
     * Keys.
     */
    @Test
    void keys()
    {
        System.out.println(jedis.keys("*"));
        System.out.println(jedis.keys("ke*"));
        System.out.println(jedis.keys("k*1"));
    }

    /**
     * Move.
     */
    @Test
    void move()
    {
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.move("key1", 1));
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.select(1));
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.select(0));
    }

    /**
     * Persist.
     */
    @Test
    void persist()
    {
        System.out.println(jedis.set("key2", "hello"));
        System.out.println(jedis.expire("key2", 120));
        System.out.println(jedis.persist("key2"));
    }

    /**
     * Pexpire.
     */
    @Test
    void pexpire()
    {
        System.out.println(jedis.pexpire("key2", 30000));
    }

    /**
     * Randomkey.
     */
    @Test
    void randomkey()
    {
        System.out.println(jedis.randomKey());
    }

    /**
     * Rename.
     */
    @Test
    void rename()
    {
        System.out.println(jedis.rename("1", "key"));
    }

    /**
     * Scan.
     */
    @Test
    void scan()
    {
        /*
        Redis SCAN 命令的基本语法如下：
        SCAN cursor [MATCH pattern] [COUNT count]
        参数说明：
        cursor：游标，游标起始值一般为 0。
        pattern： 指定匹配模式。
        count：指定从数据库中返回多少个 key，默认为 10 。
        */

        {
            ScanResult<String> result = jedis.scan("0");
            List<String> list = result.getResult();
            System.out.println(list);
        }

        {
            ScanResult<String> result = jedis.scan("1");
            List<String> list = result.getResult();
            System.out.println(list);
        }

        {
            ScanResult<String> result = jedis.scan("0", new ScanParams().match("key*"));
            List<String> list = result.getResult();
            System.out.println(list);
        }

    }

    /**
     * Ttl.
     */
    @Test
    void TTL()
    {
        System.out.println(jedis.ttl("key1"));
        //System.out.println(jedis.set("key5", "hello"));
        //System.out.println(jedis.expire("key5", 100));
        System.out.println(jedis.ttl("key5"));
    }

    /**
     * Type.
     */
    @Test
    void type()
    {
        System.out.println(jedis.type("key1"));
    }

}
