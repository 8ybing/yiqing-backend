package cn.bybing;

import cn.bybing.entity.Chinatotal;
import cn.bybing.entity.Details;
import cn.bybing.entity.History;
import cn.bybing.mapper.ChinatotalMapper;
import cn.bybing.mapper.DetailsMapper;
import cn.bybing.service.ChinatotalService;
import cn.bybing.service.DetailsService;
import cn.bybing.service.HistoryService;
import cn.bybing.task.DetailsTask;
import cn.bybing.task.HistoryTask;
import cn.bybing.task.TotalTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


@SpringBootTest
class CovidWebBackendApplicationTests {

    @Resource
    private DetailsMapper detailsMapper;

    @Resource
    private DetailsService detailsService;

    @Resource
    private DetailsTask detailsTask;

    @Resource
    private HistoryTask historyTask;

    @Resource
    private HistoryService historyService;

    @Resource
    private ChinatotalMapper chinatotalMapper;

    @Test
    void contextLoads() throws IOException {
        DetailsTask detailsTask = new DetailsTask();
        Map<String, Details> details = detailsTask.getAllDetails();
        System.out.println(details);
    }

    @Test
    void test1(){
        System.out.println(detailsMapper.findCityCode("武汉"));
    }

    @Test
    void saveTest(){
        Map<String,Details> allDetails = detailsTask.getAllDetails();
        for(Map.Entry<String,Details> entry:allDetails.entrySet()){
            String key = entry.getKey();
            Details details = allDetails.get(key);
            this.detailsService.saveDetails(details);
        }
    }

    @Test
    void historyTest(){
        Map<String, History> history = historyTask.getHistory();
        System.out.println(history);
    }

    @Test
    void saveHistoryTest(){
        Map<String,History> allHistory = historyTask.getHistory();
        for(Map.Entry<String,History> entry:allHistory.entrySet()){
            String key = entry.getKey();
            History history = allHistory.get(key);
            this.historyService.saveHistory(history);
        }
    }

    @Test
    void test3(){
        TotalTask totalTask = new TotalTask();
        Map<String, Chinatotal> all = totalTask.getAll();
        System.out.println(all);
    }

    @Test
    void test4(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = dateFormat.format(date);
        System.out.println(format);

    }

    @Test
    void testSaveTotal(){
        TotalTask totalTask = new TotalTask();
        Map<String, Chinatotal> all = totalTask.getAll();
        chinatotalMapper.insert(all.get("chinaTotal"));
    }

}
