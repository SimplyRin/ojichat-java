package net.simplyrin.ojichat.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

import net.simplyrin.ojichat.pattern.OjisanEmotion;
import net.simplyrin.ojichat.pattern.Onara;
import net.simplyrin.ojichat.pattern.Tags;

/**
 * Created by SimplyRin on 2025/09/13.
 * 
 * Copyright (c) 2019 Yamada, Yasuhiro
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class Generator {

    private static final Random random = new Random();

    /**
     * 句読点挿入の設定
     */
    public static class PunctuationConfig {
        private final List<String> targetHinshis;
        private final int rate;

        public PunctuationConfig(List<String> targetHinshis, int rate) {
            this.targetHinshis = targetHinshis;
            this.rate = rate;
        }

        public List<String> getTargetHinshis() {
            return targetHinshis;
        }

        public int getRate() {
            return rate;
        }
    }

    private static final List<PunctuationConfig> pconfigs = Arrays.asList(
        new PunctuationConfig(new ArrayList<>(), 0),
        new PunctuationConfig(Arrays.asList("助動詞"), 30),
        new PunctuationConfig(Arrays.asList("助動詞", "助詞"), 60),
        new PunctuationConfig(Arrays.asList("助動詞", "助詞"), 100)
    );

    /**
     * おじさんの文言を生成
     */
    public static String start(Config config) throws Exception {
        // メッセージを選択する
        String selectedMessage = selectMessage();

        // メッセージに含まれるタグを変換
        selectedMessage = Tags.convertTags(selectedMessage, config.getTargetName(), config.getEmojiNum());

        int level = config.getPunctuationLevel();
        if (level < 0 || level > 3) {
            throw new IllegalArgumentException("句読点挿入頻度レベルが不正です: " + level);
        }
        // 句読点レベルに応じて、おじさんのように文中に句読点を適切に挿入する
        String result = insertPunctuations(selectedMessage, pconfigs.get(level));

        return result;
    }

    private static String selectMessage() {
        // アルゴリズム (ONARA) を無作為に選定
        List<OjisanEmotion> selectedOnara = Onara.ONARA.get(random.nextInt(Onara.ONARA.size()));

        // 重複した表現を避けるためのブラックリストを感情ごとに用意
        Map<OjisanEmotion, Set<Integer>> blacklist = new HashMap<>();
        for (OjisanEmotion emotion : OjisanEmotion.values()) {
            blacklist.put(emotion, new HashSet<>());
        }

        // アルゴリズム内で表現されたそれぞれの感情に対応した文言を選定
        StringBuilder selectedMessage = new StringBuilder();
        for (OjisanEmotion s : selectedOnara) {
            List<String> messages = Onara.ONARA_MESSAGES.get(s.ordinal());
            while (true) {
                int index = random.nextInt(messages.size());
                if (!blacklist.get(s).contains(index)) {
                    blacklist.get(s).add(index);
                    selectedMessage.append(messages.get(index));
                    break;
                }
                // 既にすべての表現を使い切っていたら諦める
                if (blacklist.get(s).size() >= messages.size()) {
                    blacklist.get(s).add(index);
                    selectedMessage.append(messages.get(index));
                    break;
                }
            }
            // 挨拶以外の感情に関しては語尾を最大2文字までカタカナに変換するおじさんカタカナ活用を適用する
            if (s != OjisanEmotion.GREETING) {
                String msg = selectedMessage.toString();
                selectedMessage.setLength(0);
                selectedMessage.append(katakanaKatsuyou(msg, random.nextInt(3)));
            }
        }

        return selectedMessage.toString();
    }

    // カタカナ活用を適用する
    private static String katakanaKatsuyou(String message, int number) {
        if (number < 1) {
            return message;
        }
        // ひらがなの末尾をカタカナに変換 (簡易版)
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(.+)((?:[\u3041-\u3096\u309D\u309E]){" + number + "})(.*)");
        java.util.regex.Matcher matcher = pattern.matcher(message);
        if (matcher.matches()) {
            String prefix = matcher.group(1);
            String hiragana = matcher.group(2);
            String suffix = matcher.group(3);
            return prefix + hiraganaToKatakana(hiragana) + suffix;
        }
        return message;
    }

    private static String hiraganaToKatakana(String hiragana) {
        StringBuilder sb = new StringBuilder();
        for (char c : hiragana.toCharArray()) {
            if (c >= '\u3041' && c <= '\u3096') {
                sb.append((char) (c + 0x60));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    // 句読点レベルに応じ、助詞、助動詞の後に句読点を挿入する
    private static String insertPunctuations(String message, PunctuationConfig config) {
        if (config.getRate() == 0) {
            return message;
        }
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(message);
        StringBuilder result = new StringBuilder();
        for (Token token : tokens) {
            if (token.getPartOfSpeechLevel1().equals("記号")) {
                continue;
            }
            boolean hinshiFlag = false;
            for (String hinshi : config.getTargetHinshis()) {
                if (hinshi.equals(token.getPartOfSpeechLevel1())) {
                    hinshiFlag = true;
                    break;
                }
            }
            if (hinshiFlag && random.nextInt(100) <= config.getRate()) {
                result.append(token.getSurface()).append("、");
            } else {
                result.append(token.getSurface());
            }
        }
        return result.toString();
    }

}
