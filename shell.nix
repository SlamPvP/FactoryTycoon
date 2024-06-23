{ pkgs ? import <nixpkgs> {} }:
  pkgs.mkShell {
    nativeBuildInputs = with pkgs.buildPackages; [ 
      zulu
      maven
      postgresql_16
    ];

    VIRTUAL_ENV = "Java";
    JAVA_HOME = "/nix/store/svhhz4950xbk0ds4g2h2r4jphv4zkhxl-zulu-ca-jdk-21.0.2/";

    HOST = "localhost";
    POST = "5432";
    NAME = "factory";
    USER = "postgres";
    PASSWORD = "password";
}
