package AdaptorPattern;

import java.nio.IntBuffer;
import java.nio.channels.*;
import java.util.concurrent.Callable;

public class MyCallable<V> implements Callable<V> {
    @Override
    public V call() throws Exception {
        // TODO: 2020/11/16 实现业务逻辑，返回值是该任务的返回值
        return null;
    }
}



