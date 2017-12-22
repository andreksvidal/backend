/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import utilidades.Conexion;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Julian Esteban Solarte Rivera - Universidad del Cauca
 */
@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {

    private String url;
    private String idSucursualActual;
    public static Conexion conexion;

    public LoginController() {
    }

    public String getIdSucursualActual() {
        return idSucursualActual;
    }

    public void setIdSucursualActual(String idSucursualActual) {
        this.idSucursualActual = idSucursualActual;
    }   

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static Conexion getConexion() {
        return conexion;
    }

    public static void setConexion(Conexion conexion) {
        LoginController.conexion = conexion;
    }

    
    public String iniciarConexion() {
        conexion=new Conexion(idSucursualActual);      
        System.out.println(""+conexion.getIdSucursalActual());
        System.out.println(""+conexion.getUrl());
        return "ingresargiro.xhtml?faces-redirect=true";
    }

}
