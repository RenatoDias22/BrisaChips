package com.renatodias.brisachips.Utils;

import com.renatodias.brisachips.Fragmants.Cidades.Model.City;
import com.renatodias.brisachips.Fragmants.Colaboradores.Model.Ponts;
import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;
import com.renatodias.brisachips.Fragmants.Regiao.Model.Regioes;
import com.renatodias.brisachips.Login.Model.AuthUser;

import java.util.List;

public class Constantes {

    public static final String BASE_URL = "http://chips.brisanet.net.br/api/";
    public static String url_id_cidade = "";
    public static String url_id_pontos_colaborador = "";
    public static String token;
    public static AuthUser.User user;
    public static List<ColaboradorSuper> colaboradorSuper;
    public static ColaboradorSuper colaborador;
    public static List<Regioes> regioes;
    public static List<City> citys;
    public static List<Ponts> ponts;

}
