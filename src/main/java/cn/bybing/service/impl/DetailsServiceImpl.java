package cn.bybing.service.impl;

import cn.bybing.api.ApiResult;
import cn.bybing.entity.Details;
import cn.bybing.mapper.DetailsMapper;
import cn.bybing.service.DetailsService;
import cn.bybing.task.DetailsTask;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@Service
public class DetailsServiceImpl extends ServiceImpl<DetailsMapper, Details> implements DetailsService {


    public void saveDetails(Details details){
        HashMap<String, Object> map = new HashMap<>();
        map.put("city",details.getCity());
        map.put("province",details.getProvince());
        List<Details> list = this.baseMapper.selectByMap(map);

        if(list.size() == 0){
            //数据库中没有数据
            //新增
            this.baseMapper.insert(details);
        }else{
            //已有数据，更新数据
            this.baseMapper.updateDetails(details);
        }

    }

    @Override
    public void updateDetails(Details details) {
        this.baseMapper.updateDetails(details);
    }

    @Override
    public List<Details> findDetails(Details details) {
        return null;
    }

    @Override
    public List<String> findProvince() {
        return null;
    }

    @Override
    public List<Integer> findProvinceValue() {
        return null;
    }

    @Override
    public List<String> findCity() {
        return null;
    }

    @Override
    public List<Long> findCityValue() {
        return null;
    }

    @Override
    public String findCityCode(String cityName) {
        return this.baseMapper.findCityCode(cityName);
    }

}
