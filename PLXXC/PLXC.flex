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

        "print"                                         { return new Symbol(sym.PRINT); }
        "else"                                          { return new Symbol(sym.ELSE); }
		"int"											{ return new Symbol(sym.INT); }
		"float"											{ return new Symbol(sym.FLOAT); }
        "if"                                            { return new Symbol(sym.IF, Generador.getTag()); }
        "while"                                         { return new Symbol(sym.WHILE, Generador.getTag()); }
        "do"                                            { return new Symbol(sym.DO, Generador.getTag()); }
        "for"                                           { return new Symbol(sym.FOR, Generador.getTag()); }
        "to"                                            { return new Symbol(sym.TO, Generador.getTag()); }
        "downto"                                        { return new Symbol(sym.DOWNTO, Generador.getTag()); }
        "step"                                          { return new Symbol(sym.STEP); }
		"in"											{ return new Symbol(sym.IN, Generador.getTag()); }
		"switch"										{ return new Symbol(sym.SWITCH, Generador.getTag()); }
		"case"											{ return new Symbol(sym.CASE, Generador.getTag()); }
		"break"											{ return new Symbol(sym.BREAK); }
		"default"										{ return new Symbol(sym.DEFAULT); }
		"typedef"										{ return new Symbol(sym.TYPEDEF); }
		"true"											{ return new Symbol(sym.TRUE); }
		"false"											{ return new Symbol(sym.FALSE); }
		"char"											{ return new Symbol(sym.CHAR); }
		"(int)"											{ return new Symbol(sym.CASINT); }
		"(float)"										{ return new Symbol(sym.CASFLOAT); }


                                        /* Separators */

        "("                                             { return new Symbol(sym.AP); }
        ")"                                             { return new Symbol(sym.CP); }
        "{"                                             { return new Symbol(sym.AL); }
        "}"                                             { return new Symbol(sym.CL); }
        "["                                             { return new Symbol(sym.AC); }
        "]"                                             { return new Symbol(sym.CC); }
        ";"                                             { return new Symbol(sym.PYC); }
		","												{ return new Symbol(sym.COMA); }
		":"												{ return new Symbol(sym.DOSPUNTOS); }
		"'"												{ return new Symbol(sym.COMILLAS); }
										/* Operators */

        "="                                             { return new Symbol(sym.ASIG); }
		">"												{ return new Symbol(sym.MAQ);  }
		"<"												{ return new Symbol(sym.MEQ); }
        "!"                                             { return new Symbol(sym.NOT); }
        "=="                                            { return new Symbol(sym.EQ); }
		"<="											{ return new Symbol(sym.MEOQ); }
		">="											{ return new Symbol(sym.MAOQ); }
        "!="                                            { return new Symbol(sym.NEQ); }
        "&&"                                            { return new Symbol(sym.AND); }
        "||"                                            { return new Symbol(sym.OR); }
		"++"											{ return new Symbol(sym.MASMAS); }
		"--"											{ return new Symbol(sym.MENOSMENOS); }
		"+"												{ return new Symbol(sym.MAS); }
		"-"												{ return new Symbol(sym.MENOS); }
		"*"												{ return new Symbol(sym.POR); }
		"/"												{ return new Symbol(sym.DIV); }
		"%"												{ return new Symbol(sym.MOD); }
		"?"												{ return new Symbol(sym.QUESTION, Generador.getTag()); }
		"&"												{ return new Symbol(sym.DIR); }
		"+="											{ return new Symbol(sym.MASEQ); }
	
                                                /* Integer numbers*/

        0|[1-9][0-9]*                                   { return new Symbol(sym.NUMERO, new Integer(yytext())); }
				
												/* floating point numbers and exponents */

  		{DoubleLiteral}      		          			{ return new Symbol(sym.NUMEROF, new Double(yytext())); }

												/* Comments */

		{Comment}										{ }

												/* Others */
	
		{WhiteSpace}									{ }

												/* Identifiers */

        [_a-zA-Z][_a-zA-Z0-9]*                          { return new Symbol(sym.IDENT, yytext()); }

						/* Error */

		[^]												{ throw new Error("Illegal character <"+yytext()+">"); }

