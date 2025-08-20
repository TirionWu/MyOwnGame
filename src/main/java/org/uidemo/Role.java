package org.uidemo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Role {
    private String name;
    private double hp;
    private double atk;;
    private double def;
}
