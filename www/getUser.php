<?php
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
$id = $data['id'];

$first_name ="";
$last_name ="";
$email ="";
$id_habitat=0;


// Préparer et exécuter la requête SQL
$sql = "SELECT * FROM user WHERE id = ? ";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i",$id );
$stmt->execute();
$result = $stmt->get_result();

if ($row = $result->fetch_assoc()) {
    $id = (int)$row['id'];
    $first_name = $row['firstname'];
    $last_name = $row['lastname'];
    $email = $row['email'];
    $id_habitat = (int)$row['id_habitat'];

}

// Vérifier les résultats
if ($result->num_rows > 0) {
    // Connexion réussie
    $response = array("success" => true, "message" => "Login successful"  . strval($id),
        "idUser" => $id,
        "fName" => $first_name,
        "lName"=>$last_name,
        "email" => $email,
        "idHabitat" => $id_habitat
    );

} // Connexion échouée
else{ $response = array("success" => false, "message" => "Invalid credentials");

}

// Fermer la connexion
$stmt->close();
$conn->close();

// Retourner la réponse en JSON
header('Content-Type: application/json');
echo json_encode($response);
?>


