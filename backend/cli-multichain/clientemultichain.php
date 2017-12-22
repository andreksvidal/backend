<?php  

/**
* Clase que separa funciones de alto nivel o del negocio
* con operaciones basicas del stub cliente de multichain.
*/

require_once 'stub.php';


class Cliente 
{

	private $stub;
	private $streams;

	function __construct()
	{
		$this->stub= new Stub();
		$this->conectarBlockchain();
		$this->streams=$this->obtenerEstreams();
	}


	/**
	*funcion que permite conectarse con la blockchain 
	*espcificada en el archivo de configuración : 'config.txt'
	* y la fija para como parametro para trabajar.
	**/
	private function conectarBlockchain()
	{
		$config=$this->stub->read_config();
		$cadenadatos= $config['default'];
		$this->stub->set_multichain_chain($cadenadatos);
	}



	/**
	* funcion que obtiene los nombres de los diferentes streams
	* creados actualmente para el negocio, a partir
	* del archivo de configuración : 'config-streams.txt'.
	**/
	private function obtenerEstreams()
	{
		return json_decode(file_get_contents(__DIR__ .'/config-streams.txt'),true);		
	}


	/**
	*funcion que crea un stream con el nombre especificado
	* en el @param $nombrestream.
	* Por ahora, SIEMPRE retorna true dado que si no existe el stream, lo crea.
	* NOTA: Si se incluyen validaciones acerca de la conectividad con la blockchain, el retorno debería cambiar. 
	**/
	private function crearStream($nombrestream)
	{
		
		if(!in_array($nombrestream,$this->getStreamsExistentes()))
		{
			$this->stub->multichain("create" , "stream" ,$nombrestream,true);
		}

		return TRUE;
	}


	/**
	*funcion encargada de subscribir el nodo actual 
	*al stream especificado en el @param $nombrestream.
	**/
	private function subscribirStream($nombrestream)
	{
		$this->stub->multichain("subscribe",$nombrestream);
	}




	/**
	*funcion encargada de obtener los nombres
	*de los diferentes streams existentes en la blockchain.
	**/
	private function getStreamsExistentes()
	{
		$retorno=array();
		
		$arraystreams=$this->stub->multichain('liststreams')['result'];

		foreach ($arraystreams as &$stream) {
			array_push($retorno,$stream['name']);
		}

		return $retorno;

	}


	/**
	*funcion que contiene la logica necesaria para 
	*preparar correctamente al API para publicar
	*en el estream especificado en el @param $nombrestream.
	**/
	private function prepararEscritura($nombrestream)
	{
		$existentes=$this->getStreamsExistentes();

		if(!in_array($nombrestream, $existentes))
		{
			$this->crearStream($nombrestream);
		}

		$this->subscribirStream($nombrestream);

	}


	/**
	*funcion con la lógica necesaria para consultar 
	*los datos  de  una transaccion asociadas a un txid especifico. 
	**/
	public function getDatosTransaccion($txid)
	{
		$retorno=array('error' => 'No se pudo consultar. Compruebe sintaxis');
		
		$resultado=$this->stub->multichain('getrawtransaction',$txid,1);


		if($resultado['error']==NULL)
		{
			$datoshexa=$resultado['result']['data'][0];
			$arraydatos=json_decode($this->stub->hex2Str($datoshexa),true);


			$retorno = array('result' => $arraydatos);
		}
		else
		{
			if($resultado['error']['code']==-8)
			{
				$retorno= array('error' => 'txid no existe');
			}

		}

		
		return $retorno;
	}


	/**
	*funcion con la lógica necesaria para consultar 
	*un conjunto de transacciones asociadas a consingnaciones 
	*en el proceso de : Realizar un giro
	*y relacionadas con los parametros:
	*@param idsucursal contiene el id de la sucursal sobre la cual filtrar.
	*@param esremitente booleano que indica si se filtra sobre la sucursal remitente o receptora.
	*@param fecha contiene la fecha sobre la cual se quiere filtrar, si es 'null' ó 'NULL' no se filtra por fecha.
	**/

	public function getConsignacionesGiroxSucursal($idsucursal,$esremitente,$fecha)
	{

		$retorno=array('error' => 'no existe el stream o no se pudo consultar. Compruebe sintaxis');
		

		$this->subscribirStream($this->streams['consignaciongiro']);

		if($esremitente=='true' or $esremitente=='false')
		{
			$resultado=$this->stub->multichain('liststreamitems',$this->streams['consignaciongiro']);
			$consulta=$resultado['result'];
			
			$items=array();
			
			if($consulta==NULL)
			{
				$retorno=array('result' => 'vacio');		
			}
			else
			{
				if($fecha!='null' and $fecha!='NULL')
				{
					$key=$fecha.'-'.$this->streams['consignaciongiro'];
					foreach ($consulta as &$registro) {
						if($registro['key']==$key)
						{
							array_push($items,$registro);
						}
					}
			
				}
				else
				{
					$items=$consulta;
				}	
			}
			

			$seleccionados=array();

			foreach ($items as &$item) {
				$datostransaccion= json_decode($this->stub->hex2Str($item["data"]),true);
				
				$txid=array('txid' =>$item['txid']);

				if($esremitente=='true')
				{
					if($datostransaccion['idremitente']==$idsucursal)
					{
						
						$arraydatos= array('datos' => $datostransaccion );
						array_push($seleccionados,array_merge($txid,$arraydatos));
							
					}	
				}
				else
				{
					if($datostransaccion['idreceptora']==$idsucursal)
					{

						$arraydatos= array('datos' => $datostransaccion );
						array_push($seleccionados,array_merge($txid,$arraydatos));
					}	
				}

			}

			if(count($seleccionados)>0)
			{
				$retorno=$seleccionados;
			}
			else
			{
				$retorno=array('result' => 'vacio');
			}	
		}
		



		return $retorno;
	}



	/**
	*funcion con la lógica necesaria para consultar 
	*un conjunto de transacciones asociadas a pagos de facturas
	*en el proceso de : PAGO DE FACTURAS
	*y relacionadas con los parametros:
	*@param numerorefer contiene el numero de referencia que identifica la factura sobre la cual  filtrar.
	**/

	public function getPagoFactura($numerorefer)
	{
		$retorno=array('error' => 'no existe el stream o no se pudo consultar. Compruebe sintaxis');

		$this->subscribirStream($this->streams['pagofactura']);

		$resultado=$this->stub->multichain('liststreamitems',$this->streams['pagofactura']);
			
		$consulta=$resultado['result'];	
			
		$items=array();
			
		if($consulta==NULL)
		{
			$retorno=array('result' => 'vacio');		
		}
		else
		{
			
			$seleccionados=array();

			foreach ($consulta as &$item) {
				$datostransaccion= json_decode($this->stub->hex2Str($item["data"]),true);
				$txid=array('txid' =>$item['txid']);

				if($datostransaccion['numerorefer']==$numerorefer)
				{
					$arraydatos= array('datos' => $datostransaccion );
					array_push($seleccionados,array_merge($txid,$arraydatos));
				}
			}
			if(count($seleccionados)>0)
			{
				$retorno=$seleccionados;
			}
			else
			{
				$retorno=array('result' => 'vacio');
			}	

		}

		return $retorno;


	}




	/**
	*funcion con la lógica necesaria para consultar 
	*un conjunto de transacciones asociadas a pagos de abonos a una cuenta
	*en el proceso de : PAGO DE ABONO
	*y relacionadas con los parametros:
	*@param numerorefer contiene el numero de referencia que identifica la factura sobre la cual  filtrar.
	**/

	public function getPagosAbonosxCuenta($numerocuenta)
	{
		$retorno=array('error' => 'no existe el stream o no se pudo consultar. Compruebe sintaxis');


		$this->subscribirStream($this->streams['pagoabono']);

		$resultado=$this->stub->multichain('liststreamitems',$this->streams['pagoabono']);
			
		$consulta=$resultado['result'];	
			
		$items=array();
			
		if($consulta==NULL)
		{
			$retorno=array('result' => 'vacio');		
		}
		else
		{
			
			$seleccionados=array();

			foreach ($consulta as &$item) {
				$datostransaccion= json_decode($this->stub->hex2Str($item["data"]),true);
				$txid=array('txid' =>$item['txid']);

				if($datostransaccion['numerocuenta']==$numerocuenta)
				{
					$arraydatos= array('datos' => $datostransaccion );
					array_push($seleccionados,array_merge($txid,$arraydatos));
				}
			}
			if(count($seleccionados)>0)
			{
				$retorno=$seleccionados;
			}
			else
			{
				$retorno=array('result' => 'vacio');
			}	

		}

		return $retorno;


	}




	/**
	*funcion con la lógica necesaria para consultar 
	*un conjunto de transacciones asociadas a pagos de facturas 
	*en el proceso de : PAGO DE FACTURAS
	*y relacionadas con los parametros:
	*@param idsucursal contiene el id de la sucursal sobre la cual filtrar.
	*@param esremitente booleano que indica si se filtra sobre la sucursal remitente o receptora.
	*@param fecha contiene la fecha sobre la cual se quiere filtrar, si es 'null' ó 'NULL' no se filtra por fecha.
	**/

	public function getPagosFacturas($idsucursal,$esremitente,$fecha)
	{

		$retorno=array('error' => 'no existe el stream o no se pudo consultar. Compruebe sintaxis');
		
		$this->subscribirStream($this->streams['pagofactura']);

		
		if($esremitente=='true' or $esremitente=='false')
		{
			$resultado=$this->stub->multichain('liststreamitems',$this->streams['pagofactura']);
			$consulta=$resultado['result'];	
			
			$items=array();
			
			if($consulta==NULL)
			{
				$retorno=array('result' => 'vacio');		
			}
			else
			{
				if($fecha!='null' and $fecha!='NULL')
				{
					$key=$fecha.'-'.$this->streams['pagofactura'];
					foreach ($consulta as &$registro) {
						if($registro['key']==$key)
						{
							array_push($items,$registro);
						}
					}
			
				}
				else
				{
					$items=$consulta;
				}	
			}
			

			$seleccionados=array();

			foreach ($items as &$item) {
				$datostransaccion= json_decode($this->stub->hex2Str($item["data"]),true);
				
				$txid=array('txid' =>$item['txid']);

				if($esremitente=='true')
				{
					if($datostransaccion['idremitente']==$idsucursal)
					{
						
						$arraydatos= array('datos' => $datostransaccion );
						array_push($seleccionados,array_merge($txid,$arraydatos));
							
					}	
				}
				else
				{
					if($datostransaccion['idreceptora']==$idsucursal)
					{

						$arraydatos= array('datos' => $datostransaccion );
						array_push($seleccionados,array_merge($txid,$arraydatos));
					}	
				}

			}

			if(count($seleccionados)>0)
			{
				$retorno=$seleccionados;
			}
			else
			{
				$retorno=array('result' => 'vacio');
			}	
		}

		

		return $retorno;
	}



	/**
	*funcion con la lógica necesaria para consultar 
	*un conjunto de transacciones asociadas a pagos de abonos 
	*en el proceso de : PAGO DE ABONOS
	*y relacionadas con los parametros:
	*@param idsucursal contiene el id de la sucursal sobre la cual filtrar.
	*@param esremitente booleano que indica si se filtra sobre la sucursal remitente o receptora.
	*@param fecha contiene la fecha sobre la cual se quiere filtrar, si es 'null' ó 'NULL' no se filtra por fecha.
	**/

	public function getPagosAbonos($idsucursal,$esremitente,$fecha)
	{

		$retorno=array('error' => 'no existe el stream o no se pudo consultar. Compruebe sintaxis');
		

		$this->subscribirStream($this->streams['pagoabono']);
		
		if($esremitente=='true' or $esremitente=='false')
		{
			$resultado=$this->stub->multichain('liststreamitems',$this->streams['pagoabono']);
			$consulta=$resultado['result'];	
			
			$items=array();
			
			if($consulta==NULL)
			{
				$retorno=array('result' => 'vacio');		
			}
			else
			{
				if($fecha!='null' and $fecha!='NULL')
				{
					$key=$fecha.'-'.$this->streams['pagoabono'];
					foreach ($consulta as &$registro) {
						if($registro['key']==$key)
						{
							array_push($items,$registro);
						}
					}
			
				}
				else
				{
					$items=$consulta;
				}	
			}
			

			$seleccionados=array();

			foreach ($items as &$item) {
				$datostransaccion= json_decode($this->stub->hex2Str($item["data"]),true);
				
				$txid=array('txid' =>$item['txid']);

				if($esremitente=='true')
				{
					if($datostransaccion['idremitente']==$idsucursal)
					{
						
						$arraydatos= array('datos' => $datostransaccion );
						array_push($seleccionados,array_merge($txid,$arraydatos));
							
					}	
				}
				else
				{
					if($datostransaccion['idreceptora']==$idsucursal)
					{

						$arraydatos= array('datos' => $datostransaccion );
						array_push($seleccionados,array_merge($txid,$arraydatos));
					}	
				}

			}

			if(count($seleccionados)>0)
			{
				$retorno=$seleccionados;
			}
			else
			{
				$retorno=array('result' => 'vacio');
			}	
		}	

		return $retorno;
	}





	/**
	*funcion con la lógica necesaria para consultar 
	*un conjunto de transacciones asociadas a los pagos  
	*en el proceso de : Realizar un giro
	*y relacionadas con los parametros:
	*@param idsucursal contiene el id de la sucursal sobre la cual filtrar,si es 'null' ó 'NULL' no se filtra por fecha.
	*@param fecha contiene la fecha sobre la cual se quiere filtrar, si es 'null' ó 'NULL' no se filtra por fecha.
	*Si AMBOS parametros son NULL, se traen todos los pagos de giros registrados.
	**/

	public function getPagosGiro($idsucursal,$fecha)
	{
		$retorno=array('result' => 'No se pudo consultar. Compruebe sintaxis');
		


		$this->subscribirStream($this->streams['pagogiro']);



		$resultado=array();
		if($fecha!='null' and $fecha!='NULL')
		{
			$key=$fecha.'-'.$this->streams['pagogiro'];
			$resultado=$this->stub->multichain('liststreamkeyitems',$this->streams['pagogiro'],$key);	
		}
		else
		{
			$resultado=$this->stub->multichain('liststreamitems',$this->streams['pagogiro']);
		}


		
		if($resultado['result']!=NULL)
		{
			$retorno= array();

			$items=$resultado['result'];

			foreach ($items as &$item) {
				
				$datospago=array('datospago' => json_decode($this->stub->hex2Str($item['data']),true));
				
				
				$infoconsignacion=$this->getDatosTransaccion($datospago['datospago']['txid']);

				$datosconsignacion=array();

				if(array_key_exists('result', $infoconsignacion))
				{
					$datosconsignacion=array('datosconsignacion' => $infoconsignacion['result']);
				}
				else
				{
					$datosconsignacion=array('datosconsignacion' => $infoconsignacion);
				}

				
				if($idsucursal!='null' and  $idsucursal!='NULL')
				{
	
					if(array_key_exists('idreceptora', $datosconsignacion['datosconsignacion']))
					{
						if($datosconsignacion['datosconsignacion']['idreceptora']==$idsucursal)
						{
							array_push($retorno, array_merge($datospago,$datosconsignacion));		
						}	
					}
					
				}
				else
				{
					array_push($retorno, array_merge($datospago,$datosconsignacion));	
				}

				
			}

			if(count($retorno)==0)
			{
				$retorno= array('result' => 'vacio');		
			}	


		}
		else
		{
			$retorno= array('error' => 'Error de configuracion.');
		}


		return $retorno;

	}



	/**
	*funcion que contiene la lógica para registrar una consignación de dinero
	* para el proceso de : Realizar un giro.
	**/
	public function regConsignacionGiro($datos)
	{
		
		#retorno de la función, con un valor por defecto.
		$retorno= array('error' => 'no se pudo realizar la transaccion en la cadena.');

		#prepara todo para poder escribir  o consultar sobre el stream leido desde la configuración.
		$this->prepararEscritura($this->streams['consignaciongiro']);

		#obtiene y registra la fecha de la transaccion.
		$date=getdate();
		$datos['fecha']=$date['year'].'-'.$date['mon'].'-'.$date['mday'].'-'.$date['hours'].':'.$date['minutes'].':'.$date['seconds'];

		#convesión metadatos a hexadecimal. OBLIGATORIO para multichain.
		$datosHexa=$this->stub->str2Hex(json_encode($datos));
		

		#conformacion de la clave
		$key = $date['year'].$date['mon'].$date['mday'].'-'.$this->streams['consignaciongiro'];
		#fin conformación clave.


		#escritura de la transacción.
		$resultado=$this->stub-> multichain('publish',$this->streams['consignaciongiro'],$key,$datosHexa);

		if(array_key_exists('result',$resultado))
		{
			$retorno=array('ok' => 'transaccion exitosa');
		}

		return $retorno;
	}



	/**
	*funcion que contiene la lógica para registrar un pago 
	* para el proceso de : Realizar un giro.
	**/

	public function regPagoGiro($datos)
	{
		#retorno de la función, con un valor por defecto.
		$retorno= array('error' => 'no se pudo realizar la transaccion en la cadena.');

		#prepara todo para poder escribir sobre el stream leido desde la configuración.
		$this->prepararEscritura($this->streams['pagogiro']);
		
		$date=getdate();
		
		#metadatos de la transacción.
		$fecha=$date['year'].'-'.$date['mon'].'-'.$date['mday'].'-'.$date['hours'].':'.$date['minutes'].':'.$date['seconds'];
		
		$infoconsignacion=$this->getDatosTransaccion($datos['txid']);

		if(array_key_exists('result', $infoconsignacion))
		{
			$metadatos= array('txid' => $datos['txid'] , 'fecha' => $fecha);

			#convesión metadatos a hexadecimal. OBLIGATORIO para multichain.
			$datosHexa=$this->stub->str2Hex(json_encode($metadatos));	


			#conformacion de la clave
			$key = $date['year'].$date['mon'].$date['mday'].'-'.$this->streams['pagogiro'];
			#fin conformación clave.

			#escritura de la transacción.
			$resultado=$this->stub-> multichain('publish',$this->streams['pagogiro'],$key,$datosHexa);

			if(array_key_exists('result',$resultado))
			{
				$retorno=array('ok' => 'transaccion exitosa');
			}
	
		}
		else
		{
			$retorno= array('error' => 'No existe el txid de la consignacion.');			
		}

		
		return $retorno;

	}




	/**
	*funcion que contiene la lógica para registrar un pago
	* para el proceso de : PAGO DE FACTURAS.
	**/

	public function regPagoFactura($datos)
	{
		
		#retorno de la función, con un valor por defecto.
		$retorno= array('error' => 'no se pudo realizar la transaccion en la cadena.');

		#prepara todo para poder escribir sobre el stream leido desde la configuración.
		$this->prepararEscritura($this->streams['pagofactura']);



		#obtiene y registra la fecha de la transaccion.
		$date=getdate();
		$datos['fecha']=$date['year'].'-'.$date['mon'].'-'.$date['mday'].'-'.$date['hours'].':'.$date['minutes'].':'.$date['seconds'];

		#convesión metadatos a hexadecimal. OBLIGATORIO para multichain.
		$datosHexa=$this->stub->str2Hex(json_encode($datos));
		

		#conformacion de la clave
		$key = $date['year'].$date['mon'].$date['mday'].'-'.$this->streams['pagofactura'];
		#fin conformación clave.


		#escritura de la transacción.
		$resultado=$this->stub-> multichain('publish',$this->streams['pagofactura'],$key,$datosHexa);

		if(array_key_exists('result',$resultado))
		{
			$retorno=array('ok' => 'transaccion exitosa');
		}

		return $retorno;
	}


	
	/**
	*funcion que contiene la lógica para registrar un pago
	* para el proceso de : PAGO DE ABONOS.
	**/

	public function regPagoAbono($datos)
	{
		
		#retorno de la función, con un valor por defecto.
		$retorno= array('error' => 'no se pudo realizar la transaccion en la cadena.');

		#prepara todo para poder escribir sobre el stream leido desde la configuración.
		$this->prepararEscritura($this->streams['pagoabono']);



		#obtiene y registra la fecha de la transaccion.
		$date=getdate();
		$datos['fecha']=$date['year'].'-'.$date['mon'].'-'.$date['mday'].'-'.$date['hours'].':'.$date['minutes'].':'.$date['seconds'];
		

		#convesión metadatos a hexadecimal. OBLIGATORIO para multichain.
		$datosHexa=$this->stub->str2Hex(json_encode($datos));
		

		#conformacion de la clave
		$key = $date['year'].$date['mon'].$date['mday'].'-'.$this->streams['pagoabono'];
		#fin conformación clave.


		#escritura de la transacción.
		$resultado=$this->stub-> multichain('publish',$this->streams['pagoabono'],$key,$datosHexa);

		if(array_key_exists('result',$resultado))
		{
			$retorno=array('ok' => 'transaccion exitosa');
		}

		return $retorno;
	}




}
?>