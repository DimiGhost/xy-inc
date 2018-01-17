package xyinc.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import xyinc.model.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class POIController {

    
    private Database database;
    
    public POIController(){
        try {
            database = new Database();
        } catch (SQLException ex) {
            Logger.getLogger(POIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GetMapping("/poi/listar")
    public ResponseEntity listarPOI() {
        List<POI> lista = new ArrayList<>();
        
        try {
            lista = database.listaTodosPOI();
        } catch (SQLException ex) {
            Logger.getLogger(POIController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu um erro inesperado, por favor entre em contato.");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }
    
    @GetMapping("/poi/procurar")
    public ResponseEntity proximosPOI(@RequestParam(value="x", defaultValue="20") int x, 
            @RequestParam(value="y", defaultValue="10") int y, 
            @RequestParam(value="distancia", defaultValue="10") int distancia) {
        List<POI> lista = new ArrayList<>();
        
        try {
            lista = database.procurarPOIProximo(x, y, distancia);
        } catch (SQLException ex) {
            Logger.getLogger(POIController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu um erro inesperado, por favor entre em contato.");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }
    
    @PostMapping("/poi/cadastrar")
    public ResponseEntity cadastrarPOI(@RequestBody POI poi) {
        
        if(poi == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A requisição deve ser no formato JSON.");
        }
        
        if(poi.getNome() == null || poi.getNome().equalsIgnoreCase("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do POI deve ser preenchido.");
        }
        
        if(poi.getCoordenadaX() == null || poi.getCoordenadaY() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As coordenadas X e Y do POI devem ser preenchidas.");
        }
        
        if(poi.getCoordenadaX() < 0 || poi.getCoordenadaY() < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As coordenadas X e Y não podem ser negativas.");
        }
        
        try {
            database.inserirPOI(poi);
        } catch (SQLException ex) {
            Logger.getLogger(POIController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu um erro inesperado, por favor entre em contato.");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body("POI cadastrado com sucesso.");
    }
}
