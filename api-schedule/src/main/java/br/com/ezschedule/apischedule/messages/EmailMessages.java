package br.com.ezschedule.apischedule.messages;

import br.com.ezschedule.apischedule.model.Client;

public class EmailMessages {

    public static String createTitle(Client cliente){
        return cliente.getName() + " você esqueceu sua senha do ezSchedule?";
    }

    public static String messageRecoveryPassword(Client cliente, String token){
        return "Olá " + cliente.getName()
        + "!\nPara verificar sua identidade, use o seguinte código: \n\n"
        + token //Colocar o link aqui direto
        + "\n\nNão Compartilhe este Token com ninguém. Nossa equipe de atendimento nunca pedirá sua senha."
        +"\nCaso não tenha exigido a troca de senha, desconsidere este email!";
    }
}
