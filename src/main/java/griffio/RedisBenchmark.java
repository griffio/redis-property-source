package griffio;

import griffio.redis.RedisOperations;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.VerboseMode;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@State(Scope.Thread)
public class RedisBenchmark {

    private RedisOperations redisOps;
    private Random random;

    @Setup
    public void initContext() throws Exception {
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/griffio/redis-property-source-context.xml");
        redisOps = applicationContext.getBean(RedisOperations.class);
        random = new Random(1000L);
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public void populateWithSet() throws Exception {
        for (int i = 0; i < 1000; i++) {
            redisOps.set("Foo" + random.nextInt(), "Bar" + random.nextInt());
        }
    }

    @GenerateMicroBenchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public void populateWithMSet() throws Exception {
        Map<String, String> map = new HashMap<>(1000);
        for (int i = 0; i < 1000; i++) {
            map.put("mFoo" + random.nextInt(), "mBar" + random.nextInt());
        }
        redisOps.mset(map);
    }

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(getArguments(".*", 10, 500, 2));
    }

    private static String[] getArguments(String className, int runs, int runForMillis, int threads) {
        return new String[]{
                className,
                "-i", "" + runs,
                "-r", runForMillis + "ms",
                "-t", "" + threads,
                "-w", "5000ms",
                "-wi", "3",
                "-v", VerboseMode.EXTRA.toString()
        };
    }

}