/* Generated By:JJTree: Do not edit this line. ASTturnout.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jmri.jmris.simpleserver.parser;

public
class ASTturnout extends SimpleNode {
  public ASTturnout(int id) {
    super(id);
  }

  public ASTturnout(JmriServerParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JmriServerParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=fa4c6faf903a5eb2f90a73b17fd924ba (do not edit this line) */
