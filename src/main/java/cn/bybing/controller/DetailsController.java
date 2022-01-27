package cn.bybing.controller;


import cn.bybing.api.ApiResult;
import cn.bybing.entity.Details;
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
    public ApiResult<List> getProvinceInfo(@RequestParam(value = "provinceName",defaultValue = "北京") String ProvinceName){
        LambdaQueryWrapper<Details> wrapper = new LambdaQueryWrapper<Details>()
                .eq(Details::getProvince, ProvinceName)
                .orderByDesc(Details::getConfirmAdd);
        List<Details> list = detailsService.list(wrapper);
        if(list.size() == 0){
            saveTask.updateDetailsData();
            return ApiResult.failed("查询失败！");
        }
        return ApiResult.success(list);
    }

    /*
    更新每天最新数据并存入数据库
     */
//    @Scheduled(cron = "0 0 * * * *")
//    private void updateDetailsData(){
//        HashMap<String, Object> map = new HashMap<>();
//        Map<String,Details> allDetails = detailsTask.getAllDetails();
//        Object updateTime = detailsTask.getUpdateTime();
//        //库中有多少条数据
//        int dataCount = detailsService.count();
//        map.put("count",dataCount);
//        //最新数据更新时间
//        map.put("update_time",updateTime);
//        for(Map.Entry<String,Details> entry:allDetails.entrySet()){
//            String key = entry.getKey();
//            Details details = allDetails.get(key);
//            this.detailsService.saveDetails(map,details);
//        }
//    }
}
