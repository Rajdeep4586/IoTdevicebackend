package com.IoTapp.IoTdevicebackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
class IoTdevicebackendApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Test context loaded successfully using H2 database!");
    }
}

