package cn.bybing;

import cn.bybing.entity.Chinatotal;
import cn.bybing.entity.Details;
import cn.bybing.entity.History;
import cn.bybing.entity.dto.ProvinceDetails;
import cn.bybing.mapper.ChinatotalMapper;
import cn.bybing.mapper.DetailsMapper;
import cn.bybing.service.ChinatotalService;
import cn.bybing.service.DetailsService;
import cn.bybing.service.HistoryService;
import cn.bybing.task.DetailsTask;
import cn.bybing.task.HistoryTask;
import cn.bybing.task.TotalTask;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.extra.pinyin.engine.tinypinyin.TinyPinyinEngine;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@SpringBootTest
@Ignore
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

    @Resource
    private ChinatotalService chinatotalService;

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
            Collection<Details> collecton = new ArrayList<>();
            String key = entry.getKey();
            Details details = allDetails.get(key);
            collecton.add(details);
            this.detailsService.saveBatch(collecton);
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

    @Test
    void checkupdatetime(){
        QueryWrapper<Details> wrapper = new QueryWrapper<Details>().select(Details.class, info -> info.getColumn().equals("update_time"));
        System.out.println(detailsMapper.selectOne(wrapper));
    }

    @Test
    void counttest(){
         int count = detailsService.count();
        System.out.println(count);
    }

    @Test
    void checkupdatetime2(){
        QueryWrapper<Details> wrapper = new QueryWrapper<Details>().last("limit 1");
        String updateTime = detailsMapper.selectOne(wrapper).getUpdateTime();
        System.out.println(updateTime);
        String s = detailsTask.getUpdateTime().toString();
        if(updateTime.equals(s)){
            System.out.println("true");
        }else{
            System.out.println("false");
        }
    }

    @Test
    void test5(){
        LambdaQueryWrapper<Chinatotal> wrapper = new LambdaQueryWrapper<Chinatotal>().orderByDesc(Chinatotal::getUpdatetime).last("limit 1");
        String updatetime = chinatotalService.getOne(wrapper).getUpdatetime();
        System.out.println(updatetime);
    }

    @Test
    void counttest2(){
        List<Details> list = detailsService.list(new LambdaQueryWrapper<Details>().ne(Details::getRiskGrade,"null").orderByDesc(Details::getConfirmAdd));
        System.out.println(list);
    }

    @Test
    void testpinyin(){
        Map<String, ProvinceDetails> provinceDetails = detailsTask.getProvinceDetails();
        List<ProvinceDetails> list = new ArrayList<>();
        for(Map.Entry<String, ProvinceDetails> entry:provinceDetails.entrySet()){
            list.add(entry.getValue());
        }
        System.out.println(list);
    }

    @Test
    void testcompare(){
        String url_compare = HttpUtil.get("https://view.inews.qq.com/g2/getOnsInfo?name=disease_other");
        JSONObject jsonObject_compare = JSON.parseObject(url_compare);
        //获取key为data的json对象
        JSONObject data_compare = jsonObject_compare.getJSONObject("data");
        JSONObject provinceCompare = data_compare.getJSONObject("provinceCompare");
        System.out.println(provinceCompare.get("浙江"));
    }

}
