package org.icarus.nsvutils.sudoutils;

import java.util.List;
import java.util.Random;

public class Nya {
    public static String nya(){
        List<Object> words = List.of("呜呜",
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
                "看看腿");
        String meow = """
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

        Random rand = new Random();
        int index = rand.nextInt(words.size());
        String elem = (String) words.get(index);
        return meow + "\n" + "你今天" + elem + "了吗?";
    }
}
