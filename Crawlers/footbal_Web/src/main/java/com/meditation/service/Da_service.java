package com.meditation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @time: 2024/7/7 9:38
 * @description:
 */

@Service
public class Da_service {
    private final DecimalFormat df = new DecimalFormat("#.##");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
    private int currentYear = Year.now().getValue();

    @Autowired
    ThreadPoolExecutor pool;

    @Autowired
    com.meditation.dao.da da;

    public LinkedHashMap<String, List<List<String>>> Da_compute(String sid) {
        //long startTime1 = System.nanoTime();
        LinkedHashMap<String, List<List<String>>> maps_s = da.xiang_tongji(sid);

        /*long endTime1 = System.nanoTime();
        long duration1 = endTime1 - startTime1;
        System.out.printf("请求,所花费时间: %.3f 毫秒%n", duration1 / 1_000_000.0);*/


        // long startTime = System.nanoTime();
        for (String name : maps_s.keySet()) {
            List<CompletableFuture<List<String>>> futures = new ArrayList<>();

            //System.out.println(name);
            List<List<String>> lists = maps_s.get(name);
            // List<List<String>> lists = new CopyOnWriteArrayList<>(maps_s.get(name));
            LinkedHashMap<String, List<List<String>>> mapx = new LinkedHashMap<>(maps_s);
            mapx.remove(name);
            for (int i = 0; i < lists.size(); i++) {
                int finalI = i;
                CompletableFuture future = CompletableFuture.runAsync(() -> {
                    List<String> list = lists.get(finalI);
                    double z_subtrahend = Double.parseDouble(list.get(0));
                    double z_minuend = Double.parseDouble(list.get(0));
                    if (finalI < lists.size() - 1) {
                        z_minuend = Double.parseDouble(lists.get(finalI + 1).get(0));
                    }
                    String z_Num = df.format(z_subtrahend - z_minuend);

                    //System.out.println("主:" + z_subtrahend + "-" + z_minuend + "=" + z_Num);

                    double k_subtrahend = Double.parseDouble(list.get(2));
                    double k_minuend = Double.parseDouble(list.get(2));
                    if (finalI < lists.size() - 1) {
                        k_minuend = Double.parseDouble(lists.get(finalI + 1).get(2));
                    }
                    String k_Num = df.format(k_subtrahend - k_minuend);

                    //System.out.println("客:" + k_subtrahend + "-" + k_minuend + "=" + k_Num);

                    String Thistime = list.get(3);
                    List<Double> double_value = mean_value(mapx, Thistime, z_subtrahend, k_subtrahend);
                    list.add(z_Num);
                    list.add(df.format(double_value.get(0)));
                    list.add(k_Num);
                    list.add(df.format(double_value.get(1)));
                    // System.out.println(finalI+" : "+Thread.currentThread().getName());
                }, pool);
                futures.add(future);
            }

            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

            // 阻塞主线程，等待所有任务及其后续处理完成
            combinedFuture.join();

            mapx.clear();
        }
        // 计算并打印执行时间
       /* long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.printf("计算,所花费时间: %.3f 毫秒%n", duration / 1_000_000.0);*/
        return maps_s;
    }


    private List<Double> mean_value(LinkedHashMap<String, List<List<String>>> mapx, String Thistime,
                                    double z_subtrahend,
                                    double k_subtrahend) {
        //System.out.println(Thistime);
        LocalDateTime localThistime = LocalDateTime.parse(currentYear + "-" + Thistime,
                formatter);
        List<Double> zdoubles = new ArrayList<>();
        zdoubles.add(z_subtrahend);
        List<Double> kdoubles = new ArrayList<>();
        kdoubles.add(k_subtrahend);
        for (String name : mapx.keySet()) {
            List<List<String>> lists = mapx.get(name);
            int x = this.binary_search(lists, localThistime);


            if (x != -1) {
                zdoubles.add(Double.parseDouble(lists.get(x).get(0)));
                kdoubles.add(Double.parseDouble(lists.get(x).get(2)));
            }

           /* for (int i = 0; i < lists.size(); i++) {
                List<String> list = lists.get(i);
                String b_time = list.get(3);
                if (Thistime.equals(b_time)) {
                    //System.out.println(list);
                    zdoubles.add(Double.parseDouble(list.get(0)));
                    kdoubles.add(Double.parseDouble(list.get(2)));
                    break;
                } else {
                    LocalDateTime localThistime = LocalDateTime.parse(currentYear + "-" + Thistime,
                            formatter);
                    LocalDateTime localB_time = LocalDateTime.parse(currentYear + "-" + b_time, formatter);
                    if (localB_time.isBefore(localThistime)) {
                        //System.out.println(list);
                        zdoubles.add(Double.parseDouble(list.get(0)));
                        kdoubles.add(Double.parseDouble(list.get(2)));
                        break;
                    }
                }
            }*/
        }
        double Znum = zdoubles.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
        double Knum = kdoubles.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
        List<Double> double_value = new ArrayList<>();
        double_value.add(Znum);
        double_value.add(Knum);

        return double_value;
    }

    public int binary_search(List<List<String>> lists, LocalDateTime targetDateTime) {
        int left = 0;
        int right = lists.size() - 1;
        int closestIndex = -1;
        if (lists.size() != 0) {
            if (targetDateTime.isAfter(LocalDateTime.parse(currentYear + "-" + lists.get(0).get(3), formatter))) {
                return 0;
            }

            if (targetDateTime.isBefore(LocalDateTime.parse(currentYear + "-" + lists.get(lists.size() - 1).get(3),
                    formatter))) {
                return -1;
            }
        }
        while (left <= right) {
            int mid = left + (right - left) / 2;
            LocalDateTime midDateTime = LocalDateTime.parse(currentYear + "-" + lists.get(mid).get(3), formatter);

            // 检查是否找到了匹配项或需要调整搜索范围
            int comparisonResult = midDateTime.compareTo(targetDateTime);
            if (comparisonResult <= 0) { // 找到了不大于目标日期的日期
                closestIndex = mid;
                if (comparisonResult == 0) break; // 如果找到确切匹配，停止搜索
                right = mid - 1; // 否则，继续在左侧寻找更接近的日期
            } else {
                left = mid + 1; // 逆序，因此在右侧继续搜索
            }
        }

        // 如果closestIndex仍为-1，说明所有日期都大于目标日期，这是不可能的，因为我们期望至少有一个不大于目标日期的日期
        // 但为了逻辑的完整性，这里应该不会发生
        return closestIndex;
    }

    public LinkedHashMap<String, List<List<String>>> time_filtrate(String id, String timeStr, int hour) {
        LocalDateTime thisTime = LocalDateTime.parse(timeStr, formatter);
        LocalDateTime minusTime = thisTime.minusHours(hour);
        //System.out.println(timeStr);
        //System.out.println(minusTime);

        LinkedHashMap<String, List<List<String>>> maps_s = this.Da_compute(id);
        for (String name : maps_s.keySet()) {
            //System.out.println(name);
            List<List<String>> lists = maps_s.get(name);
            for (int i = 0; i < lists.size(); i++) {
                LocalDateTime parse = LocalDateTime.parse(currentYear + "-" + lists.get(i).get(3), formatter);
                if ((thisTime.isEqual(parse) || thisTime.isAfter(parse)) & (minusTime.isEqual(parse) || minusTime.isBefore(parse))) {
                    //System.out.println("true:"+lists.get(i).get(3));
                } else {
                    //System.out.println("false:"+lists.get(i).get(3));
                    lists.remove(i);
                    i--;
                }
            }
            //System.out.println("----------------------------------------");
        }
        return maps_s;
    }
}
