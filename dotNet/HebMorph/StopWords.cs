﻿/***************************************************************************
 * HebMorph - making Hebrew properly searchable
 * 
 *   Copyright (C) 2010-2012                                               
 *      Itamar Syn-Hershko <itamar at code972 dot com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

using System;
using System.Collections.Generic;
using System.Text;

namespace HebMorph
{
    public static class StopWords
    {
        public static string[] BasicStopWordsSet = { "אם","כי","בתוך","לתוך","הוא","היא","הם","הן","לא","היכן","יש",
            "כן","או","היה","היו","יהיה","יהיו","להיות","תהיינה","למה","מדוע","האם","אבל","ע\"י","עבור","זה","זאת",
            "בשביל","מה","גם","הם","אז","כלומר", "רק", "בגלל", "מכיוון", "עד", "כמו", "מאד", "של", "את",
            "בעיקר", "זו","הזה","מלבד","בלבד","בין", "ובין","לבין","למשל","שבהם", "כך", "אך","למרות" };
        // אף אחד, שום דבר, אי פעם, על פי, אף על פי, על ידי
    }
}
