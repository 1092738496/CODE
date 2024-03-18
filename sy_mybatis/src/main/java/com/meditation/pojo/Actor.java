package com.meditation.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 *
 *
 *
 *@time: 2023/12/28 4:49
 *@description: 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
@Slf4j
public class Actor {

    private int actorId;
    private String firstName;
    private String lastName;
    private Date lastUpdate;


}