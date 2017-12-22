<?php
/**
* Clase encargada de realizar la validaciones 
* de la cantidad y tipo de parametros de las partes de una petición.
*/
class Validaciones
{

	/**
	*funcion que valida si ciertas claves de la peticion existen 
	*y si son exactamente las requeridas.
	**/
	public function validarCampos($clavesValidas,$datosValidar)
	{
		

		if(count($clavesValidas)!=count(array_keys($datosValidar)))
		{
			return FALSE;
		}

		foreach ($clavesValidas as &$clave) {
	    	if(!array_key_exists($clave,$datosValidar))
	    	{
	    		return FALSE;
	    	}
    	}

    	return TRUE;	
	}
}
?>