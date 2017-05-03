<?php
	
	require 'Login.php';
	
	if($_SERVER['REQUEST_METHOD']== 'GET'){
		try{
			
			$repuesta = Registro::obtenertodoslosusuario();
			echo json_encode($repuesta);
		}catch(PDOException $e){
			echo "Ocurrio un error";
		}
	}
?>