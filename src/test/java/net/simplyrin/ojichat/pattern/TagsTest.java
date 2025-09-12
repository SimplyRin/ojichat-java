package net.simplyrin.ojichat.pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TagsTest {

    @Test
    void testConvertTags() {
        String message = "{TARGET_NAME}、お疲れ様〜{EMOJI_POS}";
        String result = Tags.convertTags(message, "テスト", 4);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("テスト"));
        assertFalse(result.contains("{TARGET_NAME}"));
        assertFalse(result.contains("{EMOJI_POS}"));
    }

    @Test
    void testConvertTagsWithEmptyName() {
        String message = "{TARGET_NAME}、お疲れ様〜{EMOJI_POS}";
        String result = Tags.convertTags(message, "", 4);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertFalse(result.contains("{TARGET_NAME}"));
        assertFalse(result.contains("{EMOJI_POS}"));
    }

    @Test
    void testConvertTagsWithZeroEmoji() {
        String message = "{TARGET_NAME}、お疲れ様〜{EMOJI_POS}";
        String result = Tags.convertTags(message, "テスト", 0);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("テスト"));
        assertFalse(result.contains("{TARGET_NAME}"));
        assertTrue(result.contains("。"));
    }

}
