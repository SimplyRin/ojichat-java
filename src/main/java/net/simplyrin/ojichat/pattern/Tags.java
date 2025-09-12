package net.simplyrin.ojichat.pattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

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
public class Tags {

    private static final Random random = new Random();

    // 文章中一種類に統一されるタグ
    private static final Map<String, List<String>> uniqTags = new HashMap<>();

    static {
        uniqTags.put("{TARGET_NAME}", new ArrayList<>()); // 動的に設定
        uniqTags.put("{FIRST_PERSON}", Arrays.asList(
            "僕", "ボク", "ﾎﾞｸ", "俺", "オレ", "ｵﾚ", "小生", "オジサン", "ｵｼﾞｻﾝ",
            "オヂサン", "ｵﾁﾞｻﾝ", "おじさん", "おぢさん", "オイラ"
        ));
        uniqTags.put("{DAY_OF_WEEK}", Arrays.asList("月", "火", "水", "木", "金", "土", "日"));
        uniqTags.put("{LOCATION}", Arrays.asList(
            "愛知", "青森", "秋田", "石川", "茨城", "岩手", "愛媛", "大分", "大阪", "岡山",
            "沖縄", "香川", "鹿児島", "神奈川", "岐阜", "京都", "熊本", "群馬", "高知", "埼玉",
            "佐賀", "滋賀", "静岡", "島根", "千葉", "東京", "徳島", "栃木", "鳥取", "富山",
            "長崎", "長野", "奈良", "新潟", "兵庫", "広島", "福井", "福岡", "福島", "北海道",
            "三重", "宮城", "宮崎", "山形", "山口", "山梨", "和歌山"
        ));
        uniqTags.put("{RESTAURANT}", Arrays.asList(
            "お寿司🍣", "イタリアン🍝", "バー🍷", "ラーメン屋さん🍜", "中華🍜",
            "ステーキハウス🥩", "フレンチ🍽️", "カクテルバー🍸", "ワインバー🍷",
            "スポーツバー🏀", "パブ🍻", "ビアガーデン🍺", "ジャズバー🎷"
        ));
        uniqTags.put("{FOOD}", Arrays.asList(
            "お惣菜", "サラダ🥗", "おにぎり🍙", "きんぴらごぼう", "ピッツァ🍕",
            "パスタ🍝", "スイーツ🍮", "ケーキ🎂", "ステーキ🥩", "お寿司🍣",
            "タコス🌮", "バーベキュー🍖", "ハンバーガー🍔", "クラブサンドイッチ🥪",
            "オムレツ🍳", "餃子🥟", "フレンチトースト🍞", "アイスクリーム🍦", "ドーナツ🍩"
        ));
        uniqTags.put("{WEATHER}", Arrays.asList(
            "曇り🌥️", "晴れ☀️", "快晴🌞", "大雨🌧️", "雨🌧️", "雪⛄️", "台風🌀"
        ));
        uniqTags.put("{NANCHATTE}", Arrays.asList(
            "ﾅﾝﾁｬｯﾃ{EMOJI_POS}", "ナンチャッテ{EMOJI_POS}", "なんちゃって{EMOJI_POS}",
            "なんてね{EMOJI_POS}", "冗談{EMOJI_POS}", "" // おじさんはたまに本気
        ));
        uniqTags.put("{HOTEL}", Arrays.asList("ホテル🏨", "ホテル🏩", "旅館"));
        uniqTags.put("{DATE}", Arrays.asList(
            "デート❤", "カラオケ🎤", "ドライブ🚗", "映画デート🎬", "遊園地デート🎢",
            "ショッピング🛍️", "パチンコ🎰", "サウナ🧖", "ゴルフ ⛳"
        ));
        uniqTags.put("{METAPHOR}", Arrays.asList("天使", "女神", "女優さん", "お姫様"));
    }

    // 文章中複数回変更&繰り返されるタグ
    private static final Map<String, List<String>> flexTags = new HashMap<>();

    static {
        flexTags.put("{EMOJI_POS}", Arrays.asList(
            "😃♥ ", "😃☀ ", "😃", "😃✋", "❗", "😄", "😆", "😚", "😘", "😚",
            "💕", "💗", "😍", "😁", "😋", "😂", "😊", "🎵", "(^_^)", "(^o^)",
            "(^з<)", "（笑）"
        ));
        flexTags.put("{EMOJI_NEG}", Arrays.asList(
            "💦", "💔", "😱", "😰", "😭", "😓", "😣", "😖", "😥", "😢",
            "😿", "😔", "🥺", "😧", "(◎ ＿◎;)", "(T_T)", "^^;", "(^_^;",
            "(・_・;", "(￣Д￣；；", "(^▽^;)", "(-_-;)"
        ));
        flexTags.put("{EMOJI_NEUT}", Arrays.asList(
            "💤", "😴", "🙂", "🤑", "✋", "😪", "🛌", "😎", "😤", "😒",
            "😙", "😏", "😳", "😌", "（￣▽￣）", "(＃￣З￣)", "(^^;;"
        ));
        flexTags.put("{EMOJI_ASK}", Arrays.asList(
            "⁉", "❓", "❗❓", "🤔", "😜⁉️", "✋❓", "（￣ー￣?）"
        ));
    }

    // 女性の名前リスト (gimei の代わり)
    private static final List<String> femaleNames = Arrays.asList(
        "あかり", "あきら", "あすか", "あやか", "あゆみ", "いずみ", "えみ", "えり", "かおり",
        "かずみ", "きよみ", "くみこ", "けいこ", "さおり", "さき", "さとみ", "しおり",
        "じゅんこ", "すみれ", "たかこ", "ちか", "つばさ", "ともこ", "なぎさ", "なつみ",
        "のぞみ", "ひかり", "ひろみ", "ふみ", "まき", "まさみ", "みき", "みゆき",
        "めぐみ", "ももこ", "ゆい", "ゆかり", "ゆみ", "りえ", "りな", "るみ"
    );

    /**
     * message内にあるタグを置換して結果を返す
     */
    public static String convertTags(String message, String targetName, int emojiNumber) {
        // TARGET_NAME の設定
        if (!targetName.isEmpty()) {
            uniqTags.put("{TARGET_NAME}", Arrays.asList(targetName + randomNameSuffix()));
        } else {
            uniqTags.put("{TARGET_NAME}", Arrays.asList(randomFirstName() + randomNameSuffix()));
        }

        // uniqTags の置換
        for (Map.Entry<String, List<String>> entry : uniqTags.entrySet()) {
            String tag = entry.getKey();
            List<String> patterns = entry.getValue();
            String content = patterns.get(random.nextInt(patterns.size()));
            message = message.replace(tag, content);
        }

        // flexTags の置換
        for (Map.Entry<String, List<String>> entry : flexTags.entrySet()) {
            String tag = entry.getKey();
            List<String> patterns = entry.getValue();
            int count = countOccurrences(message, tag);
            for (int j = 0; j < count; j++) {
                String content;
                if (emojiNumber > 0) {
                    content = combineMultiplePatterns(patterns, random.nextInt(emojiNumber) + 1);
                } else {
                    content = "。";
                }
                message = message.replaceFirst(Pattern.quote(tag), content);
            }
        }
        return message;
    }

    private static int countOccurrences(String str, String sub) {
        return str.split(Pattern.quote(sub), -1).length - 1;
    }

    private static String combineMultiplePatterns(List<String> patterns, int number) {
        StringBuilder result = new StringBuilder();
        List<String> temp = new ArrayList<>(patterns);
        Collections.shuffle(temp, random);
        for (int i = 0; i < Math.min(number, temp.size()); i++) {
            result.append(temp.get(i));
        }
        return result.toString();
    }

    private static String randomFirstName() {
        return femaleNames.get(random.nextInt(femaleNames.size()));
    }

    private static String randomNameSuffix() {
        int n = random.nextInt(100);
        if (n < 5) return ""; // たまに呼び捨て
        if (n < 20) return "チャン";
        if (n < 40) return "ﾁｬﾝ";
        return "ちゃん"; // 多くの場合
    }

}
