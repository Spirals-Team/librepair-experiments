Structure:
* Element has one Position
* Position has one or more Fragments (for example fragments of CtClass are: modifiers, name, extends, implements, body)
* Fragment has startOffset, endOffset and one or more CtRoles (for exmaple CtRoles of CtClass.modifiers are annotation, modifier). CtRole comment is everywhere. 

We know which element is actually printed. DJPP#enter/exit can be used to detect that.

We need to know which Fragment of element is actually printed. E.g. by listenning on TokenWriter#writerKeyword

If no attribute of fragment of printed element is modified, then fragment can be copied from origin source code
If any attribute of fragment of printed element is modified, then fragment must be printed by DJPP

