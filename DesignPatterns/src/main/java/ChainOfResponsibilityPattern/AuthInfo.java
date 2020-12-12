package ChainOfResponsibilityPattern;

/**
 * 不是关键类，只是作为全部审核完毕后的返回值而已，拿给大家看看的罢了
 * <p>一天审核链走完后，将审核过程信息汇总后返回，才会用到这个类</p>
 */
public class AuthInfo {
    private String code;
    private String info="";
    public AuthInfo(String code, String... infos){
        this.code=code;
        for (String info:infos){
            this.info=this.info.concat(info);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
