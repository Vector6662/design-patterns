package ObserverPattern;

public interface Observer {
    /*
    * 又get一个新技能：泛型方法的申明优先于返回值的比如public <T> void func(...)或default <T> T func(...)
    * */
    <T> void onEvent(Event<T> event);
}
