package com.lti.recast.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.lti.recast.web.model.PowerBITargetConParamsModel;
import com.lti.recast.web.model.StrategizerCalculatedFormulaModel;
import com.lti.recast.web.model.StrategizerQueryModel;
import com.lti.recast.web.model.StrategizerVisualizationConvertor;
import com.opencsv.CSVWriter;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;

public class CSVGenerationUtility {


//	private static void compareToIgnoreCase(int counter) {
//		// TODO Auto-generated method stub
//		counter++;
//	}


	private static void generateCSVFile(List<List<String>> queryList, String path,String fileName) throws IOException {
		// TODO Auto-generated method stub
		
		//System.out.println("In generate CSV");
		
		System.out.println("Path::"+path+"//"+fileName);
		
		List<String[]> csvDetailsStringArray = StrategizerUtility.convertToListStringArray(queryList);
		CSVWriter writer = new CSVWriter(new FileWriter(path+"//"+fileName));
		writer.writeAll(csvDetailsStringArray);
	    writer.flush();
	}

	
	public void generateCSVForCalculatedColumns(List<List<String>> formulaList, String fileName,int stratId) throws IOException {
		// TODO Auto-generated method stub
		
		//System.out.println("In generate CSV");
		
		String path = createTabFolder(fileName,stratId);
		List<String[]> csvDetailsStringArray = StrategizerUtility.convertToListStringArray(formulaList);
		CSVWriter writer = new CSVWriter(new FileWriter(path+"//calculated_column.csv"));
		writer.writeAll(csvDetailsStringArray);
	    writer.flush();
	}

	
	private String createTabFolder(String csvPath,int stratId) {
		// TODO Auto-generated method stub
		
		
		String reportId = csvPath.split("~")[0];
		String reportTabId = csvPath.split("~")[1];
		
		  try {
			  
			  FileReader reader=new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/strategizer/strategizer.properties");
			  Properties p=new Properties();
			  p.load(reader);
			  
			// System.out.println(p.getProperty("CSVOutputPath"));
			  String csvOutputPath = p.getProperty("CSVOutputPath");
			  
			   Path path = Paths.get(csvOutputPath+stratId+"//"+reportId+"//"+reportTabId);

			    //java.nio.file.Files;
			    Files.createDirectories(path);

			    System.out.println("Directory is created!");
			    
			    String finalPath = path.toString();
			    return finalPath;
			    
			  } catch (IOException e) {
				  
				  
			    System.err.println("Failed to create directory!" + e.getMessage());
			    return null;
			  }
		  
		 // return null;
	}


	public void createFolder(Set<String> reportIdSet,int stratId) {
		// TODO Auto-generated method stub
		
		Iterator<String> iter = reportIdSet.iterator();
		
		while(iter.hasNext())
		{
			String reportId = iter.next();
			try {
				FileReader fileReader = new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/strategizer/strategizer.properties");
				Properties p=new Properties();
				 p.load(fileReader);
				 
				 String csvOutputPath = p.getProperty("CSVOutputPath");
				 Path path = Paths.get(csvOutputPath+stratId+"//"+reportId);
				 Files.createDirectories(path);
				 System.out.println("Directory is created!");
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Failed to create directory!" + e.getMessage());
				e.printStackTrace();
			}
		}
		
	}


	public void generateCSVForQuery(List<StrategizerQueryModel> strategizerQueryModelList,Set<String> reportIdSet,int stratId) {
		// TODO Auto-generated method stub
		System.out.println("In generate CSV for Query");
		
		FileReader fileReader;
		try {
			fileReader = new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/strategizer/strategizer.properties");
			Properties p=new Properties();
			p.load(fileReader);
			
			reportIdSet.forEach(x->{
				
				List<List<String>> csvDetailsList = new ArrayList<List<String>>();
				
				strategizerQueryModelList.forEach(y->{
					
					if(y.getReportId().equalsIgnoreCase(x))
					{
						List<String> csvRows = new ArrayList<String>();
						
						csvRows.add(y.getDatabaseType());
						csvRows.add(y.getHostname());
						csvRows.add(y.getDatabaseName());
						csvRows.add(y.getConvertedQueryStatement());
						csvRows.add(y.getQueryName());
						csvRows.add(y.getConvertedQueryName());
						csvDetailsList.add(csvRows);
					}
					
					
				});
				 String csvOutputPath = p.getProperty("CSVOutputPath");
				 String path = Paths.get(csvOutputPath+stratId+"//"+x).toString();
		
				try {
					generateCSVFile(csvDetailsList,path,"datasource.csv");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
	}


	public void generateCSVForCalculatedVariablesMeasures(
			List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaList, Set<String> reportIdSet,int stratId) {
		// TODO Auto-generated method stub
		
		System.out.println("In generate CSV for Calculated Measures and Variables");
		
		FileReader fileReader;
		try {
			fileReader = new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/strategizer/strategizer.properties");
			Properties p=new Properties();
			p.load(fileReader);
			
			reportIdSet.forEach(x->{
				List<List<String>> csvDetailsList = new ArrayList<List<String>>();
				List<List<String>> csvDetailsMeasureList = new ArrayList<List<String>>();
				strategizerCalculatedFormulaList.forEach(y->{
					
					if(y.getReportId().equalsIgnoreCase(x))
					{
						if(!y.getColumnQualification().equalsIgnoreCase("Measure"))
						{
							List<String> csvRows = new ArrayList<String>();
							csvRows.add(y.getCalculatedFormula());
							csvDetailsList.add(csvRows);
						}
						else
						{
							List<String> csvRows = new ArrayList<String>();
							csvRows.add(y.getCalculatedFormula());
							csvDetailsMeasureList.add(csvRows);
						}
					}
					
				});
				
				 String csvOutputPath = p.getProperty("CSVOutputPath");
				 String path = Paths.get(csvOutputPath+stratId+"//"+x).toString();
				
				 try {
						generateCSVFile(csvDetailsList,path,"calculated_column.csv");
						generateCSVFile(csvDetailsMeasureList,path,"measure.csv");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			});
			
		}
		 catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
	}


	public void generateCSVForVisualization(List<StrategizerVisualizationConvertor> strategizerVisualConvertorList,
			Set<String> reportIdSet,int stratId) {
		// TODO Auto-generated method stub
		System.out.println("In generate CSV for Visualization ");
		
		FileReader fileReader;
		try {
			fileReader = new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/strategizer/strategizer.properties");
			Properties p=new Properties();
			p.load(fileReader);
			
			reportIdSet.forEach(x->{
				
				List<List<String>> csvDetailsList = new ArrayList<List<String>>();
				List<List<String>> csvDetailsFooterList = new ArrayList<List<String>>();
				List<List<String>> csvDetailsChartList = new ArrayList<List<String>>();
				strategizerVisualConvertorList.forEach(y->{
					
					if(y.getReportId().equalsIgnoreCase(x))
					{
						
						if(y.getParentElement().equalsIgnoreCase("Header"))
						{
							List<String> csvRows = addRowsForVisualization(y);
							
							csvDetailsList.add(csvRows);
						}
						else if(y.getParentElement().equalsIgnoreCase("Footer"))
						{
							List<String> csvRows = addRowsForVisualization(y);
							csvDetailsFooterList.add(csvRows);
						}
						else if(y.getParentElement().equalsIgnoreCase("Body"))
						{
							List<String> csvRows = addRowsForVisualization(y);
							csvDetailsChartList.add(csvRows);
						}

					}
					
				});
				
				 String csvOutputPath = p.getProperty("CSVOutputPath");
				 String path = Paths.get(csvOutputPath+stratId+"//"+x).toString();
				
				 try {
						generateCSVFile(csvDetailsList,path,"header.csv");
						generateCSVFile(csvDetailsFooterList,path,"footer.csv");
						generateCSVFile(csvDetailsChartList,path,"chart.csv");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			});
			
			
		}
			 catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
	}


	private List<String> addRowsForVisualization(StrategizerVisualizationConvertor y) {
		// TODO Auto-generated method stub
		
		List<String> csvRows = new ArrayList<String>();
		csvRows.add(y.getTargetComponentName());
		csvRows.add(y.getTargetPositionX());
		csvRows.add(y.getTargetPositionY());
		csvRows.add(y.getTargetMinimalWidth());
		csvRows.add(y.getTargetMinimalHeight());
		csvRows.add(y.getFormulaName());
		csvRows.add(y.getFont());
		csvRows.add(y.getColor());
		csvRows.add(y.getValueType());
		return csvRows;
	}
	
	
//	 public void generateCSVQueryFields(List<List<String>> csvDetailsList,String x)
//	 {
//		 FileReader fileReader;
//			try {
//				fileReader = new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/strategizer/strategizer.properties");
//				Properties p=new Properties();
//				p.load(fileReader);
//				String csvOutputPath = p.getProperty("CSVOutputPath");
//				String path = Paths.get(csvOutputPath+x).toString();
//				try {
//					generateCSVFile(csvDetailsList,path,"query_with_fields.csv");
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//			catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//	 }
	 
	 public void generateCSVPowerBiPublish(String reportName,String x, PowerBITargetConParamsModel pbiTargetConParams,int stratId)
	 {
		 FileReader fileReader;
			try {
				fileReader = new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/strategizer/strategizer.properties");
				Properties p=new Properties();
				p.load(fileReader);
				List<List<String>> csvDetailsList = new ArrayList<List<String>>();
				List<String> csvRows = new ArrayList<String>();
//				csvRows.add("https://app.powerbi.com/home");
//				csvRows.add("arunbalaji.g@lntinfotech.com");
//				csvRows.add("password");
//				csvRows.add("Power BI Workspace");
				
				csvRows.add(pbiTargetConParams.getServiceUrl());
				csvRows.add(pbiTargetConParams.getUsername());
				csvRows.add(pbiTargetConParams.getPassword());
				csvRows.add(pbiTargetConParams.getWorkspace());
				
				csvRows.add(reportName);
				csvDetailsList.add(csvRows);
				String csvOutputPath = p.getProperty("CSVOutputPath");
				String path = Paths.get(csvOutputPath+stratId+"//"+x).toString();
				try {
					generateCSVFile(csvDetailsList,path,"powerBI_publish.csv");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 }


	public void generateCSVQueryFields(List<StrategizerQueryModel> strategizerQueryModelList, Set<String> reportIdSet,int stratId) {
		// TODO Auto-generated method stub
		
		FileReader fileReader;
		
		try {
			fileReader = new FileReader(getClass().getClassLoader().getResource("").getPath().substring(1).replaceAll("%20", " ")+"/strategizer/strategizer.properties");
			Properties p=new Properties();
			p.load(fileReader);
			
			reportIdSet.forEach(x->{
				
				List<List<String>> csvDetailsList = new ArrayList<List<String>>();
				
				strategizerQueryModelList.forEach(y->{
					
					if(y.getReportId().equalsIgnoreCase(x))
					{
						List<String> csvRows = new ArrayList<String>();
						
						String queryName = y.getConvertedQueryName();
						csvRows.add(queryName);
						
						String query = y.getConvertedQueryStatement();
						
						try {
							csvRows.addAll(fetchColumnAlias(query));
							
							
						} catch (JSQLParserException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						csvDetailsList.add(csvRows);
					}
					
					
				});
				
				 String csvOutputPath = p.getProperty("CSVOutputPath");
				 String path = Paths.get(csvOutputPath+stratId+"//"+x).toString();
			
				 try {
						generateCSVFile(csvDetailsList,path,"query_with_fields.csv");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			});
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	private List<String> fetchColumnAlias(String sql) throws JSQLParserException 
	{
		System.out.println("Sql::"+sql);
		
		List<String> aliasList = new ArrayList<String>();
	    Statement statement = CCJSqlParserUtil.parse(sql);
	    Select selectStatement = (Select) statement;
	    for (SelectItem selectItem : ((PlainSelect)selectStatement.getSelectBody()).getSelectItems()) {
	    selectItem.accept(new SelectItemVisitorAdapter() {
	    @Override
	    public void visit(SelectExpressionItem item) {
	    if(item.getAlias()!=null)
	    {
	    	aliasList.add(item.getAlias().getName().toString().replaceAll("\'",""));
	    }
	    }
	    });
	    }
	   
	    System.out.println("Alias List ::"+aliasList);
	    
	    return aliasList;
	}
	
	
}
