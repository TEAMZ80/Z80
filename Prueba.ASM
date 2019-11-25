	CPU:Z80

CARGA = MACRO #a1, #a2, #a3
	LD A, #a1
	LD C, #a2
	LD D, #a3
	MEND

		CARGA B, 2, (IX+0)
		LD A, 70
		LD C, A
		LD A, (200)
		LD D, A
ETI2:	RLCA
		CP 1
		JR Z, ETI1
		DEC C
		JP NZ, ETI2
		DEC C
		CARGA C, (IX+1), 2
ETI1:	HALT