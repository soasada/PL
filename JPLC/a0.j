.method public static polinomio (I)I
	sipush 1
	iload 0
	imul
	iload 0
	imul
	sipush 2
	iload 0
	imul
	iadd
	sipush 3
	iadd
	ireturn
	nop
	.limit stack 3
	.limit locals 1
.end method
.method public static main (I)I
	iload 1
	invokestatic JPL/polinomio(I)I
	ireturn
	nop
	.limit stack 3
	.limit locals 2
.end method
