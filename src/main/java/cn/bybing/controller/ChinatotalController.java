package cn.bybing.controller;


import cn.bybing.api.ApiResult;
import cn.bybing.entity.Chinatotal;
import cn.bybing.entity.Details;
import cn.bybing.mapper.ChinatotalMapper;
import cn.bybing.service.ChinatotalService;
import cn.bybing.task.SaveTask;
import cn.bybing.task.TotalTask;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@RestController
@RequestMapping(value = "/total",produces = "application/json; charset=utf-8")
public class ChinatotalController {

    @Resource
    private TotalTask totalTask;

    @Resource
    private ChinatotalService chinatotalService;

    @Resource
    private SaveTask saveTask;



    @GetMapping("/all")
    public ApiResult<Map<String, Object>> getAll(){
        Map<String, Object> map = new HashMap<>();
        Map<String, Chinatotal> all = totalTask.getAll();
        chinatotalService.saveChinatotal(all.get("chinaTotal"));
        List<Chinatotal> list = chinatotalService.list(new LambdaQueryWrapper<Chinatotal>().orderByDesc(Chinatotal::getUpdatetime));
        if(list.size() == 0){
            saveTask.updateChinaTotalData();
        }
        map.put("updateTime",totalTask.getUpdateTime());
        map.put("info",list);
        return ApiResult.success(map);
    }
}
