package xyz.luotao.v1.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyCountDTO {
    private String date; // yyyy-MM-dd
    private int count;
}

