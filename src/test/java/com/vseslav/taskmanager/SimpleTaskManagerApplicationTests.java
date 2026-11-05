package com.vseslav.taskmanager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Needs running postgres, redis and kafka")
@SpringBootTest
class SimpleTaskManagerApplicationTests {

    @Test
    void contextLoads() {
    }
}
