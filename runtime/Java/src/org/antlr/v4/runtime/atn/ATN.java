/*
 * Copyright (c) 2012-2017 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */

package org.antlr.v4.runtime.atn;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.IntervalSet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** */
public class ATN {
	public static final int INVALID_ALT_NUMBER = 0;


	public final List<ATNState> states = new ArrayList<ATNState>();

	/** Each subrule/rule is a decision point and we must track them so we
	 *  can go back later and build DFA predictors for them.  This includes
	 *  all the rules, subrules, optional blocks, ()+, ()* etc...
	 */
	public final List<DecisionState> decisionToState = new ArrayList<DecisionState>();

	/**
	 * Maps from rule index to starting state number.
	 */
	public RuleStartState[] ruleToStartState;

	/**
	 * Maps from rule index to stop state number.
	 */
	public RuleStopState[] ruleToStopState;


	public final Map<String, TokensStartState> modeNameToStartState =
		new LinkedHashMap<String, TokensStartState>();

	/**
	 * The type of the ATN.
	 */
	public final ATNType grammarType;

	/**
	 * The maximum value for any symbol recognized by a transition in the ATN.
	 */
	public final int maxTokenType;

	/**
	 * For lexer ATNs, this maps the rule index to the resulting token type.
	 * For parser ATNs, this maps the rule index to the generated bypass token
	 * type if the
	 * {@link ATNDeserializationOptions#isGenerateRuleBypassTransitions}
	 * deserialization option was specified; otherwise, this is {@code null}.
	 */
	public int[] ruleToTokenType;

	/**
	 * For lexer ATNs, this is an array of {@link LexerAction} objects which may
	 * be referenced by action transitions in the ATN.
	 */
	public LexerAction[] lexerActions;

	public final List<TokensStartState> modeToStartState = new ArrayList<TokensStartState>();

	/** Used for runtime deserialization of ATNs from strings */
	public ATN(ATNType grammarType, int maxTokenType) {
		this.grammarType = grammarType;
		this.maxTokenType = maxTokenType;
	}

	/** Compute the set of valid tokens that can occur starting in state {@code s}.
	 *  If {@code ctx} is null, the set of tokens will not include what can follow
	 *  the rule surrounding {@code s}. In other words, the set will be
	 *  restricted to tokens reachable staying within {@code s}'s rule.
	 */
	public IntervalSet nextTokens(ATNState s, RuleContext ctx) {
		LL1Analyzer anal = new LL1Analyzer(this);
		IntervalSet next = anal.LOOK(s, ctx);
		return next;
	}

    /**
	 * Compute the set of valid tokens that can occur starting in {@code s} and
	 * staying in same rule. {@link Token#EPSILON} is in set if we reach end of
	 * rule.
     */
    public IntervalSet nextTokens(ATNState s) {
        if ( s.nextTokenWithinRule != null ) return s.nextTokenWithinRule;
        s.nextTokenWithinRule = nextTokens(s, null);
        s.nextTokenWithinRule.setReadonly(true);
        return s.nextTokenWithinRule;
    }

	public void addState(ATNState state) {
		if (state != null) {
			state.atn = this;
			state.stateNumber = states.size();
		}

		states.add(state);
	}

	public void removeState(ATNState state) {
		states.set(state.stateNumber, null); // just free mem, don't shift states in list
	}

	public int defineDecisionState(DecisionState s) {
		decisionToState.add(s);
		s.decision = decisionToState.size()-1;
		return s.decision;
	}

    public DecisionState getDecisionState(int decision) {
        if ( !decisionToState.isEmpty() ) {
            return decisionToState.get(decision);
        }
        return null;
    }

	public int getNumberOfDecisions() {
		return decisionToState.size();
	}

	/**
	 * Computes the set of input symbols which could follow ATN state number
	 * {@code stateNumber} in the specified full {@code context}. This method
	 * considers the complete parser context, but does not evaluate semantic
	 * predicates (i.e. all predicates encountered during the calculation are
	 * assumed true). If a path in the ATN exists from the starting state to the
	 * {@link RuleStopState} of the outermost context without matching any
	 * symbols, {@link Token#EOF} is added to the returned set.
	 *
	 * <p>If {@code context} is {@code null}, it is treated as {@link ParserRuleContext#EMPTY}.</p>
	 *
	 * Note that this does NOT give you the set of all tokens that could
	 * appear at a given token position in the input phrase.  In other words,
	 * it does not answer:
	 *
	 *   "Given a specific partial input phrase, return the set of all tokens
	 *    that can follow the last token in the input phrase."
	 *
	 * The big difference is that with just the input, the parser could
	 * land right in the middle of a lookahead decision. Getting
     * all *possible* tokens given a partial input stream is a separate
     * computation. See https://github.com/antlr/antlr4/issues/1428
	 *
	 * For this function, we are specifying an ATN state and call stack to compute
	 * what token(s) can come next and specifically: outside of a lookahead decision.
	 * That is what you want for error reporting and recovery upon parse error.
	 *
	 * @param stateNumber the ATN state number
	 * @param context the full parse context
	 * @return The set of potentially valid input symbols which could follow the
	 * specified state in the specified context.
	 * @throws IllegalArgumentException if the ATN does not contain a state with
	 * number {@code stateNumber}
	 */
	public IntervalSet getExpectedTokens(int stateNumber, RuleContext context) {
		if (stateNumber < 0 || stateNumber >= states.size()) {
			throw new IllegalArgumentException("Invalid state number.");
		}

		RuleContext ctx = context;
		ATNState s = states.get(stateNumber);
		IntervalSet following = nextTokens(s);
		if (!following.contains(Token.EPSILON)) {
			return following;
		}

		IntervalSet expected = new IntervalSet();
		expected.addAll(following);
		expected.remove(Token.EPSILON);
		while (ctx != null && ctx.invokingState >= 0 && following.contains(Token.EPSILON)) {
			ATNState invokingState = states.get(ctx.invokingState);
			RuleTransition rt = (RuleTransition)invokingState.transition(0);
			following = nextTokens(rt.followState);
			expected.addAll(following);
			expected.remove(Token.EPSILON);
			ctx = ctx.parent;
		}

		if (following.contains(Token.EPSILON)) {
			expected.add(Token.EOF);
		}

		return expected;
	}
}
