package com.wems.model;

public class GenericValue
{
    private int valueId;
    private String value;
    
    public GenericValue(
                    int valueId,
                    String value)
    {
        this.valueId = valueId;
        this.value   = value;
    }

    public int valueId() {return valueId;}
    public String value(){return value;  }
}