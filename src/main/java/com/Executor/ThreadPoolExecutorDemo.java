package com.Executor;

// 移除多余的 CSSPageRule 导入
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 演示通过 ThreadPoolExecutor 构造函数手动创建线程池
 * 这种方式符合《阿里巴巴 Java 开发手册》规范，能让开发者明确线程池的运行规则。
 */
public class ThreadPoolExecutorDemo {

    // 1. 核心线程数 (corePoolSize)
    // 含义：线程池中的"正式员工"。即使空闲，这些线程也不会被销毁（除非设置了 allowCoreThreadTimeOut）。
    // 在本例中，前 5 个任务会由这 5 个核心线程立即处理。
    private static final int CORE_POOL_SIZE = 5;

    // 2. 最大线程数 (maximumPoolSize)
    // 含义：线程池允许创建的最大线程数量（正式员工 + 临时工）。
    // 触发条件：只有当"核心线程满了" 且 "任务队列也满了" 时，才会创建新线程（临时工）直到达到这个最大值。
    private static final int MAX_POOL_SIZE = 10;

    // 3. 队列容量 (capacity)
    // 含义：任务队列的大小。这里使用的是 ArrayBlockingQueue (有界阻塞队列)。
    // 作用：当核心线程都在忙时，新来的任务会先进入这个"候车室"排队。
    private static final int QUEUE_CAPACITY = 100;

    // 4. 空闲线程存活时间 (keepAliveTime)
    // 含义：当线程数 > 核心线程数时，多余的"临时工"线程如果空闲超过这个时间，就会被辞退（销毁）。
    private static final Long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args) {

        // ==========================================
        // 步骤 1: 创建线程池 (最关键的部分)
        // ==========================================
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,                      // 参数1: 核心线程数 (5)
                MAX_POOL_SIZE,                       // 参数2: 最大线程数 (10)
                KEEP_ALIVE_TIME,                     // 参数3: 空闲存活时间 (1秒)
                TimeUnit.SECONDS,                    // 参数4: 时间单位 (秒)
                new ArrayBlockingQueue<>(QUEUE_CAPACITY), // 参数5: 任务队列 (容量100的有界队列)
                // 参数6: 拒绝策略 (CallerRunsPolicy)
                // 含义：如果队列满了(100) 且 线程达到最大值(10)，新任务无法处理时：
                // "谁提交的任务，谁自己去执行"。在这里，如果线程池爆了，main 主线程会自己去跑任务，
                // 这会导致 main 线程无法继续提交新任务，从而起到了"限流"的作用。
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // ==========================================
        // 步骤 2: 提交任务
        // ==========================================
        // 模拟提交 10 个任务
        for (int i = 0; i < 10; i++) {
            // 创建任务对象 (假设 MyRunnable 是你之前写的那个类)
            MyRunnable myRunnable = new MyRunnable("" + i);

            // 执行逻辑分析：
            // 1. i=0 到 4 (共5个任务)：直接由 5 个核心线程处理。
            // 2. i=5 到 9 (共5个任务)：核心线程已满，这 5 个任务会进入 ArrayBlockingQueue 排队。
            // 3. 此时队列远未满 (5/100)，所以不会创建非核心线程，也不会触发拒绝策略。
            executor.execute(myRunnable);
        }

        // ==========================================
        // 步骤 3: 关闭线程池
        // ==========================================
        // shutdown(): 平滑关闭。
        // 1. 停止接收新任务。
        // 2. 等待已提交的任务（包括正在运行的和队列里排队的）全部执行完毕。
        executor.shutdown();

        // ==========================================
        // 步骤 4: 等待所有任务结束
        // ==========================================
        // isTerminated(): 只有调用了 shutdown() 且所有任务都执行完了，才会返回 true。
        // 下面是一个"自旋"循环 (Busy Waiting)，主线程会卡在这里不断检查，直到线程池彻底关闭。
        // 注意：在实际生产中，通常建议使用 executor.awaitTermination() 方法，因为它不会像这样死循环消耗 CPU。
        while (!executor.isTerminated()) {
            // 空循环，等待...
        }

        System.out.println("Finished all threads");
    }
}