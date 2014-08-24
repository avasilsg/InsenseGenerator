#include "struct_Suaddr_apayload__.h"

#ifndef DALSMALL
static char *file_name = "struct_Suaddr_apayload__";
#endif

struct_Suaddr_apayload___PNTR copy_struct_Suaddr_apayload__( struct_Suaddr_apayload___PNTR p )  { 
	DAL_incRef( p ) ;
	struct_Suaddr_apayload___PNTR copy = construct_struct_Suaddr_apayload__( p->addr, p->payload );
	DAL_decRef( p ) ;
	return copy;
} 


void decRef_struct_Suaddr_apayload__( struct_Suaddr_apayload___PNTR pntr ) { 
	DAL_decRef( pntr->payload ) ;
} 


struct_Suaddr_apayload___PNTR construct_struct_Suaddr_apayload__( unsigned  addr,AnyTypePNTR  payload ) { 
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Code::genMallocAssign
	struct_Suaddr_apayload___PNTR  pntr=( struct_Suaddr_apayload___PNTR ) DAL_alloc( sizeof( struct_Suaddr_apayload___struct  ) ,true ) ;
	if( pntr== NULL ) { 
		DAL_error(OUT_OF_MEMORY_ERROR);
		return NULL;
	} 
	pntr->_decRef=decRef_struct_Suaddr_apayload__;
	pntr->addr=addr;
	DAL_assign( &pntr->payload,payload ) ;
	return pntr;
} 


