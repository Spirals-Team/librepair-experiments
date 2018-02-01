Template definition
1) compilable easy understandable Template
2) The good names of template parameters - well assigned to AST nodes
3) even the attributes of AST nodes might be a parameters (e.g. modifier of class)
 
Notes:
- it doesn't matter what is the current AST node at place of parameter. It will be replaced by parameter value converted to instance of expected type
- we have to define which Types, methodNames are variables and which already have required value 
- we have to define which statements, expressions are optional/mandatory
	e.g. by
	if (optional1) {
		...some optional statements...
	}

Generation of code from Template
1) filling template parameters by values
2) cloning template AST
3) substituting cloned AST parameter nodes by values

Types of template parameters
A) AST node of type CtStatement, CtExpression (e.g. CtVariableAccess, ...)
B) replacing of CtTypeReference by another CtTypeReference
C) replacing of whole or part of simpleName or Reference.name by String value
D) replacing of any attribute of AST node by value of appropriate type

Searching for code, which matches template
1) definition of filters on searched nodes
2) matching with AST
3) creating of Map/Structure of parameter to matching value from AST


{@link Pattern} knows the AST of the pattern model.
It knows list of parts of pattern model, which are target for substitution.

The substitution target can be:
A) node replace parameters - it means whole AST node (subtree) is replaced by value of parameter
		The type of such value is defined by parent attribute which holds this node:
		Examples: CtTypeMember, CtStatement, CtExpression, CtParameter, ...
		A1) Single node replace parameter - there must be exactly one node (with arbitrary subtree) as parameter value
			Examples:
				CtCatch.parameter, CtReturn.expression, CtBinaryOperator.leftOperand,
				CtForEach.variable,
				CtLoop.body,
				CtField.type
				 ...
		A2) Multiple nodes replace parameter - there must be 0, 1 or more nodes (with arbitrary subtrees) as parameter value
			Examples:
				CtType.interfaces, CtType.typeMembers
		note: There can be 0 or 1 parameter assigned to model node

Definition of such CtElement based parameters:
------------------------------
- by `TemplateParameter.S()` - it works only for some node types. Does not work for CtCase, CtCatch, CtComment, CtAnnotation, CtEnumValue, ...
- by pointing to such node(s) - it works for all nodes. How? During  building of Pattern, the client's code has to somehow select the parameter nodes
			and add them into list of to be substituted nodes. Client may use
			- Filter ... here we can filter for `TemplateParameter.S()`
			- CtPath ... after it is fully implemented
			- Filtering by their name - legacy templates are using that approach together with Parameter annotation
			- manual navigation and collecting of substituted nodes

B) node attribute replace - it means value of node attribute is replaced by value of parameter
		B1) Single value attribute - there must be exactly one value as parameter value
			Types are String, boolean, BinaryOperatorKind, UnaryOperatorKind, CommentType, primitive type of Literal.value
		B2) Unordered multiple value attribute - there must be exactly one value as parameter value
			There is only: CtModifiable.modifiers with type Enum ModifierKind

		note: There can be no parameter of type (A) assigned to node whose attributes are going to be replaced.
			There can be more attributes replaced for one node
			But there can be 0 or 1 parameter assigned to attribute of model node

Definition of such Object based parameters:
------------------------------------------------------
by pointing to such node(s)
	+ with specification of CtRole of that attribute

C) Substring attribute replace - it means substring of string value is replaced
		Examples: CtNamedElement.simpleName, CtStatement.label, CtComment.comment

		note: There can be no parameter of type (A) assigned to node whose String attributes are going to be replaced.
			There can be 0, 1 or more parameter assigned to String of model node. Each must have different identifier.

Definition of such parameters:
------------------------------
by pointing to such node(s)
	+ with specification of CtRole of that attribute
	+ with specification of to be replaced substring
It can be done by searching in all String attributes of each node and searching for a variable marker. E.g. "$var_name$"

Optionally there might be defined a variable value formatter, which assures that variable value is converted to expected string representation

Why {@link Pattern} needs such high flexibility?
Usecase: The Pattern instance might be created by comparing of two similar models (not templates, but part of normal code).
All the differences anywhere would be considered as parameters of generated Pattern instance.
Request: Such pattern instance must be printable and compilable, so client can use it for further matching and replacing by different pattern.


Why ParameterInfo type?
----------------------
Can early check whether parameter values can be accepted by Pattern
Needs a validation by SubstitutionRequests of ParameterInfo
Can act as a filter of TemplateMatcher parameter value