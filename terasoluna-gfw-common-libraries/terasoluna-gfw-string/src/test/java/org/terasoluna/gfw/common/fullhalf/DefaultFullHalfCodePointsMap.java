/*
 * Copyright (C) 2013-2017 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.terasoluna.gfw.common.fullhalf;

import java.util.concurrent.ConcurrentHashMap;

public class DefaultFullHalfCodePointsMap extends
                                       ConcurrentHashMap<String, String> {

    private static final long serialVersionUID = 1L;

    // @formatter:off
    private static final char[][][] fullHalfCodePoints = {
            { { 0xff01 }, { 0x0021 } }, // [full:！ half:!]
            { { 0x201d }, { 0x0022 } }, // [full:” half:"]
            { { 0xff03 }, { 0x0023 } }, // [full:＃ half:#]
            { { 0xff04 }, { 0x0024 } }, // [full:＄ half:$]
            { { 0xff05 }, { 0x0025 } }, // [full:％ half:%]
            { { 0xff06 }, { 0x0026 } }, // [full:＆ half:&]
            { { 0x2019 }, { 0x0027 } }, // [full:’ half:']
            { { 0xff08 }, { 0x0028 } }, // [full:（ half:(]
            { { 0xff09 }, { 0x0029 } }, // [full:） half:)]
            { { 0xff0a }, { 0x002a } }, // [full:＊ half:*]
            { { 0xff0b }, { 0x002b } }, // [full:＋ half:+]
            { { 0xff0c }, { 0x002c } }, // [full:， half:,]
            { { 0xff0d }, { 0x002d } }, // [full:－ half:-]
            { { 0xff0e }, { 0x002e } }, // [full:． half:.]
            { { 0xff0f }, { 0x002f } }, // [full:／ half:/]
            { { 0xff10 }, { 0x0030 } }, // [full:０ half:0]
            { { 0xff11 }, { 0x0031 } }, // [full:１ half:1]
            { { 0xff12 }, { 0x0032 } }, // [full:２ half:2]
            { { 0xff13 }, { 0x0033 } }, // [full:３ half:3]
            { { 0xff14 }, { 0x0034 } }, // [full:４ half:4]
            { { 0xff15 }, { 0x0035 } }, // [full:５ half:5]
            { { 0xff16 }, { 0x0036 } }, // [full:６ half:6]
            { { 0xff17 }, { 0x0037 } }, // [full:７ half:7]
            { { 0xff18 }, { 0x0038 } }, // [full:８ half:8]
            { { 0xff19 }, { 0x0039 } }, // [full:９ half:9]
            { { 0xff1a }, { 0x003a } }, // [full:： half::]
            { { 0xff1b }, { 0x003b } }, // [full:； half:;]
            { { 0xff1c }, { 0x003c } }, // [full:＜ half:<]
            { { 0xff1d }, { 0x003d } }, // [full:＝ half:=]
            { { 0xff1e }, { 0x003e } }, // [full:＞ half:>]
            { { 0xff1f }, { 0x003f } }, // [full:？ half:?]
            { { 0xff20 }, { 0x0040 } }, // [full:＠ half:@]
            { { 0xff21 }, { 0x0041 } }, // [full:Ａ half:A]
            { { 0xff22 }, { 0x0042 } }, // [full:Ｂ half:B]
            { { 0xff23 }, { 0x0043 } }, // [full:Ｃ half:C]
            { { 0xff24 }, { 0x0044 } }, // [full:Ｄ half:D]
            { { 0xff25 }, { 0x0045 } }, // [full:Ｅ half:E]
            { { 0xff26 }, { 0x0046 } }, // [full:Ｆ half:F]
            { { 0xff27 }, { 0x0047 } }, // [full:Ｇ half:G]
            { { 0xff28 }, { 0x0048 } }, // [full:Ｈ half:H]
            { { 0xff29 }, { 0x0049 } }, // [full:Ｉ half:I]
            { { 0xff2a }, { 0x004a } }, // [full:Ｊ half:J]
            { { 0xff2b }, { 0x004b } }, // [full:Ｋ half:K]
            { { 0xff2c }, { 0x004c } }, // [full:Ｌ half:L]
            { { 0xff2d }, { 0x004d } }, // [full:Ｍ half:M]
            { { 0xff2e }, { 0x004e } }, // [full:Ｎ half:N]
            { { 0xff2f }, { 0x004f } }, // [full:Ｏ half:O]
            { { 0xff30 }, { 0x0050 } }, // [full:Ｐ half:P]
            { { 0xff31 }, { 0x0051 } }, // [full:Ｑ half:Q]
            { { 0xff32 }, { 0x0052 } }, // [full:Ｒ half:R]
            { { 0xff33 }, { 0x0053 } }, // [full:Ｓ half:S]
            { { 0xff34 }, { 0x0054 } }, // [full:Ｔ half:T]
            { { 0xff35 }, { 0x0055 } }, // [full:Ｕ half:U]
            { { 0xff36 }, { 0x0056 } }, // [full:Ｖ half:V]
            { { 0xff37 }, { 0x0057 } }, // [full:Ｗ half:W]
            { { 0xff38 }, { 0x0058 } }, // [full:Ｘ half:X]
            { { 0xff39 }, { 0x0059 } }, // [full:Ｙ half:Y]
            { { 0xff3a }, { 0x005a } }, // [full:Ｚ half:Z]
            { { 0xff3b }, { 0x005b } }, // [full:［ half:[]
            { { 0xffe5 }, { 0x005c } }, // [full:￥ half:\]
            { { 0xff3d }, { 0x005d } }, // [full:］ half:]]
            { { 0xff3e }, { 0x005e } }, // [full:＾ half:^]
            { { 0xff3f }, { 0x005f } }, // [full:＿ half:_]
            { { 0xff40 }, { 0x0060 } }, // [full:｀ half:`]
            { { 0xff41 }, { 0x0061 } }, // [full:ａ half:a]
            { { 0xff42 }, { 0x0062 } }, // [full:ｂ half:b]
            { { 0xff43 }, { 0x0063 } }, // [full:ｃ half:c]
            { { 0xff44 }, { 0x0064 } }, // [full:ｄ half:d]
            { { 0xff45 }, { 0x0065 } }, // [full:ｅ half:e]
            { { 0xff46 }, { 0x0066 } }, // [full:ｆ half:f]
            { { 0xff47 }, { 0x0067 } }, // [full:ｇ half:g]
            { { 0xff48 }, { 0x0068 } }, // [full:ｈ half:h]
            { { 0xff49 }, { 0x0069 } }, // [full:ｉ half:i]
            { { 0xff4a }, { 0x006a } }, // [full:ｊ half:j]
            { { 0xff4b }, { 0x006b } }, // [full:ｋ half:k]
            { { 0xff4c }, { 0x006c } }, // [full:ｌ half:l]
            { { 0xff4d }, { 0x006d } }, // [full:ｍ half:m]
            { { 0xff4e }, { 0x006e } }, // [full:ｎ half:n]
            { { 0xff4f }, { 0x006f } }, // [full:ｏ half:o]
            { { 0xff50 }, { 0x0070 } }, // [full:ｐ half:p]
            { { 0xff51 }, { 0x0071 } }, // [full:ｑ half:q]
            { { 0xff52 }, { 0x0072 } }, // [full:ｒ half:r]
            { { 0xff53 }, { 0x0073 } }, // [full:ｓ half:s]
            { { 0xff54 }, { 0x0074 } }, // [full:ｔ half:t]
            { { 0xff55 }, { 0x0075 } }, // [full:ｕ half:u]
            { { 0xff56 }, { 0x0076 } }, // [full:ｖ half:v]
            { { 0xff57 }, { 0x0077 } }, // [full:ｗ half:w]
            { { 0xff58 }, { 0x0078 } }, // [full:ｘ half:x]
            { { 0xff59 }, { 0x0079 } }, // [full:ｙ half:y]
            { { 0xff5a }, { 0x007a } }, // [full:ｚ half:z]
            { { 0xff5b }, { 0x007b } }, // [full:｛ half:{]
            { { 0xff5c }, { 0x007c } }, // [full:｜ half:|]
            { { 0xff5d }, { 0x007d } }, // [full:｝ half:}]
            { { 0xff5e }, { 0x007e } }, // [full:\uff5e half:~]
            { { 0x3002 }, { 0xff61 } }, // [full:。 half:｡]
            { { 0x300c }, { 0xff62 } }, // [full:「 half:｢]
            { { 0x300d }, { 0xff63 } }, // [full:」 half:｣]
            { { 0x3001 }, { 0xff64 } }, // [full:、 half:､]
            { { 0x30fb }, { 0xff65 } }, // [full:・ half:･]
            { { 0x30a1 }, { 0xff67 } }, // [full:ァ half:ｧ]
            { { 0x30a3 }, { 0xff68 } }, // [full:ィ half:ｨ]
            { { 0x30a5 }, { 0xff69 } }, // [full:ゥ half:ｩ]
            { { 0x30a7 }, { 0xff6a } }, // [full:ェ half:ｪ]
            { { 0x30a9 }, { 0xff6b } }, // [full:ォ half:ｫ]
            { { 0x30e3 }, { 0xff6c } }, // [full:ャ half:ｬ]
            { { 0x30e5 }, { 0xff6d } }, // [full:ュ half:ｭ]
            { { 0x30e7 }, { 0xff6e } }, // [full:ョ half:ｮ]
            { { 0x30c3 }, { 0xff6f } }, // [full:ッ half:ｯ]
            { { 0x30fc }, { 0xff70 } }, // [full:ー half:ｰ]
            { { 0x30a2 }, { 0xff71 } }, // [full:ア half:ｱ]
            { { 0x30a4 }, { 0xff72 } }, // [full:イ half:ｲ]
            { { 0x30a6 }, { 0xff73 } }, // [full:ウ half:ｳ]
            { { 0x30a8 }, { 0xff74 } }, // [full:エ half:ｴ]
            { { 0x30aa }, { 0xff75 } }, // [full:オ half:ｵ]
            { { 0x30ab }, { 0xff76 } }, // [full:カ half:ｶ]
            { { 0x30ad }, { 0xff77 } }, // [full:キ half:ｷ]
            { { 0x30af }, { 0xff78 } }, // [full:ク half:ｸ]
            { { 0x30b1 }, { 0xff79 } }, // [full:ケ half:ｹ]
            { { 0x30b3 }, { 0xff7a } }, // [full:コ half:ｺ]
            { { 0x30b5 }, { 0xff7b } }, // [full:サ half:ｻ]
            { { 0x30b7 }, { 0xff7c } }, // [full:シ half:ｼ]
            { { 0x30b9 }, { 0xff7d } }, // [full:ス half:ｽ]
            { { 0x30bb }, { 0xff7e } }, // [full:セ half:ｾ]
            { { 0x30bd }, { 0xff7f } }, // [full:ソ half:ｿ]
            { { 0x30cf }, { 0xff80 } }, // [full:ハ half:ﾀ]
            { { 0x30c1 }, { 0xff81 } }, // [full:チ half:ﾁ]
            { { 0x30c4 }, { 0xff82 } }, // [full:ツ half:ﾂ]
            { { 0x30c6 }, { 0xff83 } }, // [full:テ half:ﾃ]
            { { 0x30c8 }, { 0xff84 } }, // [full:ト half:ﾄ]
            { { 0x30ca }, { 0xff85 } }, // [full:ナ half:ﾅ]
            { { 0x30cb }, { 0xff86 } }, // [full:ニ half:ﾆ]
            { { 0x30cc }, { 0xff87 } }, // [full:ヌ half:ﾇ]
            { { 0x30cd }, { 0xff88 } }, // [full:ネ half:ﾈ]
            { { 0x30ce }, { 0xff89 } }, // [full:ノ half:ﾉ]
            { { 0x30cf }, { 0xff8a } }, // [full:ハ half:ﾊ]
            { { 0x30d2 }, { 0xff8b } }, // [full:ヒ half:ﾋ]
            { { 0x30d5 }, { 0xff8c } }, // [full:フ half:ﾌ]
            { { 0x30d8 }, { 0xff8d } }, // [full:ヘ half:ﾍ]
            { { 0x30db }, { 0xff8e } }, // [full:ホ half:ﾎ]
            { { 0x30de }, { 0xff8f } }, // [full:マ half:ﾏ]
            { { 0x30df }, { 0xff90 } }, // [full:ミ half:ﾐ]
            { { 0x30e0 }, { 0xff91 } }, // [full:ム half:ﾑ]
            { { 0x30e1 }, { 0xff92 } }, // [full:メ half:ﾒ]
            { { 0x30e2 }, { 0xff93 } }, // [full:モ half:ﾓ]
            { { 0x30e4 }, { 0xff94 } }, // [full:ヤ half:ﾔ]
            { { 0x30e6 }, { 0xff95 } }, // [full:ユ half:ﾕ]
            { { 0x30e8 }, { 0xff96 } }, // [full:ヨ half:ﾖ]
            { { 0x30e9 }, { 0xff97 } }, // [full:ラ half:ﾗ]
            { { 0x30ea }, { 0xff98 } }, // [full:リ half:ﾘ]
            { { 0x30eb }, { 0xff99 } }, // [full:ル half:ﾙ]
            { { 0x30ec }, { 0xff9a } }, // [full:レ half:ﾚ]
            { { 0x30ed }, { 0xff9b } }, // [full:ロ half:ﾛ]
            { { 0x30ef }, { 0xff9c } }, // [full:ワ half:ﾜ]
            { { 0x30f2 }, { 0xff66 } }, // [full:ヲ half:ｦ]
            { { 0x30f3 }, { 0xff9d } }, // [full:ン half:ﾝ]
            { { 0x30ac }, { 0xff76, 0xff9e } }, // [full:ガ half:ｶﾞ]
            { { 0x30ae }, { 0xff77, 0xff9e } }, // [full:ギ half:ｷﾞ]
            { { 0x30b0 }, { 0xff78, 0xff9e } }, // [full:グ half:ｸﾞ]
            { { 0x30b2 }, { 0xff79, 0xff9e } }, // [full:ゲ half:ｹﾞ]
            { { 0x30b4 }, { 0xff7a, 0xff9e } }, // [full:ゴ half:ｺﾞ]
            { { 0x30b6 }, { 0xff7b, 0xff9e } }, // [full:ザ half:ｻﾞ]
            { { 0x30b8 }, { 0xff7c, 0xff9e } }, // [full:ジ half:ｼﾞ]
            { { 0x30ba }, { 0xff7d, 0xff9e } }, // [full:ズ half:ｽﾞ]
            { { 0x30bc }, { 0xff7e, 0xff9e } }, // [full:ゼ half:ｾﾞ]
            { { 0x30be }, { 0xff7f, 0xff9e } }, // [full:ゾ half:ｿﾞ]
            { { 0x30c0 }, { 0xff80, 0xff9e } }, // [full:ダ half:ﾀﾞ]
            { { 0x30c2 }, { 0xff81, 0xff9e } }, // [full:ヂ half:ﾁﾞ]
            { { 0x30c5 }, { 0xff82, 0xff9e } }, // [full:ヅ half:ﾂﾞ]
            { { 0x30c7 }, { 0xff83, 0xff9e } }, // [full:デ half:ﾃﾞ]
            { { 0x30c9 }, { 0xff84, 0xff9e } }, // [full:ド half:ﾄﾞ]
            { { 0x30d0 }, { 0xff8a, 0xff9e } }, // [full:バ half:ﾊﾞ]
            { { 0x30d3 }, { 0xff8b, 0xff9e } }, // [full:ビ half:ﾋﾞ]
            { { 0x30d6 }, { 0xff8c, 0xff9e } }, // [full:ブ half:ﾌﾞ]
            { { 0x30d9 }, { 0xff8d, 0xff9e } }, // [full:ベ half:ﾍﾞ]
            { { 0x30dc }, { 0xff8e, 0xff9e } }, // [full:ボ half:ﾎﾞ]
            { { 0x30d1 }, { 0xff8a, 0xff9f } }, // [full:パ half:ﾊﾟ]
            { { 0x30d4 }, { 0xff8b, 0xff9f } }, // [full:ピ half:ﾋﾟ]
            { { 0x30d7 }, { 0xff8c, 0xff9f } }, // [full:プ half:ﾌﾟ]
            { { 0x30da }, { 0xff8d, 0xff9f } }, // [full:ペ half:ﾍﾟ]
            { { 0x30dd }, { 0xff8e, 0xff9f } }, // [full:ポ half:ﾎﾟ]
            { { 0x30f4 }, { 0xff73, 0xff9e } }, // [full:ヴ half:ｳﾞ]
            { { 0x30f7 }, { 0xff9c, 0xff9e } }, // [full:\u30f7 half:ﾜﾞ]
            { { 0x30fa }, { 0xff66, 0xff9e } }, // [full:\u30fa half:ｦﾞ]
            { { 0x309b }, { 0xff9e } }, // [full:゛ half:ﾞ]
            { { 0x309c }, { 0xff9f } }, // [full:゜ half:ﾟ]
            { { 0x3000 }, { 0x0020 } }, // [full:　 half: ]
    };
    // @formatter:on

    public DefaultFullHalfCodePointsMap() {
        for (char[][] codePoint : fullHalfCodePoints) {
            put(String.valueOf(codePoint[0]), String.valueOf(codePoint[1]));
        }
    }

}
