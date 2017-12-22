/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;
/**
 *
 * @author Julian Esteban Solarte Rivera - Universidad del Cauca
 */


public class Conexion {

    public String url = null;
    public String idSucursalActual = null;

    public Conexion(String idSucursal) {
        //try {
            //Path path = Paths.get("./../../servidor.txt");
            //List<String> readAllLines = Files.readAllLines(path);
            //url=readAllLines.get(0);
            
        //} catch (IOException ex) {
        //    Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        //}      
        url="http://192.168.0.65/api/index.php";
        idSucursalActual=idSucursal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdSucursalActual() {
        return idSucursalActual;
    }

    public void setIdSucursalActual(String idSucursalActual) {
        this.idSucursalActual = idSucursalActual;
    }

   
    
    
    

}
