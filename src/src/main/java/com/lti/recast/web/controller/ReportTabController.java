package com.lti.recast.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lti.recast.jpa.repository.VisualDetailsRepository;
import com.lti.recast.service.ConnectionService;
import com.lti.recast.service.ProjectService;
import com.lti.recast.service.ReportTabService;
import com.lti.recast.web.model.ProjectModel;
import com.lti.recast.web.model.ReportTabModel;
import com.lti.recast.web.model.ReportTypeModel;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/reportTab")
public class ReportTabController {
	private Logger logger = LoggerFactory.getLogger(ReportTabController.class);

	@Autowired(required = false)
	private ReportTabService reportTabService;
	
	@GetMapping("/getTabDetails/{taskId}")
	public List<ReportTabModel> getReportTab(@PathVariable int taskId)
	{
		return reportTabService.getReportElementList(taskId);
	}
	
	
	
	

}
