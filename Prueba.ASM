
PILA = MACRO #v1, #v2
	PUSH HL
	LD L, #v1
	LD H, #v2
	MEND

	LD A, 3
	LD B, A
	LD A, 2
	LD C, A
	PILA B, C
	LD A, 5
	LD D, A
	LD A, 7
	LD E, A
	PILA D, E

	END