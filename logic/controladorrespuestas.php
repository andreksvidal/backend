<?php

/**
* Clase que se encarga de procesar el resutaldo de una solicitud 
* y generar una respuesta en formato JSON.
*/
class ControladorRespuestas 
{
	
	private $resultado;

	function __construct($resultado)
	{
		$this->resultado=$resultado;

	}

	public function setResultado($resultado)
	{
		$this->resultado=$resultado;		
	}


	public function procesarResultado()
	{
		$json_string=json_encode(array("estado" => 0,"resultado" => "bad request"));

		if($this->resultado!=NULL)
		{
			 $json_string = json_encode(array("estado" => 1,"resultado" => $this->resultado));
		}

		#NOTA: por el momento , no se cifra la respuesta. 
		#codigo para cifrar...#		
		
		return $json_string;	
	}
}
?>