
/**
 * <一句话功能描述>
 * <p>设备检维修信息mapper
 * @author 陈宇林
 * @version [版本号, 2019年9月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.dao;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.soa.entity.EquMaintenanceInfo;
import cn.soa.entity.QueryCondition;

@Mapper
public interface EquMaintenanceMapper {

	/**
	 * 根据条件查询数据
	 * @param condition
	 * @return
	 */
	Page<EquMaintenanceInfo> selectByCondition(QueryCondition condition);
	
}
