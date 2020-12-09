package TemplatePattern;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

public abstract class NetMall {
    protected Logger logger  = LoggerFactory.getLogger(this.getClass());

    private String uId;
    private String uPwd;

    public NetMall(String uId, String uPwd) {
        this.uId = uId;
        this.uPwd = uPwd;
    }

    /**
     * 生成商品推广海报
     * @param skuUrl
     * @return
     */
    public String generateGoodsPoster(String skuUrl) {
        //这个细节我以前没有意识到，我记得像这种在一个方法内调用另一个方法
        // 我一般都会在generateGoodsPoster上传入login所需要的参数，
        // 其实duck不必，直接调用这个类的参数即可！
        if (!login(uId, uPwd)) return null;
        Map<String, String> reptile = reptile(skuUrl);
        return createBase64(reptile);
    }

    /**
     * 模拟登录
     * @param uId
     * @param uPwd
     * @return
     */
    protected abstract Boolean login(String uId, String uPwd);

    /**
     * 爬虫提取商品信息（登录后的优惠价格）
     * @param skuUrl
     * @return
     */
    protected abstract Map<String,String> reptile(String skuUrl);

    /**
     * 生成商品海报信息
     * @param goodsInfo
     * @return
     */
    protected abstract String createBase64(Map<String, String> goodsInfo);


}
