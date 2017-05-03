<?php

	require 'Database.php';

	//Registro::obtenertodoslosusuario();

	class Token{
		function _construct(){
		}

		public static function obtenertodoslosusuario()
		{
			$consultar = "SELECT * FROM Token";

			try{
				$resultado = Database::getInstance()->getDb()->prepare($consultar);

				$resultado->execute();

				$tabla = $resultado->fetchAll(PDO::FETCH_ASSOC);

				//echo json_encode($tabla);	
				return $tabla;
			}catch(PDOExeption $e){
				echo "error";	
			}
			
		}

		public static function obtenerporusuario($id){
			$consultar ="SELECT * FROM Token WHERE id= ?";

			try {
				$resultado = Database::getInstance()->getDb()->prepare($consultar);

				$resultado->execute(array($id));

				$tabla = $resultado->fetch(PDO::FETCH_ASSOC);

				return $tabla;	
			} catch (PDOExeption $e) {
				return false;
			}
			
		}

		public static function insertarNuevoDato($id,$token){
			
			try{
				if(!self::obtenerporusuario($id)){
					$consulta ="INSERT INTO Token (id,token) VALUES (?,?)";
					$resultado = Database::getInstance()->getDb()->prepare($consulta);	
					return $resultado->execute(array($id,$token));	
				}else{
					return false;	
				}		
			}catch(PDOExeption $e){
				return false;
			}
			
		}

		public static function actualizarDato($id,$token)
		{
			if(self::obtenerporusuario($id)){
				$consultar = "UPDATE Token SET token=? WHERE id=?";
				$resultado = Database::getInstance()->getDb()->prepare($consultar);
				return $resultado->execute(array($token,$id));	
			}else{
				return false;
			}
			
		}

	}
?>