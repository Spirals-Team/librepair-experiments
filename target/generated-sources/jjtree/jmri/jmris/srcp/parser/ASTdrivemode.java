/* Generated By:JJTree: Do not edit this line. ASTdrivemode.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jmri.jmris.srcp.parser;

public
class ASTdrivemode extends SimpleNode {
  public ASTdrivemode(int id) {
    super(id);
  }

  public ASTdrivemode(SRCPParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SRCPParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=d5cf3ced054ed8176c398b57aa023d14 (do not edit this line) */
