package spi;

/**
 * service provider interface
 * 参考文章：https://blog.csdn.net/sigangjun/article/details/79071850
 * ”一旦代码里涉及具体的实现类，就违反了可拔插的原则，如果需要替换一种实现，就需要修改代码。“
 * 这种原生的SPI的PREFIX为META-INF/services/，而spring定义的PREFIX为METF-INF/spring.factories
 */
public class Main {
    public static void main(String[] args) {
        Search search = SearchFactory.newSearch();
        search.search("java spi test");
    }
}
