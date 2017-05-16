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
using Lucene.Net.Analysis.Tokenattributes;

namespace Lucene.Net.Analysis.Hebrew
{
    public class StreamLemmasFilter : Tokenizer
    {
        private HebMorph.StreamLemmatizer _streamLemmatizer;

        private ITermAttribute termAtt;
        private IOffsetAttribute offsetAtt;
        private IPositionIncrementAttribute posIncrAtt;
        private ITypeAttribute typeAtt;
        //protected PayloadAttribute payAtt;

        public bool alwaysSaveMarkedOriginal;
        public HebMorph.LemmaFilters.LemmaFilterBase lemmaFilter = null;

        private readonly List<HebMorph.Token> stack = new List<HebMorph.Token>();
        private readonly IList<HebMorph.Token> filterCache = new List<HebMorph.Token>();
        private int index = 0;
        private readonly Dictionary<string, bool> previousLemmas = new Dictionary<string,bool>();

        #region Constructors
        public StreamLemmasFilter(System.IO.TextReader input, HebMorph.StreamLemmatizer _lemmatizer)
            //: base(input) <- converts to CharStream, and causes issues due to a call to ReadToEnd in ctor
        {
            Init(input, _lemmatizer, null, false);
        }

        public StreamLemmasFilter(System.IO.TextReader input, HebMorph.StreamLemmatizer _lemmatizer, bool AlwaysSaveMarkedOriginal)
            //: base(input) <- converts to CharStream, and causes issues due to a call to ReadToEnd in ctor
        {
            Init(input, _lemmatizer, null, AlwaysSaveMarkedOriginal);
        }

        public StreamLemmasFilter(System.IO.TextReader input, HebMorph.StreamLemmatizer _lemmatizer,
            HebMorph.LemmaFilters.LemmaFilterBase _lemmaFilter, bool AlwaysSaveMarkedOriginal)
        //: base(input) <- converts to CharStream, and causes issues due to a call to ReadToEnd in ctor
        {
            Init(input, _lemmatizer, _lemmaFilter, AlwaysSaveMarkedOriginal);
        }

        public StreamLemmasFilter(System.IO.TextReader input, HebMorph.StreamLemmatizer _lemmatizer,
            HebMorph.LemmaFilters.LemmaFilterBase _lemmaFilter)
        //: base(input) <- converts to CharStream, and causes issues due to a call to ReadToEnd in ctor
        {
            Init(input, _lemmatizer, _lemmaFilter, false);
        }

        private void Init(System.IO.TextReader input, HebMorph.StreamLemmatizer _lemmatizer,
            HebMorph.LemmaFilters.LemmaFilterBase _lemmaFilter, bool AlwaysSaveMarkedOriginal)
        {
			termAtt = AddAttribute <ITermAttribute>();
	        offsetAtt = AddAttribute<IOffsetAttribute>();
	        posIncrAtt = AddAttribute<IPositionIncrementAttribute>();
			typeAtt = AddAttribute <ITypeAttribute>();
            //payAtt = (PayloadAttribute)AddAttribute(typeof(PayloadAttribute));

        	this.input = input;
            this._streamLemmatizer = _lemmatizer;
            this._streamLemmatizer.SetStream(input);
            this.alwaysSaveMarkedOriginal = AlwaysSaveMarkedOriginal;
            this.lemmaFilter = _lemmaFilter;
        }
        #endregion

        public override bool IncrementToken()
        {
            // Index all unique lemmas at the same position
            while (index < stack.Count)
            {
                HebMorph.HebrewToken res = stack[index++] as HebMorph.HebrewToken;

                if (res == null || previousLemmas.ContainsKey(res.Lemma)) // Skip multiple lemmas (we will merge morph properties later)
                    continue;

                previousLemmas.Add(res.Lemma, true);

                if (CreateHebrewToken(res))
                    return true;
            }

            // Reset state
            ClearAttributes();
            index = 0;
            stack.Clear();
            previousLemmas.Clear();

            // Lemmatize next word in stream. The HebMorph lemmatizer will always return a token, unless
            // an unrecognized Hebrew word is hit, then an empty tokens array will be returned.
            string word = string.Empty; // to hold the original word from the stream
            if (_streamLemmatizer.LemmatizeNextToken(out word, stack) == 0)
                return false; // EOS

            // Store the location of the word in the original stream
            offsetAtt.SetOffset(CorrectOffset(_streamLemmatizer.StartOffset), CorrectOffset(_streamLemmatizer.EndOffset));

            // A non-Hebrew word
            if (stack.Count == 1 && !(stack[0] is HebMorph.HebrewToken))
            {
                SetTermText(word);

                HebMorph.Token tkn = stack[0];
                if (tkn.IsNumeric)
                    typeAtt.Type = HebrewTokenizer.TokenTypeSignature(HebrewTokenizer.TOKEN_TYPES.Numeric);
                else
                    typeAtt.Type = HebrewTokenizer.TokenTypeSignature(HebrewTokenizer.TOKEN_TYPES.NonHebrew);
                    
                // Applying LowerCaseFilter for Non-Hebrew terms
                char[] buffer = termAtt.TermBuffer();
                int length = termAtt.TermLength();
                for (int i = 0; i < length; i++)
                    buffer[i] = System.Char.ToLower(buffer[i]);

                stack.Clear();
                return true;
            }

            // If we arrived here, we hit a Hebrew word
            // Do some filtering if requested...
            if (lemmaFilter != null && lemmaFilter.FilterCollection(stack, filterCache) != null)
            {
                stack.Clear();
                stack.AddRange(filterCache);
            }

            // OOV case -- for now store word as-is and return true
            if (stack.Count == 0)
            {
                // TODO: To allow for more advanced uses, fill stack with processed tokens and
                // SetPositionIncrement(0)

                SetTermText(word + "$");
                typeAtt.Type = HebrewTokenizer.TokenTypeSignature(HebrewTokenizer.TOKEN_TYPES.Hebrew);
                return true;
            }

            // If only one lemma was returned for this word
            if (stack.Count == 1)
            {
                HebMorph.HebrewToken hebToken = stack[0] as HebMorph.HebrewToken;

                // Index the lemma alone if it exactly matches the word minus prefixes
                if (!alwaysSaveMarkedOriginal && hebToken.Lemma.Equals(word.Substring(hebToken.PrefixLength)))
                {
                    CreateHebrewToken(hebToken);
                    posIncrAtt.PositionIncrement = 1;
                    stack.Clear();
                    return true;
                }
                // Otherwise, index the lemma plus the original word marked with a unique flag to increase precision
                else
                {
                    // DILEMMA: Does indexing word.Substring(hebToken.PrefixLength) + "$" make more or less sense?
                    // For now this is kept the way it is below to support duality of SimpleAnalyzer and MorphAnalyzer
                    SetTermText(word + "$");
                }
            }

            // More than one lemma exist. Mark and store the original term to increase precision, while all
            // lemmas will be popped out of the stack and get stored at the next call to IncrementToken.
            else
            {
                SetTermText(word + "$");
            }

            typeAtt.Type = HebrewTokenizer.TokenTypeSignature(HebrewTokenizer.TOKEN_TYPES.Hebrew);

            return true;
        }

        protected virtual bool CreateHebrewToken(HebMorph.HebrewToken hebToken)
        {
            SetTermText(hebToken.Lemma ?? hebToken.Text.Substring(hebToken.PrefixLength));
            posIncrAtt.PositionIncrement = 0;

            // TODO: typeAtt.SetType(TokenTypeSignature(TOKEN_TYPES.Acronym));
            typeAtt.Type = HebrewTokenizer.TokenTypeSignature(HebrewTokenizer.TOKEN_TYPES.Hebrew);

            /*
             * Morph payload
             * 
            byte[] data = new byte[1];
            data[0] = (byte)morphResult.Mask; // TODO: Set bits selectively
            Payload payload = new Payload(data);
            payAtt.SetPayload(payload);
            */

            return true;
        }

        private void SetTermText(string token)
        {
            // Record the term string
            if (termAtt.TermLength() < token.Length)
                termAtt.SetTermBuffer(token);
            else // Perform a copy to save on memory operations
            {
                char[] buf = termAtt.TermBuffer();
                token.CopyTo(0, buf, 0, token.Length);
            }
            termAtt.SetTermLength(token.Length);
        }

        public override void Reset(System.IO.TextReader input)
        {
            base.Reset(input);
            stack.Clear();
            index = 0;
            _streamLemmatizer.SetStream(input);
        }
    }
}
