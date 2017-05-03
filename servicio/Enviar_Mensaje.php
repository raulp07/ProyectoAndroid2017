<?php

	require 'Mensajes.php';
	setlocale(LC_TIME, 'es_PE.UTF-8');
	date_default_timezone_set('America/Lima');

	if($_SERVER['REQUEST_METHOD']=='POST'){
		$datos = json_decode(file_get_contents("php://input"),true);

		$emisor = $datos["emisor"];
		$receptor = $datos["receptor"];
		$mensaje = $datos["mensaje"];

		$NameTableEmisor =  "Mensajes_" . $emisor;
		$NameTableReceptor = "Mensajes_" . $receptor;

		$token_tabla = Mensaje::getTokenUser($receptor);

		if ($token_tabla) {

			$token = $token_tabla["token"];

			$respuestaCrearTableReceptor = Mensaje::CrearTable($NameTableReceptor);
			$respuestaCrearTableEmisor = Mensaje::CrearTable($NameTableEmisor);

			/*if ($respuestaCrearTable == 200) {
				echo 'La tabla del receptor se ha creado correctamente';
			}else
			{
				echo "La tabla del receptor ya existe";
			}
			if ($respuestaCrearTable == 200) {
				echo 'La tabla del emisor se ha creado correctamente';
			}else
			{
				echo "La tabla del emisor ya existe";
			}*/

			

			$fechaActual = getdate();
			$segundos= $fechaActual['seconds'];
			$minutos= $fechaActual['minutes'];
			$hora= $fechaActual['hours'];
			$dia= $fechaActual['mday'];
			$mes= $fechaActual['mon'];
			$year= $fechaActual['year'];

			$milisecond = DateTime::createFromFormat('U.u',microtime(true));

			//$id_user_emisor= $emisor . $hora . $minutos . $segundos . $milisecond->format("u");
			$id_user_emisor = $emisor . "_" . $hora . $minutos . $segundos . $milisecond->format("u");
			$id_user_receptor = $receptor . "_" . $hora . $minutos . $segundos . $milisecond->format("u");

			$hora_del_mensaje = strftime("%H:%M , %A, %d de %B de %Y");

			$MEE = false;
			$MER = false;

			$respuestaenviarmensajeEmisor = Mensaje::EnviarMensaje($NameTableEmisor,$id_user_emisor,$mensaje,1,$hora_del_mensaje);
			if($respuestaenviarmensajeEmisor == 200)$MEE = true;
			else echo "No se pudo enviar el mensaje";

			$respuestaenviarmensajeReceptor = Mensaje::EnviarMensaje($NameTableReceptor,$id_user_receptor ,$mensaje,2,$hora_del_mensaje);
			if($respuestaenviarmensajeReceptor == 200) $MER = true;
			else echo "No se pudo enviar el mensaje";


			if ($MEE & $MER) {
				echo json_encode(array('resultado'=>'Mensaje enviado'));
				Mensaje::EnviarNotificacion($mensaje,$hora_del_mensaje,$token,$emisor);				
			}
		}else{
			echo json_encode(array('resultado'=>'el usuario receptor no existe'));
		}

		
		
		
	}
	
?>