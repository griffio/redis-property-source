package griffio.redis;

public interface RedisOperations {

    String get(String key);

    RedisResponse set(String key, String value);

}
