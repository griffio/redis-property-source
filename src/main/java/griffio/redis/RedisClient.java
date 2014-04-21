package griffio.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

public class RedisClient implements RedisOperations {

    private final JedisPool jedisPool;

    public RedisClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public String get(String key) {
        Jedis resource = jedisPool.getResource();
        try {
            return resource.get(key);
        } finally {
            jedisPool.returnResource(resource);
        }
    }

    @Override
    public RedisResponse set(String key, String value) {
        Jedis resource = jedisPool.getResource();
        try {
            return new RedisResponse(resource.set(key, value));
        } finally {
            jedisPool.returnResource(resource);
        }
    }

    @Override
    public RedisResponse mset(Map<String, String> map) {
        int index = 0;
        byte[][] result = new byte[map.size() * 2][];
        for (Map.Entry<String, String> entry : map.entrySet()) {
            result[index++] = entry.getKey().getBytes();
            result[index++] = entry.getValue().getBytes();
        }
        Jedis resource = jedisPool.getResource();
        try {
            return new RedisResponse(resource.mset(result));
        } finally {
            jedisPool.returnResource(resource);
        }
    }
}
