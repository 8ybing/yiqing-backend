package cn.bybing.service;

import cn.bybing.entity.Details;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
public interface DetailsService extends IService<Details> {

    //存储
    void saveDetails(Details details);

    //更新
    void updateDetails(Details details);

    //查找(省份和市名相同的)
    List<Details> findDetails(Details details);

    //查找省
    List<String> findProvince();

    //查找每个省的确诊人数
    List<Integer> findProvinceValue();

    //查找城市
    List<String> findCity();

    //查找每个城市的确诊人数
    List<Long> findCityValue();

    /**
     * 根据城市名查区号
     * @param cityName
     * @return
     */
    String findCityCode(String cityName);
}
