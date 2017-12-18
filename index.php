<?php 
	

	#Comentar esta linea si se desea conocer el reporte de errores.
	#error_reporting(0);


	/*inclusión de los archivos de la logica principal de la aplicacion*/
	$archivos=array("cifrado","controlador","controladorgiro" ,"controladorrespuestas" , "controladorfacturas" , "controladorabonos", "validador");
    
    foreach ($archivos as &$nombre) {
    	require_once __DIR__ . "/logic/".$nombre.".php";	
    }
    
    /*******************************************************************/



	$cifrado=new Cifrado("md5"); //variable que define el metodo de cifrado de las peticiones y respuestas del API.
	$mensajero= new ControladorRespuestas(NULL);//variable para procesamiento y formato de las respuestas del API.
	$validador= new Validaciones();//variable que contiene herramientas para validaciones de datos.
	


	if ($_SERVER['REQUEST_METHOD'] == 'POST') {
		
		/*con cifrado:*/
		/*$stream=file_get_contents("php://input");
   		$peticion=json_decode($cifrado->descifrar($stream), true);*/

		/*o sin cifrado:*/

		$peticion = json_decode(file_get_contents("php://input"), true);

		/*fin sin cifrado*/

		$cabecera=array("proceso","peticion");

	


		if($validador->validarCampos($cabecera,$peticion))
		{

			$controlador=NULL;
	
			$proceso= $peticion['proceso'];
			

			switch ($proceso) {
			 	case 'giro':
					$controlador=new ControladorGiro($peticion['peticion']);						
			 		break;
			 	case 'pagofacturas':
			 		$controlador=new ControladorFacturas($peticion['peticion']);
			 		break;
			 	case 'pagoabonos':
			 		$controlador=new ControladorAbonos($peticion['peticion']);
			 		break;
			}


			$resultado=NULL;

			if($controlador!=NULL)
			{
				$resultado=$controlador->procesarPeticion();
			}

			$mensajero->setResultado($resultado);

		}
		
	}

	echo $mensajero->procesarResultado();


?>