package griffio.env;

import griffio.redis.RedisOperations;
import org.springframework.core.env.PropertySource;

public class RedisPropertySource extends PropertySource<RedisOperations> {

    public RedisPropertySource(RedisOperations source) {
        super("redis", source);
    }

    @Override
    public Object getProperty(String name) {
        return getSource().get(name);
    }
}
