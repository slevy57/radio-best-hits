package com.codecool.database;



import java.sql.*;

public class RadioCharts {
    private final String db_url;
    private final String db_user;
    private final String db_password;

    public RadioCharts(String db_url, String db_user, String db_password) {
        this.db_url = db_url;
        this.db_user = db_user;
        this.db_password = db_password;
        getConnection();
    }

    public Connection getConnection() {
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(db_url, db_user, db_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }

    public String getMostPlayedSong(){

        String sql = "SELECT song, MAX(times_aired) " +
                    "FROM music_broadcast " +
                    "GROUP BY song " +
                    "ORDER BY times_aired DESC " +
                    "LIMIT 1";

        return getResult(sql, "song");
    }

    public String getMostActiveArtist(){
        String sql = "SELECT artist, song " +
                    "FROM music_broadcast " +
                    "GROUP BY artist, song " +
                    "ORDER BY COUNT(song) DESC";

        return getResult(sql, "artist");
    }

    private String getResult(String sql, String column){

        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return resultSet.getString(column);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }
}
