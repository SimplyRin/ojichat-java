package net.simplyrin.ojichat.generator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class GeneratorTest {

    @Test
    void testStart() throws Exception {
        OjichatConfig config = new OjichatConfig();
        config.setTargetName("テスト");
        config.setEmojiNum(4);
        config.setPunctuationLevel(0);

        String result = OjichatGenerator.start(config);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("テスト"));
    }

    @Test
    void testStartWithEmptyName() throws Exception {
        OjichatConfig config = new OjichatConfig();
        config.setTargetName("");
        config.setEmojiNum(4);
        config.setPunctuationLevel(0);

        String result = OjichatGenerator.start(config);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testStartWithPunctuation() throws Exception {
        OjichatConfig config = new OjichatConfig();
        config.setTargetName("テスト");
        config.setEmojiNum(4);
        config.setPunctuationLevel(3);

        String result = OjichatGenerator.start(config);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testInvalidPunctuationLevel() {
        OjichatConfig config = new OjichatConfig();
        config.setTargetName("テスト");
        config.setEmojiNum(4);
        config.setPunctuationLevel(5); // invalid

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            OjichatGenerator.start(config);
        });
        assertTrue(exception.getMessage().contains("句読点挿入頻度レベルが不正"));
    }

}
