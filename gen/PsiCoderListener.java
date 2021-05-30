// Generated from D:/JavaPrograms/PsiCoderANTLR/grammar\PsiCoder.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PsiCoderParser}.
 */
public interface PsiCoderListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(PsiCoderParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(PsiCoderParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGlobalDeclaration(PsiCoderParser.GlobalDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGlobalDeclaration(PsiCoderParser.GlobalDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclaration(PsiCoderParser.StructDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclaration(PsiCoderParser.StructDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#structMember}.
	 * @param ctx the parse tree
	 */
	void enterStructMember(PsiCoderParser.StructMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#structMember}.
	 * @param ctx the parse tree
	 */
	void exitStructMember(PsiCoderParser.StructMemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(PsiCoderParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(PsiCoderParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#returnExpression}.
	 * @param ctx the parse tree
	 */
	void enterReturnExpression(PsiCoderParser.ReturnExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#returnExpression}.
	 * @param ctx the parse tree
	 */
	void exitReturnExpression(PsiCoderParser.ReturnExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(PsiCoderParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(PsiCoderParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#variableAssignment}.
	 * @param ctx the parse tree
	 */
	void enterVariableAssignment(PsiCoderParser.VariableAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#variableAssignment}.
	 * @param ctx the parse tree
	 */
	void exitVariableAssignment(PsiCoderParser.VariableAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#structInstantiation}.
	 * @param ctx the parse tree
	 */
	void enterStructInstantiation(PsiCoderParser.StructInstantiationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#structInstantiation}.
	 * @param ctx the parse tree
	 */
	void exitStructInstantiation(PsiCoderParser.StructInstantiationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#instructions}.
	 * @param ctx the parse tree
	 */
	void enterInstructions(PsiCoderParser.InstructionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#instructions}.
	 * @param ctx the parse tree
	 */
	void exitInstructions(PsiCoderParser.InstructionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(PsiCoderParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(PsiCoderParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#read}.
	 * @param ctx the parse tree
	 */
	void enterRead(PsiCoderParser.ReadContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#read}.
	 * @param ctx the parse tree
	 */
	void exitRead(PsiCoderParser.ReadContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(PsiCoderParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(PsiCoderParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#conditional}.
	 * @param ctx the parse tree
	 */
	void enterConditional(PsiCoderParser.ConditionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#conditional}.
	 * @param ctx the parse tree
	 */
	void exitConditional(PsiCoderParser.ConditionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void enterWhileLoop(PsiCoderParser.WhileLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#whileLoop}.
	 * @param ctx the parse tree
	 */
	void exitWhileLoop(PsiCoderParser.WhileLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#doWhile}.
	 * @param ctx the parse tree
	 */
	void enterDoWhile(PsiCoderParser.DoWhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#doWhile}.
	 * @param ctx the parse tree
	 */
	void exitDoWhile(PsiCoderParser.DoWhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#forLoop}.
	 * @param ctx the parse tree
	 */
	void enterForLoop(PsiCoderParser.ForLoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#forLoop}.
	 * @param ctx the parse tree
	 */
	void exitForLoop(PsiCoderParser.ForLoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#multSelection}.
	 * @param ctx the parse tree
	 */
	void enterMultSelection(PsiCoderParser.MultSelectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#multSelection}.
	 * @param ctx the parse tree
	 */
	void exitMultSelection(PsiCoderParser.MultSelectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#defaultCase}.
	 * @param ctx the parse tree
	 */
	void enterDefaultCase(PsiCoderParser.DefaultCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#defaultCase}.
	 * @param ctx the parse tree
	 */
	void exitDefaultCase(PsiCoderParser.DefaultCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(PsiCoderParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(PsiCoderParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link PsiCoderParser#primitiveValue}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveValue(PsiCoderParser.PrimitiveValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link PsiCoderParser#primitiveValue}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveValue(PsiCoderParser.PrimitiveValueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(PsiCoderParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(PsiCoderParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqExpression(PsiCoderParser.EqExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqExpression(PsiCoderParser.EqExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(PsiCoderParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(PsiCoderParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code minusExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMinusExpression(PsiCoderParser.MinusExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code minusExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMinusExpression(PsiCoderParser.MinusExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdExpression(PsiCoderParser.IdExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdExpression(PsiCoderParser.IdExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCompExpression(PsiCoderParser.CompExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCompExpression(PsiCoderParser.CompExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nestedExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNestedExpression(PsiCoderParser.NestedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nestedExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNestedExpression(PsiCoderParser.NestedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(PsiCoderParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(PsiCoderParser.FunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultExpression(PsiCoderParser.MultExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultExpression(PsiCoderParser.MultExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code adExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAdExpression(PsiCoderParser.AdExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code adExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAdExpression(PsiCoderParser.AdExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code negExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNegExpression(PsiCoderParser.NegExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code negExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNegExpression(PsiCoderParser.NegExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primitiveValExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveValExpression(PsiCoderParser.PrimitiveValExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primitiveValExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveValExpression(PsiCoderParser.PrimitiveValExpressionContext ctx);
}