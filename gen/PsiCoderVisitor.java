// Generated from D:/JavaPrograms/PsiCoderANTLR/grammar\PsiCoder.g4 by ANTLR 4.9.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PsiCoderParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PsiCoderVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(PsiCoderParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#globalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalDeclaration(PsiCoderParser.GlobalDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#structDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructDeclaration(PsiCoderParser.StructDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#structMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructMember(PsiCoderParser.StructMemberContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(PsiCoderParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#dataType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDataType(PsiCoderParser.DataTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#returnExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnExpression(PsiCoderParser.ReturnExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(PsiCoderParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#variableAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAssignment(PsiCoderParser.VariableAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#structInstantiation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructInstantiation(PsiCoderParser.StructInstantiationContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#structInstantiationAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructInstantiationAssignment(PsiCoderParser.StructInstantiationAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#instructions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstructions(PsiCoderParser.InstructionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#instruction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruction(PsiCoderParser.InstructionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#read}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead(PsiCoderParser.ReadContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(PsiCoderParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#conditional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditional(PsiCoderParser.ConditionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#whileLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileLoop(PsiCoderParser.WhileLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#doWhile}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoWhile(PsiCoderParser.DoWhileContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#forLoop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForLoop(PsiCoderParser.ForLoopContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#multSelection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultSelection(PsiCoderParser.MultSelectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#defaultCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultCase(PsiCoderParser.DefaultCaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(PsiCoderParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link PsiCoderParser#primitiveValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveValue(PsiCoderParser.PrimitiveValueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(PsiCoderParser.OrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqExpression(PsiCoderParser.EqExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(PsiCoderParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code minusExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinusExpression(PsiCoderParser.MinusExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpression(PsiCoderParser.IdExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompExpression(PsiCoderParser.CompExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nestedExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestedExpression(PsiCoderParser.NestedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionCallExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCallExpression(PsiCoderParser.FunctionCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExpression(PsiCoderParser.MultExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code adExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdExpression(PsiCoderParser.AdExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegExpression(PsiCoderParser.NegExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primitiveValExpression}
	 * labeled alternative in {@link PsiCoderParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveValExpression(PsiCoderParser.PrimitiveValExpressionContext ctx);
}