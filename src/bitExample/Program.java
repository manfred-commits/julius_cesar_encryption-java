package bitExample;

import java.util.Arrays;
import java.util.Comparator;
import java.util.*;

public class Program {
    public static void main(String[] args) {

        String cryptographicKey="hfiauhfso";
        float cryptographicKeyLength=cryptographicKey.length();

        String textToEncode="Buongiorno a tutti";
        textToEncode=cryptographicKey+textToEncode.replace(" ","");
        float textToEncodeLength=textToEncode.length();
        Double rowOperation=Math.ceil(textToEncodeLength/cryptographicKeyLength);
        int rows = (int) Math.round(rowOperation)+1;

        char[] keyToChar=cryptographicKey.toCharArray();
        char[] temp=textToEncode.toCharArray();
        Character[] charArrayText= new String(temp).chars()
                                    .mapToObj(c -> (char) c)
                                    .toArray(Character[]::new);

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

//        for(int i=0; i<rows;i++){
//            for(int j=0;j<(int)cryptographicKeyLength;j++){
//                System.out.println(multidimensionalArray[i][j]);
//            }
//        }


        Character[][] arrayIndex=new Character[(int)cryptographicKeyLength][1];
        for(int i=0; i<(int)cryptographicKeyLength;i++){

            int index=i;
            char convertedIndex=(char)(index+'0');
            Character[] arrayContent= {multidimensionalArray[0][i], convertedIndex};
            arrayIndex[i]= arrayContent;
        }

        //sorting
        Arrays.sort(arrayIndex, Comparator.comparing(o -> o[0]));

        StringBuffer encryptedWord=new StringBuffer();

        String alphabet="abcdefghijklmnopqrstuvwxyz";
        String alphabetUpperCase=alphabet.toUpperCase();

        char [ ] alphabetArray=alphabet.toCharArray();
        char [ ] alphabetArrayUpper=alphabetUpperCase.toCharArray();

        for(int i=0; i<arrayIndex.length;i++){
            for (int j=1;j<rows;j++){
                int sortedIndex=Integer.parseInt((Character.toString(arrayIndex[i][1])));
                boolean check=false;
                for(int k=0;k< alphabet.length();k++){
                    if(new String (String.valueOf(multidimensionalArray[j][sortedIndex])).contains(String.valueOf(alphabetArray[k]))){
                        check=true;
                    }else if(new String (String.valueOf(multidimensionalArray[j][sortedIndex])).contains(String.valueOf(alphabetArrayUpper[k]))){
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



        //parining and sorting
        Character[][] pair=new Character[(int)cryptographicKeyLength][(int)cryptographicKeyLength];
        for (int i=0;i<(int)cryptographicKeyLength;i++){
            pair[i]=new Character[]{cryptographicKey.charAt(i),(char)i};
        }

        Arrays.sort(pair, Comparator.comparing(o ->((Character) o[0])));

//        decryption
        StringBuffer decryptedWord= new StringBuffer();
        //cryptographicKeyLength

        int wordLength=result.length();
        float numberOfWhiteSpaces=cryptographicKeyLength - wordLength % cryptographicKeyLength,numberOfColumns=(int) Math.ceil((float)wordLength/cryptographicKeyLength);

        int ip=0;
        String[] subwords= new String[(int)cryptographicKeyLength];
        for (int i=0; i<wordLength;i++){

            if(Integer.valueOf(pair[ip][1])>=cryptographicKeyLength-numberOfWhiteSpaces){
                for(int j=0;j<numberOfColumns-1;j++){
                    decryptedWord.append(result.charAt(i+j));
                }
                i+=numberOfColumns-2;
            }else{
                for(int j=0;j<numberOfColumns;j++){
                    decryptedWord.append(result.charAt(i+j));
                }
                i+=numberOfColumns-1;
            }
            


            subwords[Integer.valueOf(pair[ip][1])]=decryptedWord.toString();

            decryptedWord.delete(0, decryptedWord.length());


            ip++;
        }
        decryptedWord.delete(0, decryptedWord.length());
        for (int i=0; i<numberOfColumns;i++){
            for(int j=0;j<cryptographicKeyLength;j++){
                if(i==numberOfColumns-1 && subwords[j].length()==numberOfColumns-1){
                    continue;

                }
                decryptedWord.append(subwords[j].charAt(i));
            }
        }

        String decryptedResult=decryptedWord.toString();
        System.out.println("decryption: "+decryptedResult);

    }
}

