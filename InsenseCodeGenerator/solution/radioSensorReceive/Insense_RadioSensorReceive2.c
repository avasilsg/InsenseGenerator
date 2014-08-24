// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printImplIncludes
#include "main.h"
// TODO put remainder of impl includes here
#include "struct_Suaddr_apayload__.h"
#include "union_int.h"


#ifndef DALSMALL
static char *file_name = "RadioSensorReceive2";
#endif


// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::generateDecRef
static void decRef_RadioSensorReceive2( RadioSensorReceive2PNTR this ) { 
	channel_unbind( this->output_comp ) ;
	// TODO need InceOS Channel_decRef( this->output_comp ) ;
	channel_unbind( this->received_comp ) ;
	// TODO need InceOS Channel_decRef( this->received_comp ) ;

} 

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printComponentFuncsImpl

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printBehaviourImpl
void behaviour_RadioSensorReceive2( RadioSensorReceive2PNTR this ) { 
	inceos_event_t op_status;// for exception handling

	while( ! this->stopped ) { 

	// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Sequence::complete
	struct_Suaddr_apayload___PNTR packet = NULL;

	// Make call to receive function 
	channel_receive( this->received_comp,&packet,false ) ;
;
	//any project start
	if( anyTypeIsEqual( packet->payload,"i" ) ) { 
		int  value =  anyTypeGetIntValue( packet->payload ) ;
	// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Sequence::complete
	// Make call to send op 
{ 
	int  _lvalue= value;
	channel_send( this->output_comp,&_lvalue,NULL  ) ;
	// end of send op 
} 
;
	// End of sequence
	} 
	else { 
	// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Sequence::complete
	printString_proc(this, NULL, Construct_String0("unknown type???") ) ;
	// End of sequence

	}  
	//any project end
;
	// End of sequence
	DAL_decRef( packet ) ;

	} 
	component_exit(  ) ;

} 

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorImpls
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorGlobalInitialiser
void RadioSensorReceive2_init_globals( RadioSensorReceive2PNTR this ) 
{ 
	this->decRef = decRef_RadioSensorReceive2;
	this->output_comp=channel_create( CHAN_OUT,sizeof( int  ) ,false ) ;
	this->received_comp=channel_create( CHAN_IN,sizeof( struct_Suaddr_apayload___PNTR  ) ,true ) ;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.DeclarationContainer::locationInitialisers

} 

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Constructor::generateCode
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Constructor::constructorFunctionDecl
void Construct_RadioSensorReceive20( RadioSensorReceive2PNTR this, int _argc, void* _argv[] ) { 
	RadioSensorReceive2_init_globals( this ) ;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Sequence::complete
	// End of sequence

	behaviour_RadioSensorReceive2( this ) ;
} 


