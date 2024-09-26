package konkuk.forcutbook.api.clova;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClovaStudioApiTest {

    @Autowired
    ClovaStudioApi clovaStudioApi;

    @Test
    void execute() {
        clovaStudioApi.execute("woman holding a camera up to her face while standing on a sidewalk");
    }
}