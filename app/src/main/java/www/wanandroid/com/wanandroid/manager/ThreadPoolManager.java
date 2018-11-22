package www.wanandroid.com.wanandroid.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*线程池管理类
 * */
public class ThreadPoolManager {
    private static ThreadPoolManager instance;
    private ThreadPoolExecutor threadPoolExecutor = null;
    //一个核心线程数量固定的线程池
    private ExecutorService fixedThreadPool = null;
    //可缓存线程池，此线程池优势是它可以根据程序的运行情况来调整线程池的线程数量
    private ExecutorService cachedThreadPool = null;
    //定时定期执行任务
    private ScheduledExecutorService scheduledExecutorService = null;

    private ThreadPoolManager() {

    }

    //获取单例
    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            instance = new ThreadPoolManager();
        }
        return instance;
    }

    /**
     * 获取自己构造类型的线程池对象
     *
     * @param corePoolSize   核心线程数
     * @param maxnumPoolSize 线程池最大非核心线程数
     * @return 生成的线程池对象
     */

    public ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize, int maxnumPoolSize, int workQueueSize) {
        if (threadPoolExecutor == null) {
            long keepAliveTime = 60;
            LinkedBlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>(workQueueSize);
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxnumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
            //设置核心线程不被回收
            threadPoolExecutor.allowCoreThreadTimeOut(false);
        }
        return threadPoolExecutor;
    }

    /**
     * 获取CachedThreadPool对象
     *
     * @return 生成的CachedThreadPool对象
     */
    public ExecutorService getCachedThreadPool() {
        if (cachedThreadPool == null) {
            /*fixedThreadPool是一个没有核心线程的线程池
             *核心线程超时时间为60秒
             *即意味着在一个任务执行完成后60秒内有新的任务进来,则不会创建新的线程
             *使用的队列为SynchronousQueue,队列的大小为默认(Integer.MAX_VALUE)
             */
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        return cachedThreadPool;
    }


    /*获取固定核心线程数量的线程池*/

    public ExecutorService getFixedThreadPool(int corePoolSize) {
        if (fixedThreadPool == null) {
            fixedThreadPool = Executors.newFixedThreadPool(corePoolSize);
        }
        return fixedThreadPool;
    }

    /**
     * 获取ScheduledExecutorService对象
     *
     * @param corePoolSize 核心线程数
     * @return 生成的ScheduledExecutorService对象
     */

    public ScheduledExecutorService getScheduledExecutorService(int corePoolSize) {
        if (scheduledExecutorService == null) {
            scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize);
        }
        return scheduledExecutorService;
    }



}
