/**
 * @FileName: T_SnowflakeIDGenerator.java
 * @Package: com.asura.framework.commons.algorithms
 * @author liusq23
 * @created 2016/12/2 上午10:01
 * <p>
 * Copyright 2017 Asura
 */
package com.asura.framework.commons.algorithms;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public class T_SnowflakeIDGenerator {

    @Test
    public void t_nextId() throws InterruptedException {
        SnowFlakeIDGenerator snowFlakeIDGenerator = new SnowFlakeIDGenerator(null, 1);
        List<Thread> th = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            SnowFlakeTest sn = new SnowFlakeTest(snowFlakeIDGenerator);
            Thread tha = new Thread(sn, "SNOW" + i);
            th.add(tha);
            tha.start();

        }
        for (Thread tha : th) {
            tha.join();
        }
    }

    class SnowFlakeTest implements Runnable {

        private SnowFlakeIDGenerator snowFlakeIDGenerator;

        public SnowFlakeTest(SnowFlakeIDGenerator snowFlakeIDGenerator) {
            this.snowFlakeIDGenerator = snowFlakeIDGenerator;
        }

        @Override
        public void run() {
            List<String> ss = new ArrayList<>(1000);
            for (int i = 0; i < 1000; i++) {
                String s = snowFlakeIDGenerator.nextId();
                ss.add(s);
            }
            printFile(ss);
        }

        public void printFile(List<String> strings) {
            PrintWriter out = null;
            try {
                out = new PrintWriter(new FileOutputStream("/a.txt", true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            for (String ss : strings) {
                out.println(ss);
            }

            out.flush();
            out.close();
        }
    }


}
