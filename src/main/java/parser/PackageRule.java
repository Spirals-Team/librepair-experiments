package parser;

public class PackageRule {

	int offset; 
	byte byteToCompare;
	
	public PackageRule(int offset, byte byteToCompare){
		this.offset = offset;
		this.byteToCompare = byteToCompare;
		
	}
	
	public int getOffset(){
		return this.offset;
	}
	
	public byte getByte(){
		return this.byteToCompare;
	}
	
}
