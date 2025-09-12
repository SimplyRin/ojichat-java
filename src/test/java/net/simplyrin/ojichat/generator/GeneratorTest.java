package net.simplyrin.ojichat.generator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class GeneratorTest {

    @Test
    void testStart() throws Exception {
        Config config = new Config();
        config.setTargetName("テスト");
        config.setEmojiNum(4);
        config.setPunctuationLevel(0);

        String result = Generator.start(config);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("テスト"));
    }

    @Test
    void testStartWithEmptyName() throws Exception {
        Config config = new Config();
        config.setTargetName("");
        config.setEmojiNum(4);
        config.setPunctuationLevel(0);

        String result = Generator.start(config);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testStartWithPunctuation() throws Exception {
        Config config = new Config();
        config.setTargetName("テスト");
        config.setEmojiNum(4);
        config.setPunctuationLevel(3);

        String result = Generator.start(config);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testInvalidPunctuationLevel() {
        Config config = new Config();
        config.setTargetName("テスト");
        config.setEmojiNum(4);
        config.setPunctuationLevel(5); // invalid

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Generator.start(config);
        });
        assertTrue(exception.getMessage().contains("句読点挿入頻度レベルが不正"));
    }

}
