package TemplatePattern.group;

import TemplatePattern.HttpUtil;
import TemplatePattern.NetMall;
import com.alibaba.fastjson.JSON;
import sun.misc.BASE64Encoder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JDNetMall extends NetMall {
    public JDNetMall(String uId, String uPwd) {
        super(uId, uPwd);
    }

    @Override
    protected Boolean login(String uId, String uPwd) {
        logger.info("模拟京东登录 {}，{}",uId, uPwd);
        return null;
    }

    @Override
    protected Map<String, String> reptile(String skuUrl) {
        String str = HttpUtil.doGet(skuUrl);
        Pattern pattern = Pattern.compile("(?<=title\\>).*(?=</title)");
        Matcher matcher = pattern.matcher(str);
        Map<String, String> map = new ConcurrentHashMap<>();
        if (matcher.find()) map.put("name", matcher.group());
        map.put("price","5999");
        logger.info("模拟京东商品爬虫解析{}|{}元 {}",map.get("name"), map.get("price"), skuUrl);

        return map;
    }

    @Override
    protected String createBase64(Map<String, String> goodsInfo) {
        BASE64Encoder encoder = new BASE64Encoder();
        logger.info("模拟生成京东商品base64海报");
        return encoder.encode(JSON.toJSONString(goodsInfo).getBytes());
    }
}
