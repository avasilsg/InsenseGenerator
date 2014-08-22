// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.TypeMarshaller::printIncludes
#include "InsenseRuntime.h"
#include "FunctionPair.h"
#include "BSTMap.h"
#include "GlobalObjects.h"
#include "cstring.h"
#include "marshaller.h"


#ifndef DALSMALL
static char *file_name = "marshaller"; // used by DAL_error macro
#endif
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.TypeMarshaller::printDeSerializer
void* deserialize_i( void* p )  { 
	void *result;
	int value;
	memncpy( (char *)&value,(char *)p,sizeof( int )  ) ;
	result = Construct_IntAnyType0( value,"i" ) ;
	return result;
} 

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.TypeMarshaller::printInitializeSerializerFunctions
void initializeSerializerFunctions() { 
	mapPut( serialiserMap,"i",Construct_FunctionPair( NULL,(deserialf_t)deserialize_i )  ) ;
} 

