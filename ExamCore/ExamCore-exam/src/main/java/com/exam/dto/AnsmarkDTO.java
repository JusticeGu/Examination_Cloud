package com.exam.dto;


import com.exam.entity.Examdata;
import lombok.Data;

import java.util.List;
@Data
public class AnsmarkDTO {
    private float score;

    private List<String> wrongIds;

    //private List<Examdata> examdata;
}
