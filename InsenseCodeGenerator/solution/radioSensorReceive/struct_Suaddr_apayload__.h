// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.StructDeclaration::printDOTHHeaders
#ifndef STRUCT_SUADDR_APAYLOAD___H_
#define STRUCT_SUADDR_APAYLOAD___H_
#include "InsenseRuntime.h"
#include "IString.h"
#include "AnyType.h"

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.StructDeclaration::generateStructDecl
typedef struct struct_Suaddr_apayload__ *struct_Suaddr_apayload___PNTR , struct_Suaddr_apayload___struct ;
struct struct_Suaddr_apayload__ { 
	void (*_decRef)(struct_Suaddr_apayload___PNTR  pntr);
	unsigned  addr;
	AnyTypePNTR  payload;
} ;


extern struct_Suaddr_apayload___PNTR construct_struct_Suaddr_apayload__( unsigned  addr,AnyTypePNTR  payload );

extern struct_Suaddr_apayload___PNTR copy_struct_Suaddr_apayload__( struct_Suaddr_apayload___PNTR p ) ;

#endif /* STRUCT_SUADDR_APAYLOAD___H_*/ 

