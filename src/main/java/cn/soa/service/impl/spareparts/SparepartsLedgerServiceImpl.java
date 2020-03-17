
/**
 * <一句话功能描述>
 * <p>备件台账服务层实现类
 * @author 陈宇林
 * @version [版本号, 2020年3月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
package cn.soa.service.impl.spareparts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import cn.soa.dao.spareparts.SparePartMapper;
import cn.soa.entity.QueryCondition;
import cn.soa.entity.spareparts.SparePart;
import cn.soa.service.intel.spareparts.SparepartsLedgerService;

@Service
public class SparepartsLedgerServiceImpl implements SparepartsLedgerService {

	@Autowired
	private SparePartMapper sparePartMapper;// 设备备件信息表mapper

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartsLedgerService#getSparePartsInfo(cn.
	 * soa.entity.QueryCondition)
	 */
	@Override
	public Page<SparePart> getSparePartsInfo(QueryCondition condition) {

		Page<SparePart> result = sparePartMapper.findByCondition(condition);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartsLedgerService#delSparePartsInfo(
	 * java.util.List)
	 */
	@Override
	@Transactional
	public String delSparePartsInfo(List<SparePart> spareParts) {

		Integer result = sparePartMapper.delAsBatch(spareParts);

		return "成功删除" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartsLedgerService#updateSparePartsInfo(
	 * cn.soa.entity.spareparts.SparePart)
	 */
	@Override
	public String updateSparePartsInfo(SparePart sparePart) {

		Integer result = sparePartMapper.updateSelective(sparePart);

		return "成功更新" + result + "条数据";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.soa.service.intel.spareparts.SparepartsLedgerService#addSparePartsInfo(
	 * java.util.List)
	 */
	@Override
	@Transactional
	public String addSparePartsInfo(List<SparePart> spareParts) {

		Integer result = 0;
		for (SparePart sparePart : spareParts) {

			result += sparePartMapper.insertSelective(sparePart);

		}

		return "成功添加" + result + "条数据";
	}

}
