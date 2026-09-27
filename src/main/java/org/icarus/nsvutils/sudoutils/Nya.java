package org.icarus.nsvutils.sudoutils;

import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class Nya {
    private static final List<String> WORDS = List.of(
            "呜呜",
            "嘻嘻",
            "摸鱼",
            "喵喵",
            "哞哞",
            "All Perfect",
            "爆",
            "睡觉",
            "TRACK LOST",
            "吃吃吃",
            "哼哼",
            "看看腿"
    );

    private static final String MEOW = """
            　　　 　　／＞　　フ
            　　　 　　| 　_　 _ l
            　 　　 　／` ミ＿xノ
            　　 　 /　　　 　 |
            　　　 /　 ヽ　　 ﾉ
            　 　 │　　|　|　|
            　／￣|　　 |　|　|
            　| (￣ヽ＿_ヽ_)__)
            　   二つ
            """;

    private static final Random RAND = new Random();

    public static String nya() {
        int index = RAND.nextInt(WORDS.size());
        String elem = WORDS.get(index);
        return MEOW + "\n" + "你今天" + elem + "了吗?";
    }
}
