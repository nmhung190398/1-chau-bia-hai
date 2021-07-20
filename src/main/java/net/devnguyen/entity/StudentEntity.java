package net.devnguyen.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class StudentEntity {
    private UUID id;
    private String name;
    private BigDecimal point;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }
}
