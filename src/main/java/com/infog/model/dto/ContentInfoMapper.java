package com.infog.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.infog.common.domain.ContentInfo;

public class ContentInfoMapper {

	public static List<ContentInfoDTO> toDTOList(List<ContentInfo> infos) {
		List<ContentInfoDTO> infoDTOs = new ArrayList<ContentInfoDTO>();
		for (ContentInfo tmpInfo : infos) {
			ContentInfoDTO tmpInfoDTO = new ContentInfoDTO();
			tmpInfoDTO.setDescriptions(tmpInfo.getDescriptions());
			tmpInfoDTO.setId(tmpInfo.getId());
			tmpInfoDTO.setName(tmpInfo.getName());
			tmpInfoDTO.setType(tmpInfo.getType());
			tmpInfoDTO.setUrl(tmpInfo.getUrl());
			infoDTOs.add(tmpInfoDTO);
		}
		return infoDTOs;
	}
}
