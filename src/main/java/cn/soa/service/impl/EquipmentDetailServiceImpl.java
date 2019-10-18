
/**
 * <一句话功能描述>
 * <p>设备详情页面数据处理业务层实现类
 * @author 陈宇林
 * @version [版本号, 2019年9月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import cn.soa.dao.EquMaintenanceMapper;
import cn.soa.dao.EquipmentSpareMapper;
import cn.soa.entity.EquMaintenanceInfo;
import cn.soa.entity.EquipmentSpare;
import cn.soa.entity.QueryCondition;
import cn.soa.service.intel.EquipmentDetailService;


@Service
public class EquipmentDetailServiceImpl implements EquipmentDetailService {
	
	
	@Autowired
	private EquMaintenanceMapper equMaintenanceMapper;
	
	@Autowired
	private EquipmentSpareMapper equipmentSpareMapper;

	/* (non-Javadoc)
	 * @see cn.soa.service.intel.EquipmentDetailService#getEquMaintenanceInfo(cn.soa.entity.QueryCondition)
	 */
	@Override
	public Page<EquMaintenanceInfo> getEquMaintenanceInfo(QueryCondition condition) {
		
		Page<EquMaintenanceInfo> result = equMaintenanceMapper.selectByCondition(condition);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see cn.soa.service.intel.EquipmentDetailService#getEquSpareInfo(cn.soa.entity.QueryCondition)
	 */
	@Override
	public Page<EquipmentSpare> getEquSpareInfo(QueryCondition condition) {
		
		Page<EquipmentSpare> result = equipmentSpareMapper.selectByCondition(condition);
		
		return result;
	}

}
