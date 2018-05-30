/* Generated By:JavaCC: Do not edit this line. SRCPParserVisitor.java Version 7.0.3 */
package jmri.jmris.srcp.parser;

public interface SRCPParserVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASThandshakecommand node, Object data);
  public Object visit(ASTcommand node, Object data);
  public Object visit(ASTserviceversion node, Object data);
  public Object visit(ASTconnectionmode node, Object data);
  public Object visit(ASTbus node, Object data);
  public Object visit(ASTaddress node, Object data);
  public Object visit(ASTvalue node, Object data);
  public Object visit(ASTcvno node, Object data);
  public Object visit(ASTzeroaddress node, Object data);
  public Object visit(ASTnonzeroaddress node, Object data);
  public Object visit(ASTport node, Object data);
  public Object visit(ASTdevicegroup node, Object data);
  public Object visit(ASTgo node, Object data);
  public Object visit(ASTget node, Object data);
  public Object visit(ASThandshake_set node, Object data);
  public Object visit(ASTprotocollitteral node, Object data);
  public Object visit(ASTconnectionlitteral node, Object data);
  public Object visit(ASTset node, Object data);
  public Object visit(ASTterm node, Object data);
  public Object visit(ASTcheck node, Object data);
  public Object visit(ASTwait_cmd node, Object data);
  public Object visit(ASTinit node, Object data);
  public Object visit(ASTreset node, Object data);
  public Object visit(ASTverify node, Object data);
  public Object visit(ASTgl node, Object data);
  public Object visit(ASTsm node, Object data);
  public Object visit(ASTga node, Object data);
  public Object visit(ASTfb node, Object data);
  public Object visit(ASTtime node, Object data);
  public Object visit(ASTpower node, Object data);
  public Object visit(ASTserver node, Object data);
  public Object visit(ASTsession node, Object data);
  public Object visit(ASTlock node, Object data);
  public Object visit(ASTdescription node, Object data);
  public Object visit(ASTonoff node, Object data);
  public Object visit(ASTzeroone node, Object data);
  public Object visit(ASTdelay node, Object data);
  public Object visit(ASTtimeout node, Object data);
  public Object visit(ASTprogmode node, Object data);
  public Object visit(ASTcv node, Object data);
  public Object visit(ASTcvbit node, Object data);
  public Object visit(ASTreg node, Object data);
  public Object visit(ASTprotocol node, Object data);
  public Object visit(ASTdrivemode node, Object data);
  public Object visit(ASTfunctionmode node, Object data);
}
/* JavaCC - OriginalChecksum=b98c749658a3d9bb72a650c23c41d363 (do not edit this line) */
