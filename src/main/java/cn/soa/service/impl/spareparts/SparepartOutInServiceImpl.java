
/**
 * <一句话功能描述>
 * <p>备件出入库服务层实现
 * @author 陈宇林
 * @version [版本号, 2020年3月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl.spareparts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import cn.soa.dao.spareparts.SpPutInMapper;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.spareparts.SpPutIn;
import cn.soa.service.intel.spareparts.SparepartOutInService;


@Service
public class SparepartOutInServiceImpl implements SparepartOutInService {
	
	@Autowired
	private SpPutInMapper spPutInMapper;//备件申请表mapper

	/* (non-Javadoc)
	 * @see cn.soa.service.intel.spareparts.SparepartOutInService#getSparepartApply(cn.soa.entity.QueryCondition)
	 */
	@Override
	public Page<SpPutIn> getSparepartApply(QueryCondition condition) {
		
		Page<SpPutIn> result = spPutInMapper.findByCondition(condition);
		
		return result;
	}

}
