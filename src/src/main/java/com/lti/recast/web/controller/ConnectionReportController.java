package com.lti.recast.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.recast.commons.ReportConstants;
import com.lti.recast.commons.dto.ReportConfigDTO;
import com.lti.recast.jpa.entity.ConnectionPath;
import com.lti.recast.jpa.entity.PrjRptConParams;
import com.lti.recast.jpa.entity.ReportPath;
import com.lti.recast.jpa.repository.ConnectionPathRepository;
import com.lti.recast.jpa.repository.PrjRptConParamsRepository;
import com.lti.recast.jpa.repository.ReportPathRepository;
import com.lti.recast.reportfactory.ReportAnalyzerFactory;
import com.lti.recast.tableau.TableauConstants;
import com.lti.recast.tableau.TableauReportAnalyzer;
import com.lti.recast.tableau.dto.TableauReportConfigDTO;
import com.lti.recast.tableau.model.ReportData;
import com.lti.recast.web.model.ProjectReportConModel;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class ConnectionReportController {
	@Autowired(required = false)
	private ConnectionPathRepository connectionPathRepository;
	
	@Autowired(required = false)
	private PrjRptConParamsRepository prjRptConParamsRepository;
	
	@Autowired(required = false)
	private ReportPathRepository reportPathRepository;
	
	
	//@GetMapping(value="/getFolder/{connectionId}/{reportType}")

	public void getreportdata(int connectionId,String reportType)
	{
		List<ConnectionPath> check = connectionPathRepository.findByConnectionId(connectionId);
		if(check.isEmpty())
		{
		List<PrjRptConParams> conParams = prjRptConParamsRepository.findByProjectReportConId(connectionId);
		
		Map<String, String> conn = new HashMap<String, String>();
		conParams.forEach(x -> conn.put(x.getRptConParamType().getCode(), x.getRptConParamValue()));
		
		try
		{
			if(reportType.equalsIgnoreCase(ReportConstants.REPORT_TYPE_BO))
			{
//				BoReportConfigDTO boConfigDTO = new BoReportConfigDTO();
//				boConfigDTO.setReportType(ReportConstants.REPORT_TYPE_BO);
//				boConfigDTO.setHostname(conn.get(BOConstants.HOST_NAME));
//				boConfigDTO.setPort(conn.get(BOConstants.PORT));
//				boConfigDTO.setUsername(conn.get(BOConstants.USERNAME));
//				boConfigDTO.setPassword(conn.get(BOConstants.PASSWORD));
//				//boConfigDTO.setPath(conn.get(BOConstants.PATH));
//				
//				BOReportAnalyzer analyzer = (BOReportAnalyzer) ReportAnalyzerFactory.getInstance().getReportAnalyzer((ReportConfigDTO) boConfigDTO);
//		
//		Map<String,List<ReportData>> map = analyzer.reportPathExtraction();
//		
//		for(String key : map.keySet())
//		{
//			ConnectionPath connectionPath = new ConnectionPath();
//			connectionPath.setConnectionId(connectionId);
//			connectionPath.setPathName(key);
//			connectionPathRepository.save(connectionPath);
//			List<ConnectionPath> tableDetails = connectionPathRepository.findByConnectionIdAndPathName(connectionId,key);
//			ConnectionPath cp = tableDetails.get(0);
//			Integer pathId = cp.getPathId();
//			List<ReportData> l = map.get(key);
//			for(int i=0;i<l.size();i++)
//			{
//				ReportData reportData = l.get(i);
//				ReportPath reportPath = new ReportPath();
//				reportPath.setPathId(pathId);
//				reportPath.setReportId(reportData.getReportId());
//				reportPath.setReportName(reportData.getReportName());
//				reportPath.setReportSize(reportData.getSize());
//				reportPath.setReportDate(reportData.getDate());
//				
//				List<String> reportUniversesList = new ArrayList<>(reportData.getUniverses());
//				 String delim = ",";
//				 
//			        StringBuilder sb = new StringBuilder();
//			 
//			        int j = 0;
//			        while (j < reportUniversesList.size() - 1)
//			        {
//			            sb.append(reportUniversesList.get(j));
//			            sb.append(delim);
//			            j++;
//			        }
//			        sb.append(reportUniversesList.get(j));
//			        
//			        String reportUniverses = sb.toString();
//			        reportPath.setUniverses(reportUniverses);
//			        reportPathRepository.save(reportPath);
//			}
			
	//	}
			}
			else if (reportType.equalsIgnoreCase(ReportConstants.REPORT_TYPE_TABLEAU))
			{
				TableauReportConfigDTO tabConfigDTO = new TableauReportConfigDTO();
				tabConfigDTO.setReportType(ReportConstants.REPORT_TYPE_TABLEAU);
				tabConfigDTO.setHostname(conn.get(TableauConstants.HOST_NAME));
				tabConfigDTO.setPort(conn.get(TableauConstants.PORT));
				tabConfigDTO.setUsername(conn.get(TableauConstants.USERNAME));
				tabConfigDTO.setPassword(conn.get(TableauConstants.PASSWORD));
				tabConfigDTO.setPath(conn.get(TableauConstants.PATH));
				tabConfigDTO.setContentUrl(conn.get(TableauConstants.CONTENT_URL));
				tabConfigDTO.setConnectionType(conn.get(TableauConstants.CONNECTION_TYPE));
				tabConfigDTO.setExtractType(conn.get(TableauConstants.EXTRACT_TYPE));
				
//				Creating analyzer instance
				TableauReportAnalyzer analyzer = (TableauReportAnalyzer) ReportAnalyzerFactory.getInstance().getReportAnalyzer((ReportConfigDTO) tabConfigDTO);
				
				Map<String,List<ReportData>> map = new HashMap<String, List<ReportData>>();
				if (tabConfigDTO.getExtractType().equals("twb"))
				{
					map = analyzer.reportPathExtraction();
				}
				else if (tabConfigDTO.getExtractType().equals("tds"))
				{
					map = analyzer.datasourcePathExtraction();
				}
				
				
				System.out.println("report data map in crc:::" + map);
				
				for (String key : map.keySet())
				{
					ConnectionPath connectionPath = new ConnectionPath();
					connectionPath.setConnectionId(connectionId);
					connectionPath.setPathName(key);
					connectionPathRepository.save(connectionPath);
					List<ConnectionPath> tableDetails = connectionPathRepository.findByConnectionIdAndPathName(connectionId,key);
					ConnectionPath cp = tableDetails.get(0);
					Integer pathId = cp.getPathId();
					List<ReportData> l = map.get(key);
					for (int i=0;i<l.size();i++)
					{
						ReportData reportData = l.get(i);
						ReportPath reportPath = new ReportPath();
						reportPath.setPathId(pathId);
						reportPath.setReportId(reportData.getId());
						reportPath.setReportName(reportData.getName());
						reportPath.setReportSize(reportData.getSize());
						reportPath.setReportDate(reportData.getUpdatedAt());
						
//						List<String> reportUniversesList = new ArrayList<>(reportData.getUniverses());
						List<String> reportUniversesList = new ArrayList<>();
						reportUniversesList.add("test datasource");
						
						String delim = ",";
						StringBuilder sb = new StringBuilder();
						
						System.out.println("report data:::" + reportData);
						 
				        int j = 0;
				        System.out.println("report univ size:::" +  reportUniversesList.size());
				        if (reportUniversesList.size() != 0)
				        {
				        	while (j < reportUniversesList.size() - 1)
					        {
					        	System.out.println("j:::" + j);
					            sb.append(reportUniversesList.get(j));
					            sb.append(delim);
					            j++;
					        }
				        	sb.append(reportUniversesList.get(j));
					        
					        String reportUniverses = sb.toString();
					        reportPath.setUniverses(reportUniverses);
					        reportPathRepository.save(reportPath);
				        }
				        
				        reportPathRepository.save(reportPath);
						
					}
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

}
