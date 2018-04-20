package com.example.springcloudhystrixsourcecode.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 通过Future  实现熔断
 */
public class FutureCircuitBreakerDemo {


    public static void main(String[] args) {


        ExecutorService executorService = Executors.newSingleThreadExecutor();

        RandomCommand command = new RandomCommand();

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return command.run();
            }
        });

        String result = null;

        try {
            result = future.get(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {

            result = command.fallback();
            e.printStackTrace();
        } catch (ExecutionException e) {

            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        executorService.shutdown();


    }

    private static final Random random = new Random();

    public static class RandomCommand implements Command<String> {


        @Override
        public String run() throws InterruptedException {

            long execuateTime = random.nextInt(200);

            System.out.println("执行时间 " + execuateTime);

            Thread.sleep(execuateTime);

            return "hello";
        }

        @Override
        public String fallback() {
            return "fallback";
        }
    }


    public interface Command<T> {

        /**
         * 正常执行
         *
         * @return
         */
        T run() throws Exception;

        /**
         * 异常时
         */
        T fallback();

    }


}













































































