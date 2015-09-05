.method public static main (I)I
	sipush 0
	istore 1
	sipush 0
	dup
	istore 1
	pop
	sipush 0
	istore 2
L0:
	iload 0
	iload 2
	isub
	ifgt L1
	goto L2
L1:
	sipush 0
	istore 3
	sipush 0
	istore 4
L3:
	iload 0
	iload 3
	isub
	ifgt L4
	goto L5
L4:
L6:
	iload 0
	iload 4
	isub
	ifgt L7
	goto L8
L7:
	iload 3
	sipush 1
	iadd
	dup
	istore 3
	pop
	iload 4
	sipush 1
	iadd
	dup
	istore 4
	pop
	iload 1
	iload 2
	iadd
	iload 3
	iadd
	iload 4
	iadd
	dup
	istore 1
	pop
	goto L6
L8:
	goto L3
L5:
	iload 2
	sipush 1
	iadd
	dup
	istore 2
	pop
	goto L0
L2:
	iload 1
	ireturn
	nop
	.limit stack 2
	.limit locals 5
.end method

