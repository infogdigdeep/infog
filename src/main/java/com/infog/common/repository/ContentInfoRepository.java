package com.infog.common.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.infog.common.domain.ContentInfo;

public interface ContentInfoRepository extends
		MongoRepository<ContentInfo, String> {
	ContentInfo findById(String id);
}
