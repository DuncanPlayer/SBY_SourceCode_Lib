package com.rykj.screw;

import com.rykj.screw.generate.Screw;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScrewApplicationTests {

    @Test
    void generate() {
        Screw screw = new Screw();
        screw.generate();
    }

}
