<?php

/**
* Clase encargada de procesar y ejecutar peticiones relacionadas con el 
* proceso de : Realizar un giro.
*  
*/
class ControladorGiro extends Controlador 
{


	/**
	* Implementación particular del metodo abstracto de la clase padre-
	* Metodo encargado de procesar una peticion relacionada con el
	* proceso de : Realizar un giro.
	**/
	public function procesarPeticion()
	{
		
		$resultado=NULL;

		$clavesvalidas=array("accion","datos");

		if ($this->validador->validarCampos($clavesvalidas,$this->peticion)) {
			

			$accion=$this->peticion['accion'];

			switch ($accion) {
				case 'consignacion':
					$resultado=$this->registarConsignacion();
					break;
				case 'getconssucursal':
					$resultado=$this->consultarConsignacionesSucursal();
					break;
				case 'pago':
					$resultado=$this->confirmarPago();
					break;
				case 'getpagossucursal':
					$resultado=$this->consultarPagos();
					break;
				

			}

		}
		
		return $resultado;
		
	}



	#Estructura de una petición de registro válida :
	#
	# {"accion": "consignacion",
	#	"datos": {  "idremitente" : "123", 
	#			    "idreceptora" : "015",
	#			       "cantidad" : "12546",
	#			   "infoclientes" : {		 "emisor" : {"identificacion" : "34555555" , "tipo" : "CC" },
	#								 	"beneficiario": {"identificacion" : "34444444" , "tipo" : "CC" }
	#								}
	#			 }
	# }
	#

	/**
	*funcion que contiene la lógica necesaria para registrar la transaccion asociada 
	*a una consignación de dinero para realizar un giro desde una sucursal a otra.
	**/
	private function registarConsignacion()
	{

		$respuesta= array('error' =>  "datos no validos");
		
		
		#empieza a validar lo datos.

		$datos=$this->peticion['datos'];

		$clavesvalidas= array('idremitente','idreceptora','cantidad','infoclientes');

		if($this->validador->validarCampos($clavesvalidas,$datos))
		{
			$infoclientes=$datos['infoclientes'];
			
			$clavesvalidas=array('emisor', 'beneficiario');

			if($this->validador->validarCampos($clavesvalidas,$infoclientes))
			{
				$infoemisor=$infoclientes['emisor'];
				$infobeneficiario=$infoclientes['beneficiario'];

				$clavesvalidas=array('identificacion' ,'tipo');

				if($this->validador->validarCampos($clavesvalidas,$infoemisor) and $this->validador->validarCampos($clavesvalidas,$infobeneficiario))
				{
					/*Todo correcto*/
					/*se procede a hacer la transferencia.*/
					$respuesta=$this->cliente->regConsignacionGiro($datos);
				}
			}
		}
		

		return $respuesta;


	}


	#Estructura de una petición de registro válida :
	#{"accion": "pago",
	#  "datos": {  "txid" : "015"
	#			}
	#}

	private function confirmarPago()
	{
		$respuesta= array('error' =>  "datos no validos" );

		#empieza a validar los datos
		$datos=$this->peticion['datos'];

		$clavesvalidas=array('txid');

		if($this->validador->validarCampos($clavesvalidas,$datos))
		{
			/*Todo correcto*/
			/*Se procede a hacer la consulta*/
			$respuesta=$this->cliente->getDatosTransaccion($datos['txid']);
			$respuesta=$this->cliente->regPagoGiro($datos);
		}


		return $respuesta;
	}




	#Estructura de una petición de consulta válida :
	#
	# {"accion": "getconssucursal",
	#	"datos": {  "idsucursal" : "015",
	#			    "esremitente": "true",
	#					  "fecha": "20171210"
	#			 }
	# }
	#

	/**
	*funcion que contiene la lógica necesaria para consultar un conjunto de transacciones
	*asociadas con PAGOS DE CONSIGNACIONES para giros entre diferentes sucursales.
	**/
	private function consultarConsignacionesSucursal()
	{


		$respuesta= array('error' =>  "datos no validos");

		#empieza a validar los datos.
		$datos=$this->peticion['datos'];

		$clavesvalidas=array('idsucursal','esremitente','fecha');

		if($this->validador->validarCampos($clavesvalidas,$datos))
		{
			/*Todo correcto*/
			/*Se procede a hacer la consulta*/
			$respuesta=$this->cliente->getConsignacionesGiroxSucursal($datos['idsucursal'], $datos['esremitente'],$datos['fecha']);

		}

		return $respuesta;

	}



	#Estructura de una petición de consulta válida :
	#
	#{"accion": "getpagossucursal",
	#  "datos": {  idsucursal": "015",
	#		    	   "fecha": "20171210"
	#			}
	#}
	#

	/**
	*funcion que contiene la lógica necesaria para consultar un conjunto de transacciones
	*asociadas con PAGOS DE CONSIGNACIONES para giros entre diferentes sucursales.
	**/

	private function consultarPagos()
	{
		$respuesta= array('error' =>  "datos no validos");
		#empieza a validar los datos.
		$datos=$this->peticion['datos'];
		
		$clavesvalidas=array('idsucursal','fecha');

		if($this->validador->validarCampos($clavesvalidas,$datos))
		{
			/*Todo correcto*/
			/*Se procede a hacer la consulta*/
			$respuesta=$this->cliente->getPagosGiro($datos['idsucursal'],$datos['fecha']);
		}	
			
		return $respuesta;	

	}

}
?>