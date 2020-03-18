package cn.soa.dao.spareparts;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;

import cn.soa.entity.spareparts.SpRecord;

@Mapper
public interface SpRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(SpRecord record);

    int insertSelective(SpRecord record);

    SpRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SpRecord record);

    int updateByPrimaryKey(SpRecord record);
    
    /**
     * 根据申请单号查询数据
     * @param requestCode
     * @return
     */
    Page<SpRecord> selectByRequestCode(String requestCode);
}