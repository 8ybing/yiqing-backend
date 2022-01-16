package cn.bybing.mapper;

import cn.bybing.entity.Details;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@Mapper
public interface DetailsMapper extends BaseMapper<Details> {

    /**
     * 根据城市名查区号
     * @param cityName
     * @return
     */
    String findCityCode(@Param("cityName") String cityName);

    //更新
    void updateDetails(Details details);
}
