/* Generated By:JJTree: Do not edit this line. ASTsensordevice.java Version 7.0 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package jmri.jmris.simpleserver.parser;

public
class ASTsensordevice extends SimpleNode {
  public ASTsensordevice(int id) {
    super(id);
  }

  public ASTsensordevice(JmriServerParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(JmriServerParserVisitor visitor, Object data) {

    return
    visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=42740b150a097e52f6482934278bc3e0 (do not edit this line) */
