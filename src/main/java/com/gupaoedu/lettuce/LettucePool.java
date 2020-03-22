package com.gupaoedu.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class LettucePool {
    public static void main(String[] args) throws Exception {
        RedisClient client = RedisClient.create("redis://localhost:6379");
        // 使用连接池
        GenericObjectPool<StatefulRedisConnection<String, String>> pool = ConnectionPoolSupport
                .createGenericObjectPool(() -> client.connect(), new GenericObjectPoolConfig());

        try (StatefulRedisConnection<String, String> connection = pool.borrowObject()) {
            RedisCommands<String, String> commands = connection.sync();
            commands.set("qingshan", "2673");
            System.out.println(commands.get("qingshan"));
            commands.zadd("gupao", 100, "Java");
            commands.zadd("gupao", 80, "Python");
            System.out.println("---------------------"+commands.zcard("gupao"));
        }
        pool.close();
        client.shutdown();
    }
}
