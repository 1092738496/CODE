package com.meditation.service;

import com.meditation.pojo.corporation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @time: 2024/7/18 12:04
 * @description:
 */
@Service
public class Da_service_zjg_ban {
    private final DecimalFormat df = new DecimalFormat("#.##");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m");
    private int currentYear = Year.now().getValue();


    @Autowired
    ThreadPoolExecutor pool;

    @Autowired
    com.meditation.dao.Da_zjg_ban da_zjg_ban;


    public LinkedHashMap<String, corporation> metadata(String sid){
        //long startTime1 = System.nanoTime();
        LinkedHashMap<String, corporation> maps_s = da_zjg_ban.xiang_tongji(sid);

        /*long endTime1 = System.nanoTime();
        long duration1 = endTime1 - startTime1;
        System.out.printf("请求,所花费时间: %.3f 毫秒%n", duration1 / 1_000_000.0);*/
        Map<String, String> mapz = new HashMap<>();

        // long startTime = System.nanoTime();
        for (String name : maps_s.keySet()) {
            List<CompletableFuture<List<String>>> futures = new ArrayList<>();

            //System.out.println(name);
            List<List<String>> lists = maps_s.get(name).getLists();
            // List<List<String>> lists = new CopyOnWriteArrayList<>(maps_s.get(name));
            LinkedHashMap<String, corporation> mapx = new LinkedHashMap<>(maps_s);
            mapx.remove(name);
            String addition = "";
            if (lists.size() >= 3) {
                addition += lists.get(0).get(0) + "-" + lists.get(0).get(2) + " ";
                addition += lists.get(1).get(0) + "-" + lists.get(1).get(2) + " ";
                addition += lists.get(2).get(0) + "-" + lists.get(2).get(2) + " ";
            }
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
            if (!addition.equals("")) {
                mapz.put(name, addition);
            }
            CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            // 阻塞主线程，等待所有任务及其后续处理完成
            combinedFuture.join();

            mapx.clear();
        }
        return maps_s;
    }
    public  LinkedHashMap<String, corporation> Da_compute(String sid) {
        LinkedHashMap<String, corporation> maps_s = metadata(sid);
        compute(maps_s);
        return maps_s;
    }
    public void compute(LinkedHashMap<String, corporation> maps_s){
        //需求2：找出公司之间前三条数据相同
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            alike(maps_s);
        }, pool);
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            corporation_calculate(maps_s);
        }, pool);

        //需求3：计算每家公司正负数级别
        CompletableFuture.allOf(future, future1).join();
    }

    //需求2：找出公司之间前三条数据相同
    public void alike(LinkedHashMap<String, corporation> maps_s){
        Map<String, String> mapz = new HashMap<>();
        for (String name : maps_s.keySet()) {
            List<List<String>> lists = maps_s.get(name).getLists();
            LinkedHashMap<String, corporation> mapx = new LinkedHashMap<>(maps_s);
            mapx.remove(name);
            //需求2：找出公司之间前三条数据相同，需要----------------------
            String addition = "";
            if (lists.size() >= 3) {
                addition += lists.get(0).get(0) + "-" + lists.get(0).get(2) + " ";
                addition += lists.get(1).get(0) + "-" + lists.get(1).get(2) + " ";
                addition += lists.get(2).get(0) + "-" + lists.get(2).get(2) + " ";
            }
            if (!addition.equals("")) {
                mapz.put(name, addition);
            }
        }
        for (String name : mapz.keySet()) {
            String o = mapz.get(name);
            String t = name + ",";
            Map<String, String> mapq = new HashMap<>(mapz);
            mapq.remove(name);
            for (String name2 : mapq.keySet()) {
                String s = mapz.get(name2);
                if (o.equals(s)) {
                    t += name2;
                }
            }

            String[] split = t.split(",");
            if (split.length > 1) {
                maps_s.get(name).setAlike(t + ",共" + split.length + "家,亚盘走势相同");
            }
        }
    }

    private List<Double> mean_value( LinkedHashMap<String, corporation> mapx, String Thistime,
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
            List<List<String>> lists = mapx.get(name).getLists();
            int x = this.binary_search(lists, localThistime);


            if (x != -1) {
                zdoubles.add(Double.parseDouble(lists.get(x).get(0)));
                kdoubles.add(Double.parseDouble(lists.get(x).get(2)));
            }


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
    //需求3：计算每家公司正负数级别
    //需求3：计算每家公司正负数级别
    public void corporation_calculate(LinkedHashMap<String, corporation> maps_s) {
        for (String key : maps_s.keySet()) {
            corporation corporation = maps_s.get(key);
            Map<String, Map<String, Integer>> data_transmit = new HashMap<>();
            data_transmit.put("host", null);
            data_transmit.put("guest", null);
            Map<String, Integer> z_map = new HashMap<>();
            Map<String, Integer> k_map = new HashMap<>();
            int z_up = 0;
            int k_up = 0;

            int x = 1;
            int xi = 0;
            int y = 1;
            int yi = 1;
            boolean v = false;
            boolean b = false;

            int z_cun = 0;
            int k_cun = 0;

            int z_big4 = 0;
            int k_big4 = 0;
            List<List<String>> lists = corporation.getLists();
            for (int i = lists.size() - 1; i >= 0; i--) {

                List<String> list = lists.get(i);
                double z = Double.parseDouble(list.get(5));
                double k = Double.parseDouble(list.get(7));

                if (z > 0) {
                    if (x == 0 & z_up >= 2) {
                        if (z_map.size() != 0 & z_map.get(z_up + "级传递") != null) {
                            z_map.put(z_up + "级传递", z_map.put(z_up + "级传递", 1) + 1);
                            if (z_up >= 4) {
                                z_big4++;
                            }
                            if(z_up >= 2){
                                z_cun++;
                            }
                        } else {
                            z_map.put(z_up + "级传递", 1);
                            if (z_up >= 4) {
                                z_big4++;
                            }
                            if(z_up >= 2){
                                z_cun++;
                            }
                        }
                    }
                    z_up = 0;
                    x++;
                    xi = i;
                    b = true;
                } else if (b) {
                    if (z != 0) {
                        z_up++;
                    }

                    if (z_up == 1) {
                        double z_z_up = Math.abs(z);
                        double va = Double.parseDouble(lists.get(xi).get(5));
                        if (z_z_up <= va) {
                            b = false;
                            z_up = 0;
                        }
                    }

                    if (i == 0) {
                        if (z_up >= 2) {
                            if (z_map.size() != 0 & z_map.get(z_up + "级传递") != null) {
                                z_map.put(z_up + "级传递", z_map.put(z_up + "级传递", 1) + 1);
                                if (z_up >= 4) {
                                    z_big4++;
                                }
                                if(z_up >= 2){
                                    z_cun++;
                                }
                            } else {
                                z_map.put(z_up + "级传递", 1);
                                if (z_up >= 4) {
                                    z_big4++;
                                }
                                if(z_up >= 2){
                                    z_cun++;
                                }
                            }
                        }
                    }
                    x = 0;
                }


                //------------------
                if (k > 0) {
                    if (y == 0 & k_up >= 2) {
                        if (k_map.size() != 0 && k_map.get(k_up + "级传递") != null) {
                            k_map.put(k_up + "级传递", k_map.put(k_up + "级传递", 1) + 1);
                            if (k_up >= 2) {
                                k_cun++;
                            }
                            if (k_up >= 4) {
                                k_big4++;
                            }
                        } else {
                            k_map.put(k_up + "级传递", 1);
                            if (k_up >= 2) {
                                k_cun++;
                            }
                            if (k_up >= 4) {
                                k_big4++;
                            }
                        }
                    }
                    k_up = 0;
                    y++;
                    yi = i;
                    v = true;
                } else if (v) {
                    if (k != 0) {
                        k_up++;
                    }

                    if (k_up == 1) {
                        double k_k_up = Math.abs(k);
                        double va = Double.parseDouble(lists.get(yi).get(7));
                        if (k_k_up <= va) {
                            v = false;
                            k_up = 0;
                        }
                    }
                    if (i == 0) {
                        if (k_up >= 2) {
                            if (k_map.size() != 0 && k_map.get(k_up + "级传递") != null) {
                                k_map.put(k_up + "级传递", k_map.put(k_up + "级传递", 1) + 1);
                                if (k_up >= 2) {
                                    k_cun++;
                                }
                                if (k_up >= 4) {
                                    k_big4++;
                                }
                            } else {
                                k_map.put(k_up + "级传递", 1);
                                if (k_up >= 2) {
                                    k_cun++;
                                }
                                if (k_up >= 4) {
                                    k_big4++;
                                }
                            }
                        }
                    }
                    y = 0;
                }
            }

            System.out.println(key + "| Z总数：" + z_cun + " - K总是：" + k_cun);
            System.out.println(z_big4);

            if (z_cun >= 2) {
                if (!(z_map.size() == 1 & z_map.get("2级传递") != null)) {
                    data_transmit.put("host", z_map);
                }
            }else if(z_cun == 1){
                if (z_big4>=1){
                    data_transmit.put("host", z_map);
                }
            }

            if (k_cun >= 2) {
                if (!(k_map.size() <= 1 & k_map.get("2级传递") != null)) {
                    data_transmit.put("guest", k_map);
                }
            }else if(k_cun == 1){
                if (k_big4>=1){
                    data_transmit.put("guest", k_map);
                }
            }
            maps_s.get(key).setData_transmit(data_transmit);
        }
    }

    public LinkedHashMap<String, corporation> time_filtrate(String id, String timeStr, int hour) {
        LocalDateTime thisTime = LocalDateTime.parse(timeStr, formatter);
        LocalDateTime minusTime = thisTime.minusHours(hour);
        //System.out.println(timeStr);
        //System.out.println(minusTime);

        LinkedHashMap<String, corporation> maps_s = metadata(id);
        for (String name : maps_s.keySet()) {
            //System.out.println(name);
            List<List<String>> lists = maps_s.get(name).getLists();
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
        compute(maps_s);
        return maps_s;
    }
}
