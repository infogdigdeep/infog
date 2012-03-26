package com.infog.common.service;

import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infog.common.domain.ContentInfo;
import com.infog.common.repository.ContentInfoRepository;
import com.infog.model.dto.ContentInfoMapper;

@Service
public class JasperDatasourceService {

	@Autowired
	private ContentInfoRepository repository;
	
	/**
	 * Returns a data source that's wrapped within {@link JRDataSource}
	 * @return
	 */
	public JRDataSource getDataSource() {
		List<ContentInfo> records = repository.findAll();
		return new JRBeanCollectionDataSource(ContentInfoMapper.toDTOList(records));
	}
}
