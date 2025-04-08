<?php
session_start();
error_reporting(E_ERROR | E_PARSE);

// Configuration de la base de données
$db_host = "localhost";
$db_uid = "abstract-programmer";
$db_pass = "SouleymaneHM954<3";
$db_name = "exempledb";

// Connexion à la base de données
$conn = mysqli_connect($db_host, $db_uid, $db_pass, $db_name);

// Vérifier la connexion
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$data = json_decode(file_get_contents("php://input"), true);

// Récupérer les données de la requête POST
$date = $data['datetime'];
$appareil = $data['appareil'];
$habid = $data['idhabit'];
 // Hash the password

// Validation des données
if (empty($date) || empty($appareil) || empty($habid) ) {
    error_log("All fields are required");
    exit();
}



    // Préparer et exécuter la requête SQL pour l'insertion
$sql = "INSERT INTO appliance (name, reference, wattage, habitat_id) VALUES (?, ?, ?, ?)";
$stmt = $conn->prepare($sql);
if($appareil == "Aspirateur"){
    $app = "Aspirateur";
    $ref = "AS9210";
    $watt=700;


}
else if($appareil == "Climatiseur"){
    $app = "Climatiseur";
    $ref = "CL2580";
    $watt=1200;

}
else if($appareil == "Fer a repasser"){
    $app = "Fer a repasser";
    $ref = "FR9963";
    $watt=350;

}
else if($appareil == "Lave linge"){
    $app = "Lave Linge";
    $ref = "LL1175";
    $watt=900;

}


$stmt->bind_param("sssi", $app, $ref, $watt, $habid);

if ($stmt->execute()) {
    $response = array("success" => true, "message" => "Registration successful");
} else {
    $response = array("success" => false, "message" => "Registration failed");
}


// Fermer la connexion
$stmt->close();
$conn->close();

// Retourner la réponse en JSON
header('Content-Type: application/json');
echo json_encode($response);
?>
