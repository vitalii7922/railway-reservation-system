package com.tsystems.project.dto;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class StationDto implements Serializable, Comparable<StationDto>  {

    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationDto)) return false;
        StationDto that = (StationDto) o;
        return getId() == that.getId() &&
                getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public int compareTo(@NotNull StationDto o) {
         return name.compareTo(o.name);
    }
}