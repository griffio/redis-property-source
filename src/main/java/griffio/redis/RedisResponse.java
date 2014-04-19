package griffio.redis;

public final class RedisResponse {

    private final String status;

    public RedisResponse(String status) {
        this.status = status;
    }

    public boolean isOk() {
        return "OK".equals(this.status);
    }

}
