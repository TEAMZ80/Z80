	CPU "Z80.TBL"
	HOF "INT8"
	ORG 440H

		MEMORIA: EQU 400H		;localidad de la informacion
		CADENA: EQU 420H		;localidad de la cadena

		LD A, (430H)			;tamaño de la cadena
		LD C, A					;se almacena en C
		LD A, (432H)			;tamaño de la informacion
		LD D, A 				;se almacena en D
		LD IX, MEMORIA			;poscicion inicial de la informacion
		LD IY, CADENA 			;poscicion inicial de la cadena

ETI2:	LD A, C 				;Cargamos el tamaño de la cadena en A
		CP D 					;lo comparamos con el tamañano de la longuitd para 
		JP p, ETI0 				;ver que la cadena quepa en el tamaño de las busqueda y sino acaba
		LD A, (IY+0H)			;Cargamos la primera letra de la cadena 
ETI1:	LD B, A 				;y la almecenamos en B (siguiendo elk indice IY)
		LD A, (IX+0H)			;cargamos la poscicion (IX) de la informacion
		INC IX 					;incrementamos IX
		DEC D 					;Disminuimos el tamnaño de la busqueda (informacion)
		CP B 					;comparamos las letras entra la informacion y la cadena con
		JR Z, COMP1				;los indices IX e IY respectivamente 
		LD IY, CADENA 			;en caso de que no se cumpla reiniciamos IY indice de la cdena
		JR ETI2


COMP1:	INC IY 					;Incrementamos IY
		LD A, (IY+0H) 			;leemos el vaalor de IY en su poscicion actual
		CP 0					;verificamos si es cero su valor
		JR Z, FIN				;en caso de que sea cero entonces no hay mas cadena
		JR ETI1					;terminamos la busqueda y sino regresamos para seguir comprarando

FIN:	CALL BACKUP				;llamamos a una fucnion que recupera el valor de la direccion buscada
		LD (434H), IX 			;Sacamos la direccion de IX
		LD A, (434H) 			;la leemos desde memoria para voltear sus digitos
		LD B, A 				
		LD A, (435H)
		LD (434H), A
		LD A, B
		LD (435H), A
		HALT 					;fin del programa

BACKUP:	DEC IX					;Esta funcion tiene como objetivo recuperar el valor del indicie 
		DEC C 					;donde incia la cadena buscada dentro de la informacion
		JR NZ, BACKUP 			;Sabemos que actualmente IX se encuentra al final de esta seccion
		RET 					;le decrementamos el numero de veces que tenga de longuitud la cadena buscada

ETI0:	LD A, 0					;Cargamos 0 en A
		DEC A 					;colocamos -1 en A que significa error 
		LD (434H), A 			;lo mandamos a guardar a memoria
		HALT					;Fin del programa sin encontrar la cadena.
	END
