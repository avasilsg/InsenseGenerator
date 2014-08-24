// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::print_ifdef
#ifndef RADIOSENSORRECEIVE2_H_
#define RADIOSENSORRECEIVE2_H_

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printImplIncludes
#include "main.h"
// TODO put remainder of impl includes here
#include "struct_Suaddr_apayload__.h"
#include "union_int.h"


// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printStructDecl
typedef struct RadioSensorReceive2 *RadioSensorReceive2PNTR , RadioSensorReceive2Struct ;
struct RadioSensorReceive2 { 
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printCommonDecls
	void (*decRef)(RadioSensorReceive2PNTR pntr);
	bool stopped;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printStructChannelDecls
	chan_id output_comp;
	chan_id received_comp;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.DeclarationContainer::printLocationDecls
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.DeclarationContainer::locationDecls

} ;

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorArrayFunctionDecls

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printComponentFuncsDecls

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorSignatures
extern void Construct_RadioSensorReceive20( RadioSensorReceive2PNTR this, int _argc, void* _argv[] ) ;

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printBehaviourForwardDecl
extern void behaviour_RadioSensorReceive2( RadioSensorReceive2PNTR this ) ;
#endif /* RADIOSENSORRECEIVE2_H_*/ 

