<?php
/**
* Clase encargada de proveer los mecanismos de cifrado y 
* descifrado de las peticiones y respuestas del API REST.
*/
class Cifrado 
{
	
	private $metodo;

	function __construct($metodo)
	{
		$this->metodo= $metodo;
	}


	public function cifrar($cadena)
	{
		switch ($this->metodo) {
			case 'md5':
				return  $this->cifrar_md5($cadena);
				break;
			
			default:
				# code...
				break;
		}
	}


	public function descifrar($cadena)
	{
		switch ($this->metodo) {
			case 'md5':
				return $this->descifrar_md5($cadena);
				break;
			
			default:
				# code...
				break;
		}
	}


	private function cifrar_md5($cadena)
	{
		$key='nothack';  // Una clave de codificacion, debe usarse la misma para cifrar y descifrar
    	$encrypted = base64_encode(mcrypt_encrypt(MCRYPT_RIJNDAEL_256, md5($key), $cadena, MCRYPT_MODE_CBC, md5(md5($key))));
    	return $encrypted; //Devuelve el string encriptado
	}

	private function descifrar_md5($cadena)
	{
		$key='nothack';  // Una clave de codificacion, debe usarse la misma para cifrar y descifrar
     	$decrypted = rtrim(mcrypt_decrypt(MCRYPT_RIJNDAEL_256, md5($key), base64_decode($cadena), MCRYPT_MODE_CBC, md5(md5($key))), "\0");
    	return $decrypted;  //Devuelve el string desencriptado
	}


}
?>