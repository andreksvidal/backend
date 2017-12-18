<?php 

/**
* Clase resguardo con los metodos necesarios para enviar 
* peticiones al N_S de Multichain.
*/
class Stub 
{

	private $multichain_chain;



	/**
	*funcion que lee la configuración para conectarse
	*a la blockchain.
	**/
	public function read_config()
	{
		$config=array();
		
		#obtiene la información del archivo.
		$contents=file_get_contents(__DIR__ .'/config.txt');
		
		#se divide en un vector de lineas.
		$lines=explode("\n", $contents);
		

		foreach ($lines as $line) {

			#separa los comentarios de el contenido útil.
			$content=explode('#', $line);

			#separa el valor de ingresado en cada parametro.
			$fields=explode('=', trim($content[0]));

			#si esta formado el par 'campo=valor'
			if (count($fields)==2) {

				if (is_numeric(strpos($fields[0], '.'))) {
					$parts=explode('.', $fields[0]);
					$config[$parts[0]][$parts[1]]=$fields[1];
				} else {
					$config[$fields[0]]=$fields[1];
				}
			}
		}
		
		#retorna el vector con la configuración leida.
		return $config;
	}



	/**
	*funcion que fija una blockchain especifica 
	* sobre la cual se operará.
	**/
	public function set_multichain_chain($chain)
	{
		$this->multichain_chain=$chain;
	}	



	/**
	*funcion encargada de proveer el mecanismo de conexión con el cliente.
	* UNICAMENTE DEBERÍA SER EJECUTADA si se ha fijado la blockchain con los parametros correctos. 
	**/
	public function multichain($method) // other params read from func_get_args()
	{
		$args=func_get_args();
		
		return $this->json_rpc_send($this->multichain_chain['rpchost'], $this->multichain_chain['rpcport'], $this->multichain_chain['rpcuser'],
			$this->multichain_chain['rpcpassword'], $method, array_slice($args, 1));
	}


	/**
	*funcion que obtiene la información general 
	*de la blockchain fijada.
	**/
	public function multichain_getinfo()
	{
		$multichain_getinfo=$this->multichain('getinfo');
		
		return $multichain_getinfo;
	}


	
	/**
	*funcion que ejecuta peticiones 
	*sobre el daemon RPC de multichain.
	*Sólo debe ser invocada por la función 'multichain'. 
	**/
	private function json_rpc_send($host, $port, $user, $password, $method, $params=array())
	{
		$url='http://'.$host.':'.$port.'/';
				
		$payload=json_encode(array(
			'id' => time(),
			'method' => $method,
			'params' => $params,
		));
		
	//	echo '<PRE>'; print_r($payload); echo '</PRE>';
		
		$ch=curl_init($url);
		curl_setopt($ch, CURLOPT_POST, true);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch, CURLOPT_USERPWD, $user.':'.$password);
		curl_setopt($ch, CURLOPT_HTTPHEADER, array(
			'Content-Type: application/json',
			'Content-Length: '.strlen($payload)
		));
		
		$response=curl_exec($ch);
		
	//	echo '<PRE>'; print_r($response); echo '</PRE>';
		
		$result=json_decode($response, true);
		
		if (!is_array($result)) {
			$info=curl_getinfo($ch);
			$result=array('error' => array(
				'code' => 'HTTP '.$info['http_code'],
				'message' => strip_tags($response).' '.$url
			));
		}
		
		return $result;
	}


	/**
	*función que convierte una cadena de texto plano 
	*a su equivalente en Hexadecimal.
	**/
	function str2Hex($string){
	    $hex='';
	    for ($i=0; $i < strlen($string); $i++){
	        $hex .= dechex(ord($string[$i]));
	    }
	    return $hex;
	}


	/**
	*función que convierte una cadena en hexadecimal 
	*a su equivalente en texto plano.
	**/
	function hex2Str($hex){
	    $string='';
	    for ($i=0; $i < strlen($hex)-1; $i+=2){
	        $string .= chr(hexdec($hex[$i].$hex[$i+1]));
	    }
	    return $string;
	}
}
?>