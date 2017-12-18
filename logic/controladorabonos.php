<?php  
/**
* Clase encargada de procesar y ejecutar peticiones relacionadas con el 
* proceso de : Pagar un abono.
*  
*/
class ControladorAbonos extends Controlador 
{
	/**
	* Implementación particular del metodo abstracto de la clase padre-
	* Metodo encargado de procesar una peticion relacionada con el
	* proceso de : Pagar abonos.
	**/
	public function procesarPeticion()
	{
		
		$resultado=NULL;

		$clavesvalidas=array("accion","datos");

		if ($this->validador->validarCampos($clavesvalidas,$this->peticion)) {
			

			$accion=$this->peticion['accion'];

			switch ($accion) {
				case 'pago':
					$resultado=$this->registrarPagoAbono();
					break;
				case 'getPagosSucursal':
					$resultado=$this->consultarPagosSucursal();
					break;
				case 'getPagosxCuenta':
					$resultado=$this->consultarPagosxCuenta();
					break;
				
			}



		}
		
		return $resultado;
		
	}



 	#Estructura de una petición de consulta válida :
	#
	# {"accion": "getPagosxCuenta",
	#	"datos": {   "numerocuenta": "015"
	#			 }
	# }
	#

	/**
	*funcion que contiene la lógica necesaria para consultar un conjunto de transacciones
	*asociadas con PAGOS DE ABONOS de usuarios  entre diferentes sucursales.
	**/
	private function consultarPagosxCuenta()
	{


		$respuesta= array('error' =>  "datos no validos");

		#empieza a validar los datos.
		$datos=$this->peticion['datos'];

		$clavesvalidas=array('numerocuenta');

		if($this->validador->validarCampos($clavesvalidas,$datos))
		{
			/*Todo correcto*/
			/*Se procede a hacer la consulta*/
			$respuesta=$this->cliente->getPagosAbonosxCuenta($datos['numerocuenta']);

		}

		return $respuesta;

	}






	#Estructura de una petición de consulta válida :
	#
	# {"accion": "getPagosSucursal",
	#	"datos": {   "idsucursal": "015",
	#			    "esremitente": "true",
	#					  "fecha": "20171210"
	#			 }
	# }
	#

	/**
	*funcion que contiene la lógica necesaria para consultar un conjunto de transacciones
	*asociadas con PAGOS DE ABONOS de usuarios  entre diferentes sucursales.
	**/
	private function consultarPagosSucursal()
	{


		$respuesta= array('error' =>  "datos no validos");

		#empieza a validar los datos.
		$datos=$this->peticion['datos'];

		$clavesvalidas=array('idsucursal','esremitente','fecha');

		if($this->validador->validarCampos($clavesvalidas,$datos))
		{
			/*Todo correcto*/
			/*Se procede a hacer la consulta*/
			$respuesta=$this->cliente->getPagosAbonos($datos['idsucursal'], $datos['esremitente'],$datos['fecha']);

		}

		return $respuesta;

	}




	#Estructura de una petición de registro válida :
	#
	#{"accion": "pago",
	#		   "datos": {  "idremitente"  : "123", 
	#					    "idreceptora" : "015",
	#					   "numerocuenta" : "1232132132",
	#					       "cantidad" : "12546",
	#					   "infoclientes" : {	"remitente": {"identificacion" : "34444444" , "tipo" : "CC" },
	#					   					 "beneficiario": {"identificacion" : "34444444" , "tipo" : "CC" }
	#										}
	#					 }
	#		 }
	#

	/**
	*funcion que contiene la lógica necesaria para registrar la transaccion asociada 
	*al pago de abonos de un usuario en una sucursal.
	**/

	private function registrarPagoAbono()
	{
		
		$respuesta= array('error' =>  "datos no validos");
		
		
		#empieza a validar lo datos.

		$datos=$this->peticion['datos'];

		$clavesvalidas= array('idremitente','idreceptora','numerocuenta','cantidad','infoclientes');

		if($this->validador->validarCampos($clavesvalidas,$datos))
		{
			$infoclientes=$datos['infoclientes'];
			
			$clavesvalidas=array('remitente','beneficiario');

			if($this->validador->validarCampos($clavesvalidas,$infoclientes))
			{
			
				$inforemitente=$infoclientes['remitente'];
				$infobeneficiario=$infoclientes['beneficiario'];

				$clavesvalidas=array('identificacion' ,'tipo');

				if($this->validador->validarCampos($clavesvalidas,$inforemitente) and $this->validador->validarCampos($clavesvalidas,$infobeneficiario))
				{
					/*Todo correcto*/
					/*se procede a hacer la transferencia.*/
					$respuesta=$this->cliente->regPagoAbono($datos);
				}
			}
		}

		return $respuesta;
	}






}
?>