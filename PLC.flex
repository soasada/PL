import java_cup.runtime.*;

%%

%cup

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* Comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment 	= "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment 	= "//" {InputCharacter}* {LineTerminator}
DocumentationComment    = "/**" {CommentContent} "*"+ "/"
CommentContent		= ( [^*] | \*+ [^/*] )*

%%

/* Expresiones y reglas */
	
	"++"						{ return new Symbol(sym.MASMAS); }
	"--"						{ return new Symbol(sym.MENOSMENOS); }
					/* Math ops*/

	"+"						{ return new Symbol(sym.MAS); }
	"-"						{ return new Symbol(sym.MENOS); }
	"*"						{ return new Symbol(sym.POR); }
	"/"						{ return new Symbol(sym.DIV); }
	"%"						{ return new Symbol(sym.MOD); }

					/* Operators */

	";"						{ return new Symbol(sym.PYC); }
	"="						{ return new Symbol(sym.ASIG); }
	"("						{ return new Symbol(sym.AP); }
	")"						{ return new Symbol(sym.CP); }
	"{"						{ return new Symbol(sym.AL); }
	"}"						{ return new Symbol(sym.CL); }
	"["						{ return new Symbol(sym.AC); }
	"]"						{ return new Symbol(sym.CC); }
	"=="						{ return new Symbol(sym.EQ); }
	"!="						{ return new Symbol(sym.NEQ); }
	"!"						{ return new Symbol(sym.NOT); }
	"&&"						{ return new Symbol(sym.AND); }
	"||"						{ return new Symbol(sym.OR); }
	""
					/* Bool */

	"true"						{ return new Symbol(sym.TRUE); }
	"false"						{ return new Symbol(sym.FALSE); }

					/* Reserved words */

	"print"						{ return new Symbol(sym.PRINT); }
	"if"						{ return new Symbol(sym.IF); }
	"else"						{ return new Symbol(sym.ELSE); }
	"while"						{ return new Symbol(sym.WHILE); }
	"do"						{ return new Symbol(sym.DO); }
	"for"						{ return new Symbol(sym.FOR); }
	"foreach"					{ return new Symbol(sym.FOREACH); }
	"enum"						{ return new Symbol(sym.ENUM); }
	"switch"					{ return new Symbol(sym.SWITCH); }
	"int"						{ return new Symbol(sym.INT); }
	"char"						{ return new Symbol(sym.CHAR); }
	"boolean"					{ return new Symbol(sym.BOOLEAN); }
	"double"					{ return new Symbol(sym.DOUBLE); }
	"float"						{ return new Symbol(sym.FLOAT); }
	"final"						{ return new Symbol(sym.FINAL); }
	"void"						{ return new Symbol(sym.VOID); }
	"return"					{ return new Symbol(sym.RETURN); }
	"break"						{ return new Symbol(sym.BREAK); }
	"continue"					{ return new Symbol(sym.CONTINUE); }
	"default"					{ return new Symbol(sym.DEFAULT); }
	"case"						{ return new Symbol(sym.CASE); }
	"to"						{ return new Symbol(sym.TO); }
	"downto"					{ return new Symbol(sym.DOWNTO); }
	"step"						{ return new Symbol(sym.STEP); }

					/* floating point numbers and exponents */

	[-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?		{ return new Symbol(sym.NUMEROF, new Double(yytext())); }


						/* Integer numbers*/

	0|[1-9][0-9]*					{ return new Symbol(sym.NUMERO, new Integer(yytext())); }

						/* Identifiers */

	[_a-zA-Z][_a-zA-Z0-9]*				{ return new Symbol(sym.IDENT, yytext()); }

						/* Comments */

	{Comment}					{ }
						/* Others */
	
	{WhiteSpace}					{ }
	[^]						{ throw new Error("Illegal character <"+yytext()+">"); }
