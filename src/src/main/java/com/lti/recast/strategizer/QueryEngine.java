package com.lti.recast.strategizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lti.recast.jpa.entity.AnalysisReport;
import com.lti.recast.jpa.entity.AnalysisReportsTable;
import com.lti.recast.jpa.entity.AnalysisSemanticColumns;
import com.lti.recast.util.ModelBuilder;
import com.lti.recast.util.StrategizerUtility;
import com.lti.recast.web.model.QueryColumnModel;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;

public class QueryEngine {

	public Map<String, List<List<String>>> createModifiedQuery(AnalysisReport analysisReport, List<AnalysisSemanticColumns> semanticColumns,List<AnalysisReportsTable> reportTableList) {
		// TODO Auto-generated method stub
		
		System.out.println("Inside Create modified Query method");
		
		//System.out.println("Semantic Column List::"+semanticColumns);
		
		 //Map<String, List<String>> queryStringMap = new LinkedHashMap<String, List<String>>();
		Map<String, List<List<String>>> queryStringMap = new LinkedHashMap<String, List<List<String>>>();

		//Fetch Query for the Report 
		String queryData =  analysisReport.getQueryList();
		
		JSONArray queryJSONArray = new JSONArray(queryData);
		
	//	System.out.println("QueryJSONArray:"+queryJSONArray);

		
		for(int i =0;i<queryJSONArray.length();i++)
		{
			JSONObject queryJsonObj = queryJSONArray.getJSONObject(i);
			
			String queryId = queryJsonObj.getString("queryId");
			String queryName = queryJsonObj.getString("queryName");
			//List<String> statementUpdatedList = new LinkedList<String>();
			
			List<List<String>> statementUpdatedList = new LinkedList<List<String>>();
			
			
			//System.out.println(queryJsonObj.get("queryStatements"));
			
			//Query Columns from DB
			
			List<QueryColumnModel> queryColumnList = new ArrayList<QueryColumnModel>();
			
			JSONArray queryColumnsJSONArray = queryJsonObj.getJSONArray("boReportObject");
			
			
			//System.out.println("Query column JSON Array::"+queryColumnsJSONArray.toString());
			
			for(int k=0;k<queryColumnsJSONArray.length();k++)
			{
				JSONObject querycolumnJSONObj = queryColumnsJSONArray.getJSONObject(k);
				
			//	System.out.println("Query column JSON Object::"+querycolumnJSONObj.toString());
				
				if(!querycolumnJSONObj.isNull("dataSourceObjectId"))
				{
					QueryColumnModel queryColumnModelObj = ModelBuilder.queryColumnModelBuilder(querycolumnJSONObj);	

					
					semanticColumns.forEach(x->{
						
						if(x.getObjectIdentifier().equalsIgnoreCase(queryColumnModelObj.getObjectId()))
						{
							//System.out.println("Matched::"+x.getColumnNames()+" functions::"+x.getFunctions());
							queryColumnModelObj.setAliasName(x.getColumnNames());
							
							//String functionName = StrategizerUtility.extractFunctionName(x.getFunctions());
							
							String functionName = StrategizerUtility.checkTableAliasFunctionName(x.getFunctions(),reportTableList);
							
							//System.out.println("Function Name::"+functionName);
							queryColumnModelObj.setExpression(functionName);
							queryColumnList.add(queryColumnModelObj);
							
						}

					});
				
				}
			}
			System.out.println(queryColumnList.size());
			JSONArray queryStatementJSONArray = queryJsonObj.getJSONArray("queryStatements");
			
			//System.out.println("Query Statement json Array::"+queryStatementJSONArray.toString());
			
			for(int j = 0;j<queryStatementJSONArray.length();j++)
			{
				Map<String,Alias> columnAliasMap = new LinkedHashMap<String, Alias>();
				System.out.println(queryStatementJSONArray.get(j));
				String queryStatement = queryStatementJSONArray.getString(j);
				//System.out.println("Statements::"+queryStatement);
				
				List<String>selectColumnList =  StrategizerUtility.parseQuery(queryStatement);
				//System.out.println("Select Column List ::"+selectColumnList);
				
				selectColumnList.forEach(x->{
					
					String aliasName = fetchAliasName(x,queryColumnList);
					//System.out.println("Column name:"+x+" Alias Name: "+aliasName);
					aliasName= "'"+aliasName+"'";
					
					Alias aliasNameModified = new Alias(aliasName);
					
					columnAliasMap.put(x, aliasNameModified);
				});
				
				List<String> queryListData = new LinkedList<String>();
				
				queryListData.add(queryStatement);
				String queryUpdated = modifyQuerySelectItem(queryStatement,columnAliasMap);
				//System.out.println("Final Query::"+queryUpdated);
				queryListData.add(queryUpdated);

				statementUpdatedList.add(queryListData);
			}
			
			queryStringMap.put(queryId+"~"+queryName, statementUpdatedList);
			
		}
		
		return queryStringMap;
	}


	
	//Fetch the alias name by comparing it with semantic List
	
	private String fetchAliasName(String selectColumnName, List<QueryColumnModel> queryColumnList) {
		// TODO Auto-generated method stub
		
		Iterator<QueryColumnModel> iter = queryColumnList.iterator();
		
		System.out.println("Select Column NAme::"+selectColumnName);
		

		while(iter.hasNext())
		{
			boolean flag = false;
			
			QueryColumnModel queryColumnModel = iter.next();
			
			String expression = queryColumnModel.getExpression();
		//	System.out.println("Expression::"+expression);
			
			//if(expression.startsWith("@Aggregate_Aware(\")"))
			if(expression.startsWith("@Aggregate_Aware("))
			{
				//System.out.println("Inside Aggregate Aware");
				flag=true;
				expression = StrategizerUtility.extractFunctionName(expression);
			}
				
			
			if(expression.contains(",") && flag)
			{
				String splitColumn[] = expression.split(",");
				
				for(int i=0;i<splitColumn.length;i++)
				{
					String expressionName = splitColumn[i];
					
					expressionName = expressionName.replaceAll("\\s", "");
					selectColumnName= selectColumnName.replaceAll("\\s", "");
					
					//System.out.println("Expression NAme::"+expressionName);
					//System.out.println("Select Column NAme::"+selectColumnName);
					
					if(expressionName.equalsIgnoreCase(selectColumnName))
					{
						return queryColumnModel.getColumnName();
					}
				}
			}
			else
			{
				expression = expression.replaceAll("\\s", "");
				selectColumnName= selectColumnName.replaceAll("\\s", "");

				if(expression.equalsIgnoreCase(selectColumnName))
				{
					return queryColumnModel.getColumnName();
				}
				else if(selectColumnName.equalsIgnoreCase("MAX('user1')") && expression.equalsIgnoreCase("MAX(@Variable('BOUSER'))"))
				{
					return queryColumnModel.getColumnName();
				}
			}
			
			
		}

		return null;
	}
	
	
	//Add alias to the Query
	
	
	private String modifyQuerySelectItem(String queryStatement, Map<String, Alias> columnAliasMap) {
		// TODO Auto-generated method stub

		try {
			Select stmt = (Select) CCJSqlParserUtil.parse(queryStatement);
			
			for (SelectItem selectItem : ((PlainSelect)stmt.getSelectBody()).getSelectItems()) {
			    selectItem.accept(new SelectItemVisitorAdapter() {
			        @Override
			        public void visit(SelectExpressionItem item) {
			        	//System.out.println(item.toString());
			        	item.setAlias(columnAliasMap.get(selectItem.toString()));
			        		
			        }
			    });
			}
			
			return stmt.toString();
			
		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	
	
}
