package com.lti.recast.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.lti.recast.jpa.entity.AnalysisReport;
import com.lti.recast.jpa.entity.StrategizerQueryConversion;
import com.lti.recast.jpa.entity.VisualDetails;
import com.lti.recast.strategizer.VisualConvertor;
import com.lti.recast.web.model.StrategizerCalculatedFormulaModel;

@Service
public class StrategizerCalculatedFormulaService {

	public List<StrategizerCalculatedFormulaModel> saveFormulaData(List<VisualDetails> filteredVisualDetailsList,List<AnalysisReport> analysisReportList, List<StrategizerQueryConversion> strategizerQueryConversionList) {
		// TODO Auto-generated method stub
		
		 List<StrategizerCalculatedFormulaModel> strategizerCalculatedFormulaModelList = new LinkedList<StrategizerCalculatedFormulaModel>();
		
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
			 strategizerCalculatedFormulaModelList = VisualConvertor.visualPowerBICalculatedFormula(x.getValue(),x.getKey(),analysisReportList,strategizerCalculatedFormulaModelList,strategizerQueryConversionList);
		 }
		 
		 
		 for(int i=0;i<analysisReportList.size();i++)
		 {
			
			 strategizerCalculatedFormulaModelList = VisualConvertor.fetchVariableList(analysisReportList.get(i),strategizerCalculatedFormulaModelList,strategizerQueryConversionList); 
		 }

		 
		// strategizerCalculatedFormulaModelList.forEach(x->{
		//	 System.out.println(x);
		// });
		 
		
		return strategizerCalculatedFormulaModelList;
	}

	
	
}
