/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyinc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DimiGhost
 */
public class Database {

    private Connection connection;

    public Database() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        inicializa();
    }

    private void inicializa() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        statement.executeUpdate("create table poi (nome string, coordenada_x unsigned integer, coordenada_y unsigned integer)");
        statement.executeUpdate("insert into poi(nome, coordenada_x, coordenada_y) values('Lanchonete', 27, 12)");
        statement.executeUpdate("insert into poi(nome, coordenada_x, coordenada_y) values('Posto', 31, 18)");
        statement.executeUpdate("insert into poi(nome, coordenada_x, coordenada_y) values('Joalheria', 15, 12)");
        statement.executeUpdate("insert into poi(nome, coordenada_x, coordenada_y) values('Floricultura', 19, 31)");
        statement.executeUpdate("insert into poi(nome, coordenada_x, coordenada_y) values('Pub', 12, 8)");
        statement.executeUpdate("insert into poi(nome, coordenada_x, coordenada_y) values('Supermercado', 23, 6)");
        statement.executeUpdate("insert into poi(nome, coordenada_x, coordenada_y) values('Churrascaria', 28, 2)");

    }

    public List<POI> listaTodosPOI() throws SQLException {
        List<POI> lista = new ArrayList<>();
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from poi");
        
        while (rs.next()) {
            lista.add(new POI(rs.getString("nome"), rs.getInt("coordenada_x"), rs.getInt("coordenada_y")));
        }
        
        return lista;
    }
    
    public List<POI> procurarPOIProximo(int x, int y, float distancia) throws SQLException {
        List<POI> lista = new ArrayList<>();
        
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select nome, coordenada_x, coordenada_y, (SQRT((coordenada_x - " + x + ")*(coordenada_x - " + x + ") + (coordenada_y - " + y + ")*(coordenada_y - " + y + "))) AS distancia from poi where distancia <= " + distancia);
        
        while (rs.next()) {
            lista.add(new POI(rs.getString("nome"), rs.getInt("coordenada_x"), rs.getInt("coordenada_y"), rs.getInt("distancia")));
        }
        
        return lista;
    }
    
    public void inserirPOI(POI poi) throws SQLException{
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into poi(nome, coordenada_x, coordenada_y) values('" + poi.getNome() + "', " + poi.getCoordenadaX() + ", " + poi.getCoordenadaY() + ")");
    }

}
