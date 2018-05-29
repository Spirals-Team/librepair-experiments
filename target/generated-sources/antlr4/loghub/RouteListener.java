// Generated from loghub/Route.g4 by ANTLR 4.7.1
package loghub;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RouteParser}.
 */
public interface RouteListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RouteParser#configuration}.
	 * @param ctx the parse tree
	 */
	void enterConfiguration(RouteParser.ConfigurationContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#configuration}.
	 * @param ctx the parse tree
	 */
	void exitConfiguration(RouteParser.ConfigurationContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#pipeline}.
	 * @param ctx the parse tree
	 */
	void enterPipeline(RouteParser.PipelineContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#pipeline}.
	 * @param ctx the parse tree
	 */
	void exitPipeline(RouteParser.PipelineContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#input}.
	 * @param ctx the parse tree
	 */
	void enterInput(RouteParser.InputContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#input}.
	 * @param ctx the parse tree
	 */
	void exitInput(RouteParser.InputContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#output}.
	 * @param ctx the parse tree
	 */
	void enterOutput(RouteParser.OutputContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#output}.
	 * @param ctx the parse tree
	 */
	void exitOutput(RouteParser.OutputContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#inputObjectlist}.
	 * @param ctx the parse tree
	 */
	void enterInputObjectlist(RouteParser.InputObjectlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#inputObjectlist}.
	 * @param ctx the parse tree
	 */
	void exitInputObjectlist(RouteParser.InputObjectlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#outputObjectlist}.
	 * @param ctx the parse tree
	 */
	void enterOutputObjectlist(RouteParser.OutputObjectlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#outputObjectlist}.
	 * @param ctx the parse tree
	 */
	void exitOutputObjectlist(RouteParser.OutputObjectlistContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#pipenodeList}.
	 * @param ctx the parse tree
	 */
	void enterPipenodeList(RouteParser.PipenodeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#pipenodeList}.
	 * @param ctx the parse tree
	 */
	void exitPipenodeList(RouteParser.PipenodeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#forkpiperef}.
	 * @param ctx the parse tree
	 */
	void enterForkpiperef(RouteParser.ForkpiperefContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#forkpiperef}.
	 * @param ctx the parse tree
	 */
	void exitForkpiperef(RouteParser.ForkpiperefContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#forwardpiperef}.
	 * @param ctx the parse tree
	 */
	void enterForwardpiperef(RouteParser.ForwardpiperefContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#forwardpiperef}.
	 * @param ctx the parse tree
	 */
	void exitForwardpiperef(RouteParser.ForwardpiperefContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#pipenode}.
	 * @param ctx the parse tree
	 */
	void enterPipenode(RouteParser.PipenodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#pipenode}.
	 * @param ctx the parse tree
	 */
	void exitPipenode(RouteParser.PipenodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(RouteParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(RouteParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#beansDescription}.
	 * @param ctx the parse tree
	 */
	void enterBeansDescription(RouteParser.BeansDescriptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#beansDescription}.
	 * @param ctx the parse tree
	 */
	void exitBeansDescription(RouteParser.BeansDescriptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#bean}.
	 * @param ctx the parse tree
	 */
	void enterBean(RouteParser.BeanContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#bean}.
	 * @param ctx the parse tree
	 */
	void exitBean(RouteParser.BeanContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#beanName}.
	 * @param ctx the parse tree
	 */
	void enterBeanName(RouteParser.BeanNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#beanName}.
	 * @param ctx the parse tree
	 */
	void exitBeanName(RouteParser.BeanNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#beanValue}.
	 * @param ctx the parse tree
	 */
	void enterBeanValue(RouteParser.BeanValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#beanValue}.
	 * @param ctx the parse tree
	 */
	void exitBeanValue(RouteParser.BeanValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#finalpiperef}.
	 * @param ctx the parse tree
	 */
	void enterFinalpiperef(RouteParser.FinalpiperefContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#finalpiperef}.
	 * @param ctx the parse tree
	 */
	void exitFinalpiperef(RouteParser.FinalpiperefContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#piperef}.
	 * @param ctx the parse tree
	 */
	void enterPiperef(RouteParser.PiperefContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#piperef}.
	 * @param ctx the parse tree
	 */
	void exitPiperef(RouteParser.PiperefContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#merge}.
	 * @param ctx the parse tree
	 */
	void enterMerge(RouteParser.MergeContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#merge}.
	 * @param ctx the parse tree
	 */
	void exitMerge(RouteParser.MergeContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#mergeArgument}.
	 * @param ctx the parse tree
	 */
	void enterMergeArgument(RouteParser.MergeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#mergeArgument}.
	 * @param ctx the parse tree
	 */
	void exitMergeArgument(RouteParser.MergeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#mapping}.
	 * @param ctx the parse tree
	 */
	void enterMapping(RouteParser.MappingContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#mapping}.
	 * @param ctx the parse tree
	 */
	void exitMapping(RouteParser.MappingContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#drop}.
	 * @param ctx the parse tree
	 */
	void enterDrop(RouteParser.DropContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#drop}.
	 * @param ctx the parse tree
	 */
	void exitDrop(RouteParser.DropContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#fire}.
	 * @param ctx the parse tree
	 */
	void enterFire(RouteParser.FireContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#fire}.
	 * @param ctx the parse tree
	 */
	void exitFire(RouteParser.FireContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#log}.
	 * @param ctx the parse tree
	 */
	void enterLog(RouteParser.LogContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#log}.
	 * @param ctx the parse tree
	 */
	void exitLog(RouteParser.LogContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#level}.
	 * @param ctx the parse tree
	 */
	void enterLevel(RouteParser.LevelContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#level}.
	 * @param ctx the parse tree
	 */
	void exitLevel(RouteParser.LevelContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(RouteParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(RouteParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#etl}.
	 * @param ctx the parse tree
	 */
	void enterEtl(RouteParser.EtlContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#etl}.
	 * @param ctx the parse tree
	 */
	void exitEtl(RouteParser.EtlContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(RouteParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(RouteParser.PathContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#test}.
	 * @param ctx the parse tree
	 */
	void enterTest(RouteParser.TestContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#test}.
	 * @param ctx the parse tree
	 */
	void exitTest(RouteParser.TestContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#testExpression}.
	 * @param ctx the parse tree
	 */
	void enterTestExpression(RouteParser.TestExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#testExpression}.
	 * @param ctx the parse tree
	 */
	void exitTestExpression(RouteParser.TestExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(RouteParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(RouteParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#expressionsList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionsList(RouteParser.ExpressionsListContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#expressionsList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionsList(RouteParser.ExpressionsListContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(RouteParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(RouteParser.UnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#binaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOperator(RouteParser.BinaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#binaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOperator(RouteParser.BinaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#matchOperator}.
	 * @param ctx the parse tree
	 */
	void enterMatchOperator(RouteParser.MatchOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#matchOperator}.
	 * @param ctx the parse tree
	 */
	void exitMatchOperator(RouteParser.MatchOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(RouteParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(RouteParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#map}.
	 * @param ctx the parse tree
	 */
	void enterMap(RouteParser.MapContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#map}.
	 * @param ctx the parse tree
	 */
	void exitMap(RouteParser.MapContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#source}.
	 * @param ctx the parse tree
	 */
	void enterSource(RouteParser.SourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#source}.
	 * @param ctx the parse tree
	 */
	void exitSource(RouteParser.SourceContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#eventVariable}.
	 * @param ctx the parse tree
	 */
	void enterEventVariable(RouteParser.EventVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#eventVariable}.
	 * @param ctx the parse tree
	 */
	void exitEventVariable(RouteParser.EventVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#propertyName}.
	 * @param ctx the parse tree
	 */
	void enterPropertyName(RouteParser.PropertyNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#propertyName}.
	 * @param ctx the parse tree
	 */
	void exitPropertyName(RouteParser.PropertyNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#sources}.
	 * @param ctx the parse tree
	 */
	void enterSources(RouteParser.SourcesContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#sources}.
	 * @param ctx the parse tree
	 */
	void exitSources(RouteParser.SourcesContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#sourcedef}.
	 * @param ctx the parse tree
	 */
	void enterSourcedef(RouteParser.SourcedefContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#sourcedef}.
	 * @param ctx the parse tree
	 */
	void exitSourcedef(RouteParser.SourcedefContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(RouteParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(RouteParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(RouteParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(RouteParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#floatingPointLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFloatingPointLiteral(RouteParser.FloatingPointLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#floatingPointLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFloatingPointLiteral(RouteParser.FloatingPointLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(RouteParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(RouteParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#characterLiteral}.
	 * @param ctx the parse tree
	 */
	void enterCharacterLiteral(RouteParser.CharacterLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#characterLiteral}.
	 * @param ctx the parse tree
	 */
	void exitCharacterLiteral(RouteParser.CharacterLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(RouteParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(RouteParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#patternLiteral}.
	 * @param ctx the parse tree
	 */
	void enterPatternLiteral(RouteParser.PatternLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#patternLiteral}.
	 * @param ctx the parse tree
	 */
	void exitPatternLiteral(RouteParser.PatternLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link RouteParser#nullLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(RouteParser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link RouteParser#nullLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(RouteParser.NullLiteralContext ctx);
}