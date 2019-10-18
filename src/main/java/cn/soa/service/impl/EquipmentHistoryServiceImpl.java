
/**
 * <一句话功能描述>
 * <p>设备历史操作/版本数据处理业务层
 * @author 陈宇林
 * @version [版本号, 2019年9月11日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import cn.soa.dao.EquipmentCommonInfoBackMapper;
import cn.soa.dao.EquipmentCommonInfoMapper;
import cn.soa.dao.EquipmentPropertiesMapper;
import cn.soa.entity.EquipmentCommonInfoBack;
import cn.soa.entity.QueryCondition;
import cn.soa.service.intel.EquipmentHistoryService;

@Service
public class EquipmentHistoryServiceImpl implements EquipmentHistoryService {

	
	@Autowired
	private EquipmentCommonInfoBackMapper equipmentCommonInfoBackMapper;
	
	
	@Autowired
    private EquipmentCommonInfoMapper equipmentCommonInfoMapper;
	@Autowired
	private EquipmentPropertiesMapper equipmentPropertiesMapper;
	/* (non-Javadoc)
	 * @see cn.soa.service.intel.EquipmentHistoryService#getEquHistoryList(cn.soa.entity.QueryCondition)
	 */
	@Override
	public Page<EquipmentCommonInfoBack> getEquHistoryList(QueryCondition condition) {
		
		Page<EquipmentCommonInfoBack> result = equipmentCommonInfoBackMapper.selectByCondition(condition);
		return result;
	}
	/* (non-Javadoc)
	 * @see cn.soa.service.intel.EquipmentHistoryService#recoveryEquInfo(cn.soa.entity.QueryCondition)
	 */
	@Override
	@Transactional
	public String recoveryEquInfo(QueryCondition condition) {
		
		/**
		 * 删除需要回复的数据
		 */
		equipmentCommonInfoMapper.deleteByPrimaryKey(condition.getEquId());
		equipmentPropertiesMapper.deleteByCondition(condition);
		
		/**
		 * 恢复设备通用属性
		 */
		int result = equipmentCommonInfoMapper.recoveryEquInfo(condition);
		
		//恢复扩展属性
		equipmentPropertiesMapper.recoveryEquInfo(condition);
		
		return result + "";
	}

}
