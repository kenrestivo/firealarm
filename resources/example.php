/* 
   Example post recipient.
 */
<?php

if($_POST['token'] != "sometoken"){
    header('HTTP/1.0 404 Not Found');
    exit(1);
}
  
if (mail("your@email.com", 
         $_POST['subject'], 
         $_POST['body'])) {
    echo("<p>Message successfully sent!</p>");
} else {
    echo("<p>Message delivery failed...</p>");
    print_r($_POST);
}

?>
