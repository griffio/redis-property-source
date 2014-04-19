package griffio;

import griffio.redis.RedisOperations;
import org.springframework.core.env.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/griffio/redis-property-source-context.xml");
        final RedisOperations redisOps = applicationContext.getBean(RedisOperations.class);
        final PropertySource propertySource = applicationContext.getBean(PropertySource.class);

        for (int i = 0; i < 1000; i++) {
            Assert.isTrue(redisOps.set("foo" + i, "bar").isOk());
            Assert.notNull(propertySource.getProperty("foo" + i));
        }

    }

}
