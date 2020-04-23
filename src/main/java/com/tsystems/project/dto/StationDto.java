package com.tsystems.project.dto;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

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
    public int compareTo(@NotNull StationDto o) {
         return name.compareTo(o.name);
    }
}