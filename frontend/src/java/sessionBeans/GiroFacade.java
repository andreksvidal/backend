/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBeans;

import entities.Giro;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import static controllers.LoginController.conexion;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Julian Esteban Solarte Rivera - Universidad del Cauca
 */
public class GiroFacade implements Serializable {

    public String obtenerGiros(boolean esRemitente, String fecha) {
        String salida = "";
        try {

            URL url = new URL(conexion.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"proceso\":\"giro\",\n"
                    + "\"peticion\":{\"accion\":\"getconssucursal\",\n"
                    + "			\"datos\": {  \"idsucursal\" : \"" + conexion.idSucursalActual + "\",\n"
                    + "				       \"esremitente\": \"" + esRemitente + "\",\n"
                    + "						  \"fecha\": \"" + fecha + "\"\n"
                    + "				 }\n"
                    + "		   }\n"
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                salida += output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return salida;
    }

    public void registrarGiro(Giro giro) {
        try {
            URL url = new URL(conexion.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"proceso\": \"giro\",\n"
                    + "	  \"peticion\":{\"accion\":\"consignacion\",\n"
                    + "				  \"datos\":{   \"idremitente\" : \"" + conexion.idSucursalActual + "\", \n"
                    + "						\"idreceptora\" : \"" + giro.getIdReceptora() + "\",\n"
                    + "						   \"cantidad\" : \"" + giro.getCantidad() + "\",\n"
                    + "						   \"infoclientes\" : {		 \"emisor\" : {\"identificacion\" : \"" + giro.getEmisor().getNumidentifiacion() + "\" , \"tipo\" : \"" + giro.getEmisor().getTipoidentificacion() + "\" },\n"
                    + "												\"beneficiario\": {\"identificacion\" : \"" + giro.getBeneficiario().getNumidentifiacion() + "\" , \"tipo\" : \"" + giro.getBeneficiario().getTipoidentificacion() + "\" }\n"
                    + "										    }\n"
                    + "						  }\n"
                    + "	              }\n"
                    + "	 }";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");

            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }

    public void pagarGiro(String giro) {
        try {
            URL url = new URL(conexion.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"proceso\": \"giro\",\n"
                    + " \"peticion\": {\"accion\": \"pago\",\n"
                    + "			   \"datos\": {  \"txid\" : \"" + giro + "\"\n"
                    + "						}\n"
                    + "			 }\n"
                    + "\n"
                    + "			\n"
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");

            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }

    public String obtenerGirosPagados() {
        String salida = "";
        try {

            URL url = new URL(conexion.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"proceso\": \"giro\",\n"
                    + " \"peticion\": {\"accion\": \"getpagossucursal\",\n"
                    + "			   \"datos\": { \"idsucursal\": \"" + conexion.idSucursalActual + "\",\n"
                    + "							   \"fecha\": \"null\"\n"
                    + "						}\n"
                    + "			 }\n"
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                salida += output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return salida;
    }

    public void registroPagoFactura(Giro giro) {
        try {            
            URL url = new URL(conexion.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"proceso\": \"pagofacturas\",\n"
                    + " \"peticion\": {\"accion\": \"pago\",\n"
                    + "			   \"datos\": {  \"idremitente\"  : \"" + conexion.idSucursalActual + "\", \n"
                    + "						    \"idreceptora\" : \"" + giro.getIdReceptora() + "\",\n"
                    + "						    \"numerorefer\" : \"" + giro.getBeneficiario().getCuentaahorros() + "\",\n"
                    + "						       \"cantidad\" : \"" + giro.getCantidad() + "\",\n"
                    + "						   \"infoclientes\" : { \"beneficiario\": {\"identificacion\" : \"" + giro.getBeneficiario().getNumidentifiacion() + "\" , \"tipo\" : \"" + giro.getBeneficiario().getTipoidentificacion() + "\" }\n"
                    + "											}\n"
                    + "						 }\n"
                    + "			 }\n"
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");

            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }

    public String obtenerFacturas(boolean esRemitente, String fecha) {
        String salida = "";
        try {

            URL url = new URL(conexion.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"proceso\": \"pagofacturas\",\n"
                    + " \"peticion\":   {\"accion\": \"getPagosSucursal\",\n"
                    + "				 \"datos\": {	 \"idsucursal\": \"" + conexion.idSucursalActual + "\",\n"
                    + "						    \"esremitente\": \"" + esRemitente + "\",\n"
                    + "								  \"fecha\": \"" + fecha + "\"\n"
                    + "						 }\n"
                    + "			   }\n"
                    + "}	 ";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                salida += output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return salida;
    }

    public void registroAbonoCuenta(Giro giro) {
        try {
            URL url = new URL(conexion.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"proceso\": \"pagoabonos\",\n"
                    + " \"peticion\": {\"accion\": \"pago\",\n"
                    + "			   \"datos\": {  \"idremitente\"  : \"" + conexion.idSucursalActual + "\", \n"
                    + "						    \"idreceptora\" : \"" + giro.getIdReceptora() + "\",\n"
                    + "						   \"numerocuenta\" : \"" + giro.getBeneficiario().getCuentaahorros() + "\",\n"
                    + "						       \"cantidad\" : \"" + giro.getCantidad() + "\",\n"
                    + "						   \"infoclientes\" : {	\"remitente\": {\"identificacion\" : \"" + giro.getEmisor().getNumidentifiacion() + "\" , \"tipo\" : \"" + giro.getEmisor().getTipoidentificacion() + "\" },\n"
                    + "						   					 \"beneficiario\": {\"identificacion\" : \"" + giro.getBeneficiario().getNumidentifiacion() + "\" , \"tipo\" : \"" + giro.getBeneficiario().getTipoidentificacion() + "\" }\n"
                    + "											}\n"
                    + "						 }\n"
                    + "			 }\n"
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");

            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
    }

    public String obtenerAbonosRealizados() {
        String salida = "";
        try {

            URL url = new URL(conexion.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"proceso\": \"pagoabonos\",\n"
                    + " \"peticion\":   {\"accion\": \"getPagosSucursal\",\n"
                    + "				 \"datos\": {	 \"idsucursal\": \""+conexion.idSucursalActual+"\",\n"
                    + "						    \"esremitente\": \"true\",\n"
                    + "								  \"fecha\": \"null\"\n"
                    + "						 }\n"
                    + "			   }\n"
                    + "}	 ";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                salida += output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return salida;
    }

    public String obtenerAbonosRecibidos() {
        String salida = "";
        try {

            URL url = new URL(conexion.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"proceso\": \"pagoabonos\",\n"
                    + " \"peticion\":   {\"accion\": \"getPagosSucursal\",\n"
                    + "				 \"datos\": {	 \"idsucursal\": \""+conexion.idSucursalActual+"\",\n"
                    + "						    \"esremitente\": \"false\",\n"
                    + "								  \"fecha\": \"null\"\n"
                    + "						 }\n"
                    + "			   }\n"
                    + "}	 ";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                salida += output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return salida;
    }
}
