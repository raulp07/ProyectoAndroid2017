<?php

	require 'Database.php';

	//Registro::obtenertodoslosusuario();

	class Registro{
		function _construct(){
		}

		public static function obtenertodoslosusuario()
		{
			$consultar = "SELECT * FROM login";

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
			$consultar ="SELECT * FROM login WHERE id= ?";

			try {
				$resultado = Database::getInstance()->getDb()->prepare($consultar);

				$resultado->execute(array($id));

				$tabla = $resultado->fetch(PDO::FETCH_ASSOC);

				return $tabla;	
			} catch (PDOExeption $e) {
				return false;
			}
			
		}

		public static function insertarNuevoDato($id,$password){
			$consulta ="INSERT INTO login (id,password) VALUES (?,?)";
			try{
				$resultado = Database::getInstance()->getDb()->prepare($consulta);
				return $resultado->execute(array($id,$password));	
			}catch(PDOExeption $e){
				return false;
			}
			
		}

		public static function actualizarDato($id,$password)
		{
			if(self::obtenerporusuario($id)){
				$consultar = "UPDATE login SET password = ? WHERE id = ?";
				$resultado = Database::getInstance()->getDb()->prepare($consultar);
				return $resultado->execute(array($password,$id));	
			}else{
				return false;
			}
			
		}

	}
?>