package com.lti.recast.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lti.recast.jpa.entity.AnalysisReport;
import com.lti.recast.jpa.entity.AnalysisReportsTable;
import com.lti.recast.jpa.entity.AnalysisSemanticColumns;
import com.lti.recast.strategizer.QueryEngine;
import com.lti.recast.util.ModelBuilder;
import com.lti.recast.web.model.StrategizerQueryModel;

@Service
public class StrategizerQueryService {

	
	public List<StrategizerQueryModel> saveQueryData(List<AnalysisReport> filteredAnalysisReportList,
			List<AnalysisReportsTable> filteredAnalysisReportTableList, List<AnalysisSemanticColumns> semanticColumns) {
	
		
		QueryEngine queryEngineObj = new QueryEngine();
		
		List<StrategizerQueryModel> strategizerQueryModelList = new LinkedList<StrategizerQueryModel>();
		
		
		filteredAnalysisReportList.forEach(x->{
			
			Map<String,List<List<String>>> modifiedQuery = queryEngineObj.createModifiedQuery(x,semanticColumns,filteredAnalysisReportTableList);
			//System.out.println("Query Map::"+modifiedQuery);
			
			int counter=1;
			
			modifiedQuery.entrySet().forEach(y->{
				
				List<List<String>> selectQueryList = y.getValue();
				
				String queryName = y.getKey().split("~")[1];
				
				selectQueryList.forEach(z->{
					
					StrategizerQueryModel strategizerQueryModel = ModelBuilder.StrategizerQueryBuilder(z);
					strategizerQueryModel.setQueryName(queryName);
					strategizerQueryModel.setConvertedQueryName("Query"+counter);
					compareToIgnoreCase(counter);
					strategizerQueryModel.setReportId(x.getReportId().toString());
					strategizerQueryModel.setReportName(x.getReportName());
					strategizerQueryModelList.add(strategizerQueryModel);
				});
				
			});
			
			
		});
	
	//	System.out.println("Model List Query Strategizer:"+strategizerQueryModelList);
		
		return strategizerQueryModelList;
		
	}

	
	private static void compareToIgnoreCase(int counter) {
		// TODO Auto-generated method stub
		counter++;
	}
	
	
}
