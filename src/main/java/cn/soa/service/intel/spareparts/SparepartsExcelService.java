
/**
 * <一句话功能描述>
 * <p>设备备件相关excel导入导出服务层接口
 * @author 陈宇林
 * @version [版本号, 2020年3月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.intel.spareparts;


import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import cn.soa.entity.QueryCondition;

public interface SparepartsExcelService {

	/**
	 * 设备与备件关系导入
	 * @param exportFile
	 * @return
	 */
	String importEqOrSpRe(MultipartFile exportFile) throws Exception;

	/**
	 * 备件导入
	 * @param exportFile
	 * @return
	 */
	String importSparepart(MultipartFile exportFile) throws Exception;

	/**
	 * 备件导出
	 * @param condition
	 * @param response
	 */
	void exportEquipment(QueryCondition condition, HttpServletResponse response) throws Exception;
	

}
