package cn.bybing.task;

import cn.bybing.api.ApiResult;
import cn.bybing.entity.Details;
import cn.bybing.entity.dto.ProvinceDetails;
import cn.bybing.mapper.DetailsMapper;
import cn.bybing.service.DetailsService;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Jhonny
 * @Date: 2022/01/11/14:39
 * @Description:
 */
@Component
public class DetailsTask {

    /**
     * 返回每个省份的每个城/市区详情数据
     * @return
     */
    public Map<String,Details> getAllDetails(){
        HashMap<String, Details> map = new HashMap<>();
        String info = HttpUtil.get("https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5");
        JSONObject jsonObject = JSON.parseObject(info);
        //获取key为data的json对象
        JSONObject data = jsonObject.getJSONObject("data");
        //获取key为areaTree的json数组
        JSONArray areaTree = data.getJSONArray("areaTree");
        //获取key为0的json对象,0表示中国
        JSONObject china = areaTree.getJSONObject(0);
        //获取省份列表
        JSONArray provincelist =china.getJSONArray("children");
        for(int ProvinceNum = 0;ProvinceNum < provincelist.size();ProvinceNum++){
            JSONObject province = provincelist.getJSONObject(ProvinceNum);
            Object ProvinceName = province.get("name");//获取省名
            //获取城市列表
            JSONArray cityList = province.getJSONArray("children");
            for(int CityNum=0;CityNum < cityList.size();CityNum++){
                //获取城市
                Details details = new Details();
                JSONObject city = cityList.getJSONObject(CityNum);
                //获取key为total的数据
                JSONObject city_total = city.getJSONObject("total");
                Object city_nowConfirm = city_total.get("nowConfirm");
                Object city_confirm = city_total.get("confirm");//该城市累计确诊
                Object city_heal = city_total.get("heal");//该城市累计治愈
                Object city_dead = city_total.get("dead");//该城市累计死亡
                Object city_healRate = city_total.get("healRate");//该城市治愈率
                Object city_RiskGrade = city_total.get("grade");//该城市风险级别

                long city_confirm1 = Long.valueOf(String.valueOf(city_confirm));
                long city_heal1 = Long.valueOf(String.valueOf(city_heal));
                long city_dead1 = Long.valueOf(String.valueOf(city_dead));
                String city_RiskGrade1 = String.valueOf(city_RiskGrade);
                String city_healRate1 = String.valueOf(city_healRate);
                Long city_nowConfirm1 = Long.valueOf(String.valueOf(city_nowConfirm));
                details.setConfirm(city_confirm1);//设置该市累计确诊
                details.setNowConfirm(city_nowConfirm1);//设置现有确诊
                details.setDead(city_dead1);//设置该市累计死亡
                details.setHeal(city_heal1);//设置该市累计治愈
                details.setHealRate(city_healRate1);//该城市治愈率
                details.setRiskGrade(city_RiskGrade1);//该城市风险级别

                //获取key为today的json对象 当日新增确诊
                JSONObject today = city.getJSONObject("today");
                Object today_confirm = today.get("confirm");
                Long today_confirm1 = Long.valueOf(String.valueOf(today_confirm));
                details.setConfirmAdd(today_confirm1);//设置该市当日新增确诊

                //获取最后更新时间
                Object lastUpdateTimeObj = data.get("lastUpdateTime");
                details.setProvince(ProvinceName.toString());//设置省
                details.setUpdateTime(lastUpdateTimeObj.toString());//设置更新时间
                Object cityName = city.get("name");
                details.setCity(cityName.toString());//设置市名
                UUID uuid = UUID.randomUUID();
                map.put("details-"+uuid.toString().replace("-",""),details);
            }
        }
        return map;
    }

    /**
     * 获取更新时间
     * @return
     */
    public Object getUpdateTime(){
        String info = HttpUtil.get("https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5");
        JSONObject jsonObject = JSON.parseObject(info);
        //获取key为data的json对象
        JSONObject data = jsonObject.getJSONObject("data");
        //获取最后更新时间
        Object lastUpdateTimeObj = data.get("lastUpdateTime");
        return lastUpdateTimeObj;
    }

    /**
     * 返回全部省现有确诊数据
     * @return
     */
    public Map<String, ProvinceDetails> getProvinceDetails(){
        HashMap<String, ProvinceDetails> map = new HashMap<>();

        //省数据较上日比较url
        String url_compare = HttpUtil.get("https://view.inews.qq.com/g2/getOnsInfo?name=disease_other");
        JSONObject jsonObject_compare = JSON.parseObject(url_compare);
        //获取key为data的json对象
        JSONObject data_compare = jsonObject_compare.getJSONObject("data");
        JSONObject provinceCompare = data_compare.getJSONObject("provinceCompare");

        //省数据url
        String info = HttpUtil.get("https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5");
        JSONObject jsonObject = JSON.parseObject(info);
        //获取key为data的json对象
        JSONObject data = jsonObject.getJSONObject("data");
        //获取key为areaTree的json数组
        JSONArray areaTree = data.getJSONArray("areaTree");
        //获取key为0的json对象,0表示中国
        JSONObject china = areaTree.getJSONObject(0);
        //获取省份列表
        JSONArray provincelist =china.getJSONArray("children");
        for (int ProvinceNum = 0; ProvinceNum < provincelist.size(); ProvinceNum++) {
            ProvinceDetails provinceDetails = new ProvinceDetails();
            JSONObject province = provincelist.getJSONObject(ProvinceNum);
            Object ProvinceName = province.get("name");//获取省名
            provinceDetails.setProvinceName(ProvinceName.toString());
            //获取省数据
            JSONObject today = province.getJSONObject("today");
            Object province_confirm_add = today.get("confirm");//每日新增
            JSONObject total = province.getJSONObject("total");
            Object province_all_confirm = total.get("confirm");//总共累计确诊
            Object province_now_confirm = total.get("nowConfirm");//现有确诊
            Object province_dead = total.get("dead");//累计死亡
            Object province_heal = total.get("heal");
            Object province_noinfect = total.get("wzz");//本土无症状
            Long province_confirm_add1 = Long.valueOf(String.valueOf(province_confirm_add));
            Long province_heal1 = Long.valueOf(String.valueOf(province_heal));
            Long province_dead1 = Long.valueOf(String.valueOf(province_dead));
            Long province_noinfect1 = Long.valueOf(String.valueOf(province_noinfect));
            Long province_now_confirm1 = Long.valueOf(String.valueOf(province_now_confirm));
            Long province_all_confirm1 = Long.valueOf(String.valueOf(province_all_confirm));

            //获取省较上日数据
            JSONObject province_compare = provinceCompare.getJSONObject(ProvinceName.toString());
            Object nowConfirm_compare = province_compare.get("nowConfirm");//现有确诊较上日
            Object heal_compare = province_compare.get("heal");//治愈较上日
            Object dead_compare = province_compare.get("dead");//死亡较上日
            Object zero = province_compare.get("zero");//0增加天数

            Long nowConfirm_compare1 = Long.valueOf(String.valueOf(nowConfirm_compare));
            Long heal_compare1 = Long.valueOf(String.valueOf(heal_compare));
            Long dead_compare1 = Long.valueOf(String.valueOf(dead_compare));
            Integer zero1 = Integer.valueOf(String.valueOf(zero));

            provinceDetails.setConfirmAdd(province_confirm_add1);
            provinceDetails.setDead(province_dead1);
            provinceDetails.setHeal(province_heal1);
            provinceDetails.setNoInfect(province_noinfect1);
            provinceDetails.setNowConfirm(province_now_confirm1);
            provinceDetails.setAllConfirm(province_all_confirm1);
            provinceDetails.setNowConfirmCompare(nowConfirm_compare1);
            provinceDetails.setDeadCompare(dead_compare1);
            provinceDetails.setHealCompare(heal_compare1);
            provinceDetails.setZeroDay(zero1);
            String eName = PinyinUtil.getPinyin(ProvinceName.toString(), "");
            provinceDetails.setId(eName + "-" + UUID.randomUUID());
            map.put(provinceDetails.getProvinceName(),provinceDetails);
        }
        return map;
    }


}
