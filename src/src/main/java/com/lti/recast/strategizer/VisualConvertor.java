package com.lti.recast.strategizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lti.recast.jpa.entity.AnalysisReport;
import com.lti.recast.jpa.entity.StrategizerQueryConversion;
import com.lti.recast.jpa.entity.VisualDetails;
import com.lti.recast.util.LexicalCalculation;
import com.lti.recast.util.StrategizerUtility;
import com.lti.recast.web.model.StrategizerCalculatedFormulaModel;

public class VisualConvertor {

	
	public static HashMap<String,String> selfJoin(List<VisualDetails> visualDetailsList)
	{
		HashMap<String,String> selfJoinMap = new HashMap<String,String>();
		for(int i=0;i<visualDetailsList.size();i++)
		{
			if(visualDetailsList.get(i).getElementType().equals("Page Zone"))
			{
				selfJoinMap.put(visualDetailsList.get(i).getElementName(),visualDetailsList.get(i).getElementId());
			}
		}
		return selfJoinMap;
	}
	
	
//	private static String fetchQueryName(String formulaName,List<AnalysisReport> analysisReportList,String reportId) {
//		// TODO Auto-generated method stub
//		
//		//System.out.println("Formula NAme::"+formulaName);
//		
//		formulaName = "["+formulaName+"]";
//		
//		//System.out.println("Formula name::"+formulaName);
//		Iterator<AnalysisReport> iter = analysisReportList.iterator();
//			
//			while(iter.hasNext())
//			{
//				AnalysisReport x = iter.next();
//				if(x.getReportId().equalsIgnoreCase(reportId))
//				{
//					
//					String queryListJSONString = x.getQueryList();
//					
//					JSONArray queryJSONArray = new JSONArray(queryListJSONString);
//					
//					for(int i=0;i<queryJSONArray.length();i++)
//					{
//						JSONObject queryJSONObj = queryJSONArray.getJSONObject(i);
//						
//						String queryName = queryJSONObj.getString("queryName");
//						
//						//System.out.println("Query name::"+queryName);
//						
//						JSONArray queryColumnJSONArray =  queryJSONObj.getJSONArray("boReportQueryColumns");
//						
//						for(int j=0;j<queryColumnJSONArray.length();j++)
//						{
//							
//							JSONObject queryColumnJSONObj = queryColumnJSONArray.getJSONObject(j);
//							
//							//System.out.println("Query column json array:"+queryColumnJSONArray.toString());
//							
//							if(!queryColumnJSONObj.isNull("columnExpression"))
//							{
//								//System.out.println("Query column::"+queryColumnJSONObj.getString("columnExpression"));
//								if(queryColumnJSONObj.getString("columnExpression").equalsIgnoreCase(formulaName))
//								return queryName;		
//									
//							}
//														
//						}	
//					}
//
//				}
//			}
//		return "";
//
//	}




	public static List<StrategizerCalculatedFormulaModel> visualPowerBICalculatedFormula(List<VisualDetails> visualDetailsList, String key,
			List<AnalysisReport> analysisReportList, List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaModelList, List<StrategizerQueryConversion> strategizerQueryConversionList) {
		// TODO Auto-generated method stub
		
	//	Map<String, List<String>> calculatedFormulaMap = new LinkedHashMap<String,List<String>>();
		
		List<String> elementIdList = new LinkedList<String>();
		
		for(int i=0;i<visualDetailsList.size();i++)
		{
			if(visualDetailsList.get(i).getElementType().equalsIgnoreCase("VTable"))
			{
				elementIdList.add(visualDetailsList.get(i).getElementId());
			}
		}

		String reportId = key.split("~")[0];
		String reportTabId= key.split("~")[1];
		
		LexicalCalculation lexicalCalc = new LexicalCalculation();
		
		int counter = 1;
		
		Iterator<VisualDetails> iter = visualDetailsList.iterator();
		
		while(iter.hasNext())
		{
			VisualDetails x= iter.next();
			
			String formula ="";
			
			String queryName="";
			
			for(int j=0;j<strategizerQueryConversionList.size();j++)
			{
				StrategizerQueryConversion queryConversion = strategizerQueryConversionList.get(j);
				if(queryConversion.getReportId().equalsIgnoreCase(x.getReportId()))
				{
					queryName= queryConversion.getConvertedQueryName();
					System.out.println("QueryName::"+queryName+" Reportid :"+queryConversion.getReportId());
				}
				
			}
			
			
			
			if(x.getElementType().equalsIgnoreCase("Visualization"))
			{
				JSONArray chartAxesJSONArray = new JSONArray(x.getChartAxes());
				
				for(int i=0;i<chartAxesJSONArray.length();i++)
				{
					JSONObject chartJSONObj = chartAxesJSONArray.getJSONObject(i);
					
					//System.out.println("Chart JSON Object:"+chartJSONObj);
					
					if(!chartJSONObj.isNull("formulaList"))
					{
						JSONArray formulaJSONArray = chartJSONObj.getJSONArray("formulaList");
						
						for(int j=0;j<formulaJSONArray.length();j++)
						{
							JSONObject formulaJSONObj = formulaJSONArray.getJSONObject(j);
							
							if(formulaJSONObj.isNull("dataObjectId") && formulaJSONObj.isNull("name") && x.getChartType().equalsIgnoreCase("LinearGauge"))
							{
								StrategizerCalculatedFormulaModel stratategizerCalculatedModelObj = new StrategizerCalculatedFormulaModel();
								
								String finalCounter = StrategizerUtility.counterPadding(counter);
								
								formula ="formula"+finalCounter;
								
								String formulaValue=formula+"=0";
							
								counter++;

								stratategizerCalculatedModelObj.setColumnQualification("Unnamed Dimension");
								stratategizerCalculatedModelObj.setCalculatedFormula(formulaValue);
								stratategizerCalculatedModelObj.setFormula("0");
								stratategizerCalculatedModelObj.setReportId(reportId);
								stratategizerCalculatedModelObj.setReportTabId(reportTabId);
								strategizerCalculatedFormulaModelList.add(stratategizerCalculatedModelObj);
							}
							
							else if(formulaJSONObj.isNull("dataObjectId") && !(formulaJSONObj.isNull("name")))
							{
								
								StrategizerCalculatedFormulaModel stratategizerCalculatedModelObj = new StrategizerCalculatedFormulaModel();
								
								
								String formulaName = formulaJSONObj.getString("name");
								
								Pattern p = Pattern.compile("\\[(.*?)\\]");
								Matcher m = p.matcher(formulaName);

								while(m.find()) {
								 
								   // String queryName=fetchQueryName(m.group(1),analysisReportList,x.getReportId());
	
									String finalCounter = StrategizerUtility.counterPadding(counter);

									
									formula ="formula"+finalCounter;
									
								//	String formulaValue = queryName+formulaName.substring(1);
									
								//	formulaValue=formula+"="+formulaValue;
									
									//counter++;
									
									stratategizerCalculatedModelObj.setReportId(reportId);
									stratategizerCalculatedModelObj.setReportTabId(reportTabId);
									stratategizerCalculatedModelObj.setColumnQualification("Unnamed Dimension");
									stratategizerCalculatedModelObj.setFormula(formulaName);
									//stratategizerCalculatedModelObj.setCalculatedFormula(formulaValue);
									
									try {
										String result = "";
										
										if(formulaName.startsWith("="))
										{
											result = lexicalCalc.lexicalAnalysis(formulaName,queryName);
											if(!result.equalsIgnoreCase("error"))
											{
												formula=formula+result;
												counter++;
												//csvRows.add(formulaCellArrayData[0]);
												stratategizerCalculatedModelObj.setCalculatedFormula(formula);
											}
										}
										else
										{
											formula=formula+"="+"\""+formulaName+"\"";
											counter++;
											//csvRows.add(formulaCellArrayData[0]);
											stratategizerCalculatedModelObj.setCalculatedFormula(formula);
										}
										
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									strategizerCalculatedFormulaModelList.add(stratategizerCalculatedModelObj);
								}
							}

						}
					}
					
				}
			}
			else if(x.getElementType().equalsIgnoreCase("Cell") && !(elementIdList.contains(x.getParentId())))
			{
				String formulaCell = x.getFormula();
				
				if(formulaCell!=null && !formulaCell.equalsIgnoreCase("[]"))
				{
					if(formulaCell.contains("|"))
					{
						System.out.println("true");
					}
					else
					{
						//System.out.println("Formula Cell:"+formulaCell);
						String formulaData= formulaCell.substring(11,formulaCell.length()-1);
						
						System.out.println("Formula Data::"+formulaData);
						
						String [] formulaCellArrayData = formulaData.split(",");
						String formulaObjectId = formulaCellArrayData[4];
						
						System.out.println("Formula Object Id:"+formulaObjectId);
						
						if(formulaObjectId.equalsIgnoreCase("]")&& !formulaCellArrayData[0].equalsIgnoreCase(""))
						{
							StrategizerCalculatedFormulaModel stratategizerCalculatedModelObj = new StrategizerCalculatedFormulaModel();
							
							String finalCounter = StrategizerUtility.counterPadding(counter);
							formula="formula"+finalCounter;
							try {
								
								System.out.println("Formual Data Array 0::"+formulaCellArrayData[0]);
								String result="";
								
								if(formulaCellArrayData[0].startsWith("="))
								{
									result = lexicalCalc.lexicalAnalysis(formulaCellArrayData[0],queryName);
									if(!result.equalsIgnoreCase("error"))
									{
										formula=formula+result;
										counter++;
										//csvRows.add(formulaCellArrayData[0]);
										stratategizerCalculatedModelObj.setCalculatedFormula(formula);
									}
								}
								else
								{
									formula=formula+"="+"\""+formulaCellArrayData[0]+"\"";
									counter++;
									//csvRows.add(formulaCellArrayData[0]);
									stratategizerCalculatedModelObj.setCalculatedFormula(formula);
								}	
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							stratategizerCalculatedModelObj.setColumnQualification("Unnamed Dimension");
							stratategizerCalculatedModelObj.setReportId(reportId);
							stratategizerCalculatedModelObj.setReportTabId(reportTabId);
							stratategizerCalculatedModelObj.setFormula(formulaCellArrayData[0]);
							
							strategizerCalculatedFormulaModelList.add(stratategizerCalculatedModelObj);
						}
						
						
					}
				}
				
			}
			
			
			
		}

		return strategizerCalculatedFormulaModelList;
	}




	public static List<StrategizerCalculatedFormulaModel> fetchVariableList(AnalysisReport x,
			List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaModelList, 
			List<StrategizerQueryConversion> strategizerQueryConversionList) {
		// TODO Auto-generated method stub
		LexicalCalculation lexicalCalc = new LexicalCalculation();
		
		String queryName="";
		for(int j=0;j<strategizerQueryConversionList.size();j++)
		{
			StrategizerQueryConversion queryConversion = strategizerQueryConversionList.get(j);
			if(queryConversion.getReportId().equalsIgnoreCase(x.getReportId()))
			{
				queryName= queryConversion.getConvertedQueryName();
				System.out.println("QueryName::"+queryName+" Reportid :"+queryConversion.getReportId());
			}
			
		}
		
		
		String variables = x.getVariableList();
		
		if(!variables.equalsIgnoreCase("[]"))
		{
			JSONArray variableJSONArray = new JSONArray(variables);
			
			if(variableJSONArray.length()>0)
			{
				for(int i=0;i<variableJSONArray.length();i++)
				{
					StrategizerCalculatedFormulaModel strategizerCalculatedFormula = new StrategizerCalculatedFormulaModel();
					JSONObject variableJSONObj = variableJSONArray.getJSONObject(i);
					
					strategizerCalculatedFormula.setReportId(x.getReportId());
					strategizerCalculatedFormula.setReportTabId(" ");
					if(variableJSONObj.getString("qualification")!=null)
					{
						strategizerCalculatedFormula.setColumnQualification(variableJSONObj.getString("qualification"));
					}
					
					String formulaName = variableJSONObj.getString("name");
					String definition = variableJSONObj.getString("definition");
					System.out.println("Definition::"+definition);
					String changedFormula = "";
					try {
						
						String result = lexicalCalc.lexicalAnalysis(definition,queryName);
						if(!result.equalsIgnoreCase("error"))
						{
							changedFormula = formulaName +" "+result;
						}
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("Final Formula::"+changedFormula);
					
					strategizerCalculatedFormula.setFormula(formulaName+" "+definition);
					strategizerCalculatedFormula.setCalculatedFormula(changedFormula);
					strategizerCalculatedFormulaModelList.add(strategizerCalculatedFormula);
				}
				
				
			}

		}

		return strategizerCalculatedFormulaModelList;
	}
	
	
	
}
