package com.lti.recast.commons.semanticvalueobjects;

//import com.sap.sl.sdk.authoring.datafoundation.Cardinality;
//import com.sap.sl.sdk.authoring.datafoundation.OuterType;

public class JoinColumn {
	private String joinExpression;
//	private OuterType joinType;
//	private Cardinality joinCardinality;
	
	public String getJoinExpression() {
		return joinExpression;
	}
	public void setJoinExpression(String joinExpression) {
		this.joinExpression = joinExpression;
	}
//	public OuterType getJoinType() {
//		return joinType;
//	}
//	public void setJoinType(OuterType joinType) {
//		this.joinType = joinType;
//	}
//	public Cardinality getJoinCardinality() {
//		return joinCardinality;
//	}
//	public void setJoinCardinality(Cardinality joinCardinality) {
//		this.joinCardinality = joinCardinality;
//	}
}
