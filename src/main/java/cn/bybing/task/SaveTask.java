package cn.bybing.task;

import cn.bybing.entity.Chinatotal;
import cn.bybing.entity.Details;
import cn.bybing.entity.History;
import cn.bybing.service.ChinatotalService;
import cn.bybing.service.DetailsService;
import cn.bybing.service.HistoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Jhonny
 * @Date: 2022/01/25/21:19
 * @Description:
 */
@Component
@Slf4j
public class SaveTask implements ApplicationListener<ContextRefreshedEvent> {

    //

    @Resource
    private DetailsTask detailsTask;

    @Resource
    private HistoryTask historyTask;

    @Resource
    private DetailsService detailsService;

    @Resource
    private HistoryService historyService;

    @Resource
    private ChinatotalService chinatotalService;

    /*
      更新每天最新数据并存入数据库
    */
    @Scheduled(cron = "0 0/15 * * * ? ")
    public int updateDetailsData(){
        Map<String,Details> allDetails = detailsTask.getAllDetails();;      
        //最新数据更新时间
        LambdaQueryWrapper<Chinatotal> wrapper = new LambdaQueryWrapper<Chinatotal>().orderByDesc(Chinatotal::getUpdatetime).last("limit 1");
        String updatetime = chinatotalService.getOne(wrapper).getUpdatetime();
        System.out.println(updatetime);
        for(Map.Entry<String,Details> entry:allDetails.entrySet()){
            String key = entry.getKey();
            Details details = allDetails.get(key);
            this.detailsService.saveDetails(updatetime,details);
        }
        log.info("更新details表数据完成");
        return 1;
    }

    /**
     * 更新保存历史数据并存入数据库
     */
    @Scheduled(cron = "0 0 0 ? * * ")
    public void updateHistoryData(){
        Map<String, History> allHistory = historyTask.getHistory();
        for(Map.Entry<String,History> entry:allHistory.entrySet()){
            String key = entry.getKey();
            History history = allHistory.get(key);
            this.historyService.saveHistory(history);
        }
        log.info("更新history表数据完成");
    }

    /**
     * 更新总览并存入数据库
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateChinaTotalData(){
        TotalTask totalTask = new TotalTask();
        Map<String, Chinatotal> all = totalTask.getAll();
        chinatotalService.saveChinatotal(all.get("chinaTotal"));
        log.info("更新chinatotal表数据完成");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        updateChinaTotalData();

    }
}
