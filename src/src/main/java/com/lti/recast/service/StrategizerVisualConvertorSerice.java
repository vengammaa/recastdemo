package com.lti.recast.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.recast.jpa.entity.SapboPowerbiMapping;
import com.lti.recast.jpa.entity.VisualDetails;
import com.lti.recast.jpa.repository.SapboPowerbiMappingRepository;
import com.lti.recast.util.StrategizerUtility;
import com.lti.recast.web.model.StrategizerCalculatedFormulaModel;
import com.lti.recast.web.model.StrategizerVisualizationConvertor;

@Service
public class StrategizerVisualConvertorSerice {
	
	@Autowired(required = false)
	private SapboPowerbiMappingRepository sapboPowerbiMappingRepository;
	
	static Double constval = 3600.0;
	
	
	@Transactional
	public List<StrategizerVisualizationConvertor> saveVisualConversionData(List<VisualDetails> filteredVisualDetailsList, List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaList) {
		// TODO Auto-generated method stub
		
		List<StrategizerVisualizationConvertor> strategizerVisualizationConvertorList = new LinkedList<StrategizerVisualizationConvertor>();
		
		Map<String,List<VisualDetails>> visualDetailsMap = new LinkedHashMap<String, List<VisualDetails>>();
		
		 filteredVisualDetailsList.forEach(visualListObj->{
				
				String key = visualListObj.getReportId()+"~"+visualListObj.getReportTabId();
				
				if(visualDetailsMap.containsKey(key))
				{
					List<VisualDetails> listValue = visualDetailsMap.get(key);
					listValue.add(visualListObj);
				}
				else
				{
					List<VisualDetails> listValue = new ArrayList<VisualDetails>();
					listValue.add(visualListObj);
					visualDetailsMap.put(key, listValue);
				}
				
			});
		
		 for (Entry<String, List<VisualDetails>> x : visualDetailsMap.entrySet())
		 {
			 HashMap<String,String> selfJoinMap = selfJoin(x.getValue());
			 
			 System.out.println("Self Join map::"+selfJoinMap);
			 
			 Double maxWidth = Double.parseDouble(x.getValue().get(0).getMaximumWidth());
			 Double maxHeight = Double.parseDouble(x.getValue().get(0).getMaximumHeight());
			 
			 double headerHeight = calcheaderHeight(x.getValue());
			 double bodyHeight = calculateBodyHeight(x.getValue(),selfJoinMap.get("Body"));
			 
			 
			 strategizerVisualizationConvertorList = visualPowerBIMappingHeader(x.getValue(),x.getKey(),selfJoinMap.get("Header"),maxWidth,maxHeight,strategizerVisualizationConvertorList,strategizerCalculatedFormulaList);
			 strategizerVisualizationConvertorList = visualPowerBIMappingFooter(x.getValue(),x.getKey(),selfJoinMap.get("Footer"),maxWidth,maxHeight,strategizerVisualizationConvertorList,strategizerCalculatedFormulaList,headerHeight,bodyHeight);
			 strategizerVisualizationConvertorList = visualPowerBIMappingVisual(x.getValue(),x.getKey(),selfJoinMap.get("Body"),maxWidth,maxHeight,strategizerVisualizationConvertorList,strategizerCalculatedFormulaList,headerHeight);
		 }

		return strategizerVisualizationConvertorList;
	}

	private List<StrategizerVisualizationConvertor> visualPowerBIMappingVisual(List<VisualDetails> visualDetailsList, 
			String key, String bodyId, Double maxWidth,
			Double maxHeight, List<StrategizerVisualizationConvertor> strategizerVisualizationConvertorList, 
			List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaList, double headerHeight) {
		// TODO Auto-generated method stub
		
		// List<StrategizerFeatureLog> featureLogList = new LinkedList<StrategizerFeatureLog>();	
		 List<StrategizerCalculatedFormulaModel> filteredStrategizerCalculatedFormulaList = strategizerCalculatedFormulaList.stream()
				 .filter(x ->x.getColumnQualification().equalsIgnoreCase("Unnamed Dimension"))
			      .collect(Collectors.toList());
		
		// Map<String,Double> recursiveCheck = new HashMap<String,Double>();
		 HashMap<String,Integer> elementMap = elementMap(visualDetailsList);
		
		for(int i=0;i<visualDetailsList.size();i++)
		{
			boolean positionFlag = false;
			if(!(visualDetailsList.get(i).getParentId()==null) && visualDetailsList.get(i).getParentId().equals(bodyId))
			{
				
				
				if(visualDetailsList.get(i).getElementType().equals("Visualization"))
				{
					String chartType = visualDetailsList.get(i).getChartType();
					StrategizerVisualizationConvertor strategizerVisualConverter =null;
					if(chartType.equalsIgnoreCase("VerticalBar"))
					{
						positionFlag = true;
						
						strategizerVisualConverter = new StrategizerVisualizationConvertor();
						SapboPowerbiMapping sapbomapping = sapboPowerbiMappingRepository.findBySapboComponent(visualDetailsList.get(i).getChartType());
						
						strategizerVisualConverter.setSourceComponentName(chartType);
						strategizerVisualConverter.setTargetComponentName(sapbomapping.getPowerbiComponent());
						strategizerVisualConverter = setStrategizerConvertorObject(strategizerVisualConverter,visualDetailsList.get(i),maxWidth,maxHeight);
						
						//Formula Setting
						
						JSONArray chartAxesJSONArray = new JSONArray(visualDetailsList.get(i).getChartAxes());
						
						String formulaConversion = "";
						
						for(int j=0;j<chartAxesJSONArray.length();j++)
						{
							JSONObject chartJSONObj = chartAxesJSONArray.getJSONObject(j);
							
							String role = chartJSONObj.getString("role");
							
							if(role.equalsIgnoreCase("color") && chartJSONObj.isNull("formulaList"))
							{
								System.out.println("Strategizer upcoming feature for role color");
							}
							else if(role.equalsIgnoreCase("Category"))
							{
								formulaConversion="Axis=";
								
								String formulaDerived ="";
								
								JSONArray formulaJSONArray = chartJSONObj.getJSONArray("formulaList");
								
								for(int k=0;k<formulaJSONArray.length();k++)
								{
									JSONObject formulaJSONObj = formulaJSONArray.getJSONObject(k);
									
									if(!formulaJSONObj.isNull("dataObjectId"))
									{
										//formulaConversion="Axis"+formulaJSONObj.getString("name");
										String formulaName = formulaJSONObj.getString("name");
										formulaDerived = formulaDerived+","+formulaName.substring(2,formulaName.length()-1);
										
									}
									else
									{
										//Take Formula from Calculated Column
										String formulaName = formulaJSONObj.getString("name");
										String formula = fetchCalculatedFormulaVisual(formulaName,filteredStrategizerCalculatedFormulaList,visualDetailsList.get(i));
										formulaDerived = formulaDerived+","+formula;
									}
								}
								
								formulaConversion=formulaConversion+"["+formulaDerived.substring(1)+"]*";
							}
							else if(role.equalsIgnoreCase("Value"))
							{
								String formulaConversionValue = "Value=";
								String formulaDerived = "";
								JSONArray formulaJSONArray = chartJSONObj.getJSONArray("formulaList");
								
								for(int k=0;k<formulaJSONArray.length();k++)
								{
									JSONObject formulaJSONObj = formulaJSONArray.getJSONObject(k);
									
									if(!formulaJSONObj.isNull("dataObjectId"))
									{
										//formulaConversion="Axis"+formulaJSONObj.getString("name");
										String formulaName = formulaJSONObj.getString("name");
										formulaDerived = formulaDerived+","+formulaName.substring(2,formulaName.length()-1);
									}
									else
									{
										//Take Formula from Calculated Column
										String formulaName = formulaJSONObj.getString("name");
										String formula = fetchCalculatedFormulaVisual(formulaName,filteredStrategizerCalculatedFormulaList,visualDetailsList.get(i));
										formulaDerived = formulaDerived+","+formula;
									}
								}
								
								formulaConversion = formulaConversion+formulaConversionValue+"["+formulaDerived.substring(1)+"]";
							}

						}
						
						strategizerVisualConverter.setFormulaName(formulaConversion);

						
					}
					else
					{
						System.out.println("Strategizer Feature upcoming for ::"+chartType);
					}
					
					if(positionFlag)
					{
						String minimalHeightSource = visualDetailsList.get(i).getMinimalHeight();
						String minimalWidthSource = visualDetailsList.get(i).getMinimalWidth();
						String xPositionSource = visualDetailsList.get(i).getxPosition();
						String yPositionSource = visualDetailsList.get(i).getyPosition();
						
						if(minimalHeightSource==null)
						{
							minimalHeightSource="0";
						}
						if(minimalWidthSource==null)
						{
							minimalWidthSource="0";
						}
						if(xPositionSource==null)
						{
							xPositionSource="0";
						}
						if(yPositionSource==null)
						{
							yPositionSource="0";
						}
						strategizerVisualConverter.setSourceMinimalHeight(minimalHeightSource);
						strategizerVisualConverter.setSourceMinimalWidth(minimalWidthSource);
						strategizerVisualConverter.setSourcePositionX(xPositionSource);
						strategizerVisualConverter.setSourcePositionY(yPositionSource);
						
						Double resXposition = Double.parseDouble(xPositionSource);
						Double resYposition = Double.parseDouble(yPositionSource);
						if(visualDetailsList.get(i).getHorizontalAnchorType()!=null && !visualDetailsList.get(i).getHorizontalAnchorType().equals("None")  )
						{
							String type = visualDetailsList.get(i).getHorizontalAnchorType();
							if(type.equals("End"))
							{
								
								int pos = elementMap.get(visualDetailsList.get(i).getHorizontalAnchorId());
								
				                resXposition+= Double.parseDouble(visualDetailsList.get(pos).getMinimalWidth());
				                String hid = visualDetailsList.get(i).getHorizontalAnchorId();
								
								while(!hid.trim().isEmpty())
								{
									if(elementMap.containsKey(hid))
									{
									int position = elementMap.get(hid);
									resXposition+= Double.parseDouble(visualDetailsList.get(position).getxPosition());
									hid = visualDetailsList.get(position).getHorizontalAnchorId();
									}
								}		
								
							}
							else if(type.equals("Begin"))
							{
								
								
							
								String hid = visualDetailsList.get(i).getHorizontalAnchorId();
								
								while(!hid.trim().isEmpty())
								{
									if(elementMap.containsKey(hid))
									{
									int position = elementMap.get(hid);
									resXposition+= Double.parseDouble(visualDetailsList.get(position).getxPosition());
									hid = visualDetailsList.get(position).getHorizontalAnchorId();
									}
								}		
								
							}
							
						}
						if(visualDetailsList.get(i).getVerticalAnchorType()!=null && !visualDetailsList.get(i).getVerticalAnchorType().equals("None")  )
						{
							String type = visualDetailsList.get(i).getVerticalAnchorType();
							if(type.equals("End"))
							{
								
								int pos = elementMap.get(visualDetailsList.get(i).getVerticalAnchorId());
								
				                resYposition+= Double.parseDouble(visualDetailsList.get(pos).getMinimalHeight());
				                String vid = visualDetailsList.get(i).getVerticalAnchorId();
								
								while(!vid.trim().isEmpty())
								{
									if(elementMap.containsKey(vid))
									{
									int position = elementMap.get(vid);
									resYposition+= Double.parseDouble(visualDetailsList.get(position).getyPosition());
									vid = visualDetailsList.get(position).getVerticalAnchorId();
									}
								}
							}
							else if(type.equals("Begin"))
							{
								
								
							
								String vid = visualDetailsList.get(i).getVerticalAnchorId();
								
								while(!vid.trim().isEmpty())
								{
									if(elementMap.containsKey(vid))
									{
									int position = elementMap.get(vid);
									resYposition+= Double.parseDouble(visualDetailsList.get(position).getyPosition());
									vid = visualDetailsList.get(position).getVerticalAnchorId();
									}
								}
							}
							
						}
						
						Double conxPosition = resXposition/constval;
						Double conyPosition = resYposition/constval;
						conyPosition += headerHeight;
						Double conminimalWidth = (Double.parseDouble(minimalWidthSource))/constval;
						Double conminimalHeight = (Double.parseDouble(minimalHeightSource))/constval;
						
						strategizerVisualConverter.setTargetPositionX(StrategizerUtility.calXPosition(conxPosition,maxWidth));
						strategizerVisualConverter.setTargetPositionY(StrategizerUtility.calYPosition(conyPosition,maxHeight));
						strategizerVisualConverter.setTargetMinimalWidth(StrategizerUtility.calXPosition(conminimalWidth,maxWidth));
						strategizerVisualConverter.setTargetMinimalHeight(StrategizerUtility.calYPosition(conminimalHeight,maxHeight));
						strategizerVisualConverter.setSourceElementId(visualDetailsList.get(i).getElementId());
						
						
					}
					
					strategizerVisualizationConvertorList.add(strategizerVisualConverter);
					
				}
				else if(visualDetailsList.get(i).getElementType().equals("Cell"))
				{
					StrategizerVisualizationConvertor strategizerVisualConverter = new StrategizerVisualizationConvertor();
					
					if(visualDetailsList.get(i).getFormula().contains("String"))
				 	{
				 		SapboPowerbiMapping sapbomapping = sapboPowerbiMappingRepository.findBySapboComponent("StringCell");
						strategizerVisualConverter.setTargetComponentName(sapbomapping.getPowerbiComponent());
						strategizerVisualConverter.setSourceComponentName("StringCell");
				 	}
				 	else {
				 		SapboPowerbiMapping sapbomapping = sapboPowerbiMappingRepository.findBySapboComponent("NumericCell");
				 		strategizerVisualConverter.setTargetComponentName(sapbomapping.getPowerbiComponent());
				 		strategizerVisualConverter.setSourceComponentName("NumericCell");
				 	}
					strategizerVisualConverter.setReportId(visualDetailsList.get(i).getReportId());
					strategizerVisualConverter.setReportTabId(visualDetailsList.get(i).getReportTabId());
					strategizerVisualConverter.setReportTabName(visualDetailsList.get(i).getReportTabName());
					strategizerVisualConverter.setParentElement("Body");
					
					String minimalHeightSource = visualDetailsList.get(i).getMinimalHeight();
					String minimalWidthSource = visualDetailsList.get(i).getMinimalWidth();
					String xPositionSource = visualDetailsList.get(i).getxPosition();
					String yPositionSource = visualDetailsList.get(i).getyPosition();
					
					if(minimalHeightSource==null)
					{
						minimalHeightSource="0";
					}
					if(minimalWidthSource==null)
					{
						minimalWidthSource="0";
					}
					if(xPositionSource==null)
					{
						xPositionSource="0";
					}
					if(yPositionSource==null)
					{
						yPositionSource="0";
					}
					strategizerVisualConverter.setSourceMinimalHeight(minimalHeightSource);
					strategizerVisualConverter.setSourceMinimalWidth(minimalWidthSource);
					strategizerVisualConverter.setSourcePositionX(xPositionSource);
					strategizerVisualConverter.setSourcePositionY(yPositionSource);
					strategizerVisualConverter.setSourceElementId(visualDetailsList.get(i).getElementId());
					
					//Double conxPosition = (Double.parseDouble(xPositionSource))/constval;
					//Double conyPosition = (Double.parseDouble(yPositionSource))/constval;
					//Double conminimalWidth = (Double.parseDouble(minimalWidthSource))/constval;
					//Double conminimalHeight = (Double.parseDouble(minimalHeightSource))/constval;
					
					Double resXposition = Double.parseDouble(xPositionSource);
					Double resYposition = Double.parseDouble(yPositionSource);

					if(visualDetailsList.get(i).getHorizontalAnchorType()!=null && !visualDetailsList.get(i).getHorizontalAnchorType().equals("None") )
					{
						String type = visualDetailsList.get(i).getHorizontalAnchorType();
						if(type.equals("End"))
						{
							
							int pos = elementMap.get(visualDetailsList.get(i).getHorizontalAnchorId());
							
			                resXposition+= Double.parseDouble(visualDetailsList.get(pos).getMinimalWidth());
			                String hid = visualDetailsList.get(i).getHorizontalAnchorId();
							
							while(!hid.trim().isEmpty())
							{
								if(elementMap.containsKey(hid))
								{
								int position = elementMap.get(hid);
								resXposition+= Double.parseDouble(visualDetailsList.get(position).getxPosition());
								hid = visualDetailsList.get(position).getHorizontalAnchorId();
								}
							}		
							
						}
						else if(type.equals("Begin"))
						{
							String hid = visualDetailsList.get(i).getHorizontalAnchorId();
							
							while(!hid.trim().isEmpty())
							{
								if(elementMap.containsKey(hid))
								{
								int position = elementMap.get(hid);
								resXposition+= Double.parseDouble(visualDetailsList.get(position).getxPosition());
								hid = visualDetailsList.get(position).getHorizontalAnchorId();
								}
							}
							
							/*int pos = elementMap.get(visualDetailsList.get(i).getHorizontalAnchorId());
						
							if(recursiveCheck.containsKey(visualDetailsList.get(i).getHorizontalAnchorId()))
							{
								resXposition+= recursiveCheck.get(visualDetailsList.get(i).getHorizontalAnchorId());
								recursiveCheck.put(visualDetailsList.get(i).getElementId(),resXposition);
							}
							else
							{
								resXposition+= Double.parseDouble(visualDetailsList.get(pos).getxPosition());
								recursiveCheck.put(visualDetailsList.get(i).getElementId(),resXposition);
							}*/
			                			
							
						}
						
					}
					if( visualDetailsList.get(i).getVerticalAnchorType()!=null && !visualDetailsList.get(i).getVerticalAnchorType().equals("None"))
					{
						String type = visualDetailsList.get(i).getVerticalAnchorType();
						if(type.equals("End"))
						{
							
							int pos = elementMap.get(visualDetailsList.get(i).getVerticalAnchorId());
							
			                resYposition+= Double.parseDouble(visualDetailsList.get(pos).getMinimalHeight());
			                String vid = visualDetailsList.get(i).getVerticalAnchorId();
							
							while(!vid.trim().isEmpty())
							{
								if(elementMap.containsKey(vid))
								{
								int position = elementMap.get(vid);
								resYposition+= Double.parseDouble(visualDetailsList.get(position).getyPosition());
								vid = visualDetailsList.get(position).getVerticalAnchorId();
								}
							}
						}
						else if(type.equals("Begin"))
						{
							String vid = visualDetailsList.get(i).getVerticalAnchorId();
							
							while(!vid.trim().isEmpty())
							{
								if(elementMap.containsKey(vid))
								{
								int position = elementMap.get(vid);
								resYposition+= Double.parseDouble(visualDetailsList.get(position).getyPosition());
								vid = visualDetailsList.get(position).getVerticalAnchorId();
								}
							}
						}
						
					}
					
						Double conxPosition = resXposition/constval;
						Double conyPosition = resYposition/constval;
						conyPosition += headerHeight;
						Double conminimalWidth = (Double.parseDouble(minimalWidthSource))/constval;
						Double conminimalHeight = (Double.parseDouble(minimalHeightSource))/constval;
					
					
					strategizerVisualConverter.setTargetPositionX(StrategizerUtility.calXPosition(conxPosition,maxWidth));
					strategizerVisualConverter.setTargetPositionY(StrategizerUtility.calYPosition(conyPosition,maxHeight));
					strategizerVisualConverter.setTargetMinimalWidth(StrategizerUtility.calXPosition(conminimalWidth,maxWidth));
					strategizerVisualConverter.setTargetMinimalHeight(StrategizerUtility.calYPosition(conminimalHeight,maxHeight));
					
					String font = visualDetailsList.get(i).getFont().substring(12,14);
					if(font.contains(","))
					{
						font = font.replace(",", "");
					}
					strategizerVisualConverter.setFont(font);
					
					String formula = setFormulaForCell(visualDetailsList.get(i),strategizerCalculatedFormulaList);
					strategizerVisualConverter.setFormulaName(formula);
					
					strategizerVisualConverter.setColor(visualDetailsList.get(i).getBackgroundColor());
					strategizerVisualConverter.setValueType("Function");
					strategizerVisualizationConvertorList.add(strategizerVisualConverter);
					
				}
				else
				{
					System.out.println("Strategizer Feature upcoming for ::"+visualDetailsList.get(i).getElementType());
				}
				
			}

		}
		
		return strategizerVisualizationConvertorList;
	}



	private String fetchCalculatedFormulaVisual(String formulaName,
			List<StrategizerCalculatedFormulaModel> filteredStrategizerCalculatedFormulaList,
			VisualDetails visualDetails) {
		// TODO Auto-generated method stub
		
		String reportId = visualDetails.getReportId();
		String reportTabId = visualDetails.getReportTabId();
		
		Iterator<StrategizerCalculatedFormulaModel> iter = filteredStrategizerCalculatedFormulaList.iterator();
		
		while(iter.hasNext())
		{
			StrategizerCalculatedFormulaModel x = iter.next();
			
			if(x.getReportId().equalsIgnoreCase(reportId)&&x.getReportTabId().equalsIgnoreCase(reportTabId))
			{
				if(x.getFormula().equalsIgnoreCase(formulaName))
				{
					return x.getCalculatedFormula().split("=")[0];
				}
			}
			
		}
		
		
		return formulaName;
	}



	private StrategizerVisualizationConvertor setStrategizerConvertorObject(StrategizerVisualizationConvertor strategizerVisualConverter,
			VisualDetails visualDetails, Double maxWidth, Double maxHeight) {
		// TODO Auto-generated method stub
		
		strategizerVisualConverter.setReportId(visualDetails.getReportId());
		strategizerVisualConverter.setReportTabId(visualDetails.getReportTabId());
		strategizerVisualConverter.setReportTabName(visualDetails.getReportTabName());
		strategizerVisualConverter.setParentElement("Body");
		strategizerVisualConverter.setSourceElementId(visualDetails.getElementId());
		
//		String minimalHeightSource = visualDetails.getMinimalHeight();
//		String minimalWidthSource = visualDetails.getMinimalWidth();
//		String xPositionSource = visualDetails.getxPosition();
//		String yPositionSource = visualDetails.getyPosition();
//		
//		if(minimalHeightSource==null)
//		{
//			minimalHeightSource="0";
//		}
//		if(minimalWidthSource==null)
//		{
//			minimalWidthSource="0";
//		}
//		if(xPositionSource==null)
//		{
//			xPositionSource="0";
//		}
//		if(yPositionSource==null)
//		{
//			yPositionSource="0";
//		}
//		strategizerVisualConverter.setSourceMinimalHeight(minimalHeightSource);
//		strategizerVisualConverter.setSourceMinimalWidth(minimalWidthSource);
//		strategizerVisualConverter.setSourcePositionX(xPositionSource);
//		strategizerVisualConverter.setSourcePositionY(yPositionSource);
	
		
		
		
		
		
	//Double conxPosition = (Double.parseDouble(xPositionSource))/constval;
	//	Double conyPosition = (Double.parseDouble(yPositionSource))/constval;
	//	Double conminimalWidth = (Double.parseDouble(minimalWidthSource))/constval;
	//	Double conminimalHeight = (Double.parseDouble(minimalHeightSource))/constval;
		
	//	strategizerVisualConverter.setTargetPositionX(StrategizerUtility.calXPosition(conxPosition,maxWidth));
	//	strategizerVisualConverter.setTargetPositionY(StrategizerUtility.calYPosition(conyPosition,maxHeight));
	//	strategizerVisualConverter.setTargetMinimalWidth(StrategizerUtility.calXPosition(conminimalWidth,maxWidth));
	//	strategizerVisualConverter.setTargetMinimalHeight(StrategizerUtility.calYPosition(conminimalHeight,maxHeight));
		
		//Set Formula
		
		strategizerVisualConverter.setFont("''");
		strategizerVisualConverter.setColor("''");
		strategizerVisualConverter.setValueType("''");
		
		
		return strategizerVisualConverter;
	}



	private List<StrategizerVisualizationConvertor> visualPowerBIMappingFooter(List<VisualDetails> visualDetailsList, 
			String key, String footerId, Double maxWidth,
			Double maxHeight, List<StrategizerVisualizationConvertor> strategizerVisualizationConvertorList, 
			List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaList, double headerHeight, double bodyHeight) {
		// TODO Auto-generated method stub
	//	Double constval = 3600.0;
		
		List<StrategizerCalculatedFormulaModel> filteredStrategizerCalculatedFormulaList = strategizerCalculatedFormulaList.stream()
				 .filter(x ->x.getColumnQualification().equalsIgnoreCase("Unnamed Dimension"))
			      .collect(Collectors.toList());
		
		
		for(int i=0;i<visualDetailsList.size();i++)
		{
			if(!(visualDetailsList.get(i).getParentId()==null) && visualDetailsList.get(i).getParentId().equals(footerId))
			{
				if(visualDetailsList.get(i).getElementType().equals("Cell"))
				{
					 if(visualDetailsList.get(i).getFormula().contains("String") || visualDetailsList.get(i).getFormula().contains("Numeric"))
					 {
						 StrategizerVisualizationConvertor strategizerVisualConverter = new StrategizerVisualizationConvertor();
						 if(visualDetailsList.get(i).getFormula().contains("String"))
						 	{
						 		 SapboPowerbiMapping sapbomapping = sapboPowerbiMappingRepository.findBySapboComponent("StringCell");
						 		 strategizerVisualConverter.setTargetComponentName(sapbomapping.getPowerbiComponent());
						 	}
						 	else {
						 		SapboPowerbiMapping sapbomapping = sapboPowerbiMappingRepository.findBySapboComponent("NumericCell");
						 		strategizerVisualConverter.setTargetComponentName(sapbomapping.getPowerbiComponent());
						 	}
						 	strategizerVisualConverter.setReportId(visualDetailsList.get(i).getReportId());
							strategizerVisualConverter.setReportTabId(visualDetailsList.get(i).getReportTabId());
							strategizerVisualConverter.setReportTabName(visualDetailsList.get(i).getReportTabName());
							strategizerVisualConverter.setParentElement("Footer");
							strategizerVisualConverter.setSourceComponentName("StringCell");
							strategizerVisualConverter.setSourceElementId(visualDetailsList.get(i).getElementId());
							String minimalHeightSource = visualDetailsList.get(i).getMinimalHeight();
							String minimalWidthSource = visualDetailsList.get(i).getMinimalWidth();
							String xPositionSource = visualDetailsList.get(i).getxPosition();
							String yPositionSource = visualDetailsList.get(i).getyPosition();
							
							if(minimalHeightSource==null)
							{
								minimalHeightSource="0";
							}
							if(minimalWidthSource==null)
							{
								minimalWidthSource="0";
							}
							if(xPositionSource==null)
							{
								xPositionSource="0";
							}
							if(yPositionSource==null)
							{
								yPositionSource="0";
							}
							strategizerVisualConverter.setSourceMinimalHeight(minimalHeightSource);
							strategizerVisualConverter.setSourceMinimalWidth(minimalWidthSource);
							strategizerVisualConverter.setSourcePositionX(xPositionSource);
							strategizerVisualConverter.setSourcePositionY(yPositionSource);
							
							Double conxPosition = (Double.parseDouble(xPositionSource))/constval;
							Double conyPosition = (Double.parseDouble(yPositionSource))/constval;
							conyPosition += (headerHeight + bodyHeight); 
							Double conminimalWidth = (Double.parseDouble(minimalWidthSource))/constval;
							Double conminimalHeight = (Double.parseDouble(minimalHeightSource))/constval;
							
							strategizerVisualConverter.setTargetPositionX(StrategizerUtility.calXPosition(conxPosition,maxWidth));
							//strategizerVisualConverter.setTargetPositionY("650");
							strategizerVisualConverter.setTargetPositionY(StrategizerUtility.calYPosition(conyPosition,maxHeight));
							strategizerVisualConverter.setTargetMinimalWidth(StrategizerUtility.calXPosition(conminimalWidth,maxWidth));
							strategizerVisualConverter.setTargetMinimalHeight(StrategizerUtility.calYPosition(conminimalHeight,maxHeight));
							
							
							String font = visualDetailsList.get(i).getFont().substring(12,14);
							if(font.contains(","))
							{
								font = font.replace(",", "");
							}
							
							
							strategizerVisualConverter.setFont(font);
							String formula = setFormulaForCell(visualDetailsList.get(i),filteredStrategizerCalculatedFormulaList);
							strategizerVisualConverter.setFormulaName(formula);
							
							strategizerVisualConverter.setColor(visualDetailsList.get(i).getBackgroundColor());
							strategizerVisualConverter.setValueType("Function");
							strategizerVisualizationConvertorList.add(strategizerVisualConverter);
					 }
					 else
					 {
						 //image
					 }
					
				}
				else
				{
					 System.out.println("Strategizer Coming feature for "+visualDetailsList.get(i).getElementType());
				}
			}
			
			if(visualDetailsList.get(i).getElementName().equalsIgnoreCase("Footer")&&visualDetailsList.get(i).getElementType().equalsIgnoreCase("Page Zone"))
			{
				
				//System.out.println(visualDetailsList.get(i).getElementType());
				
				StrategizerVisualizationConvertor strategizerVisualConverter = new StrategizerVisualizationConvertor();
				
				String minimalHeight = visualDetailsList.get(i).getMinimalHeight();
				Double conminimalHeight = (Double.parseDouble(minimalHeight))/constval;
				
				strategizerVisualConverter.setReportId(visualDetailsList.get(i).getReportId());
				strategizerVisualConverter.setReportTabId(visualDetailsList.get(i).getReportTabId());
				strategizerVisualConverter.setReportTabName(visualDetailsList.get(i).getReportTabName());
				strategizerVisualConverter.setParentElement("Footer");
				strategizerVisualConverter.setSourceComponentName("TextBox");
				strategizerVisualConverter.setTargetComponentName("TextBox");
				strategizerVisualConverter.setSourceElementId(visualDetailsList.get(i).getElementId());
				
				String minimalHeightSource = visualDetailsList.get(i).getMinimalHeight();
				String minimalWidthSource = visualDetailsList.get(i).getMinimalWidth();
				String xPositionSource = visualDetailsList.get(i).getxPosition();
				String yPositionSource = visualDetailsList.get(i).getyPosition();
				
				if(minimalHeightSource==null)
				{
					minimalHeightSource="0";
				}
				if(minimalWidthSource==null)
				{
					minimalWidthSource="0";
				}
				if(xPositionSource==null)
				{
					xPositionSource="0";
				}
				if(yPositionSource==null)
				{
					yPositionSource="0";
				}
				strategizerVisualConverter.setSourceMinimalHeight(minimalHeightSource);
				strategizerVisualConverter.setSourceMinimalWidth(minimalWidthSource);
				strategizerVisualConverter.setSourcePositionX(xPositionSource);
				strategizerVisualConverter.setSourcePositionY(yPositionSource);
				
				strategizerVisualConverter.setTargetPositionX("0");
				double conyPosition = maxHeight-conminimalHeight;
				//double conyPosition = maxHeight-(headerHeight + bodyHeight)+conminimalHeight;
				strategizerVisualConverter.setTargetPositionY(StrategizerUtility.calYPosition(conyPosition,maxHeight));
				
				strategizerVisualConverter.setTargetMinimalWidth("1280");
				strategizerVisualConverter.setTargetMinimalHeight(StrategizerUtility.calYPosition(conminimalHeight,maxHeight));
				
				strategizerVisualConverter.setFont("''");
				strategizerVisualConverter.setFormulaName("''");
				strategizerVisualConverter.setColor(visualDetailsList.get(i).getBackgroundColor());
				strategizerVisualConverter.setValueType("Static");
				strategizerVisualizationConvertorList.add(strategizerVisualConverter);
				
			}
				
		}
		
		
		return strategizerVisualizationConvertorList;
	}



	private List<StrategizerVisualizationConvertor> visualPowerBIMappingHeader(List<VisualDetails> visualDetailsList, String key, String headerId, Double maxWidth,
			Double maxHeight, List<StrategizerVisualizationConvertor> strategizerVisualizationConvertorList, List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaList) {
		// TODO Auto-generated method stub
		
		List<StrategizerCalculatedFormulaModel> filteredStrategizerCalculatedFormulaList = strategizerCalculatedFormulaList.stream()
				 .filter(x ->x.getColumnQualification().equalsIgnoreCase("Unnamed Dimension"))
			      .collect(Collectors.toList());
		
		for(int i=0;i<visualDetailsList.size();i++)
		{
			if(!(visualDetailsList.get(i).getParentId()==null) && visualDetailsList.get(i).getParentId().equals(headerId))
			{
				 if(visualDetailsList.get(i).getElementType().equals("Cell"))
				 {
					 
					 if(visualDetailsList.get(i).getFormula().contains("String") || visualDetailsList.get(i).getFormula().contains("Numeric"))
					 {
						 	StrategizerVisualizationConvertor strategizerVisualConverter = new StrategizerVisualizationConvertor();
						 	
						 	if(visualDetailsList.get(i).getFormula().contains("String"))
						 	{
						 		 SapboPowerbiMapping sapbomapping = sapboPowerbiMappingRepository.findBySapboComponent("StringCell");
						 		strategizerVisualConverter.setTargetComponentName(sapbomapping.getPowerbiComponent());
						 	}
						 	else {
						 		SapboPowerbiMapping sapbomapping = sapboPowerbiMappingRepository.findBySapboComponent("NumericCell");
						 		strategizerVisualConverter.setTargetComponentName(sapbomapping.getPowerbiComponent());
						 	}
						   
						 	strategizerVisualConverter.setReportId(visualDetailsList.get(i).getReportId());
							strategizerVisualConverter.setReportTabId(visualDetailsList.get(i).getReportTabId());
							strategizerVisualConverter.setReportTabName(visualDetailsList.get(i).getReportTabName());
							strategizerVisualConverter.setParentElement("Header");
							strategizerVisualConverter.setSourceComponentName("StringCell");
							strategizerVisualConverter.setSourceElementId(visualDetailsList.get(i).getElementId());
							
						 
						 	String minimalHeightSource = visualDetailsList.get(i).getMinimalHeight();
							String minimalWidthSource = visualDetailsList.get(i).getMinimalWidth();
							String xPositionSource = visualDetailsList.get(i).getxPosition();
							String yPositionSource = visualDetailsList.get(i).getyPosition();
							
							if(minimalHeightSource==null)
							{
								minimalHeightSource="0";
							}
							if(minimalWidthSource==null)
							{
								minimalWidthSource="0";
							}
							if(xPositionSource==null)
							{
								xPositionSource="0";
							}
							if(yPositionSource==null)
							{
								yPositionSource="0";
							}
							strategizerVisualConverter.setSourceMinimalHeight(minimalHeightSource);
							strategizerVisualConverter.setSourceMinimalWidth(minimalWidthSource);
							strategizerVisualConverter.setSourcePositionX(xPositionSource);
							strategizerVisualConverter.setSourcePositionY(yPositionSource);
							
							Double conxPosition = (Double.parseDouble(xPositionSource))/constval;
							Double conyPosition = (Double.parseDouble(yPositionSource))/constval;
							Double conminimalWidth = (Double.parseDouble(minimalWidthSource))/constval;
							Double conminimalHeight = (Double.parseDouble(minimalHeightSource))/constval;
							
							strategizerVisualConverter.setTargetPositionX(StrategizerUtility.calXPosition(conxPosition,maxWidth));
							strategizerVisualConverter.setTargetPositionY(StrategizerUtility.calYPosition(conyPosition,maxHeight));
							strategizerVisualConverter.setTargetMinimalWidth(StrategizerUtility.calXPosition(conminimalWidth,maxWidth));
							strategizerVisualConverter.setTargetMinimalHeight(StrategizerUtility.calYPosition(conminimalHeight,maxHeight));
								
							String font = visualDetailsList.get(i).getFont().substring(12,14);
							if(font.contains(","))
							{
								font = font.replace(",", "");
							}
							
							
							strategizerVisualConverter.setFont(font);
							//Set formula
							
							String formula = setFormulaForCell(visualDetailsList.get(i),filteredStrategizerCalculatedFormulaList);
							strategizerVisualConverter.setFormulaName(formula);
							
							strategizerVisualConverter.setColor(visualDetailsList.get(i).getBackgroundColor());
							strategizerVisualConverter.setValueType("Function");
							strategizerVisualizationConvertorList.add(strategizerVisualConverter);
	
					 }
				
					 else
					 {
						 //image
					 }
					 
					 
				 }
				 else
				 {
					 System.out.println("Strategizer Coming feature for "+visualDetailsList.get(i).getElementType());
				 }

			}
			
			if(visualDetailsList.get(i).getElementName().equalsIgnoreCase("Header"))
			{
				StrategizerVisualizationConvertor strategizerVisualConverter = new StrategizerVisualizationConvertor();
				String minimalHeight = visualDetailsList.get(i).getMinimalHeight();
				
				Double conminimalHeight = (Double.parseDouble(minimalHeight))/constval;
				
				strategizerVisualConverter.setReportId(visualDetailsList.get(i).getReportId());
				strategizerVisualConverter.setReportTabId(visualDetailsList.get(i).getReportTabId());
				strategizerVisualConverter.setReportTabName(visualDetailsList.get(i).getReportTabName());
				strategizerVisualConverter.setParentElement("Header");
				strategizerVisualConverter.setSourceComponentName("TextBox");
				strategizerVisualConverter.setTargetComponentName("TextBox");
				strategizerVisualConverter.setSourceElementId(visualDetailsList.get(i).getElementId());
				
				String minimalHeightSource = visualDetailsList.get(i).getMinimalHeight();
				String minimalWidthSource = visualDetailsList.get(i).getMinimalWidth();
				String xPositionSource = visualDetailsList.get(i).getxPosition();
				String yPositionSource = visualDetailsList.get(i).getyPosition();
				
				if(minimalHeightSource==null)
				{
					minimalHeightSource="0";
				}
				if(minimalWidthSource==null)
				{
					minimalWidthSource="0";
				}
				if(xPositionSource==null)
				{
					xPositionSource="0";
				}
				if(yPositionSource==null)
				{
					yPositionSource="0";
				}
				
				
				strategizerVisualConverter.setSourceMinimalHeight(minimalHeightSource);
				strategizerVisualConverter.setSourceMinimalWidth(minimalWidthSource);
				strategizerVisualConverter.setSourcePositionX(xPositionSource);
				strategizerVisualConverter.setSourcePositionY(yPositionSource);
				strategizerVisualConverter.setTargetPositionX("0");
				strategizerVisualConverter.setTargetPositionY("0");
				strategizerVisualConverter.setTargetMinimalWidth("1280");
				strategizerVisualConverter.setTargetMinimalHeight(StrategizerUtility.calYPosition(conminimalHeight,maxHeight));
				strategizerVisualConverter.setFont("''");
				strategizerVisualConverter.setFormulaName("''");
				strategizerVisualConverter.setColor(visualDetailsList.get(i).getBackgroundColor());
				strategizerVisualConverter.setValueType("Static");
				strategizerVisualizationConvertorList.add(strategizerVisualConverter);
			}
			
		}
		return strategizerVisualizationConvertorList;
	}

	private String setFormulaForCell(VisualDetails visualDetails,
			List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaList) {
		// TODO Auto-generated method stub
		
		String formula = visualDetails.getFormula();

		System.out.println("Formula in cell::"+formula);
		
		if(!formula.equalsIgnoreCase("[]"))
		{
			if(formula.contains("|"))
			{
				String formulaCellArray []= formula.split("\\|");
				for(int j=0;j<formulaCellArray.length;j++)
				{
					String formulaData = formulaCellArray[j];
					formulaData= formulaData.substring(11,formulaData.length()-1);
					
					String [] formulaCellArrayData = formulaData.split(",");
					String formulaObjectId = formulaCellArrayData[4];
				
					String formulaValue = formulaCellArrayData[0];
					if(formulaObjectId.equalsIgnoreCase("]")&& !formulaValue.equalsIgnoreCase(""))
					{
						return StrategizerUtility.fetchCalculatedFormula(formulaValue, visualDetails, strategizerCalculatedFormulaList);
					}
					else
					{
						return formulaValue.substring(2,formulaValue.length()-1);
					}
				}
				
			}
			else
			{
				System.out.println("Formula for Cell:"+formula);
				
				String formulaData= formula.substring(11,formula.length()-1);
				
				//	System.out.println("Formula Data:"+formulaData);
					
					String [] formulaCellArrayData = formulaData.split(",");
					String formulaObjectId = formulaCellArrayData[4];
					String formulaValue = formulaCellArrayData[0];
					//System.out.println("Formula Value::"+formulaValue);
					if(formulaObjectId.equalsIgnoreCase("]")&& !formulaValue.equalsIgnoreCase(""))
					{
					//	return formulaValue;
						return StrategizerUtility.fetchCalculatedFormula(formulaValue, visualDetails, strategizerCalculatedFormulaList);
					}
					else
					{
						return formulaValue.substring(2,formulaValue.length()-1);
					}
			}
		}
		
		return null;
		

	}


	public HashMap<String,String> selfJoin(List<VisualDetails> visualDetailsList)
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
	
	
	private HashMap<String,Integer> elementMap(List<VisualDetails> visualDetailsList)
	{
		HashMap<String,Integer> elementHashMap = new HashMap<String,Integer>();
		for(int i=0;i<visualDetailsList.size();i++)
		{
			if(!elementHashMap.containsKey(visualDetailsList.get(i).getElementId()))
			{
				elementHashMap.put(visualDetailsList.get(i).getElementId(),i);
			}
		}
		System.out.println("ElementMap :: "+elementHashMap.toString());
		return elementHashMap;
	}
	
	
	public double calcheaderHeight(List<VisualDetails> visualDetailsList)
	{
		double headerHeight = 0.0 ;
		for(int i=0;i<visualDetailsList.size();i++)
		{
			if(visualDetailsList.get(i).getElementName().equals("Header"))
			{
				headerHeight = Double.parseDouble(visualDetailsList.get(i).getMinimalHeight());
			}
			
		}
		headerHeight=headerHeight/3600;
		return headerHeight;
	}
	private double calculateBodyHeight(List<VisualDetails> visualDetailsList,String bodyId) {
		// TODO Auto-generated method stub
		
		double maxHeight=0.0;
		
		
		for(int i=0;i<visualDetailsList.size();i++)
		{
			if(!(visualDetailsList.get(i).getParentId()==null) && visualDetailsList.get(i).getParentId().equals(bodyId))
			{
				VisualDetails visualElementsObj = visualDetailsList.get(i);
				String xPositionVal = visualElementsObj.getxPosition();
				String yPositionVal = visualElementsObj.getyPosition();
				String minimalHeight= visualElementsObj.getMinimalHeight();
				
				
				if(xPositionVal ==null || xPositionVal.isEmpty())
				{
					xPositionVal="0.00";
				}
				if(yPositionVal ==null || yPositionVal.isEmpty())  
				{
					yPositionVal="0.00";
				}
				if(minimalHeight ==null || minimalHeight.isEmpty())
				{
					minimalHeight="0.00";
				}
				
				double yPosition = Double.parseDouble(yPositionVal);
				
				double height = Double.parseDouble(minimalHeight); 
				
				double sumHeight = height+yPosition;
				
				if(maxHeight<sumHeight)
				{
					maxHeight=sumHeight;
				}
			}

		}
		
		maxHeight=maxHeight/3600;
		
        BigDecimal bd2=new BigDecimal(maxHeight).setScale(2,RoundingMode.HALF_DOWN);
		
		//maxHeight is Approximate Body Height
		maxHeight=bd2.doubleValue()+0.50; 
		
		
		return maxHeight;
	}
	
}
