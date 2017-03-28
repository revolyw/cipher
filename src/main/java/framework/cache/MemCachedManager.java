package framework.cache;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import java.util.Date;
import java.util.Map;

/**
 * memcached客户端API
 * Created by Willow on 3/12/17.
 */
public class MemCachedManager {
    // create a static client as most installs only need
    // a single instance
    private static MemCachedClient mcc = new MemCachedClient();

    // set up connection pool once at class load
    static {
        // server list and weights
        String[] servers = {"127.0.0.1:11211"};
        Integer[] weights = {1};

        // grab an instance of our connection pool
        SockIOPool pool = SockIOPool.getInstance();

        // set the servers and the weights
        pool.setServers(servers);
        pool.setWeights(weights);

        // set some basic pool settings
        // 5 initial, 5 min, and 250 max conns
        // and set the max idle time for a conn
        // to 6 hours
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaxIdle(1000 * 60 * 60 * 6);

        // set the sleep for the maint thread
        // it will wake up every x seconds and
        // maintain the pool size
        pool.setMaintSleep(30);

        // set some TCP settings
        // disable nagle
        // set the read timeout to 3 secs
        // and don't set a connect timeout
        pool.setNagle(false); //Nagle algorithm
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);

        pool.setAliveCheck(true); //设置连接心跳监测开关
//        pool.setHashingAlg(SockIOPool.CONSISTENT_HASH); //一致性hash算法
//        pool.setHashingAlg( SockIOPool.NEW_COMPAT_HASH ); // use a compatible hashing algorithm

        // initialize the connection pool
        pool.initialize();

        /*Multi client Example*/
        // store primitives as strings
        // the java client serializes primitives
        //
        // note: this will not help you when it comes to
        // storing non primitives
//        mcc.setPrimitiveAsString(true);
        // don't url encode keys
        // by default the java client url encodes keys
        // to sanitize them so they will always work on the server
        // however, other clients do not do this
//        mcc.setSanitizeKeys( false );
    }

    /**
     * @param key
     * @param value
     * @return if exists the same key at cache server,return false
     * @see MemCachedClient#add(String, Object)
     */
    public static boolean add(String key, Object value) {
        return mcc.add(key, value);
    }

    /**
     * @param key
     * @param value
     * @return if exists the same key at cache server, replace it
     * @see MemCachedClient#set(String, Object)
     */
    public static boolean set(String key, Object value) {
        return mcc.set(key, value);
    }

    /**
     * @param key
     * @param value
     * @return if not exists the key , return false
     * @see MemCachedClient#replace(String, Object)
     */
    public static boolean replace(String key, Object value) {
        return mcc.replace(key, value);
    }


    /**
     * @param key
     * @return cache value
     * @see MemCachedClient#get(String)
     */
    public static Object get(String key) {
        return mcc.get(key);
    }

    /**
     * @param keys
     * @return cache values
     * @see MemCachedClient#getMulti(String[])
     */
    public static Map<String, Object> getMulti(String[] keys) {
        return mcc.getMulti(keys);
    }

    /**
     * @see MemCachedClient#prepend(String, Object)
     * @see MemCachedClient#append(String, Object)
     * @see MemCachedClient#gets(String)
     * @see MemCachedClient#cas(String, Object, Date, Integer, long)
     * @see MemCachedClient#incr(String)
     * @see MemCachedClient#decr(String)
     */
}
