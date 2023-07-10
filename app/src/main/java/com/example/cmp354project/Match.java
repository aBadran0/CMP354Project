package com.example.cmp354project;

import java.util.ArrayList;

public class Match {

    private String ChampionName;
    private String Rank;
    private String Region;
    private String Role;
    private String CreepScore;
    private  String Items;
    private String Damage;
    private String Result;

    public String getResult(){
        return Result;
    }


    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getItems() {
        return Items;

    }

    public void setItems(String items) {
        Items = items;
    }

    public String getDamage() {
        return Damage;
    }

    public void setDamage(String damage) {
        Damage = damage;
    }

    public String getCreepScore() {
        return CreepScore;
    }

    public void setCreepScore(String creepScore) {
        CreepScore = creepScore;
    }


    public Match() {}

    public Match(String championName,String role, String creepScore, String items, String damage, String result) {
        this.ChampionName = championName;
        Role = role;
        CreepScore = creepScore;
        Items = items;
        Damage = damage;
        Result = result;
    }

    public String getChampionName() {
        return ChampionName;
    }

    public void setChampionName(String championName) {
        this.ChampionName = championName;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public void setResult(String result){Result = result;}





}
