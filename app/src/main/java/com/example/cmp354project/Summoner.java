package com.example.cmp354project;

public class Summoner {

    private String summonerName;
    private String Rank;
    private String Region;



    public String toString() {
      return "Summoner{ "+
              "Summoner Name='"+ summonerName+'\''+
              ", Region = '"+ Region+'\''+
              ", Rank = '"+Rank+"}";
    };

    public Summoner() {}

    public Summoner(String summonerName, String Region, String Rank) {

    this.summonerName =summonerName;
    this.Rank=Rank;
    this.Region=Region;

    }



        public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
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




}
