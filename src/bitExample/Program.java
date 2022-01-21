package bitExample;

import java.util.Arrays;
import java.util.Comparator;

public class Program {
    public static void main(String[] args) {

        String cryptographicKey="vince";
        float cryptographicKeyLength=cryptographicKey.length();

        String textToEncode="la cavalleria deve attaccare sulla sinistra";
        textToEncode=cryptographicKey+textToEncode.replace(" ","");
        float textToEncodeLength=textToEncode.length();
        Double rowOperation=Math.ceil(textToEncodeLength/cryptographicKeyLength);
        int rows = (int) Math.round(rowOperation)+1;

        char[] charArrayText=textToEncode.toCharArray();

        char [][] multidimensionalArray= new char[rows][(int)cryptographicKeyLength];
        int contatore=0;

        for(int i=0; i<rows;i++){


            for(int j=0;j<(int)cryptographicKeyLength;j++){

                if(contatore<(int)textToEncodeLength){
                    multidimensionalArray[i][j]=charArrayText[contatore];
                    System.out.print(multidimensionalArray[i][j]);
                }
                contatore++;
            }
            System.out.println();
        }

        char[][] arrayIndex=new char[(int)cryptographicKeyLength][1];
        for(int i=0; i<(int)cryptographicKeyLength;i++){

            int index=i;
            char convertedIndex=(char)(index+'0');
            char[] arrayContent= {multidimensionalArray[0][i], convertedIndex};
            arrayIndex[i]= arrayContent;
        }

        //sorting
        Arrays.sort(arrayIndex, Comparator.comparing(o -> o[0]));

        StringBuffer encryptedWord=new StringBuffer();

        String alphabet="abcdefghijklmnopqrstuvwxyz";
        char [ ] alphabetArray=alphabet.toCharArray();

        for(int i=0; i<arrayIndex.length;i++){
            for (int j=1;j<rows;j++){
                int sortedIndex=Integer.parseInt((Character.toString(arrayIndex[i][1])));
                boolean check=false;
                for(int k=0;k< alphabet.length();k++){
                    if(new String (String.valueOf(multidimensionalArray[j][sortedIndex])).contains(String.valueOf(alphabetArray[k]))){
                        check=true;
                    }
                }
                if(check){
                    encryptedWord.append(multidimensionalArray[j][sortedIndex]);
                }
            }

        }
        String result=encryptedWord.toString().trim();
        System.out.println(result);



    }
}

