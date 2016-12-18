<?php
require_once "Huawei-E5180-API/vendor/autoload.php";

//The router class is the main entry point for interaction.
$router = new HSPDev\HuaweiApi\Router;

//If specified without http or https, assumes http://
$router->setAddress("192.168.8.1");

// Check if the user is logged in
if( false === $router->isLoggedIn() ) {
    $router->login("admin", "cathey");
}

$router->turnOffMobileData();