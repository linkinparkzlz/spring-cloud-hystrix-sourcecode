package com.example.springcloudhystrixsourcecode.rxjava;

import rx.Single;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;

public class RxJavaDemo {


    public static void main(String[] args) {


        System.out.println("主线程 :" + Thread.currentThread().getName());


    }


    private static void singleDemo() {


        Single.just("Hello   world").subscribeOn(Schedulers.io())// 在IO线程 上执行
                .subscribe(System.out::println);


    }


    private static void demoStandardReactive() throws InterruptedException {

        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);


        rx.Observable.from(values)
                .subscribeOn(Schedulers.newThread())
                .subscribe(value -> {
                    println("消费数据 :" + value);

                    if (value > 2) {
                        throw new IllegalStateException("数据不应该大于2");
                    }
                }, e -> {
                    println("异常"+ e.getMessage());
                }, () ->
                {
                    println("流程结束");
                });


    }


    public static void demoObservable() throws InterruptedException {


        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        rx.Observable.from(values)
                .subscribeOn(Schedulers.computation())
                .subscribe(RxJavaDemo::println);

        Thread.sleep(1000);
    }

    private static void println(Integer integer) {
    }


    private static void println(String integer) {


    }


}


























































