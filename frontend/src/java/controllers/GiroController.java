package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Cliente;
import entities.Giro;
import entities.Sucursal;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import sessionBeans.GiroFacade;
import utilidades.GiroJson;
import utilidades.GiroView;
import utilidades.datosTransaccion;

@Named("giroController")
@SessionScoped
public class GiroController implements Serializable {

    private String costoGiro;
    private int tipoLlamada;
    private Giro current;
    private boolean emisor = false;
    private List<Giro> girosRealizados = null;
    private List<Giro> girosFiltrados;
    private String fecha = "null";
    private GiroFacade giroFacade;
    @EJB
    private sessionBeans.SucursalFacade sucursalFacade;
    @EJB
    private sessionBeans.ClienteFacade clienteFacade;

    public GiroController() {
        current = new Giro();
        giroFacade = new GiroFacade();
    }

    public List<Giro> getGirosFiltrados() {
        return girosFiltrados;
    }

    public void setGirosFiltrados(List<Giro> girosFiltrados) {
        this.girosFiltrados = girosFiltrados;
    }

    public Giro getCurrent() {
        return current;
    }

    public void setCurrent(Giro current) {
        this.current = current;
    }

    public String elegirCliente(Cliente cliente) {
        if (emisor) {
            current.setEmisor(cliente);
        } else {
            current.setBeneficiario(cliente);
        }
        if (tipoLlamada == 1) {
            System.out.println("SI");
            return "/ingresargiro.xhtml?faces-redirect=true";
        }
        if (tipoLlamada == 2) {
            return "/abonocuenta.xhtml?faces-redirect=true";
        }
        return "";

    }

    public String esEmisor(int tipo) {
        tipoLlamada = tipo;
        emisor = true;
        return "/buscarcliente.xhtml?faces-redirect=true";
    }

    public String esBeneficiario(int tipo) {
        tipoLlamada = tipo;
        emisor = false;
        return "/buscarcliente.xhtml?faces-redirect=true";
    }

    public void limpiarCliente() {
        current.setEmisor(new Cliente());
    }

    public List<Giro> getGirosRealizados() {
        return girosRealizados;
    }

    public void setGirosRealizados(List<Giro> girosRealizados) {
        this.girosRealizados = girosRealizados;
    }

    public GiroFacade getGiroFacade() {
        return giroFacade;
    }

    public void setGiroFacade(GiroFacade giroFacade) {
        this.giroFacade = giroFacade;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Realiza un giro
     */
    public void registrarGiro(Sucursal receptora) {
        current.setIdReceptora(receptora.getId());
        giroFacade.registrarGiro(current);
        current.setCantidad("");
        current.setBeneficiario(new Cliente());
        current.setEmisor(new Cliente());
        receptora.setAgencia("");
        costoGiro="";
    }

    /**
     * Obtiene los giros que ha realizado una sucursal
     *
     * @return Giros Realizados
     */
    public List<GiroView> obtenerGirosRemitente() {
        List<GiroView> girosView = new ArrayList<>();
        String giros = giroFacade.obtenerGiros(true, fecha);
        if (!giros.contains("vacio")) {
            Gson gson = new Gson();
            GiroJson consulta = gson.fromJson(giros, GiroJson.class);
            List<datosTransaccion> listGiros = consulta.getResultado();
            for (datosTransaccion giro : listGiros) {
                girosView.add(new GiroView(giro.getTxid(), giro.getDatos(), sucursalFacade.findById(giro.getDatos().getIdreceptora()).getAgencia(), sucursalFacade.findById(giro.getDatos().getIdremitente()).getAgencia(), clienteFacade.findById(giro.getDatos().getInfoclientes().getEmisor().getIdentificacion()).getPrimernombre() + " " + clienteFacade.findById(giro.getDatos().getInfoclientes().getEmisor().getIdentificacion()).getPrimerapellido(), clienteFacade.findById(giro.getDatos().getInfoclientes().getBeneficiario().getIdentificacion()).getPrimernombre() + " " + clienteFacade.findById(giro.getDatos().getInfoclientes().getBeneficiario().getIdentificacion()).getPrimerapellido()));
            }
        }
        return girosView;
    }

    /**
     * Obtiene los giros que ha recibido la sucursal
     *
     * @return Giros Recibidos
     */
    public List<GiroView> obtenerGirosRecibidos() {
        List<GiroView> girosView = new ArrayList<>();
        String giros = giroFacade.obtenerGiros(false, fecha);
        if (!giros.contains("vacio")) {
            Gson gson = new Gson();
            GiroJson consulta = gson.fromJson(giros, GiroJson.class);
            List<datosTransaccion> listGiros = consulta.getResultado();
            for (datosTransaccion giro : listGiros) {
                girosView.add(new GiroView(giro.getTxid(), giro.getDatos(), sucursalFacade.findById(giro.getDatos().getIdreceptora()).getAgencia(), sucursalFacade.findById(giro.getDatos().getIdremitente()).getAgencia(), clienteFacade.findById(giro.getDatos().getInfoclientes().getEmisor().getIdentificacion()).getPrimernombre() + " " + clienteFacade.findById(giro.getDatos().getInfoclientes().getEmisor().getIdentificacion()).getPrimerapellido(), clienteFacade.findById(giro.getDatos().getInfoclientes().getBeneficiario().getIdentificacion()).getPrimernombre() + " " + clienteFacade.findById(giro.getDatos().getInfoclientes().getBeneficiario().getIdentificacion()).getPrimerapellido()));
            }
            girosPagados(girosView);
        }
        return girosView;
    }
    
    public void girosPagados(List<GiroView> girosRecibidos){
        List<GiroView> obtenerGirosPagados = obtenerGirosPagados();
        girosRecibidos.forEach((giroRecibido) -> {
            obtenerGirosPagados.stream().filter((giroPagado) -> (giroRecibido.getTxid().equals(giroPagado.getTxid()))).forEachOrdered((_item) -> {
                giroRecibido.setPagado(true);
            });
        });
    }

    public List<datosTransaccion> obtenerPagoFacturasRemitente() {
        List<datosTransaccion> listGiros = new ArrayList<>();
        String giros = giroFacade.obtenerFacturas(true, fecha);
        if (!giros.contains("vacio")) {
            Gson gson = new Gson();
            GiroJson consulta = gson.fromJson(giros, GiroJson.class);
            listGiros = consulta.getResultado();
        }
        return listGiros;
    }

    public List<datosTransaccion> obtenerPagoFacturasRecibidos() {
        List<datosTransaccion> listGiros = new ArrayList<>();
        String giros = giroFacade.obtenerFacturas(false, fecha);
        if (!giros.contains("vacio")) {
            Gson gson = new Gson();
            GiroJson consulta = gson.fromJson(giros, GiroJson.class);
            listGiros = consulta.getResultado();
        }
        return listGiros;
    }

    /**
     * Paga un giro que ha recibido
     *
     * @param giro
     */
    public void pagarGiro(String giro) {
        giroFacade.pagarGiro(giro);
    }

    public void registroPagoFactura(Sucursal sucursal) {
        current.setIdReceptora(sucursal.getId());
        giroFacade.registroPagoFactura(current);
        current.setCantidad("");
        current.setBeneficiario(new Cliente());
        current.setEmisor(new Cliente());
        sucursal.setAgencia("");
        costoGiro="";
    }

    public void registroAbonoCuenta(Sucursal sucursal) {
        current.setIdReceptora(sucursal.getId());
        giroFacade.registroAbonoCuenta(current);
        current.setCantidad("");
        current.setBeneficiario(new Cliente());
        current.setEmisor(new Cliente());
        sucursal.setAgencia("");
        costoGiro="";
    }

    public List<GiroView> obtenerGirosPagados() {
        List<GiroView> girosView = new ArrayList<>();
        String giros = giroFacade.obtenerGirosPagados();
        if (!giros.contains("vacio")) {
            Gson gson = new Gson();
            GiroJson consulta = gson.fromJson(giros, GiroJson.class);
            List<datosTransaccion> listGiros = consulta.getResultado();
            for (datosTransaccion giro : listGiros) {
                GiroView tmp=new GiroView(giro.getDatospago().getTxid(), giro.getDatosconsignacion(), sucursalFacade.findById(giro.getDatosconsignacion().getIdreceptora()).getAgencia(), sucursalFacade.findById(giro.getDatosconsignacion().getIdremitente()).getAgencia(), clienteFacade.findById(giro.getDatosconsignacion().getInfoclientes().getEmisor().getIdentificacion()).getPrimernombre() + " " + clienteFacade.findById(giro.getDatosconsignacion().getInfoclientes().getEmisor().getIdentificacion()).getPrimerapellido(), clienteFacade.findById(giro.getDatosconsignacion().getInfoclientes().getBeneficiario().getIdentificacion()).getPrimernombre() + " " + clienteFacade.findById(giro.getDatosconsignacion().getInfoclientes().getBeneficiario().getIdentificacion()).getPrimerapellido());
                tmp.setDatospago(giro.getDatospago());
                girosView.add(tmp);
            }
        }
        return girosView;
    }

    public List<GiroView> obtenerAbonosRealizados() {
        List<GiroView> girosView = new ArrayList<>();
        String giros = giroFacade.obtenerAbonosRealizados();
        if (!giros.contains("vacio")) {
            Gson gson = new Gson();
            GiroJson consulta = gson.fromJson(giros, GiroJson.class);
            List<datosTransaccion> listGiros = consulta.getResultado();
            for (datosTransaccion giro : listGiros) {
                girosView.add(new GiroView(giro.getTxid(), giro.getDatos(), sucursalFacade.findById(giro.getDatos().getIdreceptora()).getAgencia(), sucursalFacade.findById(giro.getDatos().getIdremitente()).getAgencia(), clienteFacade.findById(giro.getDatos().getInfoclientes().getEmisor().getIdentificacion()).getPrimernombre() + " " + clienteFacade.findById(giro.getDatos().getInfoclientes().getEmisor().getIdentificacion()).getPrimerapellido(), clienteFacade.findById(giro.getDatos().getInfoclientes().getBeneficiario().getIdentificacion()).getPrimernombre() + " " + clienteFacade.findById(giro.getDatos().getInfoclientes().getBeneficiario().getIdentificacion()).getPrimerapellido()));
            }
        }
        return girosView;
    }

    public List<GiroView> obtenerAbonosRecibidos() {
        List<GiroView> girosView = new ArrayList<>();
        String giros = giroFacade.obtenerAbonosRecibidos();
        if (!giros.contains("vacio")) {
            Gson gson = new Gson();
            GiroJson consulta = gson.fromJson(giros, GiroJson.class);
            List<datosTransaccion> listGiros = consulta.getResultado();
            for (datosTransaccion giro : listGiros) {
                girosView.add(new GiroView(giro.getTxid(), giro.getDatos(), sucursalFacade.findById(giro.getDatos().getIdreceptora()).getAgencia(), sucursalFacade.findById(giro.getDatos().getIdremitente()).getAgencia(), clienteFacade.findById(giro.getDatos().getInfoclientes().getEmisor().getIdentificacion()).getPrimernombre() + " " + clienteFacade.findById(giro.getDatos().getInfoclientes().getEmisor().getIdentificacion()).getPrimerapellido(), clienteFacade.findById(giro.getDatos().getInfoclientes().getBeneficiario().getIdentificacion()).getPrimernombre() + " " + clienteFacade.findById(giro.getDatos().getInfoclientes().getBeneficiario().getIdentificacion()).getPrimerapellido()));
            }
        }
        return girosView;
    }

    public String getCostoGiro() {
        return costoGiro;
    }

    public void setCostoGiro(String costoGiro) {
        this.costoGiro = costoGiro;
    }
    
    public void calcularCosto(){
        int cantidad = Integer.parseInt(current.getCantidad());
        int comision=0;
        if(cantidad>=10000 && cantidad<=100000){
            comision=3000;
        }if(cantidad>100001 && cantidad<=500000){
            comision=4000;
        }if(cantidad>500001 && cantidad<=1000000){
            comision=(int) (cantidad*0.0199);
        }if(cantidad>1000001 && cantidad<=5000000){
            comision=(int) (cantidad*0.021);
        }if(cantidad>5000001){
            comision=(int) (cantidad*0.0189);
        }
        comision=(int) (comision+(comision*0.19)+cantidad);
        DecimalFormat formatea = new DecimalFormat("###,###.##");
        costoGiro=formatea.format(comision);
    }
}
