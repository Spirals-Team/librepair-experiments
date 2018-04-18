package parser;

import java.util.ArrayList;
import java.util.List;

import evaluator.AndExpression;
import evaluator.ByteInOffsetExpression;
import evaluator.Expression;
import model.RawPackage;

public class Parser {

	
	public Expression<RawPackage> parse(PackageRulesFile packageRulesFile){
		
		List<Expression<RawPackage>> expressions = new  ArrayList<>();
		
		for (PackageRule r: packageRulesFile){
			expressions.add(new ByteInOffsetExpression(r.getOffset(), r.getByte()));
		}
		
		return new AndExpression<RawPackage>(expressions);
	}
	
	
}
