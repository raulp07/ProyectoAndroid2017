<?php

  ignore_user_abort();
  ob_start();

  $url = 'https://fcm.googleapis.com/fcm/send';

$Token = 'eGLTl_eHT38:APA91bEDaZChgqmRmwQThwCzVpuffzv4P4AhTRqWUG4aYgAsnTcq-Ou6IOzWsSgW6-IiRdX1Tl_vKD7wetRAI6yOkH6VKZ7HwFbtpgI98nPvYZl_jL1pbclcpuFJL6imC1Y-2uZUsTa9';

  $fields = array('to' => $Token ,
   
   'data' => array('Objetos' => 'Mesa-silla-escritorio'));
//'notification' => array('body' => 'Bienvenido a nuestra aplicacion', 'title' => 'Raul Paucar'),
  define('GOOGLE_API_KEY', 'AIzaSyAtlsA_J1qdj_o-CFbQwWrWFKeNll9BWas');

  $headers = array(
          'Authorization:key='.GOOGLE_API_KEY,
          'Content-Type: application/json'
  );      

  $ch = curl_init();
  curl_setopt($ch, CURLOPT_URL, $url);
  curl_setopt($ch, CURLOPT_POST, true);
  curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
  curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
  curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));

  $result = curl_exec($ch);
  if($result === false)
    die('Curl failed ' . curl_error());
  curl_close($ch);
  return $result;
?>