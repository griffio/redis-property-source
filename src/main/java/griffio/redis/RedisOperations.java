package griffio.redis;

import java.util.Map;

public interface RedisOperations {

    String get(String key);

    RedisResponse set(String key, String value);

    RedisResponse mset(Map<String, String> map);
}
