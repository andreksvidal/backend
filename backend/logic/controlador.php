<?php
/**
* Clase abstracta que representa una vision general
* para el procesamiento de las solitudes asociadas a un proceso en particular.
*/


require_once 'validador.php';
require_once __DIR__ . "/../cli-multichain/clientemultichain.php";

abstract class Controlador 
{
	
	
	protected  $peticion;
	protected $validador;
	protected $cliente;


	function __construct($peticion)
	{
		$this->peticion=$peticion;
		$this->validador=new Validaciones();
		$this->cliente= new Cliente();
	}

	
	/*
	*Metodo abstracto, cada clase hija lo implementa para procesar una peticion
	*de una manera particular.
	*/
	abstract protected function procesarPeticion();


}
?>