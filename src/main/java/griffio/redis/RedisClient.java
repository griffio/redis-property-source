package griffio.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
}
