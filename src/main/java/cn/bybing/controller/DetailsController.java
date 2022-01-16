package cn.bybing.controller;


import cn.bybing.api.ApiResult;
import cn.bybing.entity.Details;
import cn.bybing.service.DetailsService;
import cn.bybing.task.DetailsTask;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@RestController
@RequestMapping("/details")
public class DetailsController {

    @Resource
    private DetailsTask detailsTask;

    @Resource
    private DetailsService detailsService;

    /**
     * 获取疫情全部数据
     * @return
     */
    @GetMapping("/all")
    public ApiResult<Map<String,Object>> getInfo(){
        Map<String, Object> map = new HashMap<>();
        List<Details> list = detailsService.list(new LambdaQueryWrapper<Details>().orderByDesc(Details::getConfirmAdd));
        map.put("updateTime",detailsTask.getUpdateTime());
        map.put("info",list);
        return ApiResult.success(map);
    }

    /**
     * 查询指定省份疫情数据
     * @param ProvinceName
     * @return
     */
    @GetMapping("/info")
    public ApiResult<List> getProvinceInfo(@RequestParam("provinceName") String ProvinceName){
        LambdaQueryWrapper<Details> wrapper = new LambdaQueryWrapper<Details>()
                .eq(Details::getProvince, ProvinceName)
                .orderByDesc(Details::getConfirmAdd);
        List<Details> list = detailsService.list(wrapper);
        return ApiResult.success(list);
    }
}
