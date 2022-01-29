package cn.bybing.controller;


import cn.bybing.api.ApiResult;
import cn.bybing.entity.Details;
import cn.bybing.entity.dto.ProvinceDetails;
import cn.bybing.service.DetailsService;
import cn.bybing.task.DetailsTask;
import cn.bybing.task.SaveTask;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  前端控制器
 *  城/区/市数据详情信息
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@RestController
@RequestMapping(value = {"/details","/"},produces = "application/json; charset=utf-8")
public class DetailsController {

    @Resource
    private DetailsTask detailsTask;

    @Resource
    private SaveTask saveTask;

    @Resource
    private DetailsService detailsService;

    /**
     * 获取疫情全部数据
     * @return
     */
    @GetMapping("/all")
    public ApiResult<Map<String,Object>> getAllInfo(){
        //更新最新数据
        Map<String, Object> map = new HashMap<>();
        List<Details> list = detailsService.list(new LambdaQueryWrapper<Details>().orderByDesc(Details::getConfirmAdd).ne(Details::getRiskGrade,"null"));
        if(list.size() == 0){
            saveTask.updateDetailsData();
            list = detailsService.list(new LambdaQueryWrapper<Details>().orderByDesc(Details::getConfirmAdd).ne(Details::getRiskGrade,"null"));
        }
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
    public ApiResult<HashMap<String,Object>> findProvinceInfoByName(@RequestParam(value = "provinceName",defaultValue = "北京") String ProvinceName){
        HashMap<String, Object> map = new HashMap<>();
        LambdaQueryWrapper<Details> wrapper = new LambdaQueryWrapper<Details>()
                .eq(Details::getProvince, ProvinceName)
                .orderByDesc(Details::getConfirmAdd);
        Map<String, ProvinceDetails> provinceDetails = detailsTask.getProvinceDetails();
        map.put("provinceData",provinceDetails.get(ProvinceName));
        List<Details> list = detailsService.list(wrapper);
        if(list.size() == 0){
            saveTask.updateDetailsData();
            return ApiResult.failed("查询失败！");
        }
        map.put("citydata",list);
        return ApiResult.success(map);
    }

    /**
     * 返回省数据
     * @return
     */
    @GetMapping("/provinceData")
    public ApiResult<List<ProvinceDetails>> getProvinceInfo(){
        Map<String, ProvinceDetails> provinceDetails = detailsTask.getProvinceDetails();
        List<ProvinceDetails> list = new ArrayList<>();
        for(Map.Entry<String, ProvinceDetails> entry:provinceDetails.entrySet()){
            list.add(entry.getValue());
        }
        return ApiResult.success(list);
    }

    /**
     * 若系统没有及时更新数据，可手动更新
     * @return
     */
    @GetMapping("/update")
    public ApiResult<String> updateDetails(){
        int i = saveTask.updateDetailsData();
        if(i == 1){
            return ApiResult.success("数据更新完成");
        }
        return ApiResult.failed("数据更新异常");
    }
}
