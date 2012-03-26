package com.infog.web.controller;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.infog.common.domain.ContentInfo;
import com.infog.common.domain.User;
import com.infog.common.repository.ContentInfoRepository;
import com.infog.common.service.ContentInfoService;
import com.infog.common.service.ReportService;
import com.infog.common.service.TokenService;
import com.infog.model.dto.ContentInfoDTO;
import com.infog.model.dto.ContentInfoMapper;
import com.infog.model.response.JqGridResponse;
import com.infog.model.response.StatusResponse;
import com.infog.web.dto.GenericListDto;
import com.infog.web.dto.SelectDto;


@Controller
@RequestMapping("/contentinfo")
public class ContentInfoController {

	
	
	@Autowired
	private ContentInfoRepository repository;
	
	@Autowired
	private ContentInfoService service;
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private ReportService reportService;
	
	@RequestMapping
	public ModelAndView getContentInfoPage() {

        Map<String, Object> model = new HashMap<String, Object>();

        List<SelectDto> contentType = new ArrayList<SelectDto>();
        
        contentType.add(new SelectDto(0, "Atom"));
        contentType.add(new SelectDto(1, "RSS"));

        model.put("contentTypeList", contentType);
        model.put("contentInfo", new ContentInfo());
        Map<String, Map<String, Object>> modelForView = new HashMap<String, Map<String, Object>>();
        modelForView.put("contentInfoForm", model);

        return new ModelAndView("contentinfo", modelForView);
	}

	@RequestMapping(value = "/records")
	public @ResponseBody
	GenericListDto<ContentInfo> getContentInfos() {

		GenericListDto<ContentInfo> listDto = new GenericListDto<ContentInfo>();
		listDto.setRecords(service.readAll());
		return listDto;
	}

	@RequestMapping(value = "/jqgrecords", produces="application/json")
	public @ResponseBody JqGridResponse<ContentInfoDTO> getContentInfos(
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows) {
		Pageable pageRequest = new PageRequest(page - 1, rows);
		Page<ContentInfo> infos = repository.findAll(pageRequest);
		JqGridResponse<ContentInfoDTO> response = new JqGridResponse<ContentInfoDTO>();
		response.setPage(Long.valueOf(page).toString());
		response.setRecords(Long.valueOf(infos.getTotalElements()).toString());
		response.setRows(ContentInfoMapper.toDTOList(infos.getContent()));
		response.setTotal(Long.valueOf(infos.getTotalPages()).toString());
		return response;
	}

	@RequestMapping(value = "/get")
	public @ResponseBody
	User get(@RequestBody User user) {
		return service.read(user);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody
	ContentInfo create(@ModelAttribute("contentInfo") ContentInfo info) {
		/*
		ContentInfo newContentInfo = new ContentInfo();
		newContentInfo.setName(name);
		newContentInfo.setDescriptions(description);
		newContentInfo.setUrl(url);
		newContentInfo.setType(type);
		*/
		return service.create(info);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	ContentInfo update(@RequestParam String name,
			@RequestParam String description, @RequestParam String url,
			@RequestParam int type) {

		ContentInfo newContentInfo = new ContentInfo();
		newContentInfo.setName(name);
		newContentInfo.setDescriptions(description);
		newContentInfo.setUrl(url);
		newContentInfo.setType(type);

		return service.update(newContentInfo);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Boolean delete(@RequestParam String id) {

		ContentInfo info = new ContentInfo();
		info.setId(id);

		return service.delete(info);
	}
	
	@RequestMapping(value="/download/progress")
	public @ResponseBody StatusResponse getDownloadProgress(@RequestParam String token) {
		return new StatusResponse(true, tokenService.check(token));
	}
	
	@RequestMapping(value="/download/gettoken")
	public @ResponseBody StatusResponse getToken() {
		return new StatusResponse(true, tokenService.generate());
	}
	
	@RequestMapping(value="/download")
	public void startDownload (@RequestParam String reportType, @RequestParam String token, HttpServletResponse response) throws Exception {
		ByteArrayOutputStream bao = reportService.create(reportType);
		String fileName = "ContentInfo." + reportType;
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		response.setContentType(reportService.getMediaType(reportType));
		response.setContentLength(bao.size());

	}

	@ModelAttribute("contentInfo")
	public ContentInfo getContentInfo() {
		return new ContentInfo();
	}
	
	
}
