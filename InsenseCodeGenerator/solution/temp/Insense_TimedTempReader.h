// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::print_ifdef
#ifndef TIMEDTEMPREADER_H_
#define TIMEDTEMPREADER_H_

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printImplIncludes
#include "main.h"
// TODO put remainder of impl includes here


// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printStructDecl
typedef struct TimedTempReader *TimedTempReaderPNTR , TimedTempReaderStruct ;
struct TimedTempReader { 
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printCommonDecls
	void (*decRef)(TimedTempReaderPNTR pntr);
	bool stopped;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printStructChannelDecls
	chan_id output_comp;
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.DeclarationContainer::printLocationDecls
// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.DeclarationContainer::locationDecls

} ;

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorArrayFunctionDecls

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printComponentFuncsDecls

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printConstructorSignatures
extern void Construct_TimedTempReader0( TimedTempReaderPNTR this, int _argc, void* _argv[] ) ;

// Generated from: uk.ac.stand.cs.insense.compiler.incesosCCgen.Component::printBehaviourForwardDecl
extern void behaviour_TimedTempReader( TimedTempReaderPNTR this ) ;
#endif /* TIMEDTEMPREADER_H_*/ 

