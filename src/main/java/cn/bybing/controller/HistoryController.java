package cn.bybing.controller;


import cn.bybing.api.ApiResult;
import cn.bybing.entity.Details;
import cn.bybing.entity.History;
import cn.bybing.service.HistoryService;
import cn.bybing.task.DetailsTask;
import cn.bybing.task.HistoryTask;
import cn.bybing.task.SaveTask;
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
 *  每日历史数据
 *
 * </p>
 *
 * @author jhonny
 * @since 2022-01-11
 */
@RestController
@RequestMapping(value = "/history",produces = "application/json; charset=utf-8")
public class HistoryController {
    @Resource
    private HistoryTask historyTask;

    @Resource
    private SaveTask saveTask;


    @Resource
    private HistoryService historyService;

    @GetMapping("/all")
    public ApiResult<Map<String, Object>> getHistoryInfo(){
        updateHistoryData();
        Map<String, Object> map = new HashMap<>();
        List<History> list = historyService.list(new LambdaQueryWrapper<History>().orderByDesc(History::getDs));
        if(list.size() == 0){
            saveTask.updateHistoryData();
        }
        map.put("date",historyTask.getUpdateTime());
        map.put("info",list);
        return ApiResult.success(map);
    }

    /*
    更新历史数据
     */
    private void updateHistoryData(){
        Map<String,History> allHistory = historyTask.getHistory();
        for(Map.Entry<String,History> entry:allHistory.entrySet()){
            String key = entry.getKey();
            History history = allHistory.get(key);
            this.historyService.saveHistory(history);
        }
    }
}
