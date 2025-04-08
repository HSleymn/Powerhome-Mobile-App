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
$firstName = $data['FirstName'];
$lastName = $data['LastName'];
$email = $data['email'];
$password = $data['password']; // Hash the password

// Validation des données
if (empty($firstName) || empty($lastName) || empty($email) || empty($password)) {
    $response = array("success" => false, "message" => "All fields are required");
    echo json_encode($response);
    exit();
}

// Vérifier si l'utilisateur existe déjà
$sql = "SELECT * FROM user WHERE email = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

// Vérifier les résultats
if ($result->num_rows > 0) {
    // Utilisateur déjà existant
    $response = array("success" => false, "message" => "User already exists");
} else {
    // Créer un nouvel habitat pour l'utilisateur
    $sql_habitat = "INSERT INTO habitat (floor, area) VALUES (1, 50)"; // Exemple d'insertion
    if ($conn->query($sql_habitat) === TRUE) {
        // Récupérer l'ID du nouvel habitat
        $habitat_id = $conn->insert_id;

        // Préparer et exécuter la requête SQL pour l'insertion de l'utilisateur
        $sql = "INSERT INTO user (FirstName, LastName, Email, Password, id_habitat) VALUES (?, ?, ?, ?, ?)";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("ssssi", $firstName, $lastName, $email, $password, $habitat_id);

        if ($stmt->execute()) {
            $response = array("success" => true, "message" => "Registration successful");
        } else {
            $response = array("success" => false, "message" => "Registration failed");
        }
    } else {
        $response = array("success" => false, "message" => "Failed to create habitat");
    }
}

// Fermer la connexion
$stmt->close();
$conn->close();

// Retourner la réponse en JSON
header('Content-Type: application/json');
echo json_encode($response);
?>
