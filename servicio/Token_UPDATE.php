<?php
	require 'Token.php';

	if($_SERVER['REQUEST_METHOD']=='POST'){
		$datos = json_decode(file_get_contents("php://input"),true);
		$respuesta = Token::actualizarDato($datos["id"],$datos["token"]);
		if($respuesta){
			echo "Se actualizaron los datos ";
		}else{
			echo "No se actualizo el campo";
		}
	}

?>