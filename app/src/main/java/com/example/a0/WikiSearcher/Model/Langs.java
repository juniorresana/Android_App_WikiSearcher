package com.example.a0.WikiSearcher.Model;

import java.util.Arrays;

public class Langs {
    public static String langs[] = {
            "auto",
            "en",
            "fr",
            "es",
            "de",
            "no",
            "af",
            "als",
            "am",
            "an",
            "ang",
            "ar",
            "arc",
            "ast",
            "ay",
            "az",
            "azb",
            "ba",
            "bar",
            "bat-smg",
            "be",
            "be-x-old",
            "bg",
            "bh",
            "bjn",
            "bn",
            "bo",
            "br",
            "bs",
            "bxr",
            "ca",
            "cdo",
            "chr",
            "chy",
            "ckb",
            "co",
            "cs",
            "cy",
            "da",
            "de",
            "diq",
            "dty",
            "el",
            "eml",
            "eo",
            "es",
            "et",
            "eu",
            "ext",
            "fa",
            "fi",
            "fiu-vro",
            "fr",
            "frr",
            "fur",
            "fy",
            "ga",
            "gd",
            "gl",
            "gn",
            "gu",
            "gv",
            "hak",
            "he",
            "hi",
            "hr",
            "ht",
            "hu",
            "hy",
            "ia",
            "id",
            "ilo",
            "io",
            "is",
            "it",
            "iu",
            "ja",
            "jam",
            "jbo",
            "jv",
            "ka",
            "kbp",
            "kk",
            "kn",
            "ko",
            "krc",
            "ku",
            "ky",
            "la",
            "lb",
            "lfn",
            "li",
            "lij",
            "lmo",
            "ln",
            "lt",
            "lv",
            "mai",
            "mg",
            "mk",
            "ml",
            "mn",
            "mr",
            "ms",
            "myv",
            "nah",
            "nap",
            "nds",
            "ne",
            "new",
            "nl",
            "nn",
            "no",
            "nrm",
            "nso",
            "oc",
            "or",
            "pa",
            "pap",
            "pcd",
            "pdc",
            "pl",
            "pnb",
            "ps",
            "pt",
            "qu",
            "ro",
            "roa-rup",
            "ru",
            "rue",
            "sah",
            "sat",
            "scn",
            "sco",
            "sd",
            "sh",
            "simple",
            "sk",
            "sl",
            "so",
            "sq",
            "sr",
            "stq",
            "su",
            "sv",
            "sw",
            "ta",
            "te",
            "tg",
            "th",
            "tl",
            "tr",
            "tt",
            "tyv",
            "uk",
            "ur",
            "uz",
            "vec",
            "vep",
            "vi",
            "wa",
            "war",
            "wuu",
            "xh",
            "xmf",
            "yi",
            "za",
            "zh",
            "zh-min-nan",
            "zh-yue"
    };

    public static String intellectualLang(String word, String[] comparatList){
        char[] c = word.toCharArray();
        String l = "";
        for (int i = 0; i < c.length; i++) {
            l += c[i];
            if (Arrays.asList(comparatList).contains(l)){
                return l;
            }
        }
        return null;
    }
}
