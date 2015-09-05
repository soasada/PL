import java_cup.runtime.*;

%%

%cup

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

/* Comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment} | {HashComment}

TraditionalComment 	= "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment 	= "//" {InputCharacter}* {LineTerminator}?
DocumentationComment    = "/*" "*"+ [^/*] ~"*/"
HashComment		= "#" {InputCharacter}* {LineTerminator}?

/* floating point literals */        
DoubleLiteral = ({FLit1}|{FLit2}|{FLit3}) {Exponent}?

FLit1    = [0-9]+ \. [0-9]* 
FLit2    = \. [0-9]+ 
FLit3    = [0-9]+ 
Exponent = [eE] [+-]? [0-9]+

%%

/* Expresiones y reglas */

                                        /* Reserved words */

        "else"                                          { return new Symbol(sym.ELSE); }
	"int"						{ return new Symbol(sym.INT); }
        "if"                                            { return new Symbol(sym.IF, Generador.getTag()); }
        "while"                                         { return new Symbol(sym.WHILE, Generador.getTag()); }
	"main"						{ return new Symbol(sym.MAIN); }
	"return"					{ return new Symbol(sym.RETURN); }
	
                                        /* Separators */

        "("                                             { return new Symbol(sym.AP); }
        ")"                                             { return new Symbol(sym.CP); }
        "{"                                             { return new Symbol(sym.AL); }
        "}"                                             { return new Symbol(sym.CL); }
        ";"                                             { return new Symbol(sym.PYC); }
	","						{ return new Symbol(sym.COMA); }

					/* Operators */

        "="                                             { return new Symbol(sym.ASIG); }
	">"						{ return new Symbol(sym.MAQ);  }
	"<"						{ return new Symbol(sym.MEQ); }
	"+"						{ return new Symbol(sym.MAS); }
	"-"						{ return new Symbol(sym.MENOS); }
	"*"						{ return new Symbol(sym.POR); }
	"/"						{ return new Symbol(sym.DIV); }

                                                /* Integer numbers*/

        0|[1-9][0-9]*                                   { return new Symbol(sym.NUMERO, new Integer(yytext())); }
				
						/* floating point numbers and exponents */

  	{DoubleLiteral}                			{ return new Symbol(sym.NUMEROF, new Double(yytext())); }

						/* Comments */

	{Comment}					{ }

						/* Others */
	
	{WhiteSpace}					{ }

						/* Identifiers */

        [_a-zA-Z][_a-zA-Z0-9]*                          { return new Symbol(sym.IDENT, yytext()); }

						/* Error */

	[^]						{ throw new Error("Illegal character <"+yytext()+">"); }

