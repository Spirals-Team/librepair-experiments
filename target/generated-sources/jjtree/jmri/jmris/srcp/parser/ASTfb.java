/* Generated By:JJTree: Do not edit this line. ASTfb.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jmri.jmris.srcp.parser;

public
class ASTfb extends SimpleNode {
  public ASTfb(int id) {
    super(id);
  }

  public ASTfb(SRCPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SRCPParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=53aa9dc3965ed75754ec9d47381ac1f1 (do not edit this line) */
