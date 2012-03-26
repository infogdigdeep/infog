package com.infog.common.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infog.common.domain.ContentInfo;
import com.infog.common.domain.User;
import com.infog.common.repository.ContentInfoRepository;

@Service
public class ContentInfoService {

	@Autowired
	private ContentInfoRepository contentInfoRepository;
	
	
	public ContentInfo create(ContentInfo info) {
		info.setId(UUID.randomUUID().toString());
		
		return contentInfoRepository.save(info);
	}
	
	public User read(User user) {
		return user;
	}
	
	public List<ContentInfo> readAll() {
		return contentInfoRepository.findAll();
	}
	
	public ContentInfo update(ContentInfo info) {
		ContentInfo existingInfo = contentInfoRepository.findById(info.getId());
		
		if (existingInfo == null) {
			return null;
		}
		
		existingInfo.setDescriptions(info.getDescriptions());
		existingInfo.setName(info.getName());
		existingInfo.setType(info.getType());
		existingInfo.setUrl(info.getUrl());
		
		return contentInfoRepository.save(existingInfo);
	}
	
	public Boolean delete(ContentInfo info) {
		ContentInfo existingInfo = contentInfoRepository.findById(info.getId());
		
		if (existingInfo == null) {
			return false;
		}

		contentInfoRepository.delete(existingInfo);
		return true;
	}
}
