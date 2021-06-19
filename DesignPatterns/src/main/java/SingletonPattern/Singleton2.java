package SingletonPattern;

/**
 * 这种实现单例模式的方式很巧妙
 * 参考：liaoxuefeng.com/wiki/1252599548343744/1281319214514210
 */
public enum Singleton2 {
    INSANCE;

    Singleton2(){

    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
