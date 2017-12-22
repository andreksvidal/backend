<?php  

/**
* Clase encargada de procesar y ejecutar peticiones relacionadas con el 
* proceso de : Pagar Facturas.
*  
*/
class ControladorFacturas extends Controlador 
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
				case 'pago':
					$resultado=$this->registarPagoFactura();
					break;
				case 'getPagosSucursal':
					$resultado=$this->consultarPagosSucursal();
					break;
				case 'getPagoxRef':
					$resultado=$this->consultarPagoxReferencia();
					break;
				
			}

		}
		
		return $resultado;
		
	}




	#Estructura de una petición de consulta válida :
	#
	# {"accion": "getPagos",
	#	"datos": {   "numerorefer": "015"
	#			 }
	# }
	#

	/**
	*funcion que contiene la lógica necesaria para consultar un conjunto de transacciones
	*asociadas con PAGOS DE FACTURAS de usuarios  entre diferentes sucursales.
	**/
	private function consultarPagoxReferencia()
	{


		$respuesta= array('error' =>  "datos no validos");

		#empieza a validar los datos.
		$datos=$this->peticion['datos'];

		$clavesvalidas=array('numerorefer');

		if($this->validador->validarCampos($clavesvalidas,$datos))
		{
			/*Todo correcto*/
			/*Se procede a hacer la consulta*/
			$respuesta=$this->cliente->getPagoFactura($datos['numerorefer']);

		}

		return $respuesta;

	}







	#Estructura de una petición de consulta válida :
	#
	# {"accion": "getPagos",
	#	"datos": {   "idsucursal": "015",
	#			    "esremitente": "true",
	#					  "fecha": "20171210"
	#			 }
	# }
	#

	/**
	*funcion que contiene la lógica necesaria para consultar un conjunto de transacciones
	*asociadas con PAGOS DE FACTURAS de usuarios  entre diferentes sucursales.
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
			$respuesta=$this->cliente->getPagosFacturas($datos['idsucursal'], $datos['esremitente'],$datos['fecha']);

		}

		return $respuesta;

	}




	#Estructura de una petición de registro válida :
	#
	#{"accion": "pago",
	#		   "datos": {  "idremitente"  : "123", 
	#					    "idreceptora" : "015",
	#					    "numerorefer" : "1232132132",
	#					       "cantidad" : "12546",
	#					   "infoclientes" : { "beneficiario": {"identificacion" : "34444444" , "tipo" : "CC" }
	#										}
	#					 }
	#		 }
	#

	/**
	*funcion que contiene la lógica necesaria para registrar la transaccion asociada 
	*a l pago de factura de un usuario en una sucursal.
	**/
	private function registarPagoFactura()
	{

		$respuesta= array('error' =>  "datos no validos");
		
		
		#empieza a validar lo datos.

		$datos=$this->peticion['datos'];

		$clavesvalidas= array('idremitente','idreceptora','numerorefer','cantidad','infoclientes');

		if($this->validador->validarCampos($clavesvalidas,$datos))
		{
			$infoclientes=$datos['infoclientes'];
			
			$clavesvalidas=array('beneficiario');

			if($this->validador->validarCampos($clavesvalidas,$infoclientes))
			{
			
				$infobeneficiario=$infoclientes['beneficiario'];

				$clavesvalidas=array('identificacion' ,'tipo');

				if($this->validador->validarCampos($clavesvalidas,$infobeneficiario))
				{
					/*Todo correcto*/
					/*se procede a hacer la transferencia.*/
					$respuesta=$this->cliente->regPagoFactura($datos);
				}
			}
		}
		

		return $respuesta;


	}

}

?>