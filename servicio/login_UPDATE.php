<?php
	require 'Login.php';

	if($_SERVER['REQUEST_METHOD']=='POST'){
		$datos = json_decode(file_get_contents("php://input"),true);

		$respuesta = Registro::actualizarDato($datos["id"],$datos["password"]);
		if($respuesta){
			echo "Se actualizaron los datos ";
		}else
		{
			echo "El usuario no existe";
		}
	}

?>