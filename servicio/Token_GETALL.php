<?php
	
	require 'Token.php';
	
	if($_SERVER['REQUEST_METHOD']== 'GET'){
		try{
			
			$repuesta = Token::obtenertodoslosusuario();
			echo json_encode($repuesta);
		}catch(PDOException $e){
			echo "Ocurrio un error";
		}
	}
?>