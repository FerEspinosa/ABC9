package com.latorreencantada.abc9;

public class Global {
    public static Boolean musica = false;
    public static Boolean sonidos = true;
    public static String BackgroundImage = "rojo";
    public static String[] silabas = {"pa","ma","ca","sa","po","to","co","a","lo","bue","la","de","di","do","du","pi","za","ti","tis", "no"};;


    public static String [][][] defaultLevels = { //  [índice nivel] [indice palabra] [palabra, syl1, syl2, syl3 syl4, level]

            {//NIVEL 1
                {"DINOSAURIO", "DI", "NO", "SAU", "RIO", "1"},
                {"BALLENA", "BA", "LLE", "NA", "", "1"},
                {"CONEJO", "CO", "NE", "JO", "", "1"},
                {"ELEFANTE", "E", "LE", "FAN", "TE", "1"},
                {"JIRAFA", "JI", "RA", "FA", "", "1"},
                {"LEON", "LE", "ON", "", "", "1"},
                {"LUNA", "LU", "NA", "", "", "1"},
                {"TIGRE", "TI", "GRE", "", "", "1"},
                {"BIANCA", "BI", "AN", "CA", "", "1"},
                {"BICICLETA", "BI", "CI", "CLE", "TA", "1"},
                {"CAMELLO", "CA", "ME", "LLO", "", "1"},
                {"CARAMELO", "CA", "RA", "ME", "LO", "1"},
                {"CEBRA", "CE", "BRA", "", "", "1"}},

            { // NIVEL 2
                {"FRUTILLA", "FRU", "TI", "LLA", "l", "2"},
                {"FUEGO", "FUE", "GO", "", "", "2"},
                {"GATO", "GA", "TO", "", "", "2"},
                {"JUGO", "JU", "GO", "", "", "2"},
                {"LECHE", "LE", "CHE", "", "", "2"},
                {"MANZANA", "MAN", "ZA", "NA", "", "2"},
                {"NARANJA", "NA", "RAN", "JA", "", "2"},
                {"NEMO", "NE", "MO", "", "", "2"},
                {"PERA", "P", "E", "R", "A", "2"},
                {"PERRO", "PE", "RRO", "", "", "2"},
                {"PERROS", "PE", "RRO", "S", "", "2"},
                {"PEZ", "P", "E", "Z", "S", "2"},
                {"QUESO", "QUE", "SO", "", "", "2"}},

            { // NIVEL 3
                {"SANDIA", "SAN", "DIA", "", "", "3"},
                {"TREN", "T", "R", "E", "N", "3"},
                {"MONTAÑA", "MON", "TA", "ÑA", "", "3"},
                {"NUBE", "NU", "BE", "", "", "3"},
                {"RASCACIELO", "RAS", "CA", "CIE", "LO", "3"},
                {"SEMAFORO", "SE", "MA", "FO", "RO", "3"},
                {"CAMION", "CA", "MI", "ON", "", "3"},
                {"HELADO", "HE", "LA", "DO", "", "3"},
                {"CORAZON", "CO", "RA", "ZON", "", "3"},
                {"GOTA", "GO", "TA", "", "", "3"},
                {"AGUA", "A", "GU", "A", "", "3"},
                {"GENIO", "GE", "NI", "O", "", "3"},
                {"MAGIA", "MA", "G", "I", "A", "3"},
                {"GELATINA", "GE", "LA", "TI", "NA", "3"}},

            { // NIVEL 4
                {"MARIO", "MA", "RIO", "", "", "4"},
                {"GLOBO","GLO","BO", "", "", "4"},
                {"TIBURON", "TI", "BU", "RON", "", "4"},
                {"HORMIGA", "HOR", "MI", "GA", "", "4"},
                {"GODZILLA", "GOD", "ZI", "LLA", "", "4"}

            }
    };


}
