/* Generated By:JJTree: Do not edit this line. ASTprogmode.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jmri.jmris.srcp.parser;

public
class ASTprogmode extends SimpleNode {
  public ASTprogmode(int id) {
    super(id);
  }

  public ASTprogmode(SRCPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SRCPParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=7bbb06f9b307f6fee83fa98ff86ef63d (do not edit this line) */
