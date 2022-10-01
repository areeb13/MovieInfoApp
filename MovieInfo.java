package movie;

import javax.swing.JButton;

public class MovieInfo extends JButton{
    private String title, poster, date, director, writer, genre,
                    actors, boxOffice, awards, summary, rating;
    
    MovieInfo(){}

    //overloaded constructor
    MovieInfo(String t, String p,String d, String di, String w, String g, String a, String b, String aw, String s, String r) {
        title = t; poster = p; date = d; director = di; writer = w; genre = g;
        actors = a; boxOffice = b; awards = aw; summary = s; rating = r;
    }

    //getters
    public String getTitle(){return title;}
    public String getPoster(){return poster;}
    public String getDate(){return date;}
    public String getDirector(){return director;}
    public String getWriter(){return writer;}
    public String getGenre(){return genre;}
    public String getActors(){return actors;}
    public String getBoxOffice(){return boxOffice;}
    public String getAwards(){return awards;}
    public String getSummary(){return summary;}
    public String getRating(){return rating;}

    //setters
    public void setTitle(String t){title = t;}
    public void setPoster(String p){poster = p;}
    public void setDate(String d){date = d;}
    public void setDirector(String d){director = d;}
    public void setWriter(String w){writer = w;}
    public void setGenre(String g){genre = g;}
    public void setActors(String a){actors = a;}
    public void setBoxOffice(String b){boxOffice = b;}
    public void setAwards(String a){awards = a;}
    public void setSummary(String s){summary = s;}
    public void setRating(String r){rating = r;}
    
    
}
/*
 * image
 * date
 * director
 * writer
 * genre
 * actors
 * box office
 * awards
 * summary
 * rating
 */