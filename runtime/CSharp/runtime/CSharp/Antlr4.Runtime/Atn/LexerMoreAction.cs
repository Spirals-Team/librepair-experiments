/* Copyright (c) 2012-2017 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */
using Antlr4.Runtime;
using Antlr4.Runtime.Atn;
using Antlr4.Runtime.Misc;
using Antlr4.Runtime.Sharpen;

namespace Antlr4.Runtime.Atn
{
    /// <summary>
    /// Implements the
    /// <c>more</c>
    /// lexer action by calling
    /// <see cref="Antlr4.Runtime.Lexer.More()"/>
    /// .
    /// <p>The
    /// <c>more</c>
    /// command does not have any parameters, so this action is
    /// implemented as a singleton instance exposed by
    /// <see cref="Instance"/>
    /// .</p>
    /// </summary>
    /// <author>Sam Harwell</author>
    /// <since>4.2</since>
    public sealed class LexerMoreAction : ILexerAction
    {
        /// <summary>Provides a singleton instance of this parameterless lexer action.</summary>
        /// <remarks>Provides a singleton instance of this parameterless lexer action.</remarks>
        public static readonly Antlr4.Runtime.Atn.LexerMoreAction Instance = new Antlr4.Runtime.Atn.LexerMoreAction();

        /// <summary>
        /// Constructs the singleton instance of the lexer
        /// <c>more</c>
        /// command.
        /// </summary>
        private LexerMoreAction()
        {
        }

        /// <summary><inheritDoc/></summary>
        /// <returns>
        /// This method returns
        /// <see cref="LexerActionType.More"/>
        /// .
        /// </returns>
        public LexerActionType ActionType
        {
            get
            {
                return LexerActionType.More;
            }
        }

        /// <summary><inheritDoc/></summary>
        /// <returns>
        /// This method returns
        /// <see langword="false"/>
        /// .
        /// </returns>
        public bool IsPositionDependent
        {
            get
            {
                return false;
            }
        }

        /// <summary>
        /// <inheritDoc/>
        /// <p>This action is implemented by calling
        /// <see cref="Antlr4.Runtime.Lexer.More()"/>
        /// .</p>
        /// </summary>
        public void Execute(Lexer lexer)
        {
            lexer.More();
        }

        public override int GetHashCode()
        {
            int hash = MurmurHash.Initialize();
            hash = MurmurHash.Update(hash, (int)(ActionType));
            return MurmurHash.Finish(hash, 1);
        }

        public override bool Equals(object obj)
        {
            return obj == this;
        }

        public override string ToString()
        {
            return "more";
        }
    }
}
