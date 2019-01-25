package com.bmn.core;

/**
 * @author: zyq
 * @date: 2018/12/29
 */
@FunctionalInterface
public interface ApplicationRunner {

    void run(String[] args) throws Exception;
}
