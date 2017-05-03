<?php
	
	require 'Token.php';
	
	if($_SERVER['REQUEST_METHOD']== 'GET'){

		if (isset($_GET['id'])) {
			$identificador= $_GET['id'];

			$respuesta = Token::obtenerporusuario($identificador);

			$contenedor = array();

			if ($respuesta) {
				$contenedor["Resultado"] ="CC";
				$contenedor["datos"] =$respuesta;
				echo json_encode($contenedor);
			}else{
				echo json_encode(array('resultado'=> 'El usuario no existe'));
			}
			
		}else{
			echo json_encode(array('resultado'=> 'Falta identificador'));
		}

	}

?>